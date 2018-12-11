package dataLayer;



import dataLayer.ClientHandler;
import dataLayer.DBconnect;
import dataLayer.Packet;
import java.util.ArrayList;
import java.util.List;
import logicLayer.User;



public class DBUsers extends DBconnect {
    
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
    
    public static void main(String[] args) {
        DBUsers db = new DBUsers();
        db.deleteUser("test");
    }
    
    
    public String login(String UP) {
        String answer;
        DBconnect connect = new DBconnect();
        String[] userPass = UP.split(" ");
        String query = "SELECT type, verified FROM users WHERE email='" + userPass[0] + "' AND password='" + userPass[1] + "';";
        ArrayList<ArrayList> result = connect.sendQuery(query);
        ArrayList<Object> row;
        if (result.size() == 0) {
            answer = "invalid";
        } else if ((boolean)result.get(0).get(1)){
            row = result.get(0);
            answer = (String)row.get(0);
        } else {
            answer = "not verified";
        }

        return answer;
    }
    
    
    public void deleteUser(String email) {
        DBconnect connect = new DBconnect();
        String query = "DELETE from Users WHERE email ='" + email+"';";
        connect.sendQuery(query);
    }
}
