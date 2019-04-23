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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import net.barberia66Server.bean.publicInterface.BeanInterface;
import net.barberia66Server.dao.genericImplementation.GenericDaoImplementation;
import net.barberia66Server.dao.publicInterface.DaoInterface;
import net.barberia66Server.factory.BeanFactory;

/**
 *
 * @author a073597589g
 */
public class CitaDao extends GenericDaoImplementation implements DaoInterface {

    public CitaDao(Connection oConnection, String ob) {
        super(oConnection, ob);
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

    public ArrayList<BeanInterface> getpage(String modo, Integer id_estadocitas, LocalDateTime fecha, Integer expand) throws Exception {
        String strSQL;
        if (modo.equals("resourceTimeGridWeek")) {
            strSQL = "SET datefirst 1; SELECT * FROM " + ob;
        } else {
            strSQL = "SELECT * FROM " + ob;
        }
        if (id_estadocitas != null) {
            strSQL += " WHERE id_estadocita=" + id_estadocitas + " AND ";
        } else {
            strSQL += " WHERE ";
        }
        switch (modo) {
            case "resourceTimeGridDay":
                strSQL += "DATEPART(dayofyear, fecha_inicio) = DATEPART(dayofyear, " + fecha + ") AND YEAR(fecha_inicio) = " + fecha.getYear();
                break;
            case "resourceTimeGridWeek":
                strSQL += "DATEPART(week, fecha_inicio) = " + Integer.parseInt(new SimpleDateFormat("w").format(fecha)) + " AND YEAR(fecha_inicio) = " + fecha.getYear();
                break;
            case "dayGridMonth":
                strSQL += "MONTH(fecha_inicio) = " + fecha.getMonthValue() + " AND YEAR(fecha_inicio) = " + fecha.getYear();
                break;
        }
        ArrayList<BeanInterface> alBean;
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
        return alBean;
    }

}
