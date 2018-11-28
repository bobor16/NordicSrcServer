package dataLayer;

import java.util.HashMap;

public class DBRegister {
    public String register(HashMap<String, String> form){
        DBconnect connect = new DBconnect();
        String answer;
        String statement = "INSERT INTO users (name, email, password, type, companyname, cvr) VALUES ('" + form.get("name") + "', '" + form.get("email") + "', '" + form.get("password") + "', '" + form.get("type") + "', '" + form.get("cname") + "', " + form.get("cvr") + ");";
        System.out.println(statement);
        answer = connect.sendStatement(statement);
        System.out.println("Statement sent");
        return answer;
    }
}
