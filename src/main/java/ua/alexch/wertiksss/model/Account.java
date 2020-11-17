package ua.alexch.wertiksss.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ua.alexch.wertiksss.util.ArrayToStringConverter;
import ua.alexch.wertiksss.util.DateToStringConverter;
import ua.alexch.wertiksss.util.StringToArrayConverter;
import ua.alexch.wertiksss.util.StringToDateConverter;

/**
 * Simple domain model that represents business data.
 */
@Entity
@Table(name = "w_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Phone model is required")
    @Size(max = 50, message = "Phone model must be at most 50 characters long")
    @Column(name = "phone_model")
    private String phoneModel;

    @Size(max = 50, message = "Phone IMEI must be at most 50 characters long")
    @Column(name = "phone_imei")
    private String phoneImei;

    @Size(max = 50, message = "Phone № must be at most 50 characters long")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be at most 50 characters long")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be at most 50 characters long")
    @Column(name = "last_name")
    private String lastName;

    @JsonSerialize(converter = DateToStringConverter.class)
    @JsonDeserialize(converter = StringToDateConverter.class)
    @Column(name = "birth_date")
    private LocalDate dateOfBirth;

    @Size(max = 50)
    @Column(name = "email")
    private String email;

    @Size(max = 50)
    @Column(name = "em_password")
    private String password;

    @JsonSerialize(converter = StringToArrayConverter.class)
    @JsonDeserialize(converter = ArrayToStringConverter.class)
    @Size(max = 250)
    @Column(name = "secret_answers")
    private String secretAnswers;

//    @JsonProperty(access = Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;

    public Account() {
    }

    public Account(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneImei() {
        return phoneImei;
    }

    public void setPhoneImei(String phoneImei) {
        this.phoneImei = phoneImei;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretAnswers() {
        return secretAnswers;
    }

    public void setSecretAnswers(String secretAnswers) {
        this.secretAnswers = secretAnswers;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, id, lastName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        return Objects.equals(id, other.id) && Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName);
    }

    @Override
    public String toString() {
        return String.format("Account [id=%d, phoneModel=%s, firstName=%s, lastName=%s]", id, phoneModel, firstName,
                lastName);
    }
}
