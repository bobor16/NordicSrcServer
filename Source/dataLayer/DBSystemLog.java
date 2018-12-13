/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataLayer;


import java.util.ArrayList;
import java.util.Date;
import logicLayer.ISystemLog;

/**
 *
 * @author mehgn
 */
public class DBSystemLog implements ISystemLog {
    
    @Override
    public ArrayList<String> getSystemLog() {
        DBconnect connection = new DBconnect();
        ArrayList<ArrayList> result = connection.sendQuery("SELECT logid, userid, action, timestamp FROM log");
        ArrayList<String> log = new ArrayList<>();

        for (ArrayList entry: result) {
            String logEntry = "Log ID: " + entry.get(0) + ", User " + entry.get(1) + " " + entry.get(2) + " at " + entry.get(3);
            log.add(logEntry);
        }

        return log;
    }

    @Override
    public void setSystemLog(String user, String action) {
        Date date = new Date();
        DBconnect connection = new DBconnect();
        connection.sendStatement("INSERT INTO log (userid, action, timestamp) VALUES ('" + user + "', '" + action + "', '" +  date.toString() + "');");
    }

    @Override
    public void clearSystemLog() {
        DBconnect connection = new DBconnect();
        connection.sendStatement("");
    }
}
