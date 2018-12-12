package interfaces.iData;

import Interfaces.all.iAuthenticate;
import interfaces.all.ILogic.Ilogic;
import logicLayer.ISystemLog;


/**
 *
 * @author mehgn
 */
public interface Idata extends ISystemLog, iAuthenticate{
    public void injectLogic(Ilogic logic);
}