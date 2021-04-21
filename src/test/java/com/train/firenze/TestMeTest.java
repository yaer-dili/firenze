package com.train.firenze;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestMeTest {
    private TestMe testMe;

    @BeforeEach
    void setUp() {
        testMe = new TestMe();
    }

    @Test
    void should_return_hello_message() {
        final String welcomeMessage = testMe.sayHi();

        assertThat(welcomeMessage).isEqualTo("hello world");
    }
}