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
import co.IngCarlos.gastosQX.mvc.dto.ArticulosDTO;
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
public class ArticulosDAO {

    /**
     *
     * @param conexion
     * @param datosArticulos
     * @param usuario
     * @return
     */
    public boolean registrarArticulo(Connection conexion, ArticulosDTO datosArticulos, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO articulos (arti_referencia, arti_lote, arti_descripcion, arti_cantidad, arti_unidadmedida, arti_cantidadmax, arti_cantidadmin, arti_estado, arti_registradopor)");
            cadSQL.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, datosArticulos.getReferencia(), ps);
            AsignaAtributoStatement.setString(2, datosArticulos.getLote(), ps);
            AsignaAtributoStatement.setString(3, datosArticulos.getDescripcion(), ps);
            AsignaAtributoStatement.setString(4, datosArticulos.getCantidad(), ps);
            AsignaAtributoStatement.setString(5, datosArticulos.getUnidadMedidad(), ps);
            AsignaAtributoStatement.setString(6, datosArticulos.getCantidadMax(), ps);
            AsignaAtributoStatement.setString(7, datosArticulos.getCantidadMin(), ps);
            AsignaAtributoStatement.setString(8, datosArticulos.getEstado(), ps);
            AsignaAtributoStatement.setString(9, usuario, ps);

