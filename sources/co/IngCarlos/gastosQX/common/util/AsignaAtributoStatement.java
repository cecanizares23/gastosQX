/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.common.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Calendar;

/**
 *
 * @author carlos
 */
public class AsignaAtributoStatement {
    
    public AsignaAtributoStatement() {
    }

    public static void setString(int index, String valor, PreparedStatement p) throws SQLException {
        if (ValidaString.isNullOrEmptyString(valor)) {
            p.setNull(index, java.sql.Types.CHAR);
        } else {
            p.setString(index, valor.trim());
        }
    }

    public static void setInt(int index, String valor, PreparedStatement p) throws SQLException {
        if (ValidaString.isNullOrEmptyString(valor)) {
            p.setNull(index, java.sql.Types.INTEGER);
        } else {
            p.setInt(index, Integer.parseInt(valor.trim()));
        }
    }

    public static void setFloat(int index, String valor, PreparedStatement p) throws SQLException {
        if (ValidaString.isNullOrEmptyString(valor)) {
            p.setNull(index, java.sql.Types.FLOAT);
        } else {
            p.setFloat(index, Float.parseFloat(valor.trim()));
        }
    }

    public static void setDouble(int index, String valor, PreparedStatement ps) throws SQLException {
        if (ValidaString.isNullOrEmptyString(valor)) {
            ps.setNull(index, java.sql.Types.DOUBLE);
        } else {
            ps.setDouble(index, Double.parseDouble(valor.trim()));
        }
    }

    public static void setString(int index, String valor, CallableStatement c) throws SQLException {
        if (ValidaString.isNullOrEmptyString(valor)) {
            c.setNull(index, java.sql.Types.CHAR);
        } else {
            c.setString(index, valor.trim());
        }
    }

    public static void setInt(int index, String valor, CallableStatement c) throws SQLException {

        if (ValidaString.isNullOrEmptyString(valor)) {
            c.setNull(index, java.sql.Types.INTEGER);
        } else {
            c.setInt(index, Integer.parseInt(valor.trim()));
        }
    }

    public static void setLong(int index, String valor, CallableStatement c) throws SQLException {
        if (ValidaString.isNullOrEmptyString(valor)) {
            c.setNull(index, java.sql.Types.INTEGER);
        } else {
            c.setLong(index, Long.parseLong(valor.trim()));
        }
    }

    public static void setLong(int index, String valor, PreparedStatement ps) throws SQLException {
        if (ValidaString.isNullOrEmptyString(valor)) {
            ps.setNull(index, java.sql.Types.INTEGER);
        } else {
            ps.setLong(index, Long.parseLong(valor.trim()));
        }
    }

    public static void setFloat(int index, String valor, CallableStatement c) throws SQLException {
        if (ValidaString.isNullOrEmptyString(valor)) {
            c.setNull(index, java.sql.Types.FLOAT);
        } else {
            c.setFloat(index, Float.parseFloat(valor.trim()));
        }
    }

    public static void setDate(int index, String valor, CallableStatement c) throws SQLException {
        if (ValidaString.isNullOrEmptyString(valor)) {
            c.setNull(index, java.sql.Types.DATE);
        } else {
            c.setDate(index, Formato.parseSqlDate(valor, Generales.DATE_FECHA));
        }
    }

    public static void setDate(int index, String valor, PreparedStatement ps) throws SQLException {
        if (ValidaString.isNullOrEmptyString(valor)) {
            ps.setNull(index, java.sql.Types.DATE);
        } else {
            ps.setDate(index, Formato.parseSqlDate(valor,
                    Generales.DATE_FECHA_HORA));
        }
    }

    public static void setDate(int index, java.sql.Date valor, PreparedStatement ps) throws SQLException {
        if (valor == null) {
            ps.setNull(index, java.sql.Types.DATE);
        } else {
            ps.setDate(index, valor);
        }
    }

    public static void setDate(int index, Calendar cal, PreparedStatement ps) throws SQLException {
        if (cal == null) {
            ps.setNull(index, java.sql.Types.DATE);
        } else {
            ps.setObject(index, Formato.parseSqlTimestamp(cal));
        }
    }

    public static void setDate(int index, Calendar cal, CallableStatement cs) throws SQLException {
        if (cal == null) {
            cs.setNull(index, java.sql.Types.DATE);
        } else {
            cs.setObject(index, Formato.parseSqlDate(cal));
        }
    }

    public static void setTime(int index, Time valor, PreparedStatement ps) throws SQLException {
        if (valor == null) {
            ps.setNull(index, java.sql.Types.DATE);
        } else {
            ps.setTime(index, valor);
        }
    }
    
}
