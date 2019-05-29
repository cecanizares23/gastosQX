/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */
package co.IngCarlos.gastosQX.mvc.dao;

import co.IngCarlos.gastosQX.common.util.AsignaAtributoStatement;
import co.IngCarlos.gastosQX.common.util.LoggerMessage;
import co.IngCarlos.gastosQX.mvc.dto.DetalleGastosDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class DetalleGastosDAO {

    /**
     *
     * @param conexion
     * @param datosDetalleGasto
     * @param usuario
     * @return
     */
    public boolean registrarDetalleGasto(Connection conexion, DetalleGastosDTO datosDetalleGasto, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO detalle_gasto (gast_id, arti_id, dega_cantidad, dega_registradopor)");
            cadSQL.append("VALUES ( ?, ?, ?, ?)");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, datosDetalleGasto.getIdGastos(), ps);
            AsignaAtributoStatement.setString(2, datosDetalleGasto.getIdArticulos(), ps);
            AsignaAtributoStatement.setString(3, datosDetalleGasto.getCantidad(), ps);
            AsignaAtributoStatement.setString(4, usuario, ps);

            nRows = ps.executeUpdate();

            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosDetalleGasto.setId(rs.getString(1));
                    registroExitoso = true;
                }
                rs.close();
                rs = null;
            }
        } catch (SQLException se) {
            LoggerMessage.getInstancia().loggerMessageException(se);
        }
        return registroExitoso;
    }

    /**
     * @param conexion
     * @param datosDetalleGasto
     * @param usuario
     * @return
     */
    public boolean actualizarDetalleGatos(Connection conexion, DetalleGastosDTO datosDetalleGasto, String usuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE detalle_gasto SET gast_id = ?, arti_id = ?, dega_registradopor = ?, dega_cantidad = ? WHERE dega_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosDetalleGasto.getIdGastos(), ps);
            AsignaAtributoStatement.setString(2, datosDetalleGasto.getIdArticulos(), ps);
            AsignaAtributoStatement.setString(3, datosDetalleGasto.getCantidad(), ps);
            AsignaAtributoStatement.setString(4, usuario, ps);
            AsignaAtributoStatement.setString(5, datosDetalleGasto.getId(), ps);

            nRows = ps.executeUpdate();
            if (nRows > 0) {
                registroExitoso = true;
            }
        } catch (SQLException se) {
            LoggerMessage.getInstancia().loggerMessageException(se);
            return false;
        }
        return registroExitoso;
    }

    /**
     *
     * @param conexion
     * @param id
     * @return
     */
    public boolean eliminarDetalleGasto(Connection conexion, String id) {

        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM detalle_gasto WHERE dega_id = ? ");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, id, ps);
            nRows = ps.executeUpdate();

            if (nRows > 0) {
                deleteExitoso = true;
            }
            ps.close();
            ps = null;

        } catch (SQLException e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                return false;
            }
        }
        return deleteExitoso;
    }

    /**
     *
     * @param conexion
     * @param idGasto
     * @return
     */
    public ArrayList<DetalleGastosDTO> listarDetalleGastoXIdGasto(Connection conexion, String idGasto) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<DetalleGastosDTO> listado = null;
        DetalleGastosDTO datosDetalleGasto = null;
        StringBuilder cadSQL = null;

        System.out.println("dao idGasto ::: " + idGasto);

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT dega.dega_id, dega.gast_id, dega.arti_id, dega.dega_cantidad, dega.dega_registradopor, ");
            cadSQL.append(" arti.arti_referencia, arti.arti_lote, arti.arti_unidadmedida, arti.arti_descripcion ");
            cadSQL.append(" FROM detalle_gasto dega ");
            cadSQL.append(" INNER JOIN articulos arti ON dega.arti_id = arti.arti_id ");
            cadSQL.append(" WHERE dega.gast_id = ? ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, idGasto, ps);
            rs = ps.executeQuery();

            listado = new ArrayList();

            while (rs.next()) {
                datosDetalleGasto = new DetalleGastosDTO();
                datosDetalleGasto.setId(rs.getString("dega_id"));
                datosDetalleGasto.setIdGastos(rs.getString("gast_id"));
                datosDetalleGasto.setIdArticulos(rs.getString("arti_id"));
                datosDetalleGasto.setCantidad(rs.getString("dega_cantidad"));
                datosDetalleGasto.setRegistradoPor(rs.getString("dega_registradopor"));
                datosDetalleGasto.setReferencia(rs.getString("arti_referencia"));
                datosDetalleGasto.setLote(rs.getString("arti_lote"));
                datosDetalleGasto.setUnidadMedidad(rs.getString("arti_unidadmedida"));
                datosDetalleGasto.setDescripcionArt(rs.getString("arti.arti_descripcion"));

                listado.add(datosDetalleGasto);
            }
            ps.close();
            ps = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            return null;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                return null;
            }
        }
        return listado;
    }

}
