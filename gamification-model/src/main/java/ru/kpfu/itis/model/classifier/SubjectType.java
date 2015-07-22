package ru.kpfu.itis.model.classifier;

import ru.kpfu.itis.model.Subject;
import ru.kpfu.itis.model.enums.ClassifierType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by timur on 14.07.15.
 */
@Entity
@DiscriminatorValue(ClassifierType.Values.SUBJECT_TYPE)
public class SubjectType extends Classifier {

    @OneToMany(mappedBy = "subjectType")
    private Set<Subject> subjects = new HashSet<>();

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
