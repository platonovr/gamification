package ru.kpfu.itis.model;

import javax.persistence.*;

@Entity
@Table(name = "TASK")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "TASK_ID")),
        @AttributeOverride(name = "finishTime", column = @Column(name = "FINISH_TIME", nullable = false))
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
    //default value for now
    private TaskType type = TaskType.PERSONAL;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private TaskCategory category;

    @Column(name = "PARTICIPANTS_COUNT")
    private Integer participantsCount;

    @Column(name = "MAX_MARK", nullable = false)
    private Byte maxMark;

    @Column(name = "DESRIPTION")
    private String description;

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

}
