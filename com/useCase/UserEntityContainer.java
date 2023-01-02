package com.useCase;

import com.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A map of userID (username) -> User Entity object, which will be used to store all user entities in UserAccess
 */

public class UserEntityContainer implements Serializable {
    public final HashMap<String, User> users = new HashMap<>();

    /**
     * Add a user to the container
     * @param user a User object
     */
    public void add(User user) {
        users.put(user.toString(), user);
    }

    /**
     * Find a user entity from the HashMap users
     * @param userID a String to represent userID
     * @return a User Entity object
     */
    public User findEntity(String userID) {
        if (exists(userID)) {
            return users.get(userID);
        }
        return null;
    }

    /**
     * Delete a user entity from the HashMap users
     *
     * @param userID a String to represent userID
     */
    public void deleteEntity(String userID) {
        users.remove(userID);
    }

    /**
     * Check whether a user entity exists
     * @param userID a String to represent userID
     * @return a boolean to indicate the status of checking
     */
    public boolean exists(String userID) {
        return users.containsKey(userID);
    }

    /**
     * Get a list of all user entities
     * @return a list contains all user entities in the program
     */
    public ArrayList<User> values() {
        return new ArrayList<>(users.values());
    }
}
