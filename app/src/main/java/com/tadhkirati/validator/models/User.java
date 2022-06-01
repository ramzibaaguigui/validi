package com.tadhkirati.validator.models;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("id")
    private Long id;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("")
    private Boolean accountConfirmed;

    @SerializedName("role")
    private String role;

    @SerializedName("image")
    private String image;

    public static User createUser() {
        return new User();
    }

    @Override
    public String toString() {
        return "User{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", accountConfirmed=" + accountConfirmed +
                ", role='" + role + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getId() {
        return this.id;
    }

    public User withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User withImage(String image) {
        this.image = image;
        return this;
    }


}
