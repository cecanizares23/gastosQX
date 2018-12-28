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
import co.IngCarlos.gastosQX.mvc.dto.UsuarioDTO;
import co.IngCarlos.gastosQX.mvc.dto.UsuarioSeguridadDTO;
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
public class UsuarioSeguridadDAO {
    
    /**
     * 
     * @param conexion
     * @param usuario1
     * @param usuarioSeguridad
     * @param usuario
     * @return
     * @throws SQLException 
     */
    public boolean registrarUsuarioSeguridad(Connection conexion, UsuarioDTO usuario1, UsuarioSeguridadDTO usuarioSeguridad, String usuario) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;

        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO usuario_seguridad(usua_id,usse_usuario,usse_clave, usse_registradopor)");
            cadSQL.append(" VALUES (?, ?, SHA2(?,256), ?) ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, usuario1.getId(), ps);
            AsignaAtributoStatement.setString(2, usuarioSeguridad.getUsuario(), ps);
            AsignaAtributoStatement.setString(3, usuarioSeguridad.getClave(), ps);
            AsignaAtributoStatement.setString(4, usuario, ps);

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

    public boolean validarUsuarioSeguridad(Connection conexion, UsuarioSeguridadDTO usuarioSeguridad) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean validado = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT  usse_usuario");
            cadSQL.append(" FROM usuario_seguridad ");
            cadSQL.append(" WHERE usse_usuario = ?");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, usuarioSeguridad.getUsuario(), ps);

            rs = ps.executeQuery();
            if (rs.next()) {

                validado = true;
            }
        } catch (SQLException se) {
            LoggerMessage.getInstancia().loggerMessageException(se);
            return false;
        }
        return validado;
    }

    /**
     *
     * @param conexion
     * @param datosUsuarioSeguridad
     * @param datosUsuario
     * @return
     */
    public boolean editarUsuarioSeguridad(Connection conexion, UsuarioDTO datosUsuario, UsuarioSeguridadDTO datosUsuarioSeguridad) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE usuario_seguridad SET usse_clave = SHA2(?,256)  WHERE  usua_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosUsuarioSeguridad.getClave(), ps);
            AsignaAtributoStatement.setString(2, datosUsuario.getId(), ps);

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
     * @param datosUsuarioSeguridad
     * @return
     */
    public boolean cambiarContrasenia(Connection conexion, UsuarioSeguridadDTO datosUsuarioSeguridad) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        UsuarioSeguridadDTO datosSeguridad = datosUsuarioSeguridad;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE usuario_seguridad SET usse_clave = SHA2(?,256) WHERE  usua_id = ? AND usse_clave = SHA2(?,256) ");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosSeguridad.getNuevaClave(), ps);
            AsignaAtributoStatement.setString(2, datosSeguridad.getIdUsuario(), ps);
            AsignaAtributoStatement.setString(3, datosSeguridad.getClave(), ps);

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

    public boolean validarUsuario(Connection conexion, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<UsuarioSeguridadDTO> listarUsuarios = null;
        boolean usuarioValido = false;

        StringBuilder cadSQL = null;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT usua_id, usse_usuario");
            cadSQL.append(" FROM usuario_seguridad ");
            cadSQL.append(" WHERE  usse_usuario = ? ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, usuario, ps);

            rs = ps.executeQuery();

            listarUsuarios = new ArrayList();

            while (rs.next()) {
                usuarioValido = true;
            }
            ps.close();
            ps = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            return usuarioValido;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (listarUsuarios != null && listarUsuarios.isEmpty()) {
                    listarUsuarios = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                return usuarioValido;
            }
        }

        return usuarioValido;
    }

    /**
     *
     * @param conexion
     * @param usuaId
     * @param contrasenia
     * @return
     */
    public boolean generarContrasenia(Connection conexion, String usuaId, String contrasenia) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE usuario_seguridad SET usse_clave = SHA2(?,256) WHERE  usua_id = ? ");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, contrasenia, ps);
            AsignaAtributoStatement.setString(2, usuaId, ps);

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
     * @param datosUsuarioSeguridad
     * @return
     */
    public boolean guardarContrasenia(Connection conexion, UsuarioSeguridadDTO datosUsuarioSeguridad) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        UsuarioSeguridadDTO datosSeguridad = datosUsuarioSeguridad;
        boolean registroExitoso = false;

        try {
           
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE usuario_seguridad SET usse_clave = SHA2(?,256) WHERE  usua_id = ? ");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosSeguridad.getClave(), ps);
            AsignaAtributoStatement.setString(2, datosSeguridad.getIdUsuario(), ps);

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
     * @param datosUsuarioSeguridad
     * @param datosUsuario
     * @return
     */
    public boolean editarNombreUsuarioSeguridad(Connection conexion, UsuarioDTO datosUsuario, UsuarioSeguridadDTO datosUsuarioSeguridad) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            System.out.println("llega dao usse_usuario");
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE usuario_seguridad SET usse_usuario = ?  WHERE  usua_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosUsuarioSeguridad.getUsuario(), ps);
            AsignaAtributoStatement.setString(2, datosUsuario.getId(), ps);

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
    
}
