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

    public int getOrderIDFromOfferID(int offerID) {
        DBconnect connect = new DBconnect();
        String query = "SELECT orderid FROM \"offer\" WHERE offerid = " + offerID + "";
        ArrayList<ArrayList> result = connect.sendQuery(query);
        ArrayList<Integer> list = new ArrayList<>();
        int orderID = (int)result.get(0).get(0);
        return orderID;

    }

    public void deleteNonAcceptedOffers(int id, String acceptedManufacturer) {
        DBconnect connect = new DBconnect();
        String query = "DELETE FROM \"offer\" WHERE orderid=" + id + " AND manfemail <> '" + acceptedManufacturer + "'";
        connect.sendStatement(query);
    }

    public void updateOffer(Offer offer) {
        DBconnect connect = new DBconnect();
        String query = "UPDATE \"offer\" SET amount = '" + offer.getAmount() + "', priceper = " + offer.getPriceper() + ", pricetotal = " + offer.getPricetotal() + ", completiondate = '" + offer.getCompletionDate() + "', deliverydate = '" + offer.getDeliveryDate() + "', briefdescription = '" + offer.getBriefDescription() + "', psname = '" + offer.getPsName() + "', ps = ? WHERE offerid=" + offer.getOfferID()+ ";";
        try {
            connect.sendPreparedStatement(query, offer.getPsBytes());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /*
     UPDATE Customers
SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
WHERE CustomerID = 1;
     */
    public void acceptOffer(String manufacturer, int orderID) {
        DBconnect connection = new DBconnect();
        String query = "UPDATE \"order\" SET manufacturer = '" + manufacturer + "', status = 'true' WHERE orderid = " + orderID;
        deleteNonAcceptedOffers(orderID, manufacturer);
        connection.sendStatement(query);
    }

    public ArrayList<String> getOfferList(String message, String user) {
        DBconnect connection = new DBconnect();
        String query;
        if (message.equals("pending")) {
            query = "select title,manfemail from \"order\", offer where \"order\".orderid=offer.orderid and offer.status = 'false'";
        } else if (message.equals("approved")) {
            query = "select title,manfemail from \"order\", offer where \"order\".orderid=offer.orderid and offer.status = 'true'";
        } else {
            query = "";
        }
        ArrayList<ArrayList> result = connection.sendQuery(query);
        ArrayList<String> list = new ArrayList<>();

        for (ArrayList row : result) {
            list.add(row.get(0) + " " + row.get(1));
        }
        return list;
    }

    public static void main(String[] args) {
        DBOffer d = new DBOffer();
        Offer offer = new Offer(5, 1, 1000000, 1000000, "2020-04-30", "20-20-06-30", "We can make this for you", "GISDFGFJDSGBFDS", null);
        Offer editOffer = new Offer(5, 2, 10, 10, "2020-04-31", "20-20-06-31", "We CANT make this for you", "GISDFGFJDSGBFDS", null);
        editOffer.setOfferID(9);
        //d.getOrderIDFromOfferID(8);//VIRKER
//        d.createOffer(offer, "china@china.dk"); VIRKER
        //  d.deleteNonAcceptedOffers(5, "china@china.dk"); VIRKER
//         d.updateOffer(editOffer); //VIRKER
        // d.acceptOffer("china@china.dk", 5); //VIRKER
        System.out.println(d.getOfferList("pending", "china@china.dk"));
    }
}
