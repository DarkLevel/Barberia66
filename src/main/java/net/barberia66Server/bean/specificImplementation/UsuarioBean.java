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
import net.barberia66Server.dao.specificImplementation.RegistroDao;
import net.barberia66Server.dao.specificImplementation.TipoUsuarioDao;
import net.barberia66Server.helper.EncodingHelper;

/**
 *
 * @author a073597589g
 */
public class UsuarioBean extends GenericBeanImplementation implements BeanInterface {
    
    @Expose
    private String dni;
    @Expose
    private String nombre;
    @Expose
    private String apellido1;
    @Expose
    private String apellido2;
    @Expose
    private Date fecha_alta;
    @Expose
    private String username;
    @Expose(serialize = false)
    private String password;
    @Expose
    private String color_cita;
    @Expose
    private String color_cita_realizada;
    @Expose(serialize = false)
    private int id_tipousuario;
    @Expose(deserialize = false)
    private TipoUsuarioBean obj_tipousuario;
    @Expose
    private int link_registro;
    @Expose
    private int link_cita;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getColor_cita() {
        return color_cita;
    }

    public void setColor_cita(String color_cita) {
        this.color_cita = color_cita;
    }

    public String getColor_cita_realizada() {
        return color_cita_realizada;
    }

    public void setColor_cita_realizada(String color_cita_realizada) {
        this.color_cita_realizada = color_cita_realizada;
    }

    public int getId_tipousuario() {
        return id_tipousuario;
    }

    public void setId_tipousuario(int id_tipousuario) {
        this.id_tipousuario = id_tipousuario;
    }

    public TipoUsuarioBean getObj_tipousuario() {
        return obj_tipousuario;
    }

    public void setObj_tipousuario(TipoUsuarioBean obj_tipousuario) {
        this.obj_tipousuario = obj_tipousuario;
    }

    public int getLink_registro() {
        return link_registro;
    }

    public void setLink_registro(int link_registro) {
        this.link_registro = link_registro;
    }

    public int getLink_cita() {
        return link_cita;
    }

    public void setLink_cita(int link_cita) {
        this.link_cita = link_cita;
    }
    
    @Override
    public UsuarioBean fill(ResultSet oResultSet, Connection oConnection, Integer expand) throws Exception {
        this.setId(oResultSet.getInt("id"));
        this.setDni(oResultSet.getString("dni"));
        this.setNombre(oResultSet.getString("nombre"));
        this.setApellido1(oResultSet.getString("apellido1"));
        this.setApellido2(oResultSet.getString("apellido2"));
        Timestamp fechaAlta = oResultSet.getTimestamp("fecha_alta");
        this.setFecha_alta(fechaAlta);
        this.setUsername(oResultSet.getString("username"));
        this.setPassword(oResultSet.getString("password"));
        this.setColor_cita(oResultSet.getString("color_cita"));
        this.setColor_cita_realizada(oResultSet.getString("color_cita_realizada"));
        this.setLink_registro((new RegistroDao(oConnection, "registro")).getcountX(oResultSet.getInt("id")));
        if (expand > 0) {
            TipoUsuarioDao otipoUsuarioDao = new TipoUsuarioDao(oConnection, "tipousuario");
            this.setObj_tipousuario((TipoUsuarioBean) otipoUsuarioDao.get(oResultSet.getInt("id_tipousuario"), expand - 1));
        } else {
            this.setId_tipousuario(oResultSet.getInt("id_tipousuario"));
        }
        return this;
    }

    @Override
    public String getColumns() {
        String strColumns = "";
        strColumns += "id,";
        strColumns += "dni,";
        strColumns += "nombre,";
        strColumns += "apellido1,";
        strColumns += "apellido2,";
        strColumns += "fecha_alta,";
        strColumns += "username,";
        strColumns += "password,";
        strColumns += "color_cita,";
        strColumns += "color_cita_realizada,";
        strColumns += "id_tipousuario";
        return strColumns;
    }

    @Override
    public String getValues() {
        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha_alta.toInstant();

        //Converting the Date to LocalDate
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
        
        String strColumns = "";
        strColumns += "null,";
        strColumns += EncodingHelper.quotate(dni) + ",";
        strColumns += EncodingHelper.quotate(nombre) + ",";
        strColumns += EncodingHelper.quotate(apellido1) + ",";
        strColumns += EncodingHelper.quotate(apellido2) + ",";
        strColumns += EncodingHelper.quotate(localDateTime.toString()) + ",";
        strColumns += EncodingHelper.quotate(username) + ",";
        strColumns += EncodingHelper.quotate(password) + ",";
        strColumns += EncodingHelper.quotate(color_cita) + ",";
        strColumns += EncodingHelper.quotate(color_cita_realizada) + ",";
        strColumns += id_tipousuario;
        return strColumns;
    }

    @Override
    public String getPairs() {
        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant instant = fecha_alta.toInstant();

        //Converting the Date to LocalDate
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
        
        String strPairs = "";
        strPairs += "id=" + id + ",";
        strPairs += "dni=" + EncodingHelper.quotate(dni) + ",";
        strPairs += "nombre=" + EncodingHelper.quotate(nombre) + ",";
        strPairs += "apellido1=" + EncodingHelper.quotate(apellido1) + ",";
        strPairs += "apellido2=" + EncodingHelper.quotate(apellido2) + ",";
        strPairs += "fecha_alta=" + EncodingHelper.quotate(localDateTime.toString()) + ",";
        strPairs += "username=" + EncodingHelper.quotate(username) + ",";
        strPairs += "color_cita=" + EncodingHelper.quotate(color_cita) + ",";
        strPairs += "color_cita_realizada=" + EncodingHelper.quotate(color_cita_realizada) + ",";
        strPairs += "id_tipousuario=" + id_tipousuario;
        strPairs += " WHERE id = " + id;
        return strPairs;
    }
    
    public String getPairsPassword() {
        String strPairs = "";
        strPairs += "password=" + EncodingHelper.quotate(password);
        strPairs += " WHERE id = " + id;
        return strPairs;
    }
    
}
