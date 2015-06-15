package ru.kpfu.itis.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TASK")
@AttributeOverride(name = "id", column = @Column(name = "TASK_ID"))
public class Task extends BaseLongIdEntity {


    //создатель задания
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private Account author;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskType type;


    @Column(name = "CATEGORY", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskCategory category;

    @Column(name = "PARTICIPANTS_COUNT", nullable = false)
    private Integer participantsCount;

    //todo://время завершения. Как лучше считать?
    @Column(name = "FINISH_DATE", nullable = false)
    private Date finishDate;

    @Column(name = "AVAILABILITY")
    private Boolean availability;

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

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }

    public Integer getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(Integer participantsCount) {
        this.participantsCount = participantsCount;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
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

    public enum TaskCategory implements EnumedDictionary {
        COOL("крутое"),
        BULLSHIT("не очень");

        private String caption;

        TaskCategory(String caption) {
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
