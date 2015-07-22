package ru.kpfu.itis.mapper;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.SubjectDto;
import ru.kpfu.itis.model.Subject;
import ru.kpfu.itis.model.classifier.SubjectType;

import static org.hibernate.Hibernate.isInitialized;

/**
 * Created by timur on 14.07.15.
 */
@Component
public class SubjectMapper implements Mapper<Subject, SubjectDto> {
    @Override
    public Subject fromDto(SubjectDto dto) {
        if (dto != null) {
            Subject subject = new Subject();
            subject.setName(dto.getName());
            return subject;
        }
        return null;
    }

    @Override
    public SubjectDto toDto(Subject subject) {
        if (subject != null) {
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.setId(subject.getId());
            subjectDto.setName(subject.getName());
            SubjectType subjectType = subject.getSubjectType();
            if (isInitialized(subjectType) && subjectType != null)
                subjectDto.setType(subjectType.getName());
            return subjectDto;
        }
        return null;
    }
}
