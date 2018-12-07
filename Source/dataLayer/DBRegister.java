package dataLayer;

import java.util.ArrayList;
import java.util.HashMap;

public class DBRegister {
    public String register(HashMap<String, String> form){
        DBconnect connect = new DBconnect();
        ArrayList response = connect.sendQuery("SELECT * FROM users WHERE email='" + form.get("email") + "';");
        if (response.size() > 0){
            return "user exists";
        }
        String answer;
        String statement = "INSERT INTO users (name, email, password, type, companyname, cvr, verified) VALUES ('" + form.get("name") + "', '" + form.get("email") + "', '" + form.get("password") + "', '" + form.get("type") + "', '" + form.get("cname") + "', " + form.get("cvr") + ", FALSE);";
        answer = connect.sendStatement(statement);
        return answer;
    }
}
