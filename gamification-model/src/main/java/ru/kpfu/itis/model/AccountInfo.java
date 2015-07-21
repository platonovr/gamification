package ru.kpfu.itis.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * Created by Roman on 22.03.2015.
 */

@Entity
@Table(name = "ACCOUNT_INFO")
@AttributeOverride(name = "id", column = @Column(name = "ACCOUNT_INFO_ID"))
public class AccountInfo extends BaseLongIdEntity {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @ManyToOne
    @JoinColumn(name = "FACULTY_ID", referencedColumnName = "FACULTY_ID")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "ACADEMIC_GROUP_ID")
    private AcademicGroup group;

    @Column(name = "ENTRANCE_YEAR")
    private Integer entranceYear;

    @Column(name = "PHOTO")
    private String photo;

    @OneToOne(optional = false)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getEntranceYear() {
        return entranceYear;
    }

    public void setEntranceYear(Integer entranceYear) {
        this.entranceYear = entranceYear;
    }

    public AcademicGroup getGroup() {
        return group;
    }

    public void setGroup(AcademicGroup group) {
        this.group = group;
    }
}
