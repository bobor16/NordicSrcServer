/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataLayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;

import logicLayer.Order;

/**
 * @author mehgn
 */
public class DBOrder {


    public ArrayList<Order> getOrderListPending() {
        DBconnect connection = new DBconnect();
        ArrayList<ArrayList> result = connection.sendQuery("SELECT * FROM \"order\"");
        ArrayList<Order> orderList = new ArrayList();
        for (int i = 0; i < result.size(); i++) {
            orderList.add(new Order((Integer) result.get(i).get(0), (String) result.get(i).get(1), (String) result.get(i).get(2), (String) result.get(i).get(3), (Boolean) result.get(i).get(4), (Integer) result.get(i).get(5), (Double) result.get(i).get(6), (Double) result.get(i).get(7), (String) result.get(i).get(8), (String) result.get(i).get(9), (String) result.get(i).get(10), (String) result.get(i).get(11)));
        }
        return orderList;
    }

    public ArrayList<String> getOrderList(String message, String user) {
        DBconnect connection = new DBconnect();
        String query;
        if (message.equals("pending")){
            query = "SELECT orderid, title FROM \"order\" WHERE customer = '" + user + "' AND status = false";
        } else if (message.equals("approved")){
            query = "SELECT orderid, title FROM \"order\" WHERE customer = '" + user + "' AND status = true";
        } else{
            query = "SELECT orderid, title FROM \"order\" WHERE customer = '" + user + "'";
        }
        ArrayList<ArrayList> result = connection.sendQuery(query);
        ArrayList<String> list = new ArrayList<>();

        for (ArrayList row : result) {
            list.add(row.get(0) + " " + row.get(1));
        }

        return list;
    }

    public Order getOrder(String orderID) {
        DBconnect connect = new DBconnect();
        String query = "SELECT orderid, title, customer, manufacturer, archived, amount, priceper, pricetotal, completiondate, deliverydate, deadline, briefdescription FROM \"order\" WHERE orderid = " + orderID + ";";
        ArrayList<Object> row = connect.sendQuery(query).get(0);
        Order order = new Order((int) row.get(0),(String) row.get(1), (String) row.get(2),(String) row.get(3),(boolean) row.get(4),(int) row.get(5),(double) row.get(6),(double) row.get(7),(String) row.get(8),(String) row.get(9),(String) row.get(10),(String) row.get(11));
        System.out.println(order.getBriefdescription());
        File file = connect.getFile(Integer.toString(order.getId()));
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            order.setPs(file);
            order.setPsBytes(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return order;
    }

    public File getProductSpecification(String orderid){
        DBconnect connect = new DBconnect();
        File file = connect.getFile(orderid);

        return file;
    }

    public void createOrder(Order order){
        DBconnect connect = new DBconnect();
        String query = "INSERT INTO \"order\" (title, customer, archived, amount, priceper, pricetotal, completiondate, deliverydate, deadline, briefdescription, status, psname, ps) VALUES ('" + order.getTitle() + "', '" + order.getCustomer() + "', false, " + order.getAmount() + ", " +order.getPriceper() + ", "+ order.getPricetotal() + ", '" + order.getCompletionDate() + "', '" + order.getDeliveryDate() + "', '" + order.getDeadline() + "', '" + order.getBriefdescription() + "', false, ?, ?);";
        try {
            connect.sendPreparedStatement(query, order.getPs());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(String id){
        DBconnect connect = new DBconnect();
        String query = "DELETE FROM \"order\" WHERE orderid=" + id;
        connect.sendStatement(query);
    }
}