            nRows = ps.executeUpdate();

            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosArticulos.setId(rs.getString(1));
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
     * @param datosArticulos
     * @param usuario
     * @return
     */
    public boolean actualizarArticulo(Connection conexion, ArticulosDTO datosArticulos, String usuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            System.out.println("datosArticulos " + datosArticulos.toStringJson());
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE articulos SET arti_referencia = ?, arti_lote = ?, arti_cantidad = ?, arti_unidadmedida = ?, arti_cantidadmax = ?, arti_cantidadmin = ?,"
                    + "  arti_registradopor = ? WHERE   arti_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosArticulos.getReferencia(), ps);
            AsignaAtributoStatement.setString(2, datosArticulos.getLote(), ps);
            AsignaAtributoStatement.setString(3, datosArticulos.getCantidad(), ps);
            AsignaAtributoStatement.setString(4, datosArticulos.getUnidadMedidad(), ps);
            AsignaAtributoStatement.setString(5, datosArticulos.getCantidadMax(), ps);
            AsignaAtributoStatement.setString(6, datosArticulos.getCantidadMin(), ps);
            AsignaAtributoStatement.setString(7, usuario, ps);
            AsignaAtributoStatement.setString(8, datosArticulos.getId(), ps);

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
    public boolean eliminarArticulo(Connection conexion, String id) {

        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM articulos WHERE arti_id = ? ");
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
    public ArrayList<ArticulosDTO> listarTodosLosArticulos(Connection conexion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<ArticulosDTO> listado = null;
        ArticulosDTO datosArticulos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT arti_id, arti_referencia, arti_lote, arti_descripcion, arti_cantidad, arti_unidadmedida, ");
            cadSQL.append(" arti_cantidadmax, arti_cantidadmin, arti_estado, arti_registradopor, arti_fecharegistro ");
            cadSQL.append(" FROM articulos ");
            ps = conexion.prepareStatement(cadSQL.toString());
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosArticulos = new ArticulosDTO();
                datosArticulos.setId(rs.getString("arti_id"));
                datosArticulos.setReferencia(rs.getString("arti_referencia"));
                datosArticulos.setLote(rs.getString("arti_lote"));
                datosArticulos.setDescripcion(rs.getString("arti_descripcion"));
                datosArticulos.setCantidad(rs.getString("arti_cantidad"));
                datosArticulos.setUnidadMedidad(rs.getString("arti_unidadmedida"));
                datosArticulos.setCantidadMax(rs.getString("arti_cantidadmax"));
                datosArticulos.setCantidadMin(rs.getString("arti_cantidadmin"));
                datosArticulos.setEstado(rs.getString("arti_estado"));
                datosArticulos.setRegistradoPor(rs.getString("arti_registradopor"));
                datosArticulos.setFechaRegistro(rs.getString("arti_fecharegistro"));
                listado.add(datosArticulos);
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
     * @param datosArticulo
     * @return
     * @throws SQLException
     */
    public boolean validarReferencia(Connection conexion, ArticulosDTO datosArticulo) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean validado = false;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT  arti_referencia");
            cadSQL.append(" FROM articulos ");
            cadSQL.append(" WHERE arti_referencia = ?");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, datosArticulo.getReferencia(), ps);

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
     * @param id
     * @param estado
     * @return
     */
    public boolean activarEstadoArticulo(Connection conexion, String id, String estado) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE articulos SET arti_estado = ? WHERE arti_id = ?");
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
    public boolean inactivarEstadoArticulo(Connection conexion, String id, String estado) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE articulos SET arti_estado = ? WHERE arti_id = ?");
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
    public ArticulosDTO ConsultarArticulosXId(Connection conexion, String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArticulosDTO datosArticulos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT arti_id, arti_referencia, arti_lote, arti_descripcion, arti_cantidad, arti_unidadmedida, ");
            cadSQL.append(" arti_cantidadmax, arti_cantidadmin, arti_estado, arti_registradopor, arti_fecharegistro ");
            cadSQL.append(" FROM articulos where arti_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, id, ps);
            rs = ps.executeQuery();

            if (rs.next()) {
                datosArticulos = new ArticulosDTO();
                datosArticulos.setId(rs.getString("arti_id"));
                datosArticulos.setReferencia(rs.getString("arti_referencia"));
                datosArticulos.setLote(rs.getString("arti_lote"));
                datosArticulos.setDescripcion(rs.getString("arti_descripcion"));
                datosArticulos.setCantidad(rs.getString("arti_cantidad"));
                datosArticulos.setUnidadMedidad(rs.getString("arti_unidadmedida"));
                datosArticulos.setCantidadMax(rs.getString("arti_cantidadmax"));
                datosArticulos.setCantidadMin(rs.getString("arti_cantidadmin"));
                datosArticulos.setEstado(rs.getString("arti_estado"));
                datosArticulos.setRegistradoPor(rs.getString("arti_estado"));
                datosArticulos.setFechaRegistro(rs.getString("arti_fecharegistro"));

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

        return datosArticulos;
    }

    /**
     *
     * @param conexion
     * @param condicion
     * @return
     */
    public ArrayList<ArticulosDTO> buscarPorReferencia(Connection conexion, String condicion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<ArticulosDTO> listado = null;
        ArticulosDTO datosArticulos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT arti.arti_id, arti.arti_referencia, arti.arti_lote, arti.arti_descripcion, arti.arti_cantidad, ");
            cadSQL.append(" arti.arti_unidadmedida, arti.arti_cantidadmax, arti.arti_cantidadmin, arti.arti_estado ");
            cadSQL.append(" FROM articulos arti ");
            cadSQL.append(" WHERE arti.arti_referencia LIKE '%" + condicion + "%'");

            ps = conexion.prepareStatement(cadSQL.toString());
            //AsignaAtributoStatement.setString(1, condicion, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();

            while (rs.next()) {
                datosArticulos = new ArticulosDTO();
                datosArticulos.setId(rs.getString("arti_id"));
                datosArticulos.setReferencia(rs.getString("arti_referencia"));
                datosArticulos.setLote(rs.getString("arti_lote"));
                datosArticulos.setDescripcion(rs.getString("arti_descripcion"));
                datosArticulos.setCantidad(rs.getString("arti_cantidad"));
                datosArticulos.setUnidadMedidad(rs.getString("arti_unidadmedida"));
                datosArticulos.setCantidadMax(rs.getString("arti_cantidadmax"));
                datosArticulos.setCantidadMin(rs.getString("arti_cantidadmin"));
                datosArticulos.setEstado(rs.getString("arti_estado"));

                listado.add(datosArticulos);
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
     * @param condicion
     * @return
     */
    public ArrayList<ArticulosDTO> buscarPorDescripcion(Connection conexion, String condicion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<ArticulosDTO> listado = null;
        ArticulosDTO datosArticulos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT arti.arti_id, arti.arti_referencia, arti.arti_lote, arti.arti_descripcion, arti.arti_cantidad, ");
            cadSQL.append(" arti.arti_unidadmedida, arti.arti_cantidadmax, arti.arti_cantidadmin, arti.arti_estado ");
            cadSQL.append(" FROM articulos arti ");
            cadSQL.append(" WHERE arti.arti_descripcion LIKE '%" + condicion + "%'");

            ps = conexion.prepareStatement(cadSQL.toString());            
            rs = ps.executeQuery();
            listado = new ArrayList();

            while (rs.next()) {
                datosArticulos = new ArticulosDTO();
                datosArticulos.setId(rs.getString("arti_id"));
                datosArticulos.setReferencia(rs.getString("arti_referencia"));
                datosArticulos.setLote(rs.getString("arti_lote"));
                datosArticulos.setDescripcion(rs.getString("arti_descripcion"));
                datosArticulos.setCantidad(rs.getString("arti_cantidad"));
                datosArticulos.setUnidadMedidad(rs.getString("arti_unidadmedida"));
                datosArticulos.setCantidadMax(rs.getString("arti_cantidadmax"));
                datosArticulos.setCantidadMin(rs.getString("arti_cantidadmin"));
                datosArticulos.setEstado(rs.getString("arti_estado"));

                listado.add(datosArticulos);
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
     * @param cantidad
     * @return 
     */
    public boolean actualizarCantidad(Connection conexion, String cantidad, String id) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            System.out.println("recibo en el DAO cantidad ::: " + cantidad + " id ::: " + id);
            cadSQL.append("UPDATE articulos SET arti_cantidad = ? WHERE arti_id = ?");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, cantidad, ps);
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

}
