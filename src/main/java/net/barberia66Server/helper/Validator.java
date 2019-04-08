/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.helper;

import java.time.LocalDateTime;

/**
 *
 * @author a073597589g
 */
public class Validator {

    public static boolean validateId(String entero) {
        return entero.matches("^[1-9][0-9]*$");
    }
    
    public static boolean validateDates(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        return fechaInicio.compareTo(fechaFin) < 0;
    }
    
}
