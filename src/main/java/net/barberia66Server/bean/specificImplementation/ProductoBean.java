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
import net.barberia66Server.dao.specificImplementation.TipoProductoDao;
import net.barberia66Server.helper.EncodingHelper;

/**
 *
 * @author a073597589g
 */
public class ProductoBean extends GenericBeanImplementation implements BeanInterface {
    
    @Expose
    private String nombre;
    @Expose
    private String descripcion;
    @Expose
    private int existencias;
    @Expose
    private float precio;
    @Expose
    private int iva_compra;
    @Expose
    private int iva_venta;
    @Expose
    private String foto;
    @Expose(serialize = false)
    private int id_tipoproducto;
    @Expose(deserialize = false)
    private TipoProductoBean obj_tipoproducto;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getIva_compra() {
        return iva_compra;
    }

    public void setIva_compra(int iva_compra) {
        this.iva_compra = iva_compra;
    }

    public int getIva_venta() {
        return iva_venta;
    }

    public void setIva_venta(int iva_venta) {
        this.iva_venta = iva_venta;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId_tipoproducto() {
        return id_tipoproducto;
    }

    public void setId_tipoproducto(int id_tipoproducto) {
        this.id_tipoproducto = id_tipoproducto;
    }

    public TipoProductoBean getObj_tipoproducto() {
        return obj_tipoproducto;
    }

    public void setObj_tipoproducto(TipoProductoBean obj_tipoproducto) {
        this.obj_tipoproducto = obj_tipoproducto;
    }
    
    @Override
    public ProductoBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setDescripcion(oResultSet.getString("descripcion"));
        this.setExistencias(oResultSet.getInt("existencias"));
        this.setPrecio(oResultSet.getFloat("precio"));
        this.setIva_compra(oResultSet.getInt("iva_compra"));
        this.setIva_venta(oResultSet.getInt("iva_venta"));
        this.setFoto(oResultSet.getString("foto"));
        if (expand > 0) {
            TipoProductoDao oTipoProductoDao = new TipoProductoDao(oConnection, "tipoproducto");
            this.setObj_tipoproducto((TipoProductoBean) oTipoProductoDao.get(oResultSet.getInt("id_tipoproducto"), expand - 1));
        } else{
            this.setId_tipoproducto(oResultSet.getInt("id_tipoproducto"));
        }
        return this;
    }
    
    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "nombre,";
        strColumns += "descripcion,";
        strColumns += "existencias,";
        strColumns += "precio,";
        strColumns += "iva_compra,";
        strColumns += "iva_venta,";
        strColumns += "foto,";
        strColumns += "id_tipoproducto";
        return strColumns;
    }

    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(nombre) + ",";
        strColumns += EncodingHelper.quotate(descripcion) + ",";
        strColumns += existencias + ",";
        strColumns += precio + ",";
        strColumns += iva_compra + ",";
        strColumns += iva_venta + ",";
        strColumns += EncodingHelper.quotate(foto) + ",";
        strColumns += id_tipoproducto;
        return strColumns;
    }

    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "nombre=" + EncodingHelper.quotate(nombre) + ",";
        strPairs += "descripcion=" + EncodingHelper.quotate(descripcion) + ",";
        strPairs += "existencias=" + existencias + ",";
        strPairs += "precio=" + precio + ",";
        strPairs += "iva_compra=" + iva_compra + ",";
        strPairs += "iva_venta=" + iva_venta + ",";
        strPairs += "foto=" + EncodingHelper.quotate(foto) + ",";
        strPairs += "id_tipoproducto=" + id_tipoproducto;
        strPairs += " WHERE id = " + id;
        return strPairs;
    }

    
}
