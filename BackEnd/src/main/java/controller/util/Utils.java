package controller.util;

import org.mindrot.jbcrypt.BCrypt;

public class Utils {

    public static String generateHashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean passwordIsValid(String passWordInput, String hashedPassword) {
        return BCrypt.checkpw(passWordInput, hashedPassword);
    }
}
