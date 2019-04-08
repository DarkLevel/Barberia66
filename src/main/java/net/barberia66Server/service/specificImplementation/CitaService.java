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
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.barberia66Server.bean.specificImplementation.CitaBean;
import net.barberia66Server.bean.specificImplementation.ReplyBean;
import net.barberia66Server.bean.specificImplementation.UsuarioBean;
import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.constants.ConnectionConstants;
import net.barberia66Server.dao.specificImplementation.CitaDao;
import net.barberia66Server.factory.BeanFactory;
import net.barberia66Server.factory.ConnectionFactory;
import net.barberia66Server.factory.DaoFactory;
import net.barberia66Server.helper.EncodingHelper;
import net.barberia66Server.helper.ParameterCook;
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
            if (Validator.validateDates(fecha_inicio, fecha_fin)) {
                if (citaDao.comprobarCitas(citaBean.getId_usuario(), fecha_inicio, fecha_fin, 1)) {
                    citaBean = (CitaBean) citaDao.create(citaBean);
                    oReplyBean = new ReplyBean(200, oGson.toJson(citaBean));
                } else {
                    oReplyBean = new ReplyBean(400, "Ya existe una cita para esa fecha");
                }
            } else {
                oReplyBean = new ReplyBean(400, "La fecha de inicio tiene que ser anterior a la fecha de finalización");
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
            if (Validator.validateDates(fecha_inicio, fecha_fin)) {
                if (citaDao.comprobarCitas(citaBean.getId_usuario(), fecha_inicio, fecha_fin, 1)) {
                    int iRes = citaDao.update(citaBean);
                    oReplyBean = new ReplyBean(200, Integer.toString(iRes));
                } else {
                    oReplyBean = new ReplyBean(400, "Ya existe una cita para esa fecha");
                }
            } else {
                oReplyBean = new ReplyBean(400, "La fecha de inicio tiene que ser anterior a la fecha de finalización");
            }
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean getcountX() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_user = Integer.parseInt(oRequest.getParameter("id_user"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            CitaDao oCitaDao = new CitaDao(oConnection, ob);
            int registros = oCitaDao.getcountX(id_user);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean getpageX() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_usuario = Integer.parseInt(oRequest.getParameter("id_user"));
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            CitaDao oCitaDao = new CitaDao(oConnection, ob);
            ArrayList<CitaBean> alCitaBean = oCitaDao.getpageX(id_usuario, iRpp, iPage, hmOrder, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alCitaBean));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;
    }

    public ReplyBean getcountcitauser() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_user = ((UsuarioBean) oRequest.getSession().getAttribute("user")).getId();
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            CitaDao oCitaDao = new CitaDao(oConnection, ob);
            int registros = oCitaDao.getcountX(id_user);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(registros));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean getpagecitauser() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_usuario = ((UsuarioBean) oRequest.getSession().getAttribute("user")).getId();
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            CitaDao oCitaDao = new CitaDao(oConnection, "cita");
            ArrayList<CitaBean> alCitaBean = oCitaDao.getpageX(id_usuario, iRpp, iPage, hmOrder, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alCitaBean));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }

        return oReplyBean;
    }

}
