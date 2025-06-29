package controller;

import com.teachAssistantHelper.MyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MyApplication.class)
public class BasicTest {

    @Test
    void testEnvironmentWorks() {
        int result = 2 + 3;
        assertEquals(5, result);
    }
}
