/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.common.connection;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author carlos
 */
public class DataBaseConnection {
    
    private static DataBaseConnection instancia = null;

    /**
     * Creates a new instance of DataBaseConnection
     */
    public DataBaseConnection() {
    }
    
    /**
     *
     * @return
     */
    public static synchronized DataBaseConnection getInstance() {

        if (instancia == null) {
            instancia = new DataBaseConnection();
        }

        return instancia;

    }

    /**
     *
     * @param contextDataResourceName
     * @return
     * @throws Exception
     */
    public Connection getConnection(String contextDataResourceName) throws Exception {

        Context initCtx = null;
        Context envCtx = null;
        DataSource ds = null;
        Connection con = null;

        try {

            initCtx = new InitialContext();
            envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup(contextDataResourceName);
            con = ds.getConnection();

        } finally {
        }

        return con;

    }
    
}
