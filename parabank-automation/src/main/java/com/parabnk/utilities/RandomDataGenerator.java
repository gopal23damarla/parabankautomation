package com.parabnk.utilities;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomDataGenerator {

    private RandomDataGenerator() {
    }

    /** e.g. {@code user_1717862400123} - unique for every execution. */
    public static String generateUsername() {
        return "user_" + System.currentTimeMillis();
    }

    /** A reasonably strong, fixed-format password good enough for the demo app. */
    public static String generatePassword() {
        return "Pass@" + ThreadLocalRandom.current().nextInt(1000, 9999);
    }

    public static String randomFirstName() {
        String[] names = {"Anjali", "Shreya", "Khushi", "Anu", "Tanushree", "Sagar", "Ravi", "Ram"};
        return names[ThreadLocalRandom.current().nextInt(names.length)];
    }

    public static String randomLastName() {
        String[] names = {"Pandey", "Singh", "Mishra", "Rajput", "Tiwari", "Rai", "Gupta", "Verma"};
        return names[ThreadLocalRandom.current().nextInt(names.length)];
    }

    public static String randomCity() {
        String[] cities = {"Rajasthan", "Gujrat", "Jammu", "Meerut", "Delhi", "Gurgaon", "Mumbai"};
        return cities[ThreadLocalRandom.current().nextInt(cities.length)];
    }

    public static String randomState() {
        String[] states = {"RJ", "GJ", "JK", "DL", "DL", "DL", "UP", "MH"};
        return states[ThreadLocalRandom.current().nextInt(states.length)];
    }

    public static String randomStreetAddress() {
        return ThreadLocalRandom.current().nextInt(100, 9999) + " Main Street";
    }

    /** 5-digit US-style zip code. */
    public static String randomZipCode() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(10000, 99999));
    }

    /** 10-digit phone number. */
    public static String randomPhoneNumber() {
        return String.valueOf(ThreadLocalRandom.current().nextLong(1_000_000_000L, 9_999_999_999L));
    }

    /** 9-digit SSN. */
    public static String randomSsn() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100_000_000, 999_999_999));
    }
}