package ru.kpfu.itis.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.kpfu.itis.dto.TaskDto;
import ru.kpfu.itis.service.TaskService;

import java.util.Calendar;
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
        return TaskDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TaskDto taskDto = (TaskDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "task.name.empty");
        String name = taskDto.getName();
        if (name != null && !name.isEmpty() && taskService.findByName(name) != null)
            errors.rejectValue("name", "task.name.exist");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "task.category.empty");
        ValidationUtils.rejectIfEmpty(errors, "maxMark", "task.maxMark.empty");
        ValidationUtils.rejectIfEmpty(errors, "startDate", "task.startDate.empty");
        ValidationUtils.rejectIfEmpty(errors, "deadline", "task.deadline.empty");
        Date startDate = taskDto.getStartDate();
        Date deadline = taskDto.getDeadline();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.getTime();
        Date currentDateWithoutHours = calendar.getTime();
        if (startDate != null | deadline != null) {
            if (deadline != null) {
                if (startDate != null && startDate.compareTo(deadline) >= 0)
                    errors.rejectValue("startDate", "task.startDate.bigger");
                if (deadline.compareTo(currentDateWithoutHours) <= 0)
                    errors.rejectValue("deadline", "task.deadline.past");
            }
            if (startDate != null && startDate.compareTo(currentDateWithoutHours) < 0)
                errors.rejectValue("startDate", "task.startDate.past");
        }
    }
}
