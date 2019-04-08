/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.service.specificImplementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.servlet.http.HttpServletRequest;
import net.barberia66Server.bean.specificImplementation.CitaBean;
import net.barberia66Server.bean.specificImplementation.ReplyBean;
import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.constants.ConnectionConstants;
import net.barberia66Server.dao.specificImplementation.CitaDao;
import net.barberia66Server.factory.BeanFactory;
import net.barberia66Server.factory.ConnectionFactory;
import net.barberia66Server.helper.Validator;
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
            if (Validator.validateDates(fecha_inicio, fecha_fin) && fecha_inicio.getDayOfYear() == fecha_fin.getDayOfYear()) {
                if (citaDao.comprobarCitas(citaBean.getId_usuario(), fecha_inicio, fecha_fin, 1)) {
                    citaBean = (CitaBean) citaDao.create(citaBean);
                    oReplyBean = new ReplyBean(200, oGson.toJson(citaBean));
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
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            CitaBean citaBean = (CitaBean) BeanFactory.getBeanFromJson(ob, oGson, strJsonFromClient);
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            CitaDao citaDao = new CitaDao(oConnection, ob);
            LocalDateTime fecha_inicio = citaBean.getFecha_inicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime fecha_fin = citaBean.getFecha_fin().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (Validator.validateDates(fecha_inicio, fecha_fin) && fecha_inicio.getDayOfYear() == fecha_fin.getDayOfYear()) {
                if (citaDao.comprobarCitas(citaBean.getId_usuario(), fecha_inicio, fecha_fin, 1)) {
                    int iRes = citaDao.update(citaBean);
                    oReplyBean = new ReplyBean(200, Integer.toString(iRes));
                } else {
                    oReplyBean = new ReplyBean(400, "Ya existe una cita para esa fecha");
                }
            } else {
                oReplyBean = new ReplyBean(400, "La fecha de inicio o la fecha de finalización tienen valores no válidos");
            }
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

}
