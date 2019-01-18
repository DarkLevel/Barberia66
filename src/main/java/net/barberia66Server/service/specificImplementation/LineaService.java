/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.service.specificImplementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import net.barberia66Server.bean.specificImplementation.LineaBean;
import net.barberia66Server.bean.specificImplementation.RegistroBean;
import net.barberia66Server.bean.specificImplementation.ReplyBean;
import net.barberia66Server.bean.specificImplementation.UsuarioBean;
import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.constants.ConnectionConstants;
import net.barberia66Server.dao.specificImplementation.LineaDao;
import net.barberia66Server.dao.specificImplementation.RegistroDao;
import net.barberia66Server.factory.ConnectionFactory;
import net.barberia66Server.helper.EncodingHelper;
import net.barberia66Server.helper.ParameterCook;
import net.barberia66Server.service.genericImplementation.GenericServiceImplementation;
import net.barberia66Server.service.publicInterface.ServiceInterface;

/**
 *
 * @author a073597589g
 */
public class LineaService extends GenericServiceImplementation implements ServiceInterface {
    
    public LineaService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }
    
    public ReplyBean getcountX() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_registro = Integer.parseInt(oRequest.getParameter("id_fact"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            LineaDao oLineaDao = new LineaDao(oConnection, ob);
            int registros = oLineaDao.getcountX(id_registro);
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
            Integer id_registro = Integer.parseInt(oRequest.getParameter("id_fact"));
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            LineaDao oLineaDao = new LineaDao(oConnection, ob);
            ArrayList<LineaBean> alLineaBean = oLineaDao.getpageX(id_registro, iRpp, iPage, hmOrder, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alLineaBean));
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
    
    public ReplyBean getcountlineauser() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_registro = Integer.parseInt(oRequest.getParameter("id_fact"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            RegistroDao oRegistroDao = new RegistroDao(oConnection, "registro");
            RegistroBean oRegistroBean = (RegistroBean) oRegistroDao.get(id_registro, 0);
            if (oRegistroBean.getId_usuario() == ((UsuarioBean) oRequest.getSession().getAttribute("user")).getId()) {
                LineaDao oLineaDao = new LineaDao(oConnection, ob);
                int registros = oLineaDao.getcountX(id_registro);
                oReplyBean = new ReplyBean(200, oGson.toJson(registros));
            } else {
                oReplyBean = new ReplyBean(401, "Unauthorized");
            }
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean getpagelineauser() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id_registro = Integer.parseInt(oRequest.getParameter("id_fact"));
            Integer iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
            Integer iPage = Integer.parseInt(oRequest.getParameter("page"));
            HashMap<String, String> hmOrder = ParameterCook.getOrderParams(oRequest.getParameter("order"));
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            RegistroDao oRegistroDao = new RegistroDao(oConnection, "registro");
            RegistroBean oRegistroBean = (RegistroBean) oRegistroDao.get(id_registro, 0);
            if (oRegistroBean.getId_usuario() == ((UsuarioBean) oRequest.getSession().getAttribute("user")).getId()) {
                LineaDao oLineaDao = new LineaDao(oConnection, ob);
                ArrayList<LineaBean> alLineaBean = oLineaDao.getpageX(id_registro, iRpp, iPage, hmOrder, 1);
                oReplyBean = new ReplyBean(200, oGson.toJson(alLineaBean));
            } else {
                oReplyBean = new ReplyBean(401, "Unauthorized");
            }
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    
}
