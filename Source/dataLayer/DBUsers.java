package dataLayer;

import java.util.ArrayList;
import java.util.HashMap;

import logicLayer.User;



public class DBUsers{
    // This class connects to the database and can manipulate the User table in the database.
    public ArrayList<Object> displayUsers() {
        DBconnect connect = new DBconnect();
        String query = "SELECT email FROM users";
        ArrayList<ArrayList> result = connect.sendQuery(query);
        ArrayList<Object> row = new ArrayList<>();
        
        for(int i = 0; i < result.size(); i++){
            row.add(result.get(i).get(0));
        }
        return row;
    }
    
    public void deleteUser(String email) {
        DBconnect connect = new DBconnect();
        String query = "DELETE FROM users WHERE email ='" + email+"';";
        connect.sendStatement(query);
    }

    public User getUser(String email){
        DBconnect connect = new DBconnect();
        String query = "Select name, type, companyname, verified, cvr FROM users WHERE email = '" + email + "';";
        ArrayList<ArrayList> result = connect.sendQuery(query);
        String name = (String) result.get(0).get(0);
        String type = (String) result.get(0).get(1);
        String companyName = (String) result.get(0).get(2);
        boolean verified = (boolean) result.get(0).get(3);
        int cvr = (int) result.get(0).get(4);

        User user = new User(email);
        user.setType(type);
        user.setName(name);
        user.setCompanyName(companyName);
        user.setCvr(cvr);
        user.setVerified(verified);

        return user;
    }

    public void updateUser(HashMap<String, String> form){
        DBconnect connect = new DBconnect();
        boolean verified;
        if (form.get("verified").equals("true")){
            verified = true;
        } else {
            verified = false;
        }
        String query = "UPDATE users SET companyname = '" + form.get("cname") + "', verified = " + verified + ", cvr = " + form.get("cvr") + " WHERE email='" + form.get("email") + "';";
        connect.sendStatement(query);
    }
}
