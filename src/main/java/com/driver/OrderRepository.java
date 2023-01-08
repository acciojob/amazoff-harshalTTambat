package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String, Order> orderDB;
    HashMap<String, DeliveryPartner> partnerDB;
    HashMap<String,List<String>>orderList;
    HashMap<String,String> orderAndPartner;

    public OrderRepository() {
        this.orderDB = new HashMap<String, Order>();
        this.partnerDB = new HashMap<String, DeliveryPartner>();
        this.orderList = new HashMap<String,List<String>>(); //partnerId, ListOfOrders
        this.orderAndPartner = new HashMap<String,String>(); // orderId, partnerId

    }
    Integer ans;
    List<String> temp;

    public void addOrder(Order order)
    {
        String id = order.getId();
        orderDB.put(id,order);
    }
    public void addPartner(String partnerId)
    {
        partnerDB.put(partnerId, new DeliveryPartner(partnerId));
    }
    public void addOrderPartnerPair(String orderId,String partnerId)
    {
        if(orderDB.containsKey(orderId) && partnerDB.containsKey(partnerId))
        {
            this.temp = new ArrayList<>();
            if(orderList.containsKey(partnerId))
            {
                this.temp = orderList.get(partnerId);
            }
            this.temp.add(orderId);
            orderList.put(partnerId,this.temp);
        }
    }
    public Order getOrderById(String orderId)
    {
        return orderDB.get(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId)
    {
        return partnerDB.get(partnerId);
    }
    public Integer getOrderCountByPartnerId(String partnerId)
    {
        DeliveryPartner d = partnerDB.get(partnerId);
        d.setNumberOfOrders(orderList.get(partnerId).size());

        return d.getNumberOfOrders();
    }
    public List<String> getOrdersByPartnerId(String partnerId)
    {
        List<String> list = orderList.get(partnerId);
        return list;
    }
    public List<String> getAllOrders()
    {
        return new ArrayList<>(orderDB.keySet());
    }
    public Integer getCountOfUnassignedOrders()
    {
        this.ans = 0;
        for(String partner: partnerDB.keySet())
        {
            List<String> str = orderList.get(partner);
            for(String order : str)
            {
                orderAndPartner.put(order,partner);
            }
        }
        for(String order: orderDB.keySet())
        {
            if(! orderAndPartner.containsKey(order))
            {
                this.ans++;
            }
        }
        return this.ans;
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String timeS,String partnerId)
    {
        Integer hour = Integer.valueOf(timeS.substring(0, 2));
        Integer minutes = Integer.valueOf(timeS.substring(3));
        Integer time = hour*60 + minutes;

        Integer countOfOrders = 0;
        if(orderList.containsKey(partnerId)){
            List<String> orders = orderList.get(partnerId);
            for(String order: orders){
                if(orderDB.containsKey(order)){
                    Order currOrder = orderDB.get(order);
                    if(time < currOrder.getDeliveryTime()){
                        countOfOrders += 1;
                    }
                }
            }
        }
        return countOfOrders;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId)
    {
        Integer time = 0;

        if(orderList.containsKey(partnerId)){
            List<String> orders = orderList.get(partnerId);
            for(String order: orders){
                if(orderDB.containsKey(order)){
                    Order currOrder = orderDB.get(order);
                    time = Math.max(time, currOrder.getDeliveryTime());
                }
            }
        }

        Integer hour = time/60;
        Integer minutes = time%60;

        String hourInString = String.valueOf(hour);
        String minInString = String.valueOf(minutes);
        if(hourInString.length() == 1){
            hourInString = "0" + hourInString;
        }
        if(minInString.length() == 1){
            minInString = "0" + minInString;
        }

        return  hourInString + ":" + minInString;
    }


    public void deletePartnerById(String partnerId)
    {
       List<String> list =  orderList.get(partnerId);
       for(String order: list)
       {
            if (orderAndPartner.containsKey(order))
            {
                orderAndPartner.remove(order);
            }
       }
       orderList.remove(partnerId);
       partnerDB.remove(partnerId);
    }
    public void deleteOrderById(String orderId)
    {
        if(orderAndPartner.containsKey(orderId))
        {
            String partner = orderAndPartner.get(orderId);
            orderAndPartner.remove(orderId);
            List<String> list = orderList.get(partner);
            for(String order:list)
            {
                if(order.equals(orderId))
                {
                    list.remove(order);
                }
            }
        }
        orderDB.remove(orderId);
    }

}
