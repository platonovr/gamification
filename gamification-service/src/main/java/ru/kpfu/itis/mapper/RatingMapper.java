package ru.kpfu.itis.mapper;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.dto.RatingDto;
import ru.kpfu.itis.model.Rating;

/**
 * Created by Rigen on 18.07.15.
 */
@Component
public class RatingMapper implements Mapper<Rating, RatingDto> {
    @Override
    public Rating fromDto(RatingDto dto) {
        return null;
    }

    @Override
    public RatingDto toDto(Rating object) {
        if (object != null) {
            RatingDto dto = new RatingDto();
            dto.setAccountInfoId(object.getAccountInfo().getId());
            dto.setFirstName(object.getAccountInfo().getFirstName());
            dto.setLastName(object.getAccountInfo().getLastName());
            dto.setPhoto(object.getAccountInfo().getPhoto());
            dto.setPosition(object.getPosition());
            dto.setRating(object.getPoint());
            return dto;
        }
        return null;
    }
}
