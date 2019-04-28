package com.hardikdosi;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hardik Dosi on 11/28/2016.
 */
public class Authorize {
    private static Set<String> loggedInUsers = new HashSet<String>();

    public static boolean isLoggedIn(String username) {
        if (loggedInUsers.contains(username)) {
            return true;
        }
        return false;
    }

    public static void logIn(String username) {
        loggedInUsers.add(username);
    }

    public static void logOut(String username) {
        loggedInUsers.remove(username);
    }
}
