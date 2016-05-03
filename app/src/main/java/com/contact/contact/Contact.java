package com.contact.contact;

import android.net.Uri;

/**
 * Created by Kamal on 27.04.2016.
 */
public class Contact {
    private String name, surname, phoneNumber;
    private Uri imageUri;
    private int ID;

    public Contact(int ID, String name, String surname, String phoneNumber, Uri imageUri) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.imageUri = imageUri;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
