/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataLayer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import logicLayer.Offer;
import logicLayer.Order;

/**
 *
 * @author mehgn
 */
public class DBOffer {

    public void createOffer(Offer offer, String user) {
        DBconnect connect = new DBconnect();
        String query = "INSERT INTO \"offer\" (orderid, manfemail, amount, priceper, pricetotal, completiondate,deliverydate,briefdescription,status,psname,ps) VALUES ('" + offer.getOrderID() + "', '" + user + "', '" + offer.getAmount() + "', '" + offer.getPriceper() + "', '" + offer.getPricetotal() + "', '" + offer.getCompletionDate() + "', '" + offer.getDeliveryDate() + "', '" + offer.getBriefDescription() + "', false, '" + offer.getPsName() + "', ?);";
        try {
            connect.sendPreparedStatement(query, offer.getPsBytes());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public int gerOrderIDFromOfferID(int id) {
        DBconnect connect = new DBconnect();
        ArrayList<ArrayList> result = connect.sendQuery("SELECT orderid FROM \"offer\" WHERE offerid = '" + id + "'");
        String orderID = (String) result.get(0).get(0);
        int orderIDINT = Integer.parseInt(orderID);
        return orderIDINT;
    }

    public void deleteOffer(int id, String user) {
        DBconnect connect = new DBconnect();
        String query = "DELETE FROM \"offer\" WHERE orderid=" + id + " AND manfemail <> '" + user + "'";
        connect.sendStatement(query);
    }

    public void acceptOrder(String user, int orderID) {
        DBconnect connection = new DBconnect();
        String query = "UPDATE \"order\" SET manufacturer = '" + user + "', status = 'true' WHERE orderid = " + orderID;
        deleteOffer(orderID, user);
        connection.sendStatement(query);
    }

    public ArrayList<String> getOfferList(String message, String user, int offerID) {
        DBconnect connection = new DBconnect();
        String query;
        if (message.equals("pending")) {
            query = "SELECT title FROM \"order\" WHERE manufacturer = NULL and status = 'true'";
        } else if (message.equals("approved")) {
            query = "SELECT title FROM \"order\" WHERE manufacturer = '" + user + "and status = 'true'";
        } else {
            query = "";
        }
        ArrayList<ArrayList> result = connection.sendQuery(query);
        ArrayList<String> list = new ArrayList<>();

        for (ArrayList row : result) {
            list.add(row.get(0) + " " + row.get(0));
        }
        return list;
    }
}