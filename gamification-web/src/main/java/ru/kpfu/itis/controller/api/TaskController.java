package ru.kpfu.itis.controller.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.dto.ResponseDto;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.model.TaskCategory;
import ru.kpfu.itis.service.FileService;
import ru.kpfu.itis.service.TaskService;
import ru.kpfu.itis.util.Constant;
import ru.kpfu.itis.validator.TaskValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
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

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(taskValidator);
    }

    @ApiOperation("get available tasks")
    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDto> getAvailableTasks(@RequestParam(required = false) Integer offset,
                                           @RequestParam(required = false) Integer maxResult) {
        //before we make authentication userId = 1
        return taskService.getAvailableTasksByUser(1L, offset, maxResult);
    }

    @ApiOperation(value = "create challenge")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) throw new BindException(bindingResult);
        taskDto.setId(taskService.save(taskDto).getId());
        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @ApiOperation("upload challenge's attachment")
    @RequestMapping(value = "/{taskId:[1-9]+[0-9]*}/attachments", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto<String>> uploadAttachment(@RequestPart MultipartFile file,
                                                                @PathVariable Long taskId) {
        if (file.isEmpty())
            return new ResponseEntity<>(new ResponseDto<>("file is empty", NO_CONTENT.value()), NO_CONTENT);
        else
            try {
                String attachmentDir = fileService.uploadTaskAttachment(file, taskId);
                return new ResponseEntity<>(new ResponseDto<>("attachment uri", "/files/" + attachmentDir,
                        CREATED.value()), CREATED);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ResponseDto<>("Failed to upload", INTERNAL_SERVER_ERROR.value()),
                        INTERNAL_SERVER_ERROR);
            }
    }

    @ApiOperation("get challenge's attachments")
    @RequestMapping(value = "/{taskId:[1-9]+[0-9]*}/attachments", method = RequestMethod.GET)
    public Collection<String> getTaskAttachmentsNames(@PathVariable Long taskId) {
        return fileService.getTaskAttachmentsNames(taskId);
    }

    @ApiOperation("get available task categories")
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public Collection<TaskCategory> getTaskCategories() {
        return taskService.getAllCategories();
    }

}
