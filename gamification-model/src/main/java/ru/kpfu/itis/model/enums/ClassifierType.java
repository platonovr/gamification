package ru.kpfu.itis.model.enums;

/**
 * Created by timur on 14.07.15.
 */
public enum ClassifierType {
    TASK_CATEGORY, SUBJECT_TYPE;

    public interface Values {
        String TASK_CATEGORY = "TASK_CATEGORY";
        String SUBJECT_TYPE = "SUBJECT_TYPE";
    }
}
