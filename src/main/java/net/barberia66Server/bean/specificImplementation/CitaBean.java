/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.bean.specificImplementation;

import com.google.gson.annotations.Expose;
import java.sql.Connection;
import java.sql.ResultSet;
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
    private Date fecha_inicio;
    @Expose
    private Date fecha_fin;
    @Expose
    private String descripcion;
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

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        this.setFecha_inicio(oResultSet.getTimestamp("fecha_inicio"));
        this.setFecha_fin(oResultSet.getTimestamp("fecha_fin"));
        this.setDescripcion(oResultSet.getString("descripcion"));
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
        strColumns += "fecha_inicio,";
        strColumns += "fecha_fin,";
        strColumns += "descripcion,";
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
        Instant instantInicio = fecha_inicio.toInstant();
        Instant instantFin = fecha_fin.toInstant();

        //Converting the Date to LocalDate
        LocalDateTime fecha_inicioLocalDateTime = instantInicio.atZone(defaultZoneId).toLocalDateTime();
        LocalDateTime fecha_finLocalDateTime = instantFin.atZone(defaultZoneId).toLocalDateTime();

        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(fecha_inicioLocalDateTime.toString()) + ",";
        strColumns += EncodingHelper.quotate(fecha_finLocalDateTime.toString()) + ",";
        strColumns += EncodingHelper.quotate(descripcion) + ",";
        strColumns += id_usuario + ",";
        strColumns += id_tipocita + ",";
        strColumns += id_estadocita;
        return strColumns;
    }

    @Override
    public String getPairs() {
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "descripcion=" + EncodingHelper.quotate(descripcion) + ",";
        strPairs += "id_tipocita=" + id_tipocita;
        strPairs += " WHERE id = " + id;
        return strPairs;
    }

}
