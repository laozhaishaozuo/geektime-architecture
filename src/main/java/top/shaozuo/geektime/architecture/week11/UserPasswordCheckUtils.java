package top.shaozuo.geektime.architecture.week11;

import org.apache.commons.codec.binary.StringUtils;

public class UserPasswordCheckUtils {
    private UserPasswordCheckUtils() {

    }

    public static boolean checkPassword(String userId, String rawPassword,
            String encryptedPassword) {
        return StringUtils.equals(encryptedPassword, Md5Utils.md5(rawPassword, userId));
    }

    public static String encrypt(String userId, String rawPassword) {
        return Md5Utils.md5(rawPassword, userId);
    }
}
