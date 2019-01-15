/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.factory;

import java.sql.Connection;
import net.barberia66Server.dao.publicInterface.DaoInterface;
import net.barberia66Server.dao.specificImplementation.CitaDao;
import net.barberia66Server.dao.specificImplementation.EstadoCitaDao;
import net.barberia66Server.dao.specificImplementation.LineaDao;
import net.barberia66Server.dao.specificImplementation.ProductoDao;
import net.barberia66Server.dao.specificImplementation.RegistroDao;
import net.barberia66Server.dao.specificImplementation.TipoCitaDao;
import net.barberia66Server.dao.specificImplementation.TipoProductoDao;
import net.barberia66Server.dao.specificImplementation.TipoRegistroDao;
import net.barberia66Server.dao.specificImplementation.TipoUsuarioDao;
import net.barberia66Server.dao.specificImplementation.UsuarioDao;

/**
 *
 * @author a073597589g
 */
public class DaoFactory {

    public static DaoInterface getDao(Connection oConnection, String ob) {
        DaoInterface oDao = null;
        switch (ob) {
            case "cita":
                oDao = new CitaDao(oConnection, ob);
                break;
            case "estadocita":
                oDao = new EstadoCitaDao(oConnection, ob);
                break;
            case "linea":
                oDao = new LineaDao(oConnection, ob);
                break;
            case "producto":
                oDao = new ProductoDao(oConnection, ob);
                break;
            case "registro":
                oDao = new RegistroDao(oConnection, ob);
                break;
            case "tipocita":
                oDao = new TipoCitaDao(oConnection, ob);
                break;
            case "tipoproducto":
                oDao = new TipoProductoDao(oConnection, ob);
                break;
            case "tiporegistro":
                oDao = new TipoRegistroDao(oConnection, ob);
                break;
            case "tipousuario":
                oDao = new TipoUsuarioDao(oConnection, ob);
                break;
            case "usuario":
                oDao = new UsuarioDao(oConnection, ob);
                break;
        }
        return oDao;
    }
}
