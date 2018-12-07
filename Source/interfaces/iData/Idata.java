package interfaces.iData;

import interfaces.iLogic.Ilogic;
import interfaces.all.ISystemLog;
import interfaces.all.iAuthenticate;

/**
 *
 * @author mehgn
 */
public interface Idata extends ISystemLog, iAuthenticate{
    public void injectLogic(Ilogic logic);

}
