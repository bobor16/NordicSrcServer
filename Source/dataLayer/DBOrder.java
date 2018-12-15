/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataLayer;

import java.io.Serializable;
import java.util.ArrayList;

import logicLayer.Order;

/**
 * @author mehgn
 */
public class DBOrder{
    
    
    
     public ArrayList<Order> getOrderListPending() {
        DBconnect connection = new DBconnect();
        String query = "SELECT * FROM public.Order";
        ArrayList<ArrayList> result = connection.sendQuery("SELECT * FROM public.Order");
        ArrayList<Order> orderList = new ArrayList();
        for(int i = 0; i < result.size(); i++){
            orderList.add(new Order((Integer)result.get(i).get(0), (String)result.get(i).get(0+1), (Integer)result.get(i).get(0+2), (String)result.get(i).get(0+3), (String)result.get(i).get(0+4), (Boolean)result.get(i).get(0+5), (Integer)result.get(i).get(0+6), (Double)result.get(i).get(0+7), (Double)result.get(i).get(0+8), (String)result.get(i).get(0+9), (String)result.get(i).get(0+10), (String)result.get(i).get(0+11), (String)result.get(i).get(0+12), (Boolean)result.get(i).get(0+13)));
        }
        return orderList;
    }
     
   
}
