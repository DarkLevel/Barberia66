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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import net.barberia66Server.bean.publicInterface.BeanInterface;
import net.barberia66Server.bean.specificImplementation.CitaBean;
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
        strSQL += " WHERE id_usuario=" + id_usuario + " AND (fecha_inicio BETWEEN \"" + fecha_inicio + "\" AND \"" + fecha_fin + "\" OR fecha_fin BETWEEN \"" + fecha_inicio + "\" AND \"" + fecha_fin + "\")";
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            alBean = new ArrayList<>();
            while (oResultSet.next()) {
                CitaBean citaBean = (CitaBean) BeanFactory.getBean(ob);
                citaBean.fill(oResultSet, oConnection, expand);
                alBean.add(citaBean);
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

    public ArrayList<BeanInterface> getpage(String modo, boolean canceladas, LocalDate fecha, Integer expand) throws Exception {
        String strSQL = "SELECT * FROM " + ob + " WHERE id_estadocita";
        strSQL += canceladas ? " = 3 " : " != 3 ";
        switch (modo) {
            case "resourceTimeGridDay":
                strSQL += "AND fecha_inicio BETWEEN \"" + fecha + " 00:00:00\" AND \"" + fecha + " 23:59:59\"";
                break;
            case "resourceTimeGridWeek":
                strSQL += "AND week(fecha_inicio, 1) = " + fecha.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()) + " AND YEAR(fecha_inicio) = " + fecha.getYear();
                break;
            case "dayGridMonth":
                strSQL += "AND MONTH(fecha_inicio) = " + fecha.getMonthValue() + " AND YEAR(fecha_inicio) = " + fecha.getYear();
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

    public ArrayList<BeanInterface> getresources() throws Exception {
        String strSQL = "SELECT * FROM usuario WHERE id_tipousuario > 1";
        ArrayList<BeanInterface> alBean;
        ResultSet oResultSet = null;
        PreparedStatement oPreparedStatement = null;
        try {
            oPreparedStatement = oConnection.prepareStatement(strSQL);
            oResultSet = oPreparedStatement.executeQuery();
            alBean = new ArrayList<>();
            while (oResultSet.next()) {
                BeanInterface oBean = BeanFactory.getBean("usuario");
                oBean.fill(oResultSet, oConnection, 0);
                alBean.add(oBean);
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
        return alBean;
    }

}
