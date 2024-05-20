package project.oop.datadealing.datarefining.parsing;

import java.util.UUID;

public class IdGenerator {
    public static String generateUniqueId() {
        // Option 1: Use a UUID (Universally Unique Identifier)
        return UUID.randomUUID().toString();  // This generates a random string ID

    }
}
