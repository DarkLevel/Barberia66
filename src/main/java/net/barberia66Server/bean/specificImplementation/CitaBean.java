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
import net.barberia66Server.dao.specificImplementation.EstadoCitaDao;
import net.barberia66Server.dao.specificImplementation.TipoCitaDao;
import net.barberia66Server.dao.specificImplementation.UsuarioDao;
import net.barberia66Server.helper.EncodingHelper;

/**
 *
 * @author a073597589g
 */
public class CitaBean extends GenericBeanImplementation implements BeanInterface {
    
    @Expose
    private Date fecha;
    @Expose(serialize = false)
    private int id_tipocita;
    @Expose(deserialize = false)
    private TipoCitaBean obj_tipocita;
    @Expose(serialize = false)
    private int id_usuario;
    @Expose(deserialize = false)
    private UsuarioBean obj_usuario;
    @Expose(serialize = false)
    private int id_estadocita;
    @Expose(deserialize = false)
    private EstadoCitaBean obj_estadocita;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_tipocita() {
        return id_tipocita;
    }

    public void setId_tipocita(int id_tipocita) {
        this.id_tipocita = id_tipocita;
    }

    public TipoCitaBean getObj_tipocita() {
        return obj_tipocita;
    }

    public void setObj_tipocita(TipoCitaBean obj_tipocita) {
        this.obj_tipocita = obj_tipocita;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public UsuarioBean getObj_usuario() {
        return obj_usuario;
    }

    public void setObj_usuario(UsuarioBean obj_usuario) {
        this.obj_usuario = obj_usuario;
    }

    public int getId_estadocita() {
        return id_estadocita;
    }

    public void setId_estadocita(int id_estadocita) {
        this.id_estadocita = id_estadocita;
    }

    public EstadoCitaBean getObj_estadocita() {
        return obj_estadocita;
    }

    public void setObj_estadocita(EstadoCitaBean obj_estadocita) {
        this.obj_estadocita = obj_estadocita;
    }
    
    @Override
    public CitaBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        Timestamp fechaHora = oResultSet.getTimestamp("fecha");
        this.setFecha(fechaHora);
        if (expand > 0) {
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection, "usuario");
            this.setObj_usuario((UsuarioBean) oUsuarioDao.get(oResultSet.getInt("id_usuario"), expand - 1));
            TipoCitaDao oTipoCitaDao = new TipoCitaDao(oConnection, "tipocita");
            this.setObj_tipocita((TipoCitaBean) oTipoCitaDao.get(oResultSet.getInt("id_tipocita"), expand - 1));
            EstadoCitaDao oEstadoCitaDao = new EstadoCitaDao(oConnection, "estadocita");
            this.setObj_estadocita((EstadoCitaBean) oEstadoCitaDao.get(oResultSet.getInt("id_estadocita"), expand - 1));
        } else {
            this.setId_usuario(oResultSet.getInt("id_usuario"));
            this.setId_tipocita(oResultSet.getInt("id_tipocita"));
            this.setId_estadocita(oResultSet.getInt("id_estadocita"));
        }
        return this;
    }
    
    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "fecha,";
        strColumns += "id_usuario,";
        strColumns += "id_tipocita,";
        strColumns += "id_estadocita";
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
        strColumns += id_tipocita + ",";
        strColumns += id_estadocita;
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
        strPairs += "id_tipocita=" + id_tipocita + ",";
        strPairs += "id_estadocita=" + id_estadocita;
        strPairs += " WHERE id = " + id;
        return strPairs;
    }
    
}
