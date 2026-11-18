package dk.easv.mrs.BLL;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordService {

    private final Argon2 argon2;

    public PasswordService() {
        this.argon2 = Argon2Factory.create(
                Argon2Factory.Argon2Types.ARGON2id
        );
    }

    public String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException(
                    "Password cannot be empty"
            );
        }

        char[] passwordChars = password.toCharArray();

        try {
            return argon2.hash(
                    3, // Iterations
                    131072, // 128 MB memory
                    1, // Parallelism
                    passwordChars
            );
        } finally {
            argon2.wipeArray(passwordChars);
        }
    }

    public boolean verifyPassword(String password, String storedHash) {
        if (password == null || storedHash == null) {
            return false;
        }

        char[] passwordChars = password.toCharArray();

        try {
            return argon2.verify(
                    storedHash,
                    passwordChars
            );
        } finally {
            argon2.wipeArray(passwordChars);
        }
    }



}
