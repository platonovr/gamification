package ru.kpfu.itis.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 31.08.2015.
 */

@Entity
@Table(name = "STUDY_COURSE")
@AttributeOverride(name = "id", column = @Column(name = "STUDY_COURSE_ID"))
public class StudyCourse extends BaseLongIdEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    private List<AcademicGroup> academicGroups = new ArrayList<>();

    @Column(name = "NUMBER")
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<AcademicGroup> getAcademicGroups() {
        return academicGroups;
    }

    public void setAcademicGroups(List<AcademicGroup> academicGroups) {
        this.academicGroups = academicGroups;
    }
}
