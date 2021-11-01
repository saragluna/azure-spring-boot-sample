// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.sample.model;

import java.util.Random;
import java.util.UUID;

public class User {

    private String id;
    private String lastName;
    private String firstName;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", email='" + email + '\'' +
            '}';
    }

    private static final String[] FIRST_NAMES = new String[] {
        "Alice", "Bob", "Charlie", "Doe", "Emily", "Frank", "George",
        "Helen", "Irene", "Jack", "Karl", "Lily", "Mani", "Nathan",
        "Owen", "Peter", "Q", "Rambo", "Steven", "Ted",
        "Uma", "Victor", "William", "Xena", "Yev", "Z"
    };
    private static final String[] LAST_NAMES = new String[] {
        "Wong", "Fok", "Young", "Bond"
    };

    private static final Random RANDOM = new Random();
    public static User randomUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)]);
        user.setLastName(LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)]);
        user.setEmail(user.getFirstName() + "." + user.getLastName() + "@Contoso.com");
        return user;
    }
}
