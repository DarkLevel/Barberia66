/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.constants;

/**
 *
 * @author a073597589g
 */
public class ConnectionConstants {

    public static enum EnumConstans {
        Hikari,
        DBCP,
        BoneCP,
        C3P0,
        Vibur
    };

    public static final EnumConstans connectionPool = EnumConstans.Hikari;
    public static final String databaseName = "barberia66";
    public static final String databaseLogin = "root";
    public static final String databasePassword = "bitnami";
    public static final String databasePort = "3306";
    public static final String databaseHost = "localhost";
    public static final int getDatabaseMaxPoolSize = 10;
    public static final int getDatabaseMinPoolSize = 5;

    public static String getConnectionChain() {
        return "jdbc:mysql://" + ConnectionConstants.databaseHost + ":" + ConnectionConstants.databasePort + "/"
                + ConnectionConstants.databaseName;
    }
}
