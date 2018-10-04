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
import co.IngCarlos.gastosQX.mvc.dto.TipoDocumentoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class TipoDocumentoDAO {
    
    /**
     *
     * @param conexion
     * @param estado
     * @return
     */
    public ArrayList<TipoDocumentoDTO> listarTipoDocumento(Connection conexion, String estado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<TipoDocumentoDTO> listado = null;
        TipoDocumentoDTO datosTipoDocumento = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT tido_id, tido_codigo, tido_descripcion FROM tipo_documento ");
            cadSQL.append(" WHERE tido_estado = ?");
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, estado, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosTipoDocumento = new TipoDocumentoDTO();
                datosTipoDocumento.setId(rs.getString("tido_id"));
                datosTipoDocumento.setCodigo(rs.getString("tido_codigo"));
                datosTipoDocumento.setDescripcion(rs.getString("tido_descripcion"));
                listado.add(datosTipoDocumento);
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

    /**
     *
     * @param conexion
     * @param datosTipoDocumento
     * @param usuario
     * @return
     */
    public boolean registrarTipoDocumento(Connection conexion, TipoDocumentoDTO datosTipoDocumento, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO tipo_documento (tido_codigo, tido_descripcion, tido_estado, tido_registradopor)");
            cadSQL.append("VALUES ( ?, ?, ?, ?)");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, datosTipoDocumento.getCodigo(), ps);
            AsignaAtributoStatement.setString(2, datosTipoDocumento.getDescripcion(), ps);
            AsignaAtributoStatement.setString(3, datosTipoDocumento.getEstado(), ps);
            AsignaAtributoStatement.setString(4, usuario, ps);

            nRows = ps.executeUpdate();

            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosTipoDocumento.setId(rs.getString(1));
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
     * @param datosTipoDocumento
     * @param usuario
     * @return
     */
    public boolean actualizarTipoDocumento(Connection conexion, TipoDocumentoDTO datosTipoDocumento, String usuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE tipo_documento SET tido_codigo = ?, tido_descripcion = ?, tido_estado = ? WHERE   tido_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosTipoDocumento.getCodigo(), ps);
            AsignaAtributoStatement.setString(2, datosTipoDocumento.getDescripcion(), ps);
            AsignaAtributoStatement.setString(3, datosTipoDocumento.getEstado(), ps);
            AsignaAtributoStatement.setString(4, datosTipoDocumento.getId(), ps);

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
     * @param estado
     * @return
     */
    public boolean eliminarTipoDocumento(Connection conexion, String id, String estado) {

        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM tipo_documento WHERE tido_id = ? AND tido_estado = ?");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, id, ps);
            AsignaAtributoStatement.setString(2, estado, ps);
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
     * @return
     */
    public ArrayList<TipoDocumentoDTO> listarTodosLosTipoDocumento(Connection conexion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<TipoDocumentoDTO> listado = null;
        TipoDocumentoDTO datosTipoDocumento = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT tido_id, tido_codigo, tido_descripcion, tido_estado FROM tipo_documento ");
            ps = conexion.prepareStatement(cadSQL.toString());
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosTipoDocumento = new TipoDocumentoDTO();
                datosTipoDocumento.setId(rs.getString("tido_id"));
                datosTipoDocumento.setCodigo(rs.getString("tido_codigo"));
                datosTipoDocumento.setDescripcion(rs.getString("tido_descripcion"));
                datosTipoDocumento.setEstado(rs.getString("tido_estado"));
                listado.add(datosTipoDocumento);
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
