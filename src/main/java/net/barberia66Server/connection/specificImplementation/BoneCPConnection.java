/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.connection.specificImplementation;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;
import java.sql.SQLException;
import net.barberia66Server.connection.publicInterface.ConnectionInterface;
import net.barberia66Server.constants.ConnectionConstants;

/**
 *
 * @author a073597589g
 */
public class BoneCPConnection implements ConnectionInterface {

    private Connection oConnection;
    private BoneCP oConnectionPool;

    @Override
    public Connection newConnection() throws Exception {

        BoneCPConfig config = new BoneCPConfig();

        config.setJdbcUrl(ConnectionConstants.getConnectionChain()); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb

        config.setUsername(ConnectionConstants.databaseLogin);
        config.setPassword(ConnectionConstants.databasePassword);
        config.setMinConnectionsPerPartition(ConnectionConstants.getDatabaseMinPoolSize);
        config.setMaxConnectionsPerPartition(ConnectionConstants.getDatabaseMaxPoolSize);
        config.setPartitionCount(1);

        try {
            oConnectionPool = new BoneCP(config);
            oConnection = (Connection) oConnectionPool.getConnection();
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
        if (oConnectionPool != null) {
            oConnectionPool.close();
        }
    }
}
