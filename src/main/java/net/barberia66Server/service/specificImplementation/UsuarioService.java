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
import net.barberia66Server.bean.publicInterface.BeanInterface;
import net.barberia66Server.bean.specificImplementation.ReplyBean;
import net.barberia66Server.bean.specificImplementation.UsuarioBean;
import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.constants.ConnectionConstants;
import net.barberia66Server.dao.publicInterface.DaoInterface;
import net.barberia66Server.dao.specificImplementation.UsuarioDao;
import net.barberia66Server.factory.BeanFactory;
import net.barberia66Server.factory.ConnectionFactory;
import net.barberia66Server.factory.DaoFactory;
import net.barberia66Server.helper.EncodingHelper;
import net.barberia66Server.helper.ParameterCook;
import net.barberia66Server.service.genericImplementation.GenericServiceImplementation;
import net.barberia66Server.service.publicInterface.ServiceInterface;

/**
 *
 * @author a073597589g
 */
public class UsuarioService extends GenericServiceImplementation implements ServiceInterface {
    
    public UsuarioService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }
    
    public ReplyBean login() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            String strLogin = oRequest.getParameter("user").toLowerCase();
            String strPassword = oRequest.getParameter("pass");
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
            UsuarioBean oUsuarioBean = oUsuarioDao.login(strLogin, strPassword);
            if (oUsuarioBean != null) {
                oRequest.getSession().setAttribute("user", oUsuarioBean);
                Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
            } else {
                oReplyBean = new ReplyBean(401, "Bad Authentication");
            }
        } catch (Exception ex) {
            oReplyBean = new ReplyBean(500,
                    "ERROR: " + EncodingHelper.escapeQuotes(EncodingHelper.escapeLine(ex.getMessage())));
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean logout() throws Exception {
        oRequest.getSession().invalidate();
        return new ReplyBean(200, EncodingHelper.quotate("OK"));
    }

    public ReplyBean check() throws Exception {
        ReplyBean oReplyBean;
        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        if (oUsuarioBean != null) {
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oUsuarioBean));
        } else {
            oReplyBean = new ReplyBean(401, "No active session");
        }
        return oReplyBean;
    }
    
    public ReplyBean getpageall() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioDao usuarioDao = (UsuarioDao) DaoFactory.getDao(oConnection, ob);
            ArrayList<BeanInterface> alBean = usuarioDao.getpageall();
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(alBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get page: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean changepassword() throws Exception {
        ReplyBean oReplyBean = null;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            UsuarioBean oLoggedUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
            if (oLoggedUsuarioBean != null) {
                String actualPass = oLoggedUsuarioBean.getPassword();
                String oldPass = oRequest.getParameter("oldpass");
                if (actualPass.equals(oldPass)) {
                    String newPass = oRequest.getParameter("newpass");
                    if (newPass.length() == 64) {
                        UsuarioBean oUsuarioBean = new UsuarioBean();
                        oUsuarioBean.setId(oLoggedUsuarioBean.getId());
                        oUsuarioBean.setPassword(newPass);
                        UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, ob);
                        int iRes = oUsuarioDao.updatepass(oUsuarioBean);
                        oReplyBean = new ReplyBean(200, Integer.toString(iRes));
                    } else {
                        oReplyBean = new ReplyBean(400, "Unencrypted password");
                    }
                } else {
                    oReplyBean = new ReplyBean(401, "Bad Authentication");
                }
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
    
    public ReplyBean getprofile() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            Integer id = ((UsuarioBean) oRequest.getSession().getAttribute("user")).getId();
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            DaoInterface oDao = DaoFactory.getDao(oConnection, ob);
            BeanInterface oBean = oDao.get(id, 1);
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            oReplyBean = new ReplyBean(200, oGson.toJson(oBean));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: get method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean updateprofile() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = null;
        Connection oConnection;
        try {
            UsuarioBean oLoggedUser = (UsuarioBean) oRequest.getSession().getAttribute("user");
            String strJsonFromClient = oRequest.getParameter("json");
            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
            UsuarioBean oBean = (UsuarioBean) BeanFactory.getBeanFromJson(ob, oGson, strJsonFromClient);
            oBean.setId(oLoggedUser.getId());
            oBean.setPassword(null);
            oBean.setId_tipousuario(oLoggedUser.getObj_tipousuario().getId());
            oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            oConnection = oConnectionPool.newConnection();
            DaoInterface oDao = DaoFactory.getDao(oConnection, ob);
            int iRes = oDao.update(oBean);
            oReplyBean = new ReplyBean(200, Integer.toString(iRes));
        } catch (Exception ex) {
            throw new Exception("ERROR: Service level: update method: " + ob + " object", ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }
    
}
