/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.service.specificImplementation;

import com.google.gson.Gson;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import net.barberia66Server.bean.specificImplementation.CarritoBean;
import net.barberia66Server.bean.specificImplementation.LineaBean;
import net.barberia66Server.bean.specificImplementation.ProductoBean;
import net.barberia66Server.bean.specificImplementation.RegistroBean;
import net.barberia66Server.bean.specificImplementation.ReplyBean;
import net.barberia66Server.bean.specificImplementation.UsuarioBean;
import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.constants.ConnectionConstants;
import net.barberia66Server.dao.specificImplementation.LineaDao;
import net.barberia66Server.dao.specificImplementation.ProductoDao;
import net.barberia66Server.dao.specificImplementation.RegistroDao;
import net.barberia66Server.factory.ConnectionFactory;
import net.barberia66Server.helper.Validator;
import net.barberia66Server.service.genericImplementation.GenericServiceImplementation;
import net.barberia66Server.service.publicInterface.ServiceInterface;

/**
 *
 * @author a073597589g
 */
public class CarritoService extends GenericServiceImplementation implements ServiceInterface {
    
    public CarritoService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }
    
    protected Boolean checkPermission(String strMethodName) {
        UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
        return oUsuarioBean != null;
    }

    public ReplyBean add() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("add")) {
            ConnectionInterface oConnectionPool = null;
            Connection oConnection;
            Gson oGson = new Gson();
            try {
                oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
                oConnection = oConnectionPool.newConnection();
                String strProducto = oRequest.getParameter("id");
                String strCantidad = oRequest.getParameter("cant");
                if (Validator.validateId(strProducto)) {
                    if (Validator.validateId(strCantidad)) {
                        Integer id_producto = Integer.parseInt(strProducto);
                        Integer cantidad = Integer.parseInt(strCantidad);
                        ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
                        ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                        ProductoBean oProductoBean = (ProductoBean) oProductoDao.get(id_producto, 1);
                        if (oProductoBean != null) {
                            if (alCarrito != null) {
                                Boolean exists = false;
                                for (CarritoBean o : alCarrito) {
                                    if (id_producto == o.getObj_producto().getId()) {
                                        o.setCantidad(o.getCantidad() + cantidad);
                                        exists = true;
                                        break;
                                    }
                                }
                                if (!exists) {
                                    CarritoBean oCarritoBean = new CarritoBean();
                                    oProductoDao = new ProductoDao(oConnection, "producto");
                                    oProductoBean = (ProductoBean) oProductoDao.get(id_producto, 1);
                                    oCarritoBean.setCantidad(cantidad);
                                    oCarritoBean.setObj_producto(oProductoBean);
                                    alCarrito.add(oCarritoBean);
                                }
                                oRequest.getSession().setAttribute("carrito", alCarrito);
                            } else {
                                ArrayList<CarritoBean> newAlCarrito = new ArrayList<>();
                                CarritoBean oCarritoBean = new CarritoBean();
                                oProductoDao = new ProductoDao(oConnection, "producto");
                                oProductoBean = (ProductoBean) oProductoDao.get(id_producto, 1);
                                oCarritoBean.setCantidad(cantidad);
                                oCarritoBean.setObj_producto(oProductoBean);
                                newAlCarrito.add(oCarritoBean);
                                oRequest.getSession().setAttribute("carrito", newAlCarrito);
                            }
                            oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                        } else {
                            oReplyBean = new ReplyBean(400, "Ese producto no existe.");
                        }
                    } else {
                        oReplyBean = new ReplyBean(400, "La cantidad tiene que ser un numero mayor a 0.");
                    }
                } else {
                    oReplyBean = new ReplyBean(400, "El id tiene que ser un numero mayor a 0.");
                }
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: add method: " + ob + " object: " + ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean reduce() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("reduce")) {
            Gson oGson = new Gson();
            try {
                String strProducto = oRequest.getParameter("id");
                String strCantidad = oRequest.getParameter("cant");
                if (Validator.validateId(strProducto)) {
                    if (Validator.validateId(strCantidad)) {
                        Integer id_producto = Integer.parseInt(strProducto);
                        Integer cantidad = Integer.parseInt(strCantidad);
                        ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
                        if (alCarrito != null) {
                            Boolean exists = false;
                            for (CarritoBean o : alCarrito) {
                                if (id_producto == o.getObj_producto().getId()) {
                                    exists = true;
                                    if (o.getCantidad() > cantidad) {
                                        o.setCantidad(o.getCantidad() - cantidad);
                                    } else {
                                        alCarrito.remove(o);
                                    }
                                    break;
                                }
                            }
                            if (!alCarrito.isEmpty() && exists) {
                                oRequest.getSession().setAttribute("carrito", alCarrito);
                                oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                            } else {
                                if (alCarrito.isEmpty()) {
                                    oRequest.getSession().setAttribute("carrito", null);
                                    oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                                } else {
                                    oReplyBean = new ReplyBean(400, "El producto seleccionado no existe.");
                                }
                            }
                        } else {
                            oReplyBean = new ReplyBean(400, "El carrito esta vacio.");
                        }
                    } else {
                        oReplyBean = new ReplyBean(400, "La cantidad tiene que ser un numero mayor a 0.");
                    }
                } else {
                    oReplyBean = new ReplyBean(400, "El id tiene que ser un numero mayor a 0.");
                }
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: reduce method: " + ob + " object: " + ex);
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean remove() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("remove")) {
            Gson oGson = new Gson();
            try {
                String strProducto = oRequest.getParameter("id");
                if (Validator.validateId(strProducto)) {
                    Integer id_producto = Integer.parseInt(strProducto);
                    ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
                    if (alCarrito != null) {
                        Boolean exists = false;
                        for (CarritoBean o : alCarrito) {
                            if (id_producto == o.getObj_producto().getId()) {
                                exists = true;
                                alCarrito.remove(o);
                                break;
                            }
                        }
                        if (!alCarrito.isEmpty() && exists) {
                            oRequest.getSession().setAttribute("carrito", alCarrito);
                            oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                        } else {
                            if (alCarrito.isEmpty()) {
                                oRequest.getSession().setAttribute("carrito", null);
                                oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                            } else {
                                oReplyBean = new ReplyBean(400, "El producto seleccionado no existe.");
                            }
                        }
                    } else {
                        oReplyBean = new ReplyBean(400, "El carrito esta vacio.");
                    }
                } else{
                    oReplyBean = new ReplyBean(400, "El id tiene que ser un numero mayor a 0.");
                }
            } catch (Exception ex) {
                throw new Exception("ERROR: Service level: remove method: " + ob + " object: " + ex);
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean empty() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("empty")) {
            Gson oGson = new Gson();
            oRequest.getSession().setAttribute("carrito", null);
            oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean show() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("show")) {
            Gson oGson = new Gson();
            oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }

    public ReplyBean buy() throws Exception {
        ReplyBean oReplyBean;
        if (checkPermission("buy")) {
            ConnectionInterface oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
            Connection oConnection = oConnectionPool.newConnection();
            try {
                oConnection.setAutoCommit(false);
                UsuarioBean oUsuarioBean = (UsuarioBean) oRequest.getSession().getAttribute("user");
                ArrayList<CarritoBean> alCarrito = (ArrayList<CarritoBean>) oRequest.getSession().getAttribute("carrito");
                if (alCarrito != null) {
                    //Crear obj_factura (preguntar sobre iva en factura y hora de la fecha y como ensenyarla en plist)
                    RegistroBean oRegistroBean = new RegistroBean();
                    RegistroDao oRegistroDao = new RegistroDao(oConnection, "factura");
                    LocalDateTime fechaHora = LocalDateTime.now();
                    Instant instant = fechaHora.toInstant(ZoneOffset.ofHours(+1));
                    Date fecha = Date.from(instant);
                    oRegistroBean.setFecha(fecha);
                    oRegistroBean.setId_usuario(oUsuarioBean.getId());
                    oRegistroBean = (RegistroBean) oRegistroDao.create(oRegistroBean);
                    LineaBean oLineaBean = new LineaBean();
                    LineaDao oLineaDao = new LineaDao(oConnection, "linea");
                    ProductoBean oProductoBean;
                    ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                    boolean cantidadCorrecta = true;
                    String productoIncorrecto = null;
                    for (CarritoBean o : alCarrito) {
                        oLineaBean.setId_registro(oRegistroBean.getId());
                        if (o.getCantidad() <= o.getObj_producto().getExistencias()) {
                            oLineaBean.setCantidad(o.getCantidad());
                            oProductoBean = (ProductoBean) oProductoDao.get(o.getObj_producto().getId(), 0);
                            oProductoBean.setExistencias(oProductoBean.getExistencias() - o.getCantidad());
                            oProductoDao.update(oProductoBean);
                            oLineaBean.setId_producto(o.getObj_producto().getId());
                            oLineaDao.create(oLineaBean);
                        } else {
                            cantidadCorrecta = false;
                            productoIncorrecto = o.getObj_producto().getDescripcion();
                            break;
                        }
                    }
                    if (cantidadCorrecta) {
                        oConnection.commit();
                        Gson oGson = new Gson();
                        oReplyBean = new ReplyBean(200, oGson.toJson(oRequest.getSession().getAttribute("carrito")));
                    } else {
                        oConnection.rollback();
                        oReplyBean = new ReplyBean(400, "Se ha seleccionado una cantidad superior a las existencias del producto " + productoIncorrecto);
                    }
                } else {
                    oConnection.rollback();
                    oReplyBean = new ReplyBean(400, "No existen productos.");
                }
            } catch (Exception ex) {
                oConnection.rollback();
                throw new Exception("ERROR: Service level: buy method: " + ob + " object: " + ex);
            } finally {
                oConnectionPool.disposeConnection();
            }
        } else {
            oReplyBean = new ReplyBean(401, "Unauthorized");
        }
        return oReplyBean;
    }
    
}
