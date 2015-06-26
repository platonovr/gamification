package ru.kpfu.itis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.kpfu.itis.model.enums.Type;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by timur on 15.06.15.
 */
@Entity
@Table(name = "CATEGORY")
@AttributeOverride(name = "id", column = @Column(name = "CATEGORY_ID"))
public class TaskCategory extends BaseLongIdEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Task> tasks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
