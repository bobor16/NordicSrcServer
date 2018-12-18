/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataLayer;

import static dataLayer.ClientHandler.getUser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import logicLayer.Offer;
import logicLayer.Order;

/**
 *
 * @author mehgn
 */
public class DBOffer {
// This class connects to the database and can manipulate the offer table in the database.
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
        int orderID = (int) result.get(0).get(0);
        return orderID;

    }

    public String getManufacturerFromOfferID(int offerID) {
        DBconnect connect = new DBconnect();
        String query = "SELECT manfemail FROM \"offer\" WHERE offerid = " + offerID + "";
        ArrayList<ArrayList> result = connect.sendQuery(query);
        ArrayList<Integer> list = new ArrayList<>();
        String manfemail = (String) result.get(0).get(0);

        return manfemail;

    }

    public void deleteNonAcceptedOffers(int id) {
        DBconnect connect = new DBconnect();
        String query = "DELETE FROM offer WHERE NOT offerid = " + id;
        connect.sendStatement(query);
    }

    public void deleteOffer(int id){
        DBconnect connect = new DBconnect();
        String query = "DELETE FROM offer WHERE offerid=" + id;
        connect.sendStatement(query);
    }

    public void updateOffer(Offer offer) {
        DBconnect connect = new DBconnect();
        String query = "UPDATE \"offer\" SET amount = '" + offer.getAmount() + "', priceper = " + offer.getPriceper() + ", pricetotal = " + offer.getPricetotal() + ", completiondate = '" + offer.getCompletionDate() + "', deliverydate = '" + offer.getDeliveryDate() + "', briefdescription = '" + offer.getBriefDescription() + "', psname = '" + offer.getPsName() + "', ps = ? WHERE offerid=" + offer.getOfferID() + ";";
        try {
            connect.sendPreparedStatement(query, offer.getPsBytes());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public Offer getOffer(String offerid) {
        DBconnect connect = new DBconnect();
        String query = "SELECT offer.amount, offer.priceper, offer.pricetotal, offer.completiondate, offer.deliverydate, offer.briefdescription, \"order\".title, offer.orderid FROM offer, \"order\" WHERE offer.offerid = " + offerid + " AND offer.orderid = \"order\".orderid;";
        ArrayList<Object> row = connect.sendQuery(query).get(0);
        Offer offer = new Offer((int) row.get(0), (double) row.get(1), (double) row.get(2), (String) row.get(3), (String) row.get(4), (String) row.get(5));
        offer.setTitle((String)row.get(6));
        offer.setOfferID(Integer.parseInt(offerid));
        offer.setOrderID((int)row.get(7));
        File file = connect.getFile("SELECT ps, psname FROM offer WHERE offerid=?", offerid);
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            offer.setPsName(file.getName());
            offer.setPsBytes(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
        return offer;
    }

    public void acceptOffer(String id) {
        DBconnect connection = new DBconnect();
        String query = "UPDATE \"order\" SET manufacturer = '" + getUser() + "', status = 'true' WHERE orderid = " + id;
        deleteNonAcceptedOffers(Integer.parseInt(id));
        connection.sendStatement(query);
    }

    public ArrayList<String> getOfferList(String message, String user) {
        DBconnect connection = new DBconnect();
        String query;
        if (message.equals("pending")) {
            query = "select offerid, title from \"order\",offer where \"order\".orderid=offer.orderid and offer.status = false and manfemail = '" + user + "'";
        } else if (message.equals("approved")) {
            query = "select offerid, title from \"order\", offer where \"order\".orderid=offer.orderid and offer.status = true and manufacturer = '" + user + "'";
        } else if (message.equals("accepted")){
            query = "SELECT offerid, title FROM offer, \"order\" WHERE \"order\".orderid = offer.orderid AND offer.status = true AND customer = '" + user + "'";
        } else {
            query = "SELECT offerid, title FROM offer, \"order\" WHERE \"order\".orderid=offer.orderid AND offer.status = false AND customer = '" + user + "'";
        }
        ArrayList<ArrayList> result = connection.sendQuery(query);
        ArrayList<String> list = new ArrayList<>();

        for (ArrayList row : result) {
            list.add(row.get(0) + " " + row.get(1));
        }
        System.out.println(list);
        return list;
    }
}
