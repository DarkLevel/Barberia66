/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.connection.specificImplementation;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.constants.ConnectionConstants;

/**
 *
 * @author a073597589g
 */
public class C3P0Connection implements ConnectionInterface {

    private Connection oConnection;
    private ComboPooledDataSource config;

    @Override
    public Connection newConnection() throws Exception {
        config = new ComboPooledDataSource();

        config.setJdbcUrl(ConnectionConstants.getConnectionChain());
        config.setUser(ConnectionConstants.databaseLogin);
        config.setPassword(ConnectionConstants.databasePassword);
        config.setMaxPoolSize(ConnectionConstants.getDatabaseMaxPoolSize);
        config.setMinPoolSize(ConnectionConstants.getDatabaseMinPoolSize);

        config.setInitialPoolSize(5);

        config.setAcquireIncrement(5);

        config.setMaxStatements(100);

        try {

            //oConnectionPool = new ComboPooledDataSource(config);
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
