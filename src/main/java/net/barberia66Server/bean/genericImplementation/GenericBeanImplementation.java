/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.bean.genericImplementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import net.barberia66Server.bean.publicInterface.BeanInterface;

/**
 *
 * @author a073597589g
 */
public class GenericBeanImplementation implements BeanInterface {

    @Expose
    protected int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public BeanInterface fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getColumns() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getValues() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPairs() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
