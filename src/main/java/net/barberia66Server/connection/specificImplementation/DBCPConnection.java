/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.connection.specificImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.constants.ConnectionConstants;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author a073597589g
 */
public class DBCPConnection implements ConnectionInterface {

    private Connection oConnection;
    private BasicDataSource config;

    @Override
    public Connection newConnection() throws Exception {
        config = new BasicDataSource();
        config.setUrl(ConnectionConstants.getConnectionChain());
        config.setUsername(ConnectionConstants.databaseLogin);
        config.setPassword(ConnectionConstants.databasePassword);
        config.setMaxIdle(ConnectionConstants.getDatabaseMaxPoolSize);
        config.setMinIdle(ConnectionConstants.getDatabaseMinPoolSize);

        try {
            oConnection = config.getConnection();
            return oConnection;
        } catch (SQLException ex) {
            String msgError = this.getClass().getName() + ":" + (ex.getStackTrace()[1]).getMethodName();
            throw new Exception(msgError, ex);
        }
    }

    @Override
    public void disposeConnection() throws Exception {
        if (oConnection != null) {
            oConnection.close();
        }
        if (config != null) {
            config.close();
        }
    }

}
