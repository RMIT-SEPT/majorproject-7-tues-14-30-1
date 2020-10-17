package controller.util;

import org.mindrot.jbcrypt.BCrypt;

public class Utils {

//    Used to secure the passwords stored in the DB
    public static String generateHashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean passwordIsValid(String passwordInput, String hashedPassword) {
        return BCrypt.checkpw(passwordInput, hashedPassword);
    }
}
