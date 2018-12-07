/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.iLogic;

import interfaces.all.IFileChooser;
import interfaces.all.ISystemLog;
import interfaces.all.iAuthenticate;
import interfaces.iData.Idata;

/**
 *
 * @author mehgn
 */
public interface Ilogic extends iAuthenticate, ISystemLog, IFileChooser {

    public void injectData(Idata data);
    public Boolean passwordCheck(String password);

}
