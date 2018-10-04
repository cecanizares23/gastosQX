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
import co.IngCarlos.gastosQX.mvc.dto.MedicoGastoDTO;
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
public class MedicoGastoDAO {
    
    /**
     *
     * @param conexion
     * @param datosMedicoGasto
     * @param usuario
     * @return
     */
    public boolean registrarMedioGasto(Connection conexion, MedicoGastoDTO datosMedicoGasto, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO medico_gasto (gast_id, medi_id, mega_registradopor)");
            cadSQL.append("VALUES ( ?, ?, ?)");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, datosMedicoGasto.getIdGasto(), ps);
            AsignaAtributoStatement.setString(2, datosMedicoGasto.getIdMedico(), ps);            
            AsignaAtributoStatement.setString(3, usuario, ps);            

            nRows = ps.executeUpdate();

            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosMedicoGasto.setId(rs.getString(1));
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
     * @param datosMedicoGasto
     * @param usuario
     * @return
     */
    public boolean actualizarMedicoGasto(Connection conexion, MedicoGastoDTO datosMedicoGasto, String usuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE medico_gasto SET gast_id = ?, medi_id = ?, mega_registradopor = ? WHERE mega_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosMedicoGasto.getIdGasto(), ps);
            AsignaAtributoStatement.setString(2, datosMedicoGasto.getIdMedico(), ps);                                    
            AsignaAtributoStatement.setString(3, usuario, ps);                        
            AsignaAtributoStatement.setString(4, datosMedicoGasto.getId(), ps);

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
    public boolean eliminarMedicoGasto(Connection conexion, String id) {

        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM medico_gasto WHERE mega_id = ? ");
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
    public ArrayList<MedicoGastoDTO> listarMedicoGasto(Connection conexion, String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<MedicoGastoDTO> listado = null;
        MedicoGastoDTO datosMedicoGasto = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT  mega_id, gast_id, medi_id, mega_registradopor, mega_fecharegistro");            
            cadSQL.append(" FROM medico_gasto ");
            
            ps = conexion.prepareStatement(cadSQL.toString());
            //AsignaAtributoStatement.setString(1, idGasto, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosMedicoGasto = new MedicoGastoDTO();
                datosMedicoGasto.setId(rs.getString("mega_id"));
                datosMedicoGasto.setIdGasto(rs.getString("gast_id"));
                datosMedicoGasto.setIdMedico(rs.getString("medi_id"));                
                datosMedicoGasto.setRegistradoPor(rs.getString("mega_registradopor"));
                datosMedicoGasto.setFechaRegistro(rs.getString("mega_fecharegistro"));
                listado.add(datosMedicoGasto);
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
