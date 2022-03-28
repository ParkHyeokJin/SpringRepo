package com.example.springnewinstanceexam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class OrderController implements Order{

    @Override
    public void addOrder(String menu) {
        log.info("in Order : [{}]", menu);
    }

    @Override
    public void processOrder() {
        log.info("Processing Order");
    }

    @Override
    public void endOrder() {
        log.info("end Order");
    }
}
