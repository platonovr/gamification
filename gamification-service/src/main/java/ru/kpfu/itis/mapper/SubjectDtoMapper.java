package ru.kpfu.itis.mapper;

import ru.kpfu.itis.dto.SubjectDto;
import ru.kpfu.itis.model.Subject;

import java.util.function.Function;

/**
 * Created by Roman on 31.08.2015.
 */
public class SubjectDtoMapper implements Function<Subject, SubjectDto> {

    @Override
    public SubjectDto apply(Subject subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subject.getId());
        subjectDto.setName(subject.getName());
        subjectDto.setType(subject.getSubjectType().getName());
        return subjectDto;
    }
}
