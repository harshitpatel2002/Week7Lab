/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.RoleDB;
import java.sql.SQLException;
import models.Role;

/**
 *
 * @author hsp28
 */
public class RoleService
{
//    Getting the roles
    public int getRoleId(String name) throws SQLException
    {
        RoleDB rdb = new RoleDB();
        return rdb.getID(name);
    }
    
    public String getRoleName(int id) throws SQLException
    {
        RoleDB rdb = new RoleDB();
        return rdb.getName(id);
    }
}
