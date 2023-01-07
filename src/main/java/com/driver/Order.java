package com.driver;

import java.sql.Time;
import java.time.LocalTime;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        this.deliveryTime = Integer.parseInt(deliveryTime.substring(3))+Integer.parseInt(deliveryTime.substring(0,2))*60;
       /*
        LocalTime t = LocalTime.parse(deliveryTime);
        int hour = t.getHour();
        int minutes = t.getMinute();
        int time = hour*60 + minutes;
        this.deliveryTime = time;

        */
    }

    public String getId() {
        return id;
    }
    public int getDeliveryTime() {
        return deliveryTime;
    }
}
