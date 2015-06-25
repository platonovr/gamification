package ru.kpfu.itis.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.kpfu.itis.dto.TaskDTO;
import ru.kpfu.itis.service.TaskService;

import java.util.Date;

/**
 * Created by timur on 25.06.15.
 */
@Component
public class TaskValidator implements Validator {

    @Autowired
    private TaskService taskService;

    @Override
    public boolean supports(Class<?> clazz) {
        return TaskDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TaskDTO taskDTO = (TaskDTO) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "task.name.empty");
        String name = taskDTO.getName();
        if (name != null && !name.isEmpty() && taskService.findByName(name) != null)
            errors.rejectValue("name", "task.name.exist");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "task.category.empty");
        ValidationUtils.rejectIfEmpty(errors, "maxMark", "task.maxMark.empty");
        ValidationUtils.rejectIfEmpty(errors, "deadline", "task.deadline.empty");
        Date deadline = taskDTO.getDeadline();
        if (deadline != null && deadline.compareTo(new Date()) <= 0)
            errors.rejectValue("deadline", "task.deadline.past");
    }
}
