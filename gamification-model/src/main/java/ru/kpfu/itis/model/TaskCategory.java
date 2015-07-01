package ru.kpfu.itis.model;

import ru.kpfu.itis.model.enums.TaskCategoryType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by timur on 15.06.15.
 */
@Entity
@Table(name = "TASK_CATEGORY")
@AttributeOverride(name = "id", column = @Column(name = "TASK_CATEGORY_ID"))
public class TaskCategory extends BaseLongIdEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "TASK_CATEGORY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskCategoryType taskCategoryType;

    @OneToMany(mappedBy = "category")
    private Set<Task> tasks = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskCategoryType getTaskCategoryType() {
        return taskCategoryType;
    }

    public void setTaskCategoryType(TaskCategoryType taskCategoryType) {
        this.taskCategoryType = taskCategoryType;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
