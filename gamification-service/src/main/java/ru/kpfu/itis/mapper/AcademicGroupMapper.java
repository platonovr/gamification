package ru.kpfu.itis.mapper;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.AcademicGroupDto;
import ru.kpfu.itis.model.AcademicGroup;

/**
 * Created by ainurminibaev on 25.08.15.
 */
@Component
public class AcademicGroupMapper implements Mapper<AcademicGroup, AcademicGroupDto> {
    @Override
    public AcademicGroup fromDto(AcademicGroupDto dto) {
        return null;
    }

    @Override
    public AcademicGroupDto toDto(AcademicGroup academicGroup) {
        return new AcademicGroupDto(academicGroup.getId(), academicGroup.getName());
    }
}
