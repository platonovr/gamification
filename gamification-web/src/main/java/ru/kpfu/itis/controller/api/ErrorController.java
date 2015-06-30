package ru.kpfu.itis.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by timur on 25.06.15.
 */
@ControllerAdvice
public class ErrorController {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView validationError(BindException e) {
//        List<ImmutableMap<String, String>> fieldErrors = new LinkedList<>();
        Map<String, List<String>> errorsMap = new LinkedHashMap<>();
//        e.getAllErrors().parallelStream().forEach(objectError -> {
//            if (objectError instanceof FieldError) {
//                FieldError fieldError = (FieldError) objectError;
//                errorsMap.put(fieldError.getField(), messageSource.getMessage(fieldError.getCode(),
//                        null, Locale.getDefault()));
//                fieldErrors.add(ImmutableMap.of(fieldError.getField(),
//                        messageSource.getMessage(fieldError.getCode(), null, Locale.getDefault())));
//                fieldErrors.add(new FieldErrorDto(fieldError.getField(),
//                        messageSource.getMessage(fieldError.getCode(), null, Locale.getDefault())));
//            }
//        });
        e.getFieldErrors().stream().map(FieldError::getField)
                .forEach(s -> errorsMap.put(s,
                        e.getFieldErrors(s).parallelStream().map(fieldError ->
                                messageSource.getMessage(fieldError.getCode(), null,
                                        Locale.getDefault())).collect(Collectors.toList())));
        return new ModelAndView(new MappingJackson2JsonView(), "errors", errorsMap);
//        return new ModelAndView(new MappingJackson2JsonView(), "errors", fieldErrors);
    }
}
