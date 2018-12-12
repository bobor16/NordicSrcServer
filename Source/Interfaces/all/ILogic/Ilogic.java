/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.all.ILogic;
import interfaces.iData.Idata;
import java.util.List;
import logicLayer.Case;

public interface Ilogic {
    
    public void injectData(Idata data);
    public Boolean passwordCheck(String password);
    public void addCaseToList(Case aCase);
    public void addUserToDataBase(String firstName, String lastName, String password, String type, String email);
}

