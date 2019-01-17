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
import net.barberia66Server.helper.EncodingHelper;

/**
 *
 * @author a073597589g
 */
public class TipoCitaBean extends GenericBeanImplementation implements BeanInterface {
    
    @Expose
    private String descripcion;
    @Expose
    private float precio; 

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    @Override
    public TipoCitaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDescripcion(oResultSet.getString("descripcion"));
        this.setPrecio(oResultSet.getFloat("precio"));
        return this;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "descripcion,";
        strColumns += "precio";
        return strColumns;
    }
    
    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(descripcion) + ",";
        strColumns += precio;
        return strColumns;
    }
    
    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "descripcion=" + EncodingHelper.quotate(descripcion) + ",";
        strPairs += "precio=" + precio;
        strPairs += " WHERE id = " + id;
        return strPairs;
    }
    
}
