/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.service.specificImplementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import net.barberia66Server.bean.publicInterface.BeanInterface;
import net.barberia66Server.bean.specificImplementation.LineaBean;
import net.barberia66Server.bean.specificImplementation.ProductoBean;
import net.barberia66Server.bean.specificImplementation.ProductosComercioBean;
import net.barberia66Server.bean.specificImplementation.RegistroBean;
import net.barberia66Server.bean.specificImplementation.ReplyBean;
import net.barberia66Server.bean.specificImplementation.UsuarioBean;
import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.constants.ConnectionConstants;
import net.barberia66Server.dao.publicInterface.DaoInterface;
import net.barberia66Server.dao.specificImplementation.LineaDao;
import net.barberia66Server.dao.specificImplementation.ProductoDao;
import net.barberia66Server.dao.specificImplementation.RegistroDao;
import net.barberia66Server.dao.specificImplementation.UsuarioDao;
import net.barberia66Server.factory.ConnectionFactory;
import net.barberia66Server.factory.DaoFactory;
import net.barberia66Server.helper.Validator;
import net.barberia66Server.service.genericImplementation.GenericServiceImplementation;
import net.barberia66Server.service.publicInterface.ServiceInterface;

/**
 *
 * @author a073597589g
 */
public class ComercioService extends GenericServiceImplementation implements ServiceInterface {

