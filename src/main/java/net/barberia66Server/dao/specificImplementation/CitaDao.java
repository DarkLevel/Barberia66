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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import net.barberia66Server.bean.publicInterface.BeanInterface;
import net.barberia66Server.bean.specificImplementation.CitaBean;
import net.barberia66Server.dao.genericImplementation.GenericDaoImplementation;
import net.barberia66Server.dao.publicInterface.DaoInterface;
import net.barberia66Server.factory.BeanFactory;
import net.barberia66Server.helper.SqlBuilder;

/**
 *
 * @author a073597589g
 */
public class CitaDao extends GenericDaoImplementation implements DaoInterface {

    public CitaDao(Connection oConnection, String ob) {
        super(oConnection, ob);
    }

    public int getcountX(int id_user) throws Exception {
        String strSQL = "SELECT COUNT(id) FROM " + ob + " WHERE id_usuario=?";
        int res = 0;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oPreparedStatement.setInt(1, id_user);
            oResultSet = oPreparedStatement.executeQuery();
            if (oResultSet.next()) {
                res = oResultSet.getInt(1);
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
        return res;
    }

    public ArrayList<CitaBean> getpageX(int id_user, int iRpp, int iPage, HashMap<String, String> hmOrder, Integer expand) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id_usuario=?";
        strSQL += SqlBuilder.buildSqlOrder(hmOrder);
        ArrayList<CitaBean> alCitaBean;
        if (iRpp > 0 && iRpp < 100000 && iPage > 0 && iPage < 100000000) {
            strSQL += " LIMIT " + (iPage - 1) * iRpp + ", " + iRpp;
            ResultSet oResultSet = null;
            PreparedStatement oPreparedStatement = null;
            try {
                oPreparedStatement = oConnection.prepareStatement(strSQL);
                oPreparedStatement.setInt(1, id_user);
                oResultSet = oPreparedStatement.executeQuery();
                alCitaBean = new ArrayList<>();
                while (oResultSet.next()) {
                    CitaBean oCitaBean = new CitaBean();
                    oCitaBean.fill(oResultSet, oConnection, expand);
                    alCitaBean.add(oCitaBean);
                }
            } catch (SQLException e) {
                throw new Exception("Error en Dao getpage de " + ob, e);
            } finally {
                if (oResultSet != null) {
                    oResultSet.close();
                }
                if (oPreparedStatement != null) {
                    oPreparedStatement.close();
                }
            }
        } else {
            throw new Exception("Error en Dao getpage de " + ob);
        }
        return alCitaBean;
    }

    public boolean comprobarCitas(int id_usuario, LocalDateTime fecha_inicio, LocalDateTime fecha_fin, Integer expand) throws Exception {
        String strSQL = "SELECT * FROM " + ob;
        ArrayList<BeanInterface> alBean;
        strSQL += " WHERE id_usuario=" + id_usuario + " AND fecha_inicio BETWEEN '" + fecha_inicio + "' AND '" + fecha_fin + "' OR fecha_fin BETWEEN '" + fecha_inicio + "' AND '" + fecha_fin;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            alBean = new ArrayList<>();
            while (oResultSet.next()) {
                BeanInterface oBean = BeanFactory.getBean(ob);
                oBean.fill(oResultSet, oConnection, expand);
                alBean.add(oBean);
            }
        } catch (SQLException e) {
            throw new Exception("Error en Dao comprobarCitas de " + ob, e);
        } finally {
            if (oResultSet != null) {
                oResultSet.close();
            }
            if (oPreparedStatement != null) {
                oPreparedStatement.close();
            }
        }
        return alBean.isEmpty();
    }

}
