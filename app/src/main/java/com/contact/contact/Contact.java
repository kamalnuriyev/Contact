package com.contact.contact;

/**
 * Created by Kamal on 27.04.2016.
 */
public class Contact {
    private String name_, surname_, phoneNumber_;

    public Contact(String name, String surname, String phoneNumber) {
        name_ = name;
        surname_ = surname;
        phoneNumber_ = phoneNumber;
    }

    public String getName() {
        return name_;
    }

    public String getSurname() {
        return surname_;
    }

    public String getPhoneNumber() {
        return phoneNumber_;
    }
}
