package ru.kpfu.itis.model.classifier;

import ru.kpfu.itis.model.Task;
import ru.kpfu.itis.model.enums.ClassifierType;
import ru.kpfu.itis.model.enums.TaskCategoryType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by timur on 15.06.15.
 */
@Entity
@DiscriminatorValue(ClassifierType.Values.TASK_CATEGORY)
public class TaskCategory extends Classifier {

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskCategoryType taskCategoryType;

    @OneToMany(mappedBy = "category")
    private Set<Task> tasks = new HashSet<>();

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
