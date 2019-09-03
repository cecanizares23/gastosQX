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
import co.IngCarlos.gastosQX.mvc.dto.OrdenCompraDTO;
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
public class OrdenCompraDAO {
    
    /**
     *
     * @param conexion
     * @param datosOrdenCompra
     * @param usuario
     * @return
     */
    public boolean registrarOrdenCompra(Connection conexion, OrdenCompraDTO datosOrdenCompra, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;                

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO orden_compra (gast_id, orco_fecha, orco_estado, orco_confirmado, orco_registradopor) ");
            cadSQL.append("VALUES ( ?, ?, ?, ?, ?)");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, datosOrdenCompra.getIdGasto(), ps);
            AsignaAtributoStatement.setString(2, datosOrdenCompra.getFecha(), ps);
            AsignaAtributoStatement.setString(3, Constantes.ESTADO_INACTIVO, ps);
            AsignaAtributoStatement.setString(4, Constantes.NO_CONFIRMADO, ps);
            AsignaAtributoStatement.setString(5, usuario, ps);            

            nRows = ps.executeUpdate();

            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosOrdenCompra.setId(rs.getString(1));
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
    public boolean actualizarOrdenCompra(Connection conexion, OrdenCompraDTO datosOrdenCompra, String usuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE medico_gasto SET orco_fecha = ?, orco_registradopor = ? WHERE orco_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosOrdenCompra.getFecha(), ps);                                              
            AsignaAtributoStatement.setString(2, usuario, ps);
            AsignaAtributoStatement.setString(3, datosOrdenCompra.getId(), ps); 
            

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
    public boolean eliminarOrdenCompra(Connection conexion, String id) {

        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM orden_compra WHERE orco_id = ? ");
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
    public ArrayList<OrdenCompraDTO> listarOrdenCompra(Connection conexion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<OrdenCompraDTO> listado = null;
        OrdenCompraDTO datosOrdenCompra = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT  orco_id, orco_fecha, orco_estado, orco_confirmado, orco_registradopor, orco_fecharegistro");            
            cadSQL.append(" FROM orden_compra ");
            //cadSQL.append(" WHERE  orco_estado = ?");
            
            ps = conexion.prepareStatement(cadSQL.toString());
            //AsignaAtributoStatement.setString(1, Constantes.ESTADO_ACTIVO, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosOrdenCompra = new OrdenCompraDTO();
                datosOrdenCompra.setId(rs.getString("orco_id"));
                datosOrdenCompra.setFecha(rs.getString("orco_fecha"));
                datosOrdenCompra.setEstado(rs.getString("orco_estado"));
                datosOrdenCompra.setConfirmado(rs.getString("orco_confirmado"));
                datosOrdenCompra.setRegistradoPor(rs.getString("orco_registradopor"));                
                datosOrdenCompra.setFechaRegistro(rs.getString("orco_fecharegistro"));
                
                listado.add(datosOrdenCompra);
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
     * @param idGasto
     * @return 
     */
    public OrdenCompraDTO ConsultarOrdenCompraXIdGasto(Connection conexion, String idGasto) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        OrdenCompraDTO datosOrdenCompra = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT orco_id, gast_id, orco_fecha, orco_estado, orco_confirmado, orco_registradopor ");
            cadSQL.append(" FROM orden_compra ");            
            cadSQL.append(" WHERE gast_id = ? ");
            //cadSQL.append(" ORDER BY gast.gast_id DESC ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, idGasto, ps);
            rs = ps.executeQuery();

            if (rs.next()) {
                datosOrdenCompra = new OrdenCompraDTO();
                datosOrdenCompra.setId(rs.getString("orco_id"));
                datosOrdenCompra.setIdGasto(rs.getString("gast_id"));
                datosOrdenCompra.setFecha(rs.getString("orco_fecha"));
                datosOrdenCompra.setEstado(rs.getString("orco_estado"));
                datosOrdenCompra.setConfirmado(rs.getString("orco_confirmado"));
                datosOrdenCompra.setRegistradoPor(rs.getString("orco_confirmado"));

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

        return datosOrdenCompra;
    }
    
    /**
     * 
     * @param conexion
     * @param id
     * @return 
     */
    public ArrayList<OrdenCompraDTO> listarOrdenesCompraXId(Connection conexion, String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        OrdenCompraDTO datosOrdenCompra = null;
        ArrayList<OrdenCompraDTO> listado = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT orco_id, gast_id, orco_fecha, orco_estado, orco_confirmado, orco_registradopor ");
            cadSQL.append(" FROM orden_compra ");            
            cadSQL.append(" WHERE orco_id = ? ");
            //cadSQL.append(" ORDER BY gast.gast_id DESC ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, id, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            
            while (rs.next()) {
                datosOrdenCompra = new OrdenCompraDTO();
                datosOrdenCompra.setId(rs.getString("orco_id"));
                datosOrdenCompra.setIdGasto(rs.getString("gast_id"));
                datosOrdenCompra.setFecha(rs.getString("orco_fecha"));
                datosOrdenCompra.setEstado(rs.getString("orco_estado"));
                datosOrdenCompra.setConfirmado(rs.getString("orco_confirmado"));
                datosOrdenCompra.setRegistradoPor(rs.getString("orco_registradopor"));

                listado.add(datosOrdenCompra);
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

        return listado;
    }
    
    /**
     *
     * @param conexion
     * @param fecha
     * @return
     */
    public ArrayList<OrdenCompraDTO> listarOrdenCompraXFecha(Connection conexion, String fecha) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<OrdenCompraDTO> listado = null;
        OrdenCompraDTO datosOrdenCompra = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT orco_id, gast_id, orco_fecha, orco_estado, orco_confirmado, orco_registradopor ");
            cadSQL.append(" FROM orden_compra ");            
            cadSQL.append(" WHERE orco_fecha = ? ");
            
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, fecha, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosOrdenCompra = new OrdenCompraDTO();
                datosOrdenCompra.setId(rs.getString("orco_id"));
                datosOrdenCompra.setIdGasto(rs.getString("gast_id"));
                datosOrdenCompra.setFecha(rs.getString("orco_fecha"));
                datosOrdenCompra.setEstado(rs.getString("orco_estado"));
                datosOrdenCompra.setConfirmado(rs.getString("orco_confirmado"));
                datosOrdenCompra.setRegistradoPor(rs.getString("orco_registradopor"));
                
                listado.add(datosOrdenCompra);
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
