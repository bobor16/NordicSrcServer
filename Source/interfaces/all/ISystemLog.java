/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.all;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mehgn
 */
public interface ISystemLog {
    public ArrayList getSystemLog();
    public void setSystemLog(String user, String action);
    public void clearSystemLog();
    
    
    
}
