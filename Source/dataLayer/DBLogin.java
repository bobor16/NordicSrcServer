/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataLayer;

import Interfaces.All.iAuthenticate;

import java.util.ArrayList;

/**
 *
 * @author mehgn
 */
public class DBLogin implements iAuthenticate {

    public String login(String UP) {
        String answer;
        DBconnect connect = new DBconnect();
        String[] userPass = UP.split(" ");
        String query = "SELECT type FROM users WHERE email='" + userPass[0] + "' AND password='" + userPass[1] + "';";
        ArrayList<String> result = connect.sendQuery(query);
        if (result.size() == 0) {
            answer = "invalid";
        } else {
            answer = result.get(0);
        }

        return answer;
    }
}
