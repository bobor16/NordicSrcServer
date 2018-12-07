/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataLayer;

import interfaces.all.ISystemLog;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mehgn
 */
public class DBSystemLog implements ISystemLog {
    @Override
    public ArrayList getSystemLog() {
        DBconnect connection = new DBconnect();
        ArrayList<ArrayList> result = connection.sendQuery("SELECT * FROM SystemLog");
        return result;
    }

    @Override
    public void setSystemLog(String user, String action) {
        Date date = new Date();
        DBconnect connection = new DBconnect();
        connection.sendStatement("INSERT INTO log (userid, action, timestamp) VALUES ('" + user + "', '" + action + "', '" +  date.toString() + "');");
    }

    @Override
    public void clearSystemLog() {

    }
}
