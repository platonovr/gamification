package ru.kpfu.itis.dao;

import ru.kpfu.itis.model.Faculty;
import ru.kpfu.itis.model.Rating;

import java.util.List;

/**
 * Created by Rigen on 17.07.15.
 */
public interface RatingDao {

    List<Rating> getRating(Faculty faculty, Integer entranceYear, Integer offset, Integer limit);

    List<Rating> getRatingOrderedByPoint(Faculty faculty, Integer entranceYear, Integer offset, Integer limit);

    List<Rating> getRatingOrderedByField(Faculty faculty, Integer entranceYear, Integer offset,
                                         Integer limit, String orderField, boolean order);

    List<Rating> search(Faculty faculty, Integer entranceYear,
                        String searchString, Integer offset, Integer limit);

    Rating getUserRating(Long accountInfoId);

    void update(Rating entity);

    void save(Rating entity);
}
