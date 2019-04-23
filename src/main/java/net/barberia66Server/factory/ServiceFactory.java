/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.factory;

import javax.servlet.http.HttpServletRequest;
import net.barberia66Server.bean.specificImplementation.ReplyBean;
import net.barberia66Server.bean.specificImplementation.UsuarioBean;
import net.barberia66Server.service.specificImplementation.ComercioService;
import net.barberia66Server.service.specificImplementation.CitaService;
import net.barberia66Server.service.specificImplementation.EstadoCitaService;
import net.barberia66Server.service.specificImplementation.LineaService;
import net.barberia66Server.service.specificImplementation.ProductoService;
import net.barberia66Server.service.specificImplementation.RegistroService;
import net.barberia66Server.service.specificImplementation.TipoProductoService;
import net.barberia66Server.service.specificImplementation.TipoRegistroService;
import net.barberia66Server.service.specificImplementation.TipoUsuarioService;
import net.barberia66Server.service.specificImplementation.UsuarioService;

/**
 *
 * @author a073597589g
 */
public class ServiceFactory {

    public static ReplyBean executeService(HttpServletRequest oRequest) throws Exception {

        String ob = oRequest.getParameter("ob");
        String op = oRequest.getParameter("op");
        ReplyBean oReplyBean;

        int id_tipousuario = 0;
        if (oRequest.getSession().getAttribute("user") != null) {
            UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
            id_tipousuario = oUsuarioBean.getObj_tipousuario().getId();
        }

        switch (id_tipousuario) {
            case 1:
                switch (ob) {
                    case "comercio":
                        ComercioService oComercioService = new ComercioService(oRequest);
                        switch (op) {
                            case "venta":
                                oReplyBean = oComercioService.venta();
                                break;
                            case "compra":
                                oReplyBean = oComercioService.compra();
                                break;
                            case "uso":
                                oReplyBean = oComercioService.uso();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "cita":
                        CitaService oCitaService = new CitaService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oCitaService.create();
                                break;
                            case "getpage":
                                oReplyBean = oCitaService.getpage();
                                break;
                            case "update":
                                oReplyBean = oCitaService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "estadocita":
                        EstadoCitaService oEstadoCitaService = new EstadoCitaService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oEstadoCitaService.create();
                                break;
                            case "get":
                                oReplyBean = oEstadoCitaService.get();
                                break;
                            case "getcount":
                                oReplyBean = oEstadoCitaService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oEstadoCitaService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oEstadoCitaService.remove();
                                break;
                            case "update":
                                oReplyBean = oEstadoCitaService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "linea":
                        LineaService oLineaService = new LineaService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oLineaService.create();
                                break;
                            case "get":
                                oReplyBean = oLineaService.get();
                                break;
                            case "getcount":
                                oReplyBean = oLineaService.getcount();
                                break;
                            case "getcountlineauser":
                                oReplyBean = oLineaService.getcountlineauser();
                                break;
                            case "getcountX":
                                oReplyBean = oLineaService.getcountX();
                                break;
                            case "getpage":
                                oReplyBean = oLineaService.getpage();
                                break;
                            case "getpagelineauser":
                                oReplyBean = oLineaService.getpagelineauser();
                                break;
                            case "getpageX":
                                oReplyBean = oLineaService.getpageX();
                                break;
                            case "remove":
                                oReplyBean = oLineaService.remove();
                                break;
                            case "update":
                                oReplyBean = oLineaService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "producto":
                        ProductoService oProductoService = new ProductoService(oRequest);
                        switch (op) {
                            case "addimage":
                                oReplyBean = oProductoService.addimage();
                                break;
                            case "create":
                                oReplyBean = oProductoService.create();
                                break;
                            case "get":
                                oReplyBean = oProductoService.get();
                                break;
                            case "getcount":
                                oReplyBean = oProductoService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oProductoService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oProductoService.remove();
                                break;
                            case "update":
                                oReplyBean = oProductoService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "registro":
                        RegistroService oRegistroService = new RegistroService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oRegistroService.create();
                                break;
                            case "get":
                                oReplyBean = oRegistroService.get();
                                break;
                            case "getcount":
                                oReplyBean = oRegistroService.getcount();
                                break;
                            case "getcountregistrouser":
                                oReplyBean = oRegistroService.getcountregistrouser();
                                break;
                            case "getcountX":
                                oReplyBean = oRegistroService.getcountX();
                                break;
                            case "getpage":
                                oReplyBean = oRegistroService.getpage();
                                break;
                            case "getpageregistrouser":
                                oReplyBean = oRegistroService.getpageregistrouser();
                                break;
                            case "getpageX":
                                oReplyBean = oRegistroService.getpageX();
                                break;
                            case "remove":
                                oReplyBean = oRegistroService.remove();
                                break;
                            case "update":
                                oReplyBean = oRegistroService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "tipocita":
                        CitaService oService = new CitaService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oService.create();
                                break;
                            case "get":
                                oReplyBean = oService.get();
                                break;
                            case "getcount":
                                oReplyBean = oService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oService.remove();
                                break;
                            case "update":
                                oReplyBean = oService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "tipoproducto":
                        TipoProductoService oTipoProductoService = new TipoProductoService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oTipoProductoService.create();
                                break;
                            case "get":
                                oReplyBean = oTipoProductoService.get();
                                break;
                            case "getcount":
                                oReplyBean = oTipoProductoService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oTipoProductoService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oTipoProductoService.remove();
                                break;
                            case "update":
                                oReplyBean = oTipoProductoService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "tiporegistro":
                        TipoRegistroService oTipoRegistroService = new TipoRegistroService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oTipoRegistroService.create();
                                break;
                            case "get":
                                oReplyBean = oTipoRegistroService.get();
                                break;
                            case "getcount":
                                oReplyBean = oTipoRegistroService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oTipoRegistroService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oTipoRegistroService.remove();
                                break;
                            case "update":
                                oReplyBean = oTipoRegistroService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "tipousuario":
                        TipoUsuarioService oTipoUsuarioService = new TipoUsuarioService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oTipoUsuarioService.create();
                                break;
                            case "get":
                                oReplyBean = oTipoUsuarioService.get();
                                break;
                            case "getcount":
                                oReplyBean = oTipoUsuarioService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oTipoUsuarioService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oTipoUsuarioService.remove();
                                break;
                            case "update":
                                oReplyBean = oTipoUsuarioService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "usuario":
                        UsuarioService oUsuarioService = new UsuarioService(oRequest);
                        switch (op) {
                            case "changepassword":
                                oReplyBean = oUsuarioService.changepassword();
                                break;
                            case "check":
                                oReplyBean = oUsuarioService.check();
                                break;
                            case "create":
                                oReplyBean = oUsuarioService.create();
                                break;
                            case "get":
                                oReplyBean = oUsuarioService.get();
                                break;
                            case "getcount":
                                oReplyBean = oUsuarioService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oUsuarioService.getpage();
                                break;
                            case "getpagetrabajadores":
                                oReplyBean = oUsuarioService.getpageall();
                                break;
                            case "getprofile":
                                oReplyBean = oUsuarioService.getprofile();
                                break;
                            case "logout":
                                oReplyBean = oUsuarioService.logout();
                                break;
                            case "remove":
                                oReplyBean = oUsuarioService.remove();
                                break;
                            case "update":
                                oReplyBean = oUsuarioService.update();
                                break;
                            case "updateprofile":
                                oReplyBean = oUsuarioService.updateprofile();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Object doesn't exist");
                        break;
                }
                break;
            case 2:
                switch (ob) {
                    case "comercio":
                        ComercioService oComercioService = new ComercioService(oRequest);
                        switch (op) {
                            case "venta":
                                oReplyBean = oComercioService.venta();
                                break;
                            case "compra":
                                oReplyBean = oComercioService.compra();
                                break;
                            case "uso":
                                oReplyBean = oComercioService.uso();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "cita":
                        CitaService oCitaService = new CitaService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oCitaService.create();
                                break;
                            case "get":
                                oReplyBean = oCitaService.get();
                                break;
                            case "getcount":
                                oReplyBean = oCitaService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oCitaService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oCitaService.remove();
                                break;
                            case "update":
                                oReplyBean = oCitaService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "estadocita":
                        EstadoCitaService oEstadoCitaService = new EstadoCitaService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oEstadoCitaService.create();
                                break;
                            case "get":
                                oReplyBean = oEstadoCitaService.get();
                                break;
                            case "getcount":
                                oReplyBean = oEstadoCitaService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oEstadoCitaService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oEstadoCitaService.remove();
                                break;
                            case "update":
                                oReplyBean = oEstadoCitaService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "producto":
                        ProductoService oProductoService = new ProductoService(oRequest);
                        switch (op) {
                            case "addimage":
                                oReplyBean = oProductoService.addimage();
                                break;
                            case "create":
                                oReplyBean = oProductoService.create();
                                break;
                            case "get":
                                oReplyBean = oProductoService.get();
                                break;
                            case "getcount":
                                oReplyBean = oProductoService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oProductoService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oProductoService.remove();
                                break;
                            case "update":
                                oReplyBean = oProductoService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "registro":
                        RegistroService oRegistroService = new RegistroService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oRegistroService.create();
                                break;
                            case "get":
                                oReplyBean = oRegistroService.get();
                                break;
                            case "getcount":
                                oReplyBean = oRegistroService.getcount();
                                break;
                            case "getcountregistrouser":
                                oReplyBean = oRegistroService.getcountregistrouser();
                                break;
                            case "getcountX":
                                oReplyBean = oRegistroService.getcountX();
                                break;
                            case "getpage":
                                oReplyBean = oRegistroService.getpage();
                                break;
                            case "getpageregistrouser":
                                oReplyBean = oRegistroService.getpageregistrouser();
                                break;
                            case "getpageX":
                                oReplyBean = oRegistroService.getpageX();
                                break;
                            case "remove":
                                oReplyBean = oRegistroService.remove();
                                break;
                            case "update":
                                oReplyBean = oRegistroService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "tipocita":
                        CitaService oService = new CitaService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oService.create();
                                break;
                            case "get":
                                oReplyBean = oService.get();
                                break;
                            case "getcount":
                                oReplyBean = oService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oService.remove();
                                break;
                            case "update":
                                oReplyBean = oService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "tipoproducto":
                        TipoProductoService oTipoProductoService = new TipoProductoService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oTipoProductoService.create();
                                break;
                            case "get":
                                oReplyBean = oTipoProductoService.get();
                                break;
                            case "getcount":
                                oReplyBean = oTipoProductoService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oTipoProductoService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oTipoProductoService.remove();
                                break;
                            case "update":
                                oReplyBean = oTipoProductoService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "usuario":
                        UsuarioService oUsuarioService = new UsuarioService(oRequest);
                        switch (op) {
                            case "changepassword":
                                oReplyBean = oUsuarioService.changepassword();
                                break;
                            case "check":
                                oReplyBean = oUsuarioService.check();
                                break;
                            case "create":
                                oReplyBean = oUsuarioService.create();
                                break;
                            case "get":
                                oReplyBean = oUsuarioService.get();
                                break;
                            case "getcount":
                                oReplyBean = oUsuarioService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oUsuarioService.getpage();
                                break;
                            case "getpagetrabajadores":
                                oReplyBean = oUsuarioService.getpageall();
                                break;
                            case "getprofile":
                                oReplyBean = oUsuarioService.getprofile();
                                break;
                            case "logout":
                                oReplyBean = oUsuarioService.logout();
                                break;
                            case "remove":
                                oReplyBean = oUsuarioService.remove();
                                break;
                            case "update":
                                oReplyBean = oUsuarioService.update();
                                break;
                            case "updateprofile":
                                oReplyBean = oUsuarioService.updateprofile();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Object doesn't exist");
                        break;
                }
                break;
            case 3:
                switch (ob) {
                    case "comercio":
                        ComercioService oComercioService = new ComercioService(oRequest);
                        switch (op) {
                            case "venta":
                                oReplyBean = oComercioService.venta();
                                break;
                            case "compra":
                                oReplyBean = oComercioService.compra();
                                break;
                            case "uso":
                                oReplyBean = oComercioService.uso();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "cita":
                        CitaService oCitaService = new CitaService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oCitaService.create();
                                break;
                            case "get":
                                oReplyBean = oCitaService.get();
                                break;
                            case "getcount":
                                oReplyBean = oCitaService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oCitaService.getpage();
                                break;
                            case "remove":
                                oReplyBean = oCitaService.remove();
                                break;
                            case "update":
                                oReplyBean = oCitaService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "estadocita":
                        EstadoCitaService oEstadoCitaService = new EstadoCitaService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oEstadoCitaService.get();
                                break;
                            case "getcount":
                                oReplyBean = oEstadoCitaService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oEstadoCitaService.getpage();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "producto":
                        ProductoService oProductoService = new ProductoService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oProductoService.get();
                                break;
                            case "getcount":
                                oReplyBean = oProductoService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oProductoService.getpage();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "registro":
                        RegistroService oRegistroService = new RegistroService(oRequest);
                        switch (op) {
                            case "create":
                                oReplyBean = oRegistroService.create();
                                break;
                            case "get":
                                oReplyBean = oRegistroService.get();
                                break;
                            case "getcount":
                                oReplyBean = oRegistroService.getcount();
                                break;
                            case "getcountregistrouser":
                                oReplyBean = oRegistroService.getcountregistrouser();
                                break;
                            case "getcountX":
                                oReplyBean = oRegistroService.getcountX();
                                break;
                            case "getpage":
                                oReplyBean = oRegistroService.getpage();
                                break;
                            case "getpageregistrouser":
                                oReplyBean = oRegistroService.getpageregistrouser();
                                break;
                            case "getpageX":
                                oReplyBean = oRegistroService.getpageX();
                                break;
                            case "remove":
                                oReplyBean = oRegistroService.remove();
                                break;
                            case "update":
                                oReplyBean = oRegistroService.update();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "tipocita":
                        CitaService oService = new CitaService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oService.get();
                                break;
                            case "getcount":
                                oReplyBean = oService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oService.getpage();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "tipoproducto":
                        TipoProductoService oTipoProductoService = new TipoProductoService(oRequest);
                        switch (op) {
                            case "get":
                                oReplyBean = oTipoProductoService.get();
                                break;
                            case "getcount":
                                oReplyBean = oTipoProductoService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oTipoProductoService.getpage();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    case "usuario":
                        UsuarioService oUsuarioService = new UsuarioService(oRequest);
                        switch (op) {
                            case "changepassword":
                                oReplyBean = oUsuarioService.changepassword();
                                break;
                            case "check":
                                oReplyBean = oUsuarioService.check();
                                break;
                            case "get":
                                oReplyBean = oUsuarioService.get();
                                break;
                            case "getcount":
                                oReplyBean = oUsuarioService.getcount();
                                break;
                            case "getpage":
                                oReplyBean = oUsuarioService.getpage();
                                break;
                            case "getpagetrabajadores":
                                oReplyBean = oUsuarioService.getpageall();
                                break;
                            case "getprofile":
                                oReplyBean = oUsuarioService.getprofile();
                                break;
                            case "logout":
                                oReplyBean = oUsuarioService.logout();
                                break;
                            case "updateprofile":
                                oReplyBean = oUsuarioService.updateprofile();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Object doesn't exist");
                        break;
                }
                break;
            default:
                switch (ob) {
                    case "usuario":
                        UsuarioService oUsuarioService = new UsuarioService(oRequest);
                        switch (op) {
                            case "login":
                                oReplyBean = oUsuarioService.login();
                                break;
                            case "check":
                                oReplyBean = oUsuarioService.check();
                                break;
                            default:
                                oReplyBean = new ReplyBean(500, "Operation doesn't exist");
                                break;
                        }
                        break;
                    default:
                        oReplyBean = new ReplyBean(500, "Object doesn't exist");
                        break;
                }
                break;
        }
        return oReplyBean;
    }
}
