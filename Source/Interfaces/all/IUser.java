/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces.all;

import java.util.List;
import logicLayer.User;

/**
 *
 * @author mehgn
 */
public interface IUser {
     public void addUserToDataBase(String firstName, String lastName, String password, String type, String email);
   public List<User> displayUsers();
}

