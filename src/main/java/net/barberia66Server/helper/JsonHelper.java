/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.helper;

/**
 *
 * @author a073597589g
 */
public class JsonHelper {

    public static String strJson(int status, String msg) {
        String strJson = "";
        if (status == 200) {
            strJson = "{\"status\":" + status + ",\"message\":" + msg + "}";
        } else {
            strJson = "{\"status\":" + status + ",\"message\":\"" + msg + "\"}";
        }
        return strJson;
    }
}
