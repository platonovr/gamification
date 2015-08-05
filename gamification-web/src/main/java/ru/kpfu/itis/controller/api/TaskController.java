package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.dto.*;
import ru.kpfu.itis.dto.enums.Error;
import ru.kpfu.itis.model.*;
import ru.kpfu.itis.model.enums.StudyTaskType;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.*;
import ru.kpfu.itis.util.Constant;
import ru.kpfu.itis.validator.TaskValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * Created by timur on 17.06.15.
 */
@Api(value = "challenge", description = "operation with challenges")
@RequestMapping(Constant.API_URI_PREFIX + "/challenge")
@RestController("apiTaskController")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AccountTaskService accountTaskService;

    @Autowired
    private AccountBadgeService accountBadgeService;

    @Autowired
    private TaskValidator taskValidator;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private SecurityService securityService;

    @InitBinder("taskDto")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(taskValidator);
    }

    @ApiOperation("get task's information")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/{taskId:[1-9]+[0-9]*}", method = RequestMethod.GET)
    public TaskInfoDto getTaskById(@PathVariable Long taskId) {
        return taskService.findById(taskId);
    }

    @ApiOperation("get student's tasks")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(method = RequestMethod.GET)
    public ItemsDto<TaskDto> getAvailableTasks(@RequestParam(required = false) Integer offset,
                                               @RequestParam(required = false) Integer limit,
                                               @RequestParam(required = false) TaskStatus.TaskStatusType status) {
        return new ItemsDto<>(taskService.getTasksByUser(securityService.getCurrentUserId(), offset, limit, status));
    }

    @ApiOperation("get created tasks[FOR ADMIN OR TEACHER]")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public List<TaskInfoDto> getCreatedTasks(@RequestParam(required = false) Integer offset,
                                             @RequestParam(required = false) Integer limit) {
        return taskService.getCreatedTasks(securityService.getCurrentUserId(), offset, limit);
    }

    @ApiOperation(value = "create challenge")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) throw new BindException(bindingResult);
        taskDto.setId(taskService.save(taskDto).getId());
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @ApiOperation("upload challenge's attachment")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/{taskId:[1-9]+[0-9]*}/attachments", method = RequestMethod.POST)
    public ResponseEntity uploadAttachment(@RequestPart MultipartFile file,
                                           @PathVariable Long taskId) {
        if (file.isEmpty())
            return new ResponseEntity<>(new ErrorDto(Error.EMPTY_FILE), NO_CONTENT);
        else
            try {
                String attachmentDir = fileService.uploadTaskAttachment(file, taskId);
                return new ResponseEntity<>("/files/" + attachmentDir, CREATED);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity(INTERNAL_SERVER_ERROR);
            }
    }

    @ApiOperation("check challenge")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/{taskId:[1-9]+[0-9]*}/user/{accountId:[1-9]+[0-9]*}", method = RequestMethod.POST)
    public ResponseEntity checkTask(@PathVariable Long taskId,
                                    @PathVariable Long accountId,
                                    @RequestParam Integer mark) {
        AccountTask accountTask = accountTaskService.findByTaskAndAccount(taskId, accountId);
        if (accountTask != null) {
            taskService.setNewStatus(accountTask);
            accountTask.setMark(mark);
            //Change progress of linked badges
            Task task = taskService.findTaskById(taskId);
            Badge badge = task.getBadge();
            Account account = accountTask.getAccount();
            AccountBadge accountBadge = accountBadgeService.findByBadgeAndAccount(badge, account);
            if (accountBadge == null) {
                accountBadge = new AccountBadge();
                accountBadge.setAccount(account);
                accountBadge.setBadge(badge);
            }
            if (task.getStudyType().equals(StudyTaskType.PRACTICE)) {
                accountBadge.setPractice(accountBadge.getPractice() + mark);
            } else {
                accountBadge.setTheory(accountBadge.getTheory() + mark);
            }
            accountBadge.computeProgress();
            accountBadgeService.saveOrUpdate(accountBadge);
            AccountInfo accountInfo = account.getAccountInfo();
            Rating rating = ratingService.getUserRating(accountInfo.getId());
            if (rating != null) {
                rating.setPoint(rating.getPoint() + mark);
                ratingService.update(rating);
            } else {
                ratingService.createUserRating(accountInfo, Double.valueOf(mark));
            }
            ratingService.recalculateRating(accountInfo);
            return new ResponseEntity<>(OK);
        } else {
            return new ResponseEntity<>(new ErrorDto(Error.TASK_NOT_FOUND), NOT_FOUND);
        }

    }

    @ApiOperation("get challenge's attachments")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/{taskId:[1-9]+[0-9]*}/attachments", method = RequestMethod.GET)
    public List<String> getTaskAttachmentsNames(@PathVariable Long taskId) {
        return fileService.getTaskAttachmentsNames(taskId);
    }

    @ApiOperation("get available task categories")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<TaskCategoryDto> getTaskCategories() {
        return taskService.getAllCategories();
    }

    @ApiOperation("user takes task")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/{taskId:[1-9]+[0-9]*}/enroll", method = RequestMethod.POST)
    public ResponseEntity enroll(@PathVariable Long taskId) {
        Account account = securityService.getCurrentUser();
        return taskService.enroll(account, taskId);
    }
}
