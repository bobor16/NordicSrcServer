package dataLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JOptionPane;

/**
 *
 * @author Borgar Bordoy
 */
public class DBconnect {

    static Connection connection;
    String url = "jdbc:postgresql://tek-mmmi-db0a.tek.c.sdu.dk:5432/si3_2018_group_4_db";
    String user = "si3_2018_group_4";
    String password = "auto92-modal";

    public DBconnect(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to the server!");

        } catch (SQLException ex) {
            Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to connect to server");
        }
    }

    public Connection dbConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
        try {
            DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to the server!");

        } catch (SQLException ex) {
            Logger.getLogger(DBconnect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to connect to server");
        }

        return connection;
    }

    public ArrayList<String> sendQuery(String query) {
        ArrayList<String> result = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    int i = 1;
                    while (i < rs.getMetaData().getColumnCount()) {
                        //System.out.print(rs.getString(i) + " ");
                        result.add(rs.getString(i));
                        i++;
                    }
                    //System.out.println(rs.getString(i) + " ");
                    result.add(rs.getString(i));
                }
                System.out.println(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String sendStatement(String statement){
        System.out.println("Got the statement: " + statement);
        try {
            Statement st = connection.createStatement();
            System.out.println("Statement created, executing update");
            st.executeUpdate(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return "failed";
        }
        System.out.println("successfully executed update");
        return "success";
    }

//    public static void viewTable() throws SQLException {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException ex) {
//            System.out.println(ex);
//        }
//        String query = "SELECT email, password, password, type, vertified, cvr FROM users";
//        Statement stmt = null;
//
//        try {
//            stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            while (rs.next()) {
//                String name = rs.getString("name");
//                String email = rs.getString("email");
//                String password = rs.getString("password");
//                String type = rs.getString("type");
//                boolean vertified = rs.getBoolean("vertified");
//                int cvr = rs.getInt("cvr");
//
//                System.out.println("Name: " + name
//                        + "email: " + email
//                        + "password: " + password
//                        + "type: " + type
//                        + "vertified: " + vertified
//                        + "cvr: " + cvr);
//
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        } finally {
//            if (stmt != null) {
//                stmt.close();
//            }
//        }
//    }
}
