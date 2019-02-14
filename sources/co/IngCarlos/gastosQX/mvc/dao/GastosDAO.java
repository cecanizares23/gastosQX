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
import co.IngCarlos.gastosQX.mvc.dto.GastosDTO;
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
public class GastosDAO {
    
    /**
     *
     * @param conexion
     * @param datosGastos
     * @param usuario
     * @return
     */
    public boolean registrarGastos(Connection conexion, GastosDTO datosGastos, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO gastos (proc_id, gast_paciente, gast_cedula, gast_fecha, gast_estado, gast_registradopor)");
            cadSQL.append("VALUES ( ?, ?, ?, ?, ?, ?)");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, datosGastos.getIdProcedimiento(), ps);
            AsignaAtributoStatement.setString(2, datosGastos.getPaciente(), ps);
            AsignaAtributoStatement.setString(3, datosGastos.getCedula(), ps);
            AsignaAtributoStatement.setString(4, datosGastos.getFecha(), ps);
            AsignaAtributoStatement.setString(5, datosGastos.getEstado(), ps);
            AsignaAtributoStatement.setString(6, usuario, ps);            

            nRows = ps.executeUpdate();

            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosGastos.setId(rs.getString(1));
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
     * @param datosGastos
     * @param usuario
     * @return
     */
    public boolean actualizarGastos(Connection conexion, GastosDTO datosGastos, String usuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE gastos SET proc_id = ?, gast_paciente = ?, gast_cedula = ?, gast_fecha = ?, gast_estado = ?, gast_registradopor = ? WHERE gast_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosGastos.getIdProcedimiento(), ps);
            AsignaAtributoStatement.setString(2, datosGastos.getPaciente(), ps);            
            AsignaAtributoStatement.setString(3, datosGastos.getCedula(), ps);
            AsignaAtributoStatement.setString(4, datosGastos.getFecha(), ps);
            AsignaAtributoStatement.setString(5, datosGastos.getEstado(), ps);
            AsignaAtributoStatement.setString(6, usuario, ps);                        
            AsignaAtributoStatement.setString(7, datosGastos.getId(), ps);

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
    public boolean eliminarGastos(Connection conexion, String id) {

        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM gastos WHERE gast_id = ? ");
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
     * @return
     */
    public ArrayList<GastosDTO> listarGastos(Connection conexion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<GastosDTO> listado = null;
        GastosDTO datosGastos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT gast_id, proc_id, gast_paciente, gast_cedula, gast_fecha, gast_estado, gast_registradopor, gast_fecharegistro ");            
            cadSQL.append(" FROM gastos ");
            cadSQL.append(" WHERE gast_estado = ? ");
            
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, Constantes.ESTADO_ACTIVO, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosGastos = new GastosDTO();
                datosGastos.setId(rs.getString("gast_id"));
                datosGastos.setIdProcedimiento(rs.getString("proc_id"));
                datosGastos.setPaciente(rs.getString("gast_paciente"));
                datosGastos.setCedula(rs.getString("gast_cedula"));
                datosGastos.setFecha(rs.getString("gast_fecha"));
                datosGastos.setEstado(rs.getString("gast_estado"));
                datosGastos.setRegistradoPor(rs.getString("gast_registradopor"));
                datosGastos.setFechaRegistro(rs.getString("gast_fecharegistro"));
                listado.add(datosGastos);
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
    public boolean activarEstadoGastos(Connection conexion, String id, String estado) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE gastos SET gast_estado = ? WHERE gast_id = ?");
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
    public boolean inactivarEstadoGastos(Connection conexion, String id, String estado) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE gastos SET gast_estado = ? WHERE gast_id = ?");
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
    public GastosDTO ConsultarGastosXId(Connection conexion, String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;        
        GastosDTO datosGastos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT gast_id, proc_id, gast_paciente, gast_cedula, gast_fecha, gast_estado, gast_registradopor, gast_fecharegistro ");            
            cadSQL.append(" FROM gastos WHERE gast_id = ?");
            
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, id, ps);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                datosGastos = new GastosDTO();
                datosGastos.setId(rs.getString("gast_id"));
                datosGastos.setIdProcedimiento(rs.getString("proc_id"));
                datosGastos.setPaciente(rs.getString("gast_paciente"));
                datosGastos.setCedula(rs.getString("gast_cedula"));
                datosGastos.setFecha(rs.getString("gast_fecha"));
                datosGastos.setEstado(rs.getString("gast_estado"));
                datosGastos.setRegistradoPor(rs.getString("espe_registradopor"));
                datosGastos.setFechaRegistro(rs.getString("espe_fecharegistro"));
                
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

        return datosGastos;
    }

    
}
