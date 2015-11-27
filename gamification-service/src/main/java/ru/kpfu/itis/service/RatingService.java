package ru.kpfu.itis.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dto.RatingDto;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.model.Faculty;
import ru.kpfu.itis.model.Rating;

import java.util.List;

/**
 * Created by Rigen on 17.07.15.
 */
public interface RatingService {
    List<Rating> getRating(Faculty faculty, Integer entranceYear, Integer offset, Integer limit);

    List<Rating> getRating(AccountInfo accountInfo, Integer offset, Integer limit);

    List<RatingDto> getRatingDtos(Faculty faculty, Integer entranceYear, Integer offset, Integer limit);

    List<RatingDto> getRatingDtos(AccountInfo accountInfo, Integer offset, Integer limit);

    Rating getUserRating(Long accountInfoId);

    RatingDto getUserRatingDto(Long accountInfoId);

    RatingDto getUserRatingDto(AccountInfo accountInfo);

    void recalculateRating(AccountInfo accountInfo);

    List<Rating> search(Faculty faculty, Integer entranceYear,
                        String searchString, Integer offset, Integer limit);

    List<RatingDto> searchDto(AccountInfo accountInfo, String searchString, Integer offset, Integer limit);

    void update(Rating rating);

    void save(Rating rating);

    void createUserRating(AccountInfo accountInfo, Double mark);
}
