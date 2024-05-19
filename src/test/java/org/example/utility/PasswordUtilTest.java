package org.example.utility;

import com.example.finalassingment.utility.PasswordUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PasswordUtilTest {

    @Test
    void shouldBeTheSame() {
        String password = "Rmit@1234";
        String encryptedPassword = PasswordUtil.encrypt(password);
        assertEquals(encryptedPassword, "+xnkPqjMPgVb+DnX4zjojQ==");
    }

}