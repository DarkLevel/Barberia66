/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.dao.specificImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.barberia66Server.bean.specificImplementation.UsuarioBean;
import net.barberia66Server.dao.genericImplementation.GenericDaoImplementation;
import net.barberia66Server.dao.publicInterface.DaoInterface;

/**
 *
 * @author a073597589g
 */
public class UsuarioDao extends GenericDaoImplementation implements DaoInterface {
    
    public UsuarioDao(Connection oConnection, String ob) {
        super(oConnection, ob);
    }
    
    public UsuarioBean login(String strUserName, String strPassword) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE username=? AND password=?";
        UsuarioBean oUsuarioBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setString(1, strUserName);
            oPreparedStatement.setString(2, strPassword);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                oUsuarioBean = new UsuarioBean();
                oUsuarioBean.fill(oResultSet, oConnection, 1);
            } else {
                oUsuarioBean = null;
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao get de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return oUsuarioBean;
    }
    
    public int updatepass(UsuarioBean oUsuarioBean) throws Exception {
        int iResult = 0;
        String strSQL = "UPDATE " + ob + " SET ";
        strSQL += oUsuarioBean.getPairsPassword();
        try (PreparedStatement oPreparedStatement = oConnection.prepareStatement(strSQL)) {
            iResult = oPreparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error en Dao update de " + ob, e);
        }
        return iResult;
    }
    
}
