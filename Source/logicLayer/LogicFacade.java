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
import Interfaces.iData.IData;
import interfaces.all.ILogic.Ilogic;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mehgn
 */
public class LogicFacade implements Ilogic {

    private static IData data;
    private static Ilogic logic;



    public LogicFacade() {
    }

    public static Ilogic getInstance() {
        return logic;
    }

    @Override
    public void injectData(IData data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean passwordCheck(String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCaseToList(Case aCase) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addUserToDataBase(String firstName, String lastName, String password, String type, String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
