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
import Interfaces.iData.IData;
import interfaces.all.ILogic.Ilogic;
import logicLayer.SystemLog;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mehgn
 */
public class DataFacade implements IData {

    private static IData data;
    private static Ilogic logic;
    DBSystemLog DBSystemlog;

    public DataFacade() {
        DBSystemlog = new DBSystemLog();
    }

    public static IData getInstance() {
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

    }




    