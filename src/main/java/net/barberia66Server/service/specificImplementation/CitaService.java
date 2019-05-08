/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.service.specificImplementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import net.barberia66Server.bean.publicInterface.BeanInterface;
import net.barberia66Server.bean.specificImplementation.CitaBean;
import net.barberia66Server.bean.specificImplementation.ReplyBean;
import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.constants.ConnectionConstants;
import net.barberia66Server.dao.publicInterface.DaoInterface;
import net.barberia66Server.dao.specificImplementation.CitaDao;
import net.barberia66Server.factory.BeanFactory;
import net.barberia66Server.factory.ConnectionFactory;
import net.barberia66Server.factory.DaoFactory;
import static net.barberia66Server.helper.Validator.validateDates;
import net.barberia66Server.service.genericImplementation.GenericServiceImplementation;
import net.barberia66Server.service.publicInterface.ServiceInterface;

/**
 *
 * @author a073597589g
 */
public class CitaService extends GenericServiceImplementation implements ServiceInterface {

    public CitaService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }

    @Override
    public ReplyBean create() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            CitaBean citaBean = (CitaBean) BeanFactory.getBeanFromJson(ob, oGson, strJsonFromClient);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            CitaDao citaDao = new CitaDao(oConnection, ob);
            LocalDateTime fecha_inicio = citaBean.getFecha_inicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime fecha_fin = citaBean.getFecha_fin().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (validateDates(fecha_inicio, fecha_fin) && fecha_inicio.getDayOfYear() == fecha_fin.getDayOfYear()) {
                if (citaDao.comprobarCitas(citaBean.getId_usuario(), fecha_inicio.plusSeconds(1), fecha_fin.minusSeconds(1), 1)) {
                    citaDao.create(citaBean);
                    ArrayList<BeanInterface> alBean = citaDao.getpage("resourceTimeGridDay", false, citaBean.getFecha_inicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 1);
                    oReplyBean = new ReplyBean(200, oGson.toJson(alBean));
                } else {
                    oReplyBean = new ReplyBean(400, "Ya existe una cita para esa fecha");
                }
            } else {
                oReplyBean = new ReplyBean(400, "La fecha de inicio o la fecha de finalización tienen valores no válidos");
            }
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: create method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
    
    @Override
    public ReplyBean update() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strJsonFromClient = oRequest.getParameter("json");
            String modo = oRequest.getParameter("modo");
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            CitaBean citaBean = (CitaBean) BeanFactory.getBeanFromJson(ob, oGson, strJsonFromClient);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            CitaDao citaDao = new CitaDao(oConnection, ob);
            citaDao.update(citaBean);
            ArrayList<BeanInterface> alBean = citaDao.getpage(modo, false, citaBean.getFecha_inicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 1);
            oReplyBean = new ReplyBean(200, oGson.toJson(alBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
    
    @Override
    public ReplyBean getpage() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String modo = oRequest.getParameter("modo");
            boolean canceladas = false;
            if(oRequest.getParameter("estadocitas") != null){
                canceladas = oRequest.getParameter("estadocitas").equals("canceladas");
            }
            LocalDate fecha = (new SimpleDateFormat("yyyy-MM-dd").parse(oRequest.getParameter("fecha"))).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            CitaDao citaDao = (CitaDao) DaoFactory.getDao(oConnection, ob);
            ArrayList<BeanInterface> alBean = citaDao.getpage(modo, canceladas, fecha, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
    
    public ReplyBean getresources() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            CitaDao oCitaDao = (CitaDao) DaoFactory.getDao(oConnection, ob);
            ArrayList<BeanInterface> alBean = oCitaDao.getresources();
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

}
