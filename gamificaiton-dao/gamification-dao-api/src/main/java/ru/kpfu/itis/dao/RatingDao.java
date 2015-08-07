package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.Faculty;
import ru.kpfu.itis.model.Rating;

import java.util.List;

/**
 * Created by Rigen on 17.07.15.
 */
public interface RatingDao extends AbstractDao<Rating, Long> {
    List<Rating> getRating(Faculty faculty, Integer entranceYear, Double offset, Integer limit);

    List<Rating> getRatingOrderedByPoint(Faculty faculty, Integer entranceYear, Double offset, Integer limit);

    List<Rating> getRatingOrderedByField(Faculty faculty, Integer entranceYear, Double offset,
                                         Integer limit, String orderField, boolean order);

    List<Rating> search(Faculty faculty, Integer entranceYear,
                        String searchString, Double offset, Integer limit);

    Rating getUserRating(Long accountInfoId);

    void update(Rating entity);

}
