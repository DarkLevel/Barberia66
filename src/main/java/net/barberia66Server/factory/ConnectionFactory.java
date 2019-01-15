/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.factory;

import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.connection.specificImplementation.BoneCPConnection;
import net.barberia66Server.connection.specificImplementation.C3P0Connection;
import net.barberia66Server.connection.specificImplementation.DBCPConnection;
import net.barberia66Server.connection.specificImplementation.HikariConnection;
import net.barberia66Server.connection.specificImplementation.ViburConnection;
import net.barberia66Server.constants.ConnectionConstants;

/**
 *
 * @author a073597589g
 */
public class ConnectionFactory {

    public static ConnectionInterface getConnection(ConnectionConstants.EnumConstans enumConnection) {
        ConnectionInterface oConnectionInterface = null;
        switch (enumConnection) {
            case Hikari:
                oConnectionInterface = new HikariConnection();
                break;
            case DBCP:
                oConnectionInterface = new DBCPConnection();
                break;
            case BoneCP:
                oConnectionInterface = new BoneCPConnection();
                break;
            case C3P0:
                oConnectionInterface = new C3P0Connection();
                break;
            case Vibur:
                oConnectionInterface = new ViburConnection();
                break;
            default:
                oConnectionInterface = new HikariConnection();
                break;
        }
        return oConnectionInterface;

    }
}
