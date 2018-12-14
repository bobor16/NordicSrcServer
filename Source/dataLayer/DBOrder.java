/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataLayer;

import java.util.ArrayList;

import logicLayer.Order;

/**
 * @author mehgn
 */
public class DBOrder {


    public ArrayList<Order> getOrderListPending() {
        DBconnect connection = new DBconnect();
        String query = "SELECT * FROM public.Order";
        ArrayList<ArrayList> result = connection.sendQuery("SELECT * FROM public.Order");
        ArrayList<Order> orderList = new ArrayList();
        for (int i = 0; i < result.size(); i++) {
            orderList.add(new Order((Integer) result.get(i).get(0), (String) result.get(i).get(0 + 1), (Integer) result.get(i).get(0 + 2), (String) result.get(i).get(0 + 3), (String) result.get(i).get(0 + 4), (Boolean) result.get(i).get(0 + 5), (Integer) result.get(i).get(0 + 6), (Double) result.get(i).get(0 + 7), (Double) result.get(i).get(0 + 8), (String) result.get(i).get(0 + 9), (String) result.get(i).get(0 + 10), (String) result.get(i).get(0 + 11), (String) result.get(i).get(0 + 12)));
        }
        return orderList;
    }

    public ArrayList<String> getOrderList() {
        DBconnect connection = new DBconnect();
        String query = "SELECT orderid, title FROM \"order\"";
        ArrayList<ArrayList> result = connection.sendQuery(query);
        ArrayList<String> list = new ArrayList<>();

        for (ArrayList row : result) {
            list.add(row.get(0) + " " + row.get(1));
        }

        return list;
    }

    public Order getOrder(String orderID) {
        DBconnect connect = new DBconnect();
        String query = "SELECT orderid, title, psid, customer, manufacturer, archived, amount, priceper, pricetotal, completiondate, delivirydate, deadline, briefdescription FROM \"order\" WHERE orderid = " + orderID + ";";
        ArrayList<Object> row = connect.sendQuery(query).get(0);
        Order order = new Order((int) row.get(0),(String) row.get(1),(int) row.get(2),(String) row.get(3),(String) row.get(4),(boolean) row.get(5),(int) row.get(6),(double) row.get(7),(double) row.get(8),(String) row.get(9),(String) row.get(10),(String) row.get(11),(String) row.get(12));
        System.out.println(order.getBriefdescription());
        return order;
    }
}
