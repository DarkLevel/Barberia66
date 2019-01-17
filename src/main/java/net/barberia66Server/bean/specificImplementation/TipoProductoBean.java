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
public class TipoProductoBean extends GenericBeanImplementation implements BeanInterface {
    
    @Expose
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public TipoProductoBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDescripcion(oResultSet.getString("descripcion"));
        return this;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "descripcion";
        return strColumns;
    }
    
    @Override
    public String getValues() {
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(descripcion);
        return strColumns;
    }
    
    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "descripcion=" + EncodingHelper.quotate(descripcion);
        strPairs += " WHERE id = " + id;
        return strPairs;
    }
    
}
