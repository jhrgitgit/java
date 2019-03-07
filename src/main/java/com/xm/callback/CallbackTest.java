package com.xm.callback;

import org.junit.jupiter.api.Test;

public class CallbackTest {

    @Test
    public void testCallback() {
        Student student = new Ricky();
        Teacher teacher = new Teacher(student);

        teacher.askQuestion();

    }

}
