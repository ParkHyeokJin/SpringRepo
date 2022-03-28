package com.example.springnewinstanceexam;

public class OrderThread implements Runnable{

    private final String menu;
    private final Order order;

    public OrderThread(String menu) {
        this.order = (Order) ApplicationContextProvider.getApplicationContext().getBean("orderController");
        this.menu = menu;
    }

    @Override
    public void run() {
        order.addOrder(menu);
        order.processOrder();
        order.endOrder();
    }
}
