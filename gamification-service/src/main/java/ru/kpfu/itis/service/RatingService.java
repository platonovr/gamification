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
    List<Rating> getRating(Faculty faculty, Integer entranceYear, Double offset, Integer limit);

    List<Rating> getRating(AccountInfo accountInfo, Double offset, Integer limit);

    List<RatingDto> getRatingDtos(Faculty faculty, Integer entranceYear, Double offset, Integer limit);

    List<RatingDto> getRatingDtos(AccountInfo accountInfo, Double offset, Integer limit);

    Rating getUserRating(Long accountInfoId);

    RatingDto getUserRatingDto(Long accountInfoId);

    RatingDto getUserRatingDto(AccountInfo accountInfo);

    @Transactional
    void recalculateRating(AccountInfo accountInfo);

    @Transactional
    void update(Rating rating);

    @Transactional
    void save(Rating rating);

    @Transactional
    void createUserRating(AccountInfo accountInfo, Double mark);
}
