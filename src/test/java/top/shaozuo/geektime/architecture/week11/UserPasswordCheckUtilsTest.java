package top.shaozuo.geektime.architecture.week11;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPasswordCheckUtilsTest {

    String userName = "shaozuo";
    String password = "password";
    String encryptedPassword = "";

    @BeforeEach
    public void before() {
        encryptedPassword = UserPasswordCheckUtils.encrypt(userName, password);
    }

    /**
     * 正常
     */
    @Test
    void success() {
        assertTrue(UserPasswordCheckUtils.checkPassword(userName, password, encryptedPassword));
    }

    /**
     * 校验失败
     */
    @Test
    void failed() {
        assertFalse(UserPasswordCheckUtils.checkPassword(userName, password, "p@ssw0rd"));
        assertFalse(UserPasswordCheckUtils.checkPassword("laozhai", password, encryptedPassword));
        assertFalse(UserPasswordCheckUtils.checkPassword(userName, "p@ssw0rd", encryptedPassword));
    }

}
