package ru.kpfu.itis.model.classifier;

import ru.kpfu.itis.model.BaseLongIdEntity;

import javax.persistence.*;

/**
 * Created by timur on 14.07.15.
 */
@Entity
@Table(name = "CLASSIFIER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AttributeOverride(name = "id", column = @Column(name = "classifier_id"))
@DiscriminatorColumn(name = "CLASSIFIER_TYPE")
public abstract class Classifier extends BaseLongIdEntity {

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