    public ComercioService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }

    public ReplyBean venta() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
        Connection oConnection = oConnectionPool.newConnection();
        try {
            oConnection.setAutoCommit(false);
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            if (Validator.validateId(id.toString())) {
                UsuarioBean oUsuarioBean = (UsuarioBean) new UsuarioDao(oConnection, "usuario").get(id, 0);
                if (oUsuarioBean != null) {
                    ArrayList<ProductosComercioBean> alProductos = new ArrayList<>();
                    boolean producto_correcto = true;
                    String strProductos = oRequest.getParameter("productos");
                    if (strProductos != null && !strProductos.equals("[]")) {
                        strProductos = strProductos.replace("[", "");
                        strProductos = strProductos.replace("]", "");
                        String productosArray[] = strProductos.split("},");
                        for (int i = 0; i < productosArray.length; i++) {
                            if (i < productosArray.length - 1) {
                                productosArray[i] = productosArray[i] + "}";
                            }
                            productosArray[i] = productosArray[i].replace("\"id\":", "");
                            productosArray[i] = productosArray[i].replace("\"cantidad\":", "");
                            productosArray[i] = productosArray[i].replace("}", "");
                            productosArray[i] = productosArray[i].replace("{", "");
                        }
                        for (String p : productosArray) {
                            String[] objProducto = p.split(",");
                            ProductosComercioBean oProductosComercioBean = new ProductosComercioBean();
                            if (Validator.validateId(objProducto[0]) && Validator.validateId(objProducto[1])) {
                                ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                                ProductoBean oProductoBean = (ProductoBean) oProductoDao.get(Integer.parseInt(objProducto[0]), 0);
                                Integer cantidad = Integer.parseInt(objProducto[1]);
                                if (oProductoBean != null) {
                                    oProductosComercioBean.setObj_producto(oProductoBean);
                                    oProductosComercioBean.setCantidad(cantidad);
                                    alProductos.add(oProductosComercioBean);
                                } else {
                                    producto_correcto = false;
                                    break;
                                }
                            } else {
                                producto_correcto = false;
                                break;
                            }
                        }
                    }
                    if (producto_correcto) {
                        RegistroBean oRegistroBean = new RegistroBean();
                        RegistroDao oRegistroDao = new RegistroDao(oConnection, "registro");
                        LocalDateTime fechaHora = LocalDateTime.now();
                        Instant instant = fechaHora.toInstant(ZoneOffset.ofHours(+1));
                        Date fecha = Date.from(instant);
                        oRegistroBean.setFecha(fecha);
                        oRegistroBean.setId_usuario(oUsuarioBean.getId());
                        oRegistroBean.setId_tiporegistro(1);
                        oRegistroBean = (RegistroBean) oRegistroDao.create(oRegistroBean);
                        LineaBean oLineaBean = new LineaBean();
                        LineaDao oLineaDao = new LineaDao(oConnection, "linea");
                        ProductoBean oProductoBean;
                        ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                        boolean cantidadCorrecta = true;
                        String productoIncorrecto = null;
                        for (ProductosComercioBean o : alProductos) {
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
                            DaoInterface oDao = DaoFactory.getDao(oConnection, "producto");
                            ArrayList<BeanInterface> alBean = oDao.getpage(10000, 1, null, 1);
                            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                            oReplyBean = new ReplyBean(200, oGson.toJson(alBean));
                        } else {
                            oConnection.rollback();
                            oReplyBean = new ReplyBean(400, "Se ha seleccionado una cantidad superior a las existencias del producto " + productoIncorrecto);
                        }
                    } else {
                        oConnection.rollback();
                        oReplyBean = new ReplyBean(400, "Algun producto seleccionado no existe.");
                    }
                } else {
                    oConnection.rollback();
                    oReplyBean = new ReplyBean(400, "El usuario no existe.");
                }
            } else {
                oConnection.rollback();
                oReplyBean = new ReplyBean(400, "Usuario invalido.");
            }
        } catch (Exception ex) {
            oConnection.rollback();
            throw new Exception("ERROR: Service level: buy method: " + ob + " object: " + ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean uso() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
        Connection oConnection = oConnectionPool.newConnection();
        try {
            oConnection.setAutoCommit(false);
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            if (Validator.validateId(id.toString())) {
                UsuarioBean oUsuarioBean = (UsuarioBean) new UsuarioDao(oConnection, "usuario").get(id, 0);
                if (oUsuarioBean != null) {
                    ArrayList<ProductosComercioBean> alProductos = new ArrayList<>();
                    boolean producto_correcto = true;
                    String strProductos = oRequest.getParameter("productos");
                    if (strProductos != null && !strProductos.equals("[]")) {
                        strProductos = strProductos.replace("[", "");
                        strProductos = strProductos.replace("]", "");
                        String productosArray[] = strProductos.split("},");
                        for (int i = 0; i < productosArray.length; i++) {
                            if (i < productosArray.length - 1) {
                                productosArray[i] = productosArray[i] + "}";
                            }
                            productosArray[i] = productosArray[i].replace("\"id\":", "");
                            productosArray[i] = productosArray[i].replace("\"cantidad\":", "");
                            productosArray[i] = productosArray[i].replace("}", "");
                            productosArray[i] = productosArray[i].replace("{", "");
                        }
                        for (String p : productosArray) {
                            String[] objProducto = p.split(",");
                            ProductosComercioBean oProductosComercioBean = new ProductosComercioBean();
                            if (Validator.validateId(objProducto[0]) && Validator.validateId(objProducto[1])) {
                                ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                                ProductoBean oProductoBean = (ProductoBean) oProductoDao.get(Integer.parseInt(objProducto[0]), 0);
                                Integer cantidad = Integer.parseInt(objProducto[1]);
                                if (oProductoBean != null) {
                                    oProductosComercioBean.setObj_producto(oProductoBean);
                                    oProductosComercioBean.setCantidad(cantidad);
                                    alProductos.add(oProductosComercioBean);
                                } else {
                                    producto_correcto = false;
                                    break;
                                }
                            } else {
                                producto_correcto = false;
                                break;
                            }
                        }
                    }
                    if (producto_correcto) {
                        RegistroBean oRegistroBean = new RegistroBean();
                        RegistroDao oRegistroDao = new RegistroDao(oConnection, "registro");
                        LocalDateTime fechaHora = LocalDateTime.now();
                        Instant instant = fechaHora.toInstant(ZoneOffset.ofHours(+1));
                        Date fecha = Date.from(instant);
                        oRegistroBean.setFecha(fecha);
                        oRegistroBean.setId_usuario(oUsuarioBean.getId());
                        oRegistroBean.setId_tiporegistro(3);
                        oRegistroBean = (RegistroBean) oRegistroDao.create(oRegistroBean);
                        LineaBean oLineaBean = new LineaBean();
                        LineaDao oLineaDao = new LineaDao(oConnection, "linea");
                        ProductoBean oProductoBean;
                        ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                        boolean cantidadCorrecta = true;
                        String productoIncorrecto = null;
                        for (ProductosComercioBean o : alProductos) {
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
                            DaoInterface oDao = DaoFactory.getDao(oConnection, "producto");
                            ArrayList<BeanInterface> alBean = oDao.getpage(10000, 1, null, 1);
                            Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                            oReplyBean = new ReplyBean(200, oGson.toJson(alBean));
                        } else {
                            oConnection.rollback();
                            oReplyBean = new ReplyBean(400, "Se ha seleccionado una cantidad superior a las existencias del producto " + productoIncorrecto);
                        }
                    } else {
                        oConnection.rollback();
                        oReplyBean = new ReplyBean(400, "Algun producto seleccionado no existe.");
                    }
                } else {
                    oConnection.rollback();
                    oReplyBean = new ReplyBean(400, "El usuario no existe.");
                }
            } else {
                oConnection.rollback();
                oReplyBean = new ReplyBean(400, "Usuario invalido.");
            }
        } catch (Exception ex) {
            oConnection.rollback();
            throw new Exception("ERROR: Service level: buy method: " + ob + " object: " + ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

    public ReplyBean compra() throws Exception {
        ReplyBean oReplyBean;
        ConnectionInterface oConnectionPool = ConnectionFactory.getConnection(ConnectionConstants.connectionPool);
        Connection oConnection = oConnectionPool.newConnection();
        try {
            oConnection.setAutoCommit(false);
            Integer id = Integer.parseInt(oRequest.getParameter("id"));
            if (Validator.validateId(id.toString())) {
                UsuarioBean oUsuarioBean = (UsuarioBean) new UsuarioDao(oConnection, "usuario").get(id, 0);
                if (oUsuarioBean != null) {
                    ArrayList<ProductosComercioBean> alProductos = new ArrayList<>();
                    boolean producto_correcto = true;
                    String strProductos = oRequest.getParameter("productos");
                    if (strProductos != null && !strProductos.equals("[]")) {
                        strProductos = strProductos.replace("[", "");
                        strProductos = strProductos.replace("]", "");
                        String productosArray[] = strProductos.split("},");
                        for (int i = 0; i < productosArray.length; i++) {
                            if (i < productosArray.length - 1) {
                                productosArray[i] = productosArray[i] + "}";
                            }
                            productosArray[i] = productosArray[i].replace("\"id\":", "");
                            productosArray[i] = productosArray[i].replace("\"cantidad\":", "");
                            productosArray[i] = productosArray[i].replace("}", "");
                            productosArray[i] = productosArray[i].replace("{", "");
                        }
                        for (String p : productosArray) {
                            String[] objProducto = p.split(",");
                            ProductosComercioBean oProductosComercioBean = new ProductosComercioBean();
                            if (Validator.validateId(objProducto[0]) && Validator.validateId(objProducto[1])) {
                                ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                                ProductoBean oProductoBean = (ProductoBean) oProductoDao.get(Integer.parseInt(objProducto[0]), 0);
                                Integer cantidad = Integer.parseInt(objProducto[1]);
                                if (oProductoBean != null) {
                                    oProductosComercioBean.setObj_producto(oProductoBean);
                                    oProductosComercioBean.setCantidad(cantidad);
                                    alProductos.add(oProductosComercioBean);
                                } else {
                                    producto_correcto = false;
                                    break;
                                }
                            } else {
                                producto_correcto = false;
                                break;
                            }
                        }
                    }
                    if (producto_correcto) {
                        RegistroBean oRegistroBean = new RegistroBean();
                        RegistroDao oRegistroDao = new RegistroDao(oConnection, "registro");
                        LocalDateTime fechaHora = LocalDateTime.now();
                        Instant instant = fechaHora.toInstant(ZoneOffset.ofHours(+1));
                        Date fecha = Date.from(instant);
                        oRegistroBean.setFecha(fecha);
                        oRegistroBean.setId_usuario(oUsuarioBean.getId());
                        oRegistroBean.setId_tiporegistro(2);
                        oRegistroBean = (RegistroBean) oRegistroDao.create(oRegistroBean);
                        LineaBean oLineaBean = new LineaBean();
                        LineaDao oLineaDao = new LineaDao(oConnection, "linea");
                        ProductoBean oProductoBean;
                        ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
                        for (ProductosComercioBean o : alProductos) {
                            oLineaBean.setId_registro(oRegistroBean.getId());
                            oLineaBean.setCantidad(o.getCantidad());
                            oProductoBean = (ProductoBean) oProductoDao.get(o.getObj_producto().getId(), 0);
                            oProductoBean.setExistencias(oProductoBean.getExistencias() + o.getCantidad());
                            oProductoDao.update(oProductoBean);
                            oLineaBean.setId_producto(o.getObj_producto().getId());
                            oLineaDao.create(oLineaBean);
                        }
                        oConnection.commit();
                        DaoInterface oDao = DaoFactory.getDao(oConnection, "producto");
                        ArrayList<BeanInterface> alBean = oDao.getpage(10000, 1, null, 1);
                        Gson oGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().create();
                        oReplyBean = new ReplyBean(200, oGson.toJson(alBean));
                    } else {
                        oConnection.rollback();
                        oReplyBean = new ReplyBean(400, "Algun producto seleccionado no existe.");
                    }
                } else {
                    oConnection.rollback();
                    oReplyBean = new ReplyBean(400, "El usuario no existe.");
                }
            } else {
                oConnection.rollback();
                oReplyBean = new ReplyBean(400, "Usuario invalido.");
            }
        } catch (Exception ex) {
            oConnection.rollback();
            throw new Exception("ERROR: Service level: buy method: " + ob + " object: " + ex);
        } finally {
            oConnectionPool.disposeConnection();
        }
        return oReplyBean;
    }

}
