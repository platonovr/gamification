package ru.kpfu.itis.dto;

/**
 * Created by Roman on 31.08.2015.
 */
public class CourseOrGroupDto {

    private Long id;

    private boolean group;

    private String number;

    public boolean isGroup() {
        return group;
    }

    public void setIsGroup(boolean isGroup) {
        this.group = isGroup;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
