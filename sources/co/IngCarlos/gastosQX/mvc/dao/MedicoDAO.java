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
import co.IngCarlos.gastosQX.mvc.dto.MedicoDTO;
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
public class MedicoDAO {
   
    /**
     *
     * @param conexion
     * @param datosMedico
     * @param usuario
     * @return
     */
    public boolean registrarMedico(Connection conexion, MedicoDTO datosMedico, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO medico (espe_id, medi_cedula, medi_nombres, medi_apellidos, medi_registradopor)");
            cadSQL.append("VALUES ( ?, ?, ?, ?, ?)");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, datosMedico.getIdEspecialidad(), ps);
            AsignaAtributoStatement.setString(2, datosMedico.getCedula(), ps);
            AsignaAtributoStatement.setString(3, datosMedico.getNombres(), ps);
            AsignaAtributoStatement.setString(4, datosMedico.getApellidos(), ps);
            AsignaAtributoStatement.setString(5, usuario, ps);            

            nRows = ps.executeUpdate();

            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosMedico.setId(rs.getString(1));
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
     * @param datosMedico
     * @param usuario
     * @return
     */
    public boolean actualizarMedico(Connection conexion, MedicoDTO datosMedico, String usuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE medico SET espe_id = ?, medi_cedula = ?, medi_nombres = ?, medi_apellidos = ?, medi_registradopor = ? WHERE medi_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosMedico.getIdEspecialidad(), ps);
            AsignaAtributoStatement.setString(2, datosMedico.getCedula(), ps);            
            AsignaAtributoStatement.setString(3, datosMedico.getNombres(), ps);
            AsignaAtributoStatement.setString(4, datosMedico.getApellidos(), ps);            
            AsignaAtributoStatement.setString(5, usuario, ps);                        
            AsignaAtributoStatement.setString(6, datosMedico.getId(), ps);

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
    public boolean eliminarMedico(Connection conexion, String id) {

        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM medico WHERE medi_id = ? ");
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
     * @param id
     * @return
     */
    public ArrayList<MedicoDTO> listarMedicos(Connection conexion, String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<MedicoDTO> listado = null;
        MedicoDTO datosMedicos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT medi_id, espe_id, medi_cedula, medi_nombres, medi_apellidos, medi_registradopor, medi_fecharegistro ");            
            cadSQL.append(" FROM medico ");
            
            ps = conexion.prepareStatement(cadSQL.toString());
            //AsignaAtributoStatement.setString(1, idGasto, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosMedicos = new MedicoDTO();
                datosMedicos.setId(rs.getString("medi_id"));
                datosMedicos.setIdEspecialidad(rs.getString("espe_id"));
                datosMedicos.setCedula(rs.getString("medi_cedula"));
                datosMedicos.setNombres(rs.getString("medi_nombres"));
                datosMedicos.setApellidos(rs.getString("medi_apellidos"));
                datosMedicos.setRegistradoPor(rs.getString("medi_registradopor"));
                datosMedicos.setFechaRegistro(rs.getString("medi_fecharegistro"));
                listado.add(datosMedicos);
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
