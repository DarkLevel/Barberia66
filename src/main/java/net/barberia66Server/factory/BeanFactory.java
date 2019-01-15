/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.factory;

import com.google.gson.Gson;
import net.barberia66Server.bean.publicInterface.BeanInterface;
import net.barberia66Server.bean.specificImplementation.CitaBean;
import net.barberia66Server.bean.specificImplementation.EstadoCitaBean;
import net.barberia66Server.bean.specificImplementation.LineaBean;
import net.barberia66Server.bean.specificImplementation.ProductoBean;
import net.barberia66Server.bean.specificImplementation.RegistroBean;
import net.barberia66Server.bean.specificImplementation.TipoCitaBean;
import net.barberia66Server.bean.specificImplementation.TipoProductoBean;
import net.barberia66Server.bean.specificImplementation.TipoRegistroBean;
import net.barberia66Server.bean.specificImplementation.TipoUsuarioBean;
import net.barberia66Server.bean.specificImplementation.UsuarioBean;

/**
 *
 * @author a073597589g
 */
public class BeanFactory {

    public static BeanInterface getBean(String ob) {
        BeanInterface oBean = null;
        switch (ob) {
            case "cita":
                oBean = new CitaBean();
                break;
            case "estadocita":
                oBean = new EstadoCitaBean();
                break;
            case "linea":
                oBean = new LineaBean();
                break;
            case "producto":
                oBean = new ProductoBean();
                break;
            case "registro":
                oBean = new RegistroBean();
                break;
            case "tipocita":
                oBean = new TipoCitaBean();
                break;
            case "tipoproducto":
                oBean = new TipoProductoBean();
                break;
            case "tiporegistro":
                oBean = new TipoRegistroBean();
                break;
            case "tipousuario":
                oBean = new TipoUsuarioBean();
                break;
            case "usuario":
                oBean = new UsuarioBean();
                break;
        }
        return oBean;
    }

    public static BeanInterface getBeanFromJson(String ob, Gson oGson, String strJsonFromClient) {
        BeanInterface oBean = null;
        switch (ob) {
            case "cita":
                oBean = oGson.fromJson(strJsonFromClient, CitaBean.class);
                break;
            case "estadocita":
                oBean = oGson.fromJson(strJsonFromClient, EstadoCitaBean.class);
                break;
            case "linea":
                oBean = oGson.fromJson(strJsonFromClient, LineaBean.class);
                break;
            case "producto":
                oBean = oGson.fromJson(strJsonFromClient, ProductoBean.class);
                break;
            case "registro":
                oBean = oGson.fromJson(strJsonFromClient, RegistroBean.class);
                break;
            case "tipocita":
                oBean = oGson.fromJson(strJsonFromClient, TipoCitaBean.class);
                break;
            case "tipoproducto":
                oBean = oGson.fromJson(strJsonFromClient, TipoProductoBean.class);
                break;
            case "tiporegistro":
                oBean = oGson.fromJson(strJsonFromClient, TipoRegistroBean.class);
                break;
            case "tipousuario":
                oBean = oGson.fromJson(strJsonFromClient, TipoUsuarioBean.class);
                break;
            case "usuario":
                oBean = oGson.fromJson(strJsonFromClient, UsuarioBean.class);
                break;
        }
        return oBean;
    }
}
