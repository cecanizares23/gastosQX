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
import co.IngCarlos.gastosQX.common.util.Constantes;
import co.IngCarlos.gastosQX.common.util.LoggerMessage;
import co.IngCarlos.gastosQX.mvc.dto.ProcedimientoDTO;
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
public class ProcedimientoDAO {
    
    /**
     *
     * @param conexion
     * @param datosProcedimiento
     * @param usuario
     * @return
     */
    public boolean registrarProcedimiento(Connection conexion, ProcedimientoDTO datosProcedimiento, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO procedimiento (proc_codigo, proc_descripcion, proc_estado, proc_registradopor)");
            cadSQL.append("VALUES ( ?, ?, ?, ?)");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, datosProcedimiento.getCodigo(), ps);                       
            AsignaAtributoStatement.setString(2, datosProcedimiento.getDescripcion(), ps);
            AsignaAtributoStatement.setString(3, datosProcedimiento.getEstado(), ps);
            AsignaAtributoStatement.setString(4, usuario, ps);            

            nRows = ps.executeUpdate();

            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosProcedimiento.setId(rs.getString(1));
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
     * @param datosOrdenCompra
     * @param usuario
     * @return
     */
    public boolean actualizarProcedimiento(Connection conexion, ProcedimientoDTO datosOrdenCompra, String usuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE procedimiento SET proc_codigo = ?, proc_descripcion = ?, proc_estado = ?, proc_registradopor = ? WHERE proc_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosOrdenCompra.getCodigo(), ps);
            AsignaAtributoStatement.setString(2, datosOrdenCompra.getDescripcion(), ps);
            AsignaAtributoStatement.setString(3, datosOrdenCompra.getEstado(), ps);
            AsignaAtributoStatement.setString(4, usuario, ps);
            AsignaAtributoStatement.setString(5, datosOrdenCompra.getId(), ps); 
            

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
    public boolean eliminarProcedimiento(Connection conexion, String id) {

        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM procedimiento WHERE proc_id = ? ");
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
    public ArrayList<ProcedimientoDTO> listarProcedimiento(Connection conexion, String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<ProcedimientoDTO> listado = null;
        ProcedimientoDTO datosProcedimiento = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT  proc_id, proc_codigo, proc_descripcion, proc_estado, proc_registradopor, proc_fecharegistro");            
            cadSQL.append(" FROM procedimiento WHERE proc_estado = ?");
            
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, Constantes.ESTADO_ACTIVO, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosProcedimiento = new ProcedimientoDTO();
                datosProcedimiento.setId(rs.getString("proc_id"));
                datosProcedimiento.setCodigo(rs.getString("proc_codigo"));
                datosProcedimiento.setDescripcion(rs.getString("proc_descripcion"));
                datosProcedimiento.setEstado(rs.getString("proc_estado"));
                datosProcedimiento.setRegistradoPor(rs.getString("proc_registradopor"));                
                datosProcedimiento.setFechaRegistro(rs.getString("proc_fecharegistro"));
                
                listado.add(datosProcedimiento);
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
     * @param id
     * @param estado
     * @return 
     */
    public boolean activarEstadoProcedimiento(Connection conexion, String id, String estado) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE procedimiento SET proc_estado = ? WHERE proc_id = ?");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, estado, ps);
            AsignaAtributoStatement.setString(2, id, ps);
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
    public boolean inactivarEstadoProcedimiento(Connection conexion, String id, String estado) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE procedimiento SET proc_estado = ? WHERE proc_id = ?");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, estado, ps);
            AsignaAtributoStatement.setString(2, id, ps);
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
    public ProcedimientoDTO ConsultarProcedimientoXId(Connection conexion, String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;        
        ProcedimientoDTO datosProcedimientos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT proc_id, proc_codigo, proc_descripcion, proc_estado, proc_registradopor, proc_fecharegistro ");            
            cadSQL.append(" FROM procedimiento where proc_id = ?");
            
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, id, ps);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                datosProcedimientos = new ProcedimientoDTO();
                datosProcedimientos.setId(rs.getString("proc_id"));
                datosProcedimientos.setCodigo(rs.getString("proc_codigo"));
                datosProcedimientos.setDescripcion(rs.getString("proc_descripcion"));
                datosProcedimientos.setEstado(rs.getString("proc_estado"));
                datosProcedimientos.setRegistradoPor(rs.getString("proc_registradopor"));
                datosProcedimientos.setFechaRegistro(rs.getString("proc_fecharegistro"));
                
                //listado.add(datosMedicos);
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
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                return null;
            }
        }

        return datosProcedimientos;
    }
    
}
