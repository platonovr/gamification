package ru.kpfu.itis.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.dto.ResponseDTO;
import ru.kpfu.itis.dto.TaskDTO;
import ru.kpfu.itis.service.FileService;
import ru.kpfu.itis.service.TaskService;
import ru.kpfu.itis.util.Constant;

import java.io.IOException;
import java.util.Collection;

import static org.springframework.http.HttpStatus.*;

/**
 * Created by timur on 17.06.15.
 */
@RequestMapping(Constant.API_URI_PREFIX + "/tasks")
@RestController("apiTaskController")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FileService fileService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        taskService.save(taskDTO);
        taskDTO.setId(1L);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId:[1-9]+[0-9]*}/attachments", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO<String>> uploadAttachment(@RequestPart MultipartFile file,
                                                                @PathVariable Long taskId) {
        if (file.isEmpty())
            return new ResponseEntity<>(new ResponseDTO<>("file is empty", NO_CONTENT.value()), NO_CONTENT);
        else
            try {
                String attachmentDir = fileService.uploadTaskAttachment(file, taskId);
                return new ResponseEntity<>(new ResponseDTO<>("attachment uri", "/files/" + attachmentDir,
                        CREATED.value()), CREATED);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ResponseDTO<>("Failed to upload", INTERNAL_SERVER_ERROR.value()),
                        INTERNAL_SERVER_ERROR);
            }
    }

    @RequestMapping("/{taskId:[1-9]+[0-9]*}/attachments")
    public Collection<String> getTaskAttachments(@PathVariable Long taskId) {
        return fileService.getTaskAttachmentsNames(taskId);
    }

}
