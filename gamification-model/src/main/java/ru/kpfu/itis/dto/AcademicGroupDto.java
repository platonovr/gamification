package ru.kpfu.itis.dto;

/**
 * Created by ainurminibaev on 25.08.15.
 */
public class AcademicGroupDto {

    private Long id;

    private String name;

    public AcademicGroupDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
