package ru.kpfu.itis.model.enums;

import ru.kpfu.itis.model.EnumedDictionary;

/**
 * Created by timur on 15.06.15.
 */
public enum TaskCategoryType implements EnumedDictionary {
    STUDY("Учебное"), SCIENCE("Наука"), SPORTS("Спорт"), NONSTUDY("Внеучебное");

    private String caption;

    TaskCategoryType(String caption) {
        this.caption = caption;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getCaption() {
        return caption;
    }
}
