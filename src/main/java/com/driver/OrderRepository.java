package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
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

}
