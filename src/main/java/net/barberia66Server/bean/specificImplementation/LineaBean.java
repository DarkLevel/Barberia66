/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.bean.specificImplementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import net.barberia66Server.bean.genericImplementation.GenericBeanImplementation;
import net.barberia66Server.bean.publicInterface.BeanInterface;
import net.barberia66Server.dao.specificImplementation.ProductoDao;
import net.barberia66Server.dao.specificImplementation.RegistroDao;

/**
 *
 * @author a073597589g
 */
public class LineaBean extends GenericBeanImplementation implements BeanInterface {
    
    @Expose
    private int cantidad;
    @Expose(serialize = false)
    private int id_producto;
    @Expose(serialize = false)
    private int id_registro;
    @Expose(deserialize = false)
    private ProductoBean obj_producto;
    @Expose(deserialize = false)
    private RegistroBean obj_registro;
    
    public ProductoBean getObj_producto() {
        return obj_producto;
    }

    public void setObj_producto(ProductoBean obj_producto) {
        this.obj_producto = obj_producto;
    }

    public RegistroBean getObj_registro() {
        return obj_registro;
    }

    public void setObj_registro(RegistroBean obj_registro) {
        this.obj_registro = obj_registro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_registro() {
        return id_registro;
    }

    public void setId_registro(int id_registro) {
        this.id_registro = id_registro;
    }
    
    @Override
    public LineaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setCantidad(oResultSet.getInt("cantidad"));
        if (expand > 0) {
            ProductoDao oProductoDao = new ProductoDao(oConnection, "producto");
            this.setObj_producto((ProductoBean) oProductoDao.get(oResultSet.getInt("id_producto"), expand - 1));
            RegistroDao oregistroDao = new RegistroDao(oConnection, "registro");
            this.setObj_registro((RegistroBean) oregistroDao.get(oResultSet.getInt("id_registro"), expand - 1));
        } else {
            this.setId_producto(oResultSet.getInt("id_producto"));
            this.setId_registro(oResultSet.getInt("id_registro"));
        }
        return this;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "cantidad,";
        strColumns += "id_producto,";
        strColumns += "id_registro";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += cantidad + ",";
        strColumns += id_producto + ",";
        strColumns += id_registro;
        return strColumns;
    }

    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "cantidad=" + cantidad + ",";
        strPairs += "id_producto=" + id_producto + ",";
        strPairs += "id_registro=" + id_registro;
        strPairs += " WHERE id = " + id;
        return strPairs;
    }
    
}
