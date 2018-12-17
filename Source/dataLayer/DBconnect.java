package dataLayer;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JOptionPane;

/**
 * @author Borgar Bordoy
 */
public class DBconnect {

    static Connection connection;
    String url = "jdbc:postgresql://tek-mmmi-db0a.tek.c.sdu.dk:5432/si3_2018_group_4_db";
    String user = "si3_2018_group_4";
    String password = "auto92-modal";

    public DBconnect() {
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
        return connection;
    }

    public ArrayList<ArrayList> sendQuery(String query) {
        System.out.println(query);
        ArrayList<ArrayList> result = new ArrayList<>();
        ArrayList<Object> row = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                row.clear();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getObject(i));
                }
                result.add(new ArrayList<>(row));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String sendStatement(String statement) {
        System.out.println("Got the statement: " + statement);
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return "failed";
        }
        System.out.println("successfully executed update");
        return "success";
    }

    public void sendPreparedStatement(String query, byte[] bytes) throws SQLException, IOException {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setBytes(1, bytes);
        ps.executeUpdate();
        ps.close();
    }

    public File getFile(String orderid){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT ps, psname FROM \"order\" WHERE orderid=?");
            ps.setInt(1, Integer.parseInt(orderid));
            ResultSet rs = ps.executeQuery();
            File file = null;
            if (rs != null) {
                while (rs.next()) {
                    file = new File(rs.getString(2));
                    byte[] bytes = rs.getBytes(1);

                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                    file.deleteOnExit();
                }
                rs.close();
            }
            ps.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
