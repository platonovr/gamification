package ru.kpfu.itis.dto;

/**
 * Created by Roman on 21.08.2015.
 */
public class AccountDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String fullNameWithGroup;

    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullNameWithGroup() {
        return fullNameWithGroup;
    }

    public void setFullNameWithGroup(String fullNameWithGroup) {
        this.fullNameWithGroup = fullNameWithGroup;
    }
}
