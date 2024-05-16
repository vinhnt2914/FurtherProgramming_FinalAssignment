package org.example.test;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class PageManagerTest {
    @Test
    void shouldGetCorrectFXMLPath() {
        URL fxmlPath = getClass().getResource("/views/" + "Info".toLowerCase());
        assertEquals("/views/info.fxml", fxmlPath);
    }

}