/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataLayer;

/**
 *
 * @author mehgn
 */
import interfaces.all.ILogic.Ilogic;
import interfaces.iData.Idata;
import logicLayer.SystemLog;

import java.util.ArrayList;
import java.util.List;
import logicLayer.User;

/**
 *
 * @author mehgn
 */
public class DataFacade implements Idata {

    private static Idata data;
    private static Ilogic logic;
    DBSystemLog DBSystemlog;

    public DataFacade() {
        DBSystemlog = new DBSystemLog();
    }

    public static Idata getInstance() {
        return data;
    }

    @Override
    public void injectLogic(Ilogic logic) {
        this.logic = logic;
    }

//
//    @Override
//    public ArrayList<Case> getCaseList() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//
//
//    @Override
//    public void addUserToDataBase(String firstName, String lastName, String password, String type, String email) {
//    }

    @Override
    public ArrayList getSystemLog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSystemLog(String user, String action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clearSystemLog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  

    }




    