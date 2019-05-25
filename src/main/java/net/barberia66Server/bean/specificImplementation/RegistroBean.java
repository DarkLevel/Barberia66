/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.bean.specificImplementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import net.barberia66Server.bean.genericImplementation.GenericBeanImplementation;
import net.barberia66Server.bean.publicInterface.BeanInterface;
import net.barberia66Server.dao.specificImplementation.LineaDao;
import net.barberia66Server.dao.specificImplementation.TipoRegistroDao;
import net.barberia66Server.dao.specificImplementation.UsuarioDao;
import net.barberia66Server.helper.EncodingHelper;

/**
 *
 * @author a073597589g
 */
public class RegistroBean extends GenericBeanImplementation implements BeanInterface {
    
    @Expose
    private Date fecha;
    @Expose(serialize = false)
    private int id_usuario;
    @Expose(serialize = false)
    private int id_tiporegistro;
    @Expose(deserialize = false)
    private UsuarioBean obj_usuario;
    @Expose(deserialize = false)
    private TipoRegistroBean obj_tiporegistro;
    @Expose
    private int link_linea;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_tiporegistro() {
        return id_tiporegistro;
    }

    public void setId_tiporegistro(int id_tiporegistro) {
        this.id_tiporegistro = id_tiporegistro;
    }

    public UsuarioBean getObj_usuario() {
        return obj_usuario;
    }

    public void setObj_usuario(UsuarioBean obj_usuario) {
        this.obj_usuario = obj_usuario;
    }

    public TipoRegistroBean getObj_tiporegistro() {
        return obj_tiporegistro;
    }

    public void setObj_tiporegistro(TipoRegistroBean obj_tiporegistro) {
        this.obj_tiporegistro = obj_tiporegistro;
    }

    public int getLink_linea() {
        return link_linea;
    }

    public void setLink_linea(int link_linea) {
        this.link_linea = link_linea;
    }
    
    @Override
    public RegistroBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        Timestamp fechaHora = oResultSet.getTimestamp("fecha");
        this.setFecha(fechaHora);
        this.setLink_linea((new LineaDao(oConnection, "linea")).getcountX(oResultSet.getInt("id")));
        if (expand > 0) {
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, "usuario");
            this.setObj_usuario((UsuarioBean) oUsuarioDao.get(oResultSet.getInt("id_usuario"), expand - 1));
            TipoRegistroDao oTipoRegistroDao = new TipoRegistroDao(oConnection, "tiporegistro");
            this.setObj_tiporegistro((TipoRegistroBean) oTipoRegistroDao.get(oResultSet.getInt("id_tiporegistro"), expand - 1));
        } else {
            this.setId_usuario(oResultSet.getInt("id_usuario"));
            this.setId_tiporegistro(oResultSet.getInt("id_tiporegistro"));
        }
        return this;
    }
    
    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "fecha,";
        strColumns += "id_usuario,";
        strColumns += "id_tiporegistro";
        return strColumns;
    }

    @Override
    public String getValues() {
        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha.toInstant();

        //Converting the Date to LocalDate
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
        
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(localDateTime.toString()) + ",";
        strColumns += id_usuario + ",";
        strColumns += id_tiporegistro;
        return strColumns;
    }

    @Override
    public String getPairs() {
        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha.toInstant();

        //Converting the Date to LocalDate
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();

        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "fecha=" + EncodingHelper.quotate(localDateTime.toString()) + ",";
        strPairs += "id_usuario=" + id_usuario + ",";
        strPairs += "id_tiporegistro=" + id_tiporegistro;
        strPairs += " WHERE id = " + id;
        return strPairs;
    }
    
}
