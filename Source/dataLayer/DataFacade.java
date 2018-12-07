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
import interfaces.iLogic.Ilogic;
import logicLayer.SystemLog;
import interfaces.iData.Idata;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ArrayList getSystemLog() {
        return DBSystemlog.getSystemLog();
    }

    @Override
    public void setSystemLog(String user, String action) {
        DBSystemlog.setSystemLog(user, action);
    }

    @Override
    public void clearSystemLog() {
        DBSystemlog.clearSystemLog();
    }

    @Override
    public String login(String query){return "";}
}

