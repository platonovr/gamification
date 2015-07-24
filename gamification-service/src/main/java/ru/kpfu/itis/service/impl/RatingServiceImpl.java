package ru.kpfu.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.dao.RatingDao;
import ru.kpfu.itis.dto.RatingDto;
import ru.kpfu.itis.mapper.RatingMapper;
import ru.kpfu.itis.model.AccountInfo;
import ru.kpfu.itis.model.Faculty;
import ru.kpfu.itis.model.Rating;
import ru.kpfu.itis.service.RatingService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rigen on 17.07.15.
 */

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingDao ratingDao;
    @Autowired
    RatingMapper ratingMapper;

    @Transactional
    @Override
    public List<Rating> getRating(Faculty faculty, Integer entranceYear, Double offset, Integer limit) {
        if (faculty == null || entranceYear == null)
            return null;
        return ratingDao.getRating(faculty, entranceYear, offset, limit);
    }

    @Transactional
    @Override
    public List<Rating> getRating(AccountInfo accountInfo, Double offset, Integer limit) {
        if (accountInfo == null)
            return null;
        return ratingDao.getRating(accountInfo.getFaculty(), accountInfo.getEntranceYear(), offset, limit);
    }

    @Transactional
    @Override
    public List<RatingDto> getRatingDtos(Faculty faculty, Integer entranceYear, Double offset, Integer limit) {
        if (faculty == null || entranceYear == null)
            return null;
        return ratingDao.getRating(faculty, entranceYear, offset, limit).parallelStream()
                .map(ratingMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<RatingDto> getRatingDtos(AccountInfo accountInfo, Double offset, Integer limit) {
        if (accountInfo == null)
            return null;
        return ratingDao.getRating(accountInfo.getFaculty(), accountInfo.getEntranceYear(), offset, limit).parallelStream()
                .map(ratingMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Rating getUserRating(Long accountInfoId) {
        if (accountInfoId != null) {
            return ratingDao.getUserRating(accountInfoId);
        }
        return null;
    }

    @Transactional
    @Override
    public RatingDto getUserRatingDto(Long accountInfoId) {
        if (accountInfoId != null) {
            return ratingMapper.toDto(ratingDao.getUserRating(accountInfoId));
        }
        return null;
    }

    @Transactional
    @Override
    public RatingDto getUserRatingDto(AccountInfo accountInfo) {
        return getUserRatingDto(accountInfo.getId());
    }

    @Transactional
    @Override
    public void recalculateRating(AccountInfo accountInfo) {
        ArrayList<Rating> ratings = (ArrayList<Rating>) ratingDao.getRatingOrderedByPoint
                (accountInfo.getFaculty(), accountInfo.getEntranceYear(), null, null);
        Rating rating = null;
        for (int i = 1; i <= ratings.size(); i++) {
            rating = ratings.get(i - 1);
            if (rating.getPosition() != i) {
                rating.setPosition(i);
                ratingDao.update(rating);
            }
        }

    }

    @Transactional
    @Override
    public List<Rating> search(Faculty faculty, Integer entranceYear,
                               String searchString, Double offset, Integer limit) {
        return ratingDao.search(faculty, entranceYear, searchString, offset, limit);
    }

    @Transactional
    @Override
    public List<RatingDto> searchDto(AccountInfo accountInfo, String searchString, Double offset, Integer limit) {
        return ratingDao.search(accountInfo.getFaculty(), accountInfo.getEntranceYear(),
                searchString, offset, limit).parallelStream().map(ratingMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void update(Rating rating) {
        ratingDao.update(rating);
    }

    @Transactional
    @Override
    public void save(Rating rating) {
        ratingDao.save(rating);
    }

    @Transactional
    @Override
    public void createUserRating(AccountInfo accountInfo, Double mark) {
        Rating rating = new Rating();
        rating.setAccountInfo(accountInfo);
        rating.setPoint(mark);
        rating.setPosition(0);
        ratingDao.save(rating);
    }

}
