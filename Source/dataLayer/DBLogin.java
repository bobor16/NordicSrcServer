/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataLayer;


import Interfaces.all.iAuthenticate;
import java.util.ArrayList;

/**
 *
 * @author mehgn
 */
public class DBLogin implements iAuthenticate {

    // This class connects to the database and is mostly used to login on the client. 
    public String login(String UP) {
        String answer;
        DBconnect connect = new DBconnect();
        String[] userPass = UP.split(" ");
        String query = "SELECT type, verified FROM users WHERE email='" + userPass[0] + "' AND password='" + userPass[1] + "';";
        ArrayList<ArrayList> result = connect.sendQuery(query);
        ArrayList<Object> row;
        if (result.size() == 0) {
            answer = "invalid";
        } else if ((boolean) result.get(0).get(1)) {
            row = result.get(0);
            answer = (String) row.get(0);
        } else {
            answer = "not verified";
        }

        return answer;
    }

    public String getPassword(String email) {
        String answer = "";
        DBconnect connect = new DBconnect();
        String query = "SELECT password FROM users WHERE email='" + email + "';";
        ArrayList<ArrayList> result = connect.sendQuery(query);
        if (result.size() == 0) {
            answer = "invalid";
        } else if (result.size() == 1) {
            answer = result.get(0).get(0).toString();
        }
        return answer;
    }

}
