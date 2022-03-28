package com.example.springnewinstanceexam;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
public class OrderThreadTest {
    @Test
    void OrderTEST(){
        new Thread(new OrderThread("김밥")).start();
    }
}