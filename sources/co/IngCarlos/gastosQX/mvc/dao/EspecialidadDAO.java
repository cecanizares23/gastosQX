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
import co.IngCarlos.gastosQX.mvc.dto.EspecialidadDTO;
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
public class EspecialidadDAO {
   
    /**
     *
     * @param conexion
     * @param datosEspecialidad
     * @param usuario
     * @return
     */
    public boolean registrarEspecialidad(Connection conexion, EspecialidadDTO datosEspecialidad, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO especialidad (espe_descripcion, espe_prefijo, espe_registradopor)");
            cadSQL.append("VALUES ( ?, ?, ?)");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, datosEspecialidad.getDescripcion(), ps);
            AsignaAtributoStatement.setString(2, datosEspecialidad.getPrefijo(), ps);            
            AsignaAtributoStatement.setString(3, usuario, ps);            

            nRows = ps.executeUpdate();

            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosEspecialidad.setId(rs.getString(1));
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
     * @param datosEspecialidad
     * @param usuario
     * @return
     */
    public boolean actualizarEspecialidad(Connection conexion, EspecialidadDTO datosEspecialidad, String usuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE especialidad SET espe_descripcion = ?, espe_prefijo = ?, espe_registradopor = ? WHERE espe_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosEspecialidad.getDescripcion(), ps);
            AsignaAtributoStatement.setString(2, datosEspecialidad.getPrefijo(), ps);            
            AsignaAtributoStatement.setString(3, usuario, ps);                        
            AsignaAtributoStatement.setString(4, datosEspecialidad.getId(), ps);

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
    public boolean eliminarEspecialidad(Connection conexion, String id) {

        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM especialidad WHERE espe_id = ? ");
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
    public ArrayList<EspecialidadDTO> listarTodasEspecialidades(Connection conexion, String idGasto) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<EspecialidadDTO> listado = null;
        EspecialidadDTO datosEspecialidad = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT espe_id, espe_descripcion, espe_prefijo, espe_registradopor, espe_fecharegistro ");            
            cadSQL.append(" FROM especialidad ");
            
            ps = conexion.prepareStatement(cadSQL.toString());
            //AsignaAtributoStatement.setString(1, idGasto, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosEspecialidad = new EspecialidadDTO();
                datosEspecialidad.setId(rs.getString("espe_id"));
                datosEspecialidad.setDescripcion(rs.getString("espe_descripcion"));
                datosEspecialidad.setPrefijo(rs.getString("espe_prefijo"));
                datosEspecialidad.setRegistradoPor(rs.getString("espe_registradopor"));                                
                listado.add(datosEspecialidad);
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
