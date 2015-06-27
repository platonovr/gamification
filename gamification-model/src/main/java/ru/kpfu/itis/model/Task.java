package ru.kpfu.itis.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TASK")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "TASK_ID"))
})
public class Task extends BaseLongIdEntity {


    //создатель задания
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private Account author;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskType type = TaskType.PERSONAL;

    @ManyToOne
    @JoinColumn(name = "TASK_CATEGORY_ID", nullable = false)
    private TaskCategory category;

    @Column(name = "PARTICIPANTS_COUNT")
    private Integer participantsCount;

    @Column(name = "MAX_MARK", nullable = false)
    private Byte maxMark;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<AccountTask> taskAccounts = new ArrayList<>();

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE", nullable = false)
    private Date endDate;


    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public Integer getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(Integer participantsCount) {
        this.participantsCount = participantsCount;
    }

    public Byte getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(Byte maxMark) {
        this.maxMark = maxMark;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum TaskType implements EnumedDictionary {
        PERSONAL("Личное"),
        TEAM("Командое");

        private String caption;

        TaskType(String caption) {
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

        public void setCaption(String caption) {
            this.caption = caption;
        }
    }

    public List<AccountTask> getTaskAccounts() {
        return taskAccounts;
    }

    public void setTaskAccounts(List<AccountTask> taskAccounts) {
        this.taskAccounts = taskAccounts;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
