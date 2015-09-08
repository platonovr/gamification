package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.dto.*;
import ru.kpfu.itis.dto.enums.Error;
import ru.kpfu.itis.model.Account;
import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.TaskStatus;
import ru.kpfu.itis.security.SecurityService;
import ru.kpfu.itis.service.ActivityService;
import ru.kpfu.itis.service.FileService;
import ru.kpfu.itis.service.TaskService;
import ru.kpfu.itis.util.Constant;
import ru.kpfu.itis.validator.TaskValidator;

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
    private TaskValidator taskValidator;


    @Autowired
    private SecurityService securityService;

    @Autowired
    private ActivityService activityService;

//    @InitBinder("taskDto")
//    private void initBinder(WebDataBinder binder) {
//        binder.setValidator(taskValidator);
//    }

    @ApiOperation("get task's information")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(value = "/{taskId:[1-9]+[0-9]*}", method = RequestMethod.GET)
    public ResponseEntity<? super TaskInfoDto> getTaskById(@PathVariable Long taskId) {
        ErrorDto taskAvailabilityError = taskService.isTaskAvailableForUser(taskId);
        if (taskAvailabilityError != null) {
            return new ResponseEntity<>(taskAvailabilityError, FORBIDDEN);
        }
        return new ResponseEntity<>(taskService.findById(taskId), OK);
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
                                             @RequestParam(required = false) Integer limit,
                                             @RequestParam(required = false) String query
    ) {
        return taskService.getCreatedTasks(offset, limit, query);
    }

    @ApiOperation(value = "create challenge")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")})
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskEditorDto newTask) {
        Task task = taskService.save(securityService.getCurrentUser(), newTask);
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
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
        return taskService.checkTask(taskId, accountId, mark);

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
        ErrorDto taskAvailabilityError = taskService.isTaskAvailableForUser(taskId);
        if (taskAvailabilityError != null) {
            return new ResponseEntity<>(taskAvailabilityError, FORBIDDEN);
        }
        Account account = securityService.getCurrentUser();
        return taskService.enroll(account, taskId);
    }
}
