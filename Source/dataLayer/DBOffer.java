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

    public void createOffer(Offer offer) {
        DBconnect connect = new DBconnect();
        String query = "INSERT INTO \"offer\" (orderid, manfemail, offerid, amount, priceper, pricetotal, completiondate,deliverydate,briefdescription,status,psname,ps) VALUES ('" + offer.getOrderID() + "', '" + offer.getManfemail() + "', '" + offer.getOfferID() + "', '" + offer.getAmount() + "', '" + offer.getPriceper() + "', '" + offer.getPricetotal() + "', '" + offer.getCompletionDate() + "', '" + offer.getDeliveryDate() + "', '" + offer.getBriefDescription() + "', false, '" + offer.getPsname() + "', ?);";
        try {
            connect.sendPreparedStatement(query, offer.getPsBytes());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteOffer(int id) {
        DBconnect connect = new DBconnect();
        String query = "DELETE FROM \"offer\" WHERE offerid=" + id;
        connect.sendStatement(query);
    }

    public ArrayList<String> getOrderListManufacturer( ) {
        DBconnect connection = new DBconnect();
        String query = "SELECT orderid, title FROM \"order\" WHERE status = false";
        ArrayList<ArrayList> result = connection.sendQuery(query);
        ArrayList<String> list = new ArrayList<>();

        for (ArrayList row : result) {
            list.add(row.get(0) + " " + row.get(1));
        }

        return list;
    }
    public void acceptOrder(String user, int offerID, int orderID){
       DBconnect connection = new DBconnect();
       String query = "UPDATE order SET manufacturer = '" + user + "', status = 'true' WHERE orderid = " + orderID;
       deleteOffer(offerID);
       connection.sendStatement(query);
    }
    
    public static void main(String[] args) {
        DBOffer d = new DBOffer();
        d.acceptOrder("TEST MANU", 4, 5);
    }
                   
}
