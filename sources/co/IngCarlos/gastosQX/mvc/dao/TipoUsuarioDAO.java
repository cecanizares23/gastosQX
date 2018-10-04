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
import co.IngCarlos.gastosQX.mvc.dto.TipoUsuarioDTO;
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
public class TipoUsuarioDAO {
    
    /**
     *
     * @param conexion
     * @param estado
     * @return
     */
    public ArrayList<TipoUsuarioDTO> listarCargos(Connection conexion, String estado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<TipoUsuarioDTO> listado = null;
        TipoUsuarioDTO datosCargo = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT tius_id, tius_descripcion FROM tipo_usuario");
            cadSQL.append(" WHERE tius_estado = ?");
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, estado, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosCargo = new TipoUsuarioDTO();
                datosCargo.setId(rs.getString("tius_id"));
                datosCargo.setDescripcion(rs.getString("tius_descripcion"));
                listado.add(datosCargo);
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
     * @param datosCargo
     * @param usuario
     * @return
     */
    public boolean registrarCargo(Connection conexion, TipoUsuarioDTO datosCargo, String usuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO tipo_usuario (tius_descripcion, tius_estado, tius_registradopor)");
            cadSQL.append(" VALUES ( ?, ?, ?)");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosCargo.getDescripcion(), ps);
            AsignaAtributoStatement.setString(2, datosCargo.getEstado(), ps);
            AsignaAtributoStatement.setString(3, usuario, ps);
            nRows = ps.executeUpdate();
            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosCargo.setId(rs.getString(1));
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
     * @param datosCargo
     * @param usuario
     * @return
     */
    public boolean actualizarCargo(Connection conexion, TipoUsuarioDTO datosCargo, String usuario) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE tipo_usuario SET tius_descripcion = ?, tius_estado = ? WHERE   tius_id = ?");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosCargo.getDescripcion(), ps);
            AsignaAtributoStatement.setString(2, datosCargo.getEstado(), ps);
            AsignaAtributoStatement.setString(3, datosCargo.getId(), ps);
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
    public boolean eliminarCargo(Connection conexion, String id, String estado) {
        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM tipo_usuario WHERE tius_id = ? AND tius_estado = ?");
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
    public ArrayList<TipoUsuarioDTO> listarTodosLosCargos(Connection conexion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<TipoUsuarioDTO> listado = null;
        TipoUsuarioDTO datosCargo = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT tius_id, tius_descripcion, tius_estado FROM tipo_usuario");
            ps = conexion.prepareStatement(cadSQL.toString());
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosCargo = new TipoUsuarioDTO();
                datosCargo.setId(rs.getString("tius_id"));
                datosCargo.setDescripcion(rs.getString("tius_descripcion"));
                datosCargo.setEstado(rs.getString("tius_estado"));
                listado.add(datosCargo);
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
