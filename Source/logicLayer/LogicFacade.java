/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicLayer;

/**
 *
 * @author mehgn
 */
import interfaces.iLogic.Ilogic;
import interfaces.iData.Idata;
import java.util.ArrayList;

/**
 *
 * @author mehgn
 */
public class LogicFacade implements interfaces.iLogic.Ilogic {

    private static Idata data;
    private static Ilogic logic;

    private PasswordValidation passwordChecker = new PasswordValidation();

    @Override
    public void injectData(Idata data) {
        this.data = data;
    }

    public LogicFacade() {
    }

    public static Ilogic getInstance() {
        return logic;
    }

    @Override
    public ArrayList getSystemLog() {
        return data.getSystemLog();
    }

    @Override
    public void setSystemLog(String user, String action) {
        data.setSystemLog(user, action);
    }

    @Override
    public void clearSystemLog() {
        data.clearSystemLog();
    }

    @Override
    public Boolean passwordCheck(String password) {
        return passwordChecker.checkPassword(password);
    }

    @Override
    public String login(String lol){return "";}
}
