/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.helper;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author a073597589g
 */
public class SqlBuilder {

    public static String buildSqlOrder(HashMap<String, String> hmOrder) {
        String strSQLOrder;
        if (hmOrder != null) {
            strSQLOrder = " ORDER BY ";
            for (Map.Entry<String, String> oPar : hmOrder.entrySet()) {
                strSQLOrder += oPar.getKey();
                strSQLOrder += " ";
                strSQLOrder += oPar.getValue();
                strSQLOrder += ",";
            }
            strSQLOrder = strSQLOrder.substring(0, strSQLOrder.length() - 1);
        } else {
            strSQLOrder = "";
        }
        return strSQLOrder;
    }
}
