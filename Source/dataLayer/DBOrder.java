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
import logicLayer.Offer;

import logicLayer.Order;

/**
 * @author mehgn
 */
public class DBOrder {
// This class connects to the database and can manipulate the Order table in the database.
    public ArrayList<Order> getOrderListPending() {
        DBconnect connection = new DBconnect();
        ArrayList<ArrayList> result = connection.sendQuery("SELECT * FROM \"order\"");
        ArrayList<Order> orderList = new ArrayList();
        for (int i = 0; i < result.size(); i++) {
        }
        return orderList;
    }

    public ArrayList<String> getCustomerOrderList(String message, String user) {
        DBconnect connection = new DBconnect();
        String query;
        if (message.equals("pending")) {
            query = "SELECT orderid, title FROM \"order\" WHERE customer = '" + user + "' AND status = false AND manufacturer IS NULL";
        } else if (message.equals("approved")) {
            query = "SELECT orderid, title FROM \"order\" WHERE customer = '" + user + "' AND status = true AND manufacturer IS NULL";
        } else {
            query = "SELECT orderid, title FROM \"order\" WHERE customer = '" + user + "'";
        }
        ArrayList<ArrayList> result = connection.sendQuery(query);
        ArrayList<String> list = new ArrayList<>();

        for (ArrayList row : result) {
            list.add(row.get(0) + " " + row.get(1));
        }

        return list;
    }

    public ArrayList<String> getManufacturerList() {
        DBconnect connection = new DBconnect();
        String query = "SELECT orderid, title FROM \"order\" WHERE manufacturer IS NULL and status = true;";
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
        Order order = new Order((int) row.get(0), (String) row.get(1), (String) row.get(2), (String) row.get(3), (boolean) row.get(4), (int) row.get(5), (double) row.get(6), (double) row.get(7), (String) row.get(8), (String) row.get(9), (String) row.get(10), (String) row.get(11));
        System.out.println(order.getBriefdescription());
        File file = connect.getFile(Integer.toString(order.getId()));
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            order.setPsname(file.getName());
            order.setPsBytes(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return order;
    }

    public File getProductSpecification(String orderid) {
        DBconnect connect = new DBconnect();
        File file = connect.getFile(orderid);

        return file;
    }

    public void createOrder(Order order) {
        DBconnect connect = new DBconnect();
        String query = "INSERT INTO \"order\" (title, customer, archived, amount, priceper, pricetotal, completiondate, deliverydate, deadline, briefdescription, status, psname, ps) VALUES ('" + order.getTitle() + "', '" + order.getCustomer() + "', false, " + order.getAmount() + ", " + order.getPriceper() + ", " + order.getPricetotal() + ", '" + order.getCompletionDate() + "', '" + order.getDeliveryDate() + "', '" + order.getDeadline() + "', '" + order.getBriefdescription() + "', false, '" + order.getPsname() + "', ?);";
        try {
            connect.sendPreparedStatement(query, order.getPsBytes());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(String id) {
        DBconnect connect = new DBconnect();
        String query = "DELETE FROM \"order\" WHERE orderid=" + id;
        connect.sendStatement(query);
    }

    public void updateOrder(Order order) {
        DBconnect connect = new DBconnect();
        String query = "UPDATE \"order\" SET title = '" + order.getTitle() + "', amount = " + order.getAmount() + ", priceper = " + order.getPriceper() + ", pricetotal = " + order.getPricetotal() + ", completiondate = '" + order.getCompletionDate() + "', deliverydate = '" + order.getDeliveryDate() + "', deadline = '" + order.getDeadline() + "', psname = '" + order.getPsname() + "', ps = ?, briefdescription = '" + order.getBriefdescription() + "' WHERE orderid=" + order.getId() + ";";
        try {
            connect.sendPreparedStatement(query, order.getPsBytes());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
