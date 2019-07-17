/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.IngCarlos.gastosQX.mvc.dao;

import co.IngCarlos.gastosQX.common.util.AsignaAtributoStatement;
import co.IngCarlos.gastosQX.common.util.LoggerMessage;
import co.IngCarlos.gastosQX.mvc.dto.DetalleOrdenDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ing. Carlos
 */
public class DetalleOrdenDAO {

    /**
     *
     * @param conexion
     * @param datosDetalleOrden
     * @param usuario
     * @return
     */
    public boolean registrarDetalleOrden(Connection conexion, DetalleOrdenDTO datosDetalleOrden, String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" INSERT INTO detalle_orden (orco_id, arti_id, deor_cantidad, deor_registradopor) ");
            cadSQL.append("VALUES ( ?, ?, ?, ?)");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, datosDetalleOrden.getIdOrdenCompra(), ps);
            AsignaAtributoStatement.setString(2, datosDetalleOrden.getIdArticulo(), ps);
            AsignaAtributoStatement.setString(3, datosDetalleOrden.getCantidadArt(), ps);
            AsignaAtributoStatement.setString(4, usuario, ps);

            nRows = ps.executeUpdate();

            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    datosDetalleOrden.setId(rs.getString(1));
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
     * @param datosDetalleOrden
     * @param usuario
     * @return
     */
    public boolean actualizarDetalleOrden(Connection conexion, DetalleOrdenDTO datosDetalleOrden, String usuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE detalle_orden SET orco_id = ?, arti_id = ?, deor_cantidad = ?, deor_registradopor = ? WHERE orde_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosDetalleOrden.getIdOrdenCompra(), ps);
            AsignaAtributoStatement.setString(2, datosDetalleOrden.getIdArticulo(), ps);
            AsignaAtributoStatement.setString(3, datosDetalleOrden.getCantidadArt(), ps);
            AsignaAtributoStatement.setString(4, usuario, ps);
            AsignaAtributoStatement.setString(5, datosDetalleOrden.getId(), ps);

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
    public boolean eliminarDetalleOrden(Connection conexion, String id) {

        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" DELETE  FROM detalle_orden WHERE deor_id = ? ");
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
    public ArrayList<DetalleOrdenDTO> listarDetalleOrden(Connection conexion, String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<DetalleOrdenDTO> listado = null;
        DetalleOrdenDTO datosDetalleOrden = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT  deor_id, orco_id, arti_id, deor_cantidad, deor_registradopor, deor_fecharegistro");
            cadSQL.append(" FROM detalle_orden ");

            ps = conexion.prepareStatement(cadSQL.toString());
            //AsignaAtributoStatement.setString(1, Constantes.ESTADO_ACTIVO, ps);

            rs = ps.executeQuery();
            listado = new ArrayList();

            while (rs.next()) {
                datosDetalleOrden = new DetalleOrdenDTO();
                datosDetalleOrden.setId(rs.getString("deor_id"));
                datosDetalleOrden.setIdOrdenCompra(rs.getString("orco_id"));
                datosDetalleOrden.setIdArticulo(rs.getString("arti_id"));
                datosDetalleOrden.setCantidadArt(rs.getString("deor_cantidad"));
                datosDetalleOrden.setRegistradoPor(rs.getString("deor_registradopor"));
                datosDetalleOrden.setFechaRegistro(rs.getString("deor_fecharegistro"));

                listado.add(datosDetalleOrden);
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
     * @return
     */
    public DetalleOrdenDTO ConsultarDetalleOrdenXId(Connection conexion, String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        DetalleOrdenDTO datosDetalleOrden = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT deor.deor_id, deor.orco_id, deor.arti_id, deor.deor_cantidad, deor.deor_registradopor, deor.deor_fecharegistro, ");
            cadSQL.append(" arti.arti_referencia, arti.arti_lote, arti.arti_descripcion, arti.arti_unidadmedida, ");
            cadSQL.append(" arti.arti_estado, orco.orco_id, orco.gast_id, orco.orco_fecha ");
            cadSQL.append(" orco.orco_estado, orco.orco_confirmado ");
            cadSQL.append(" INNER JOIN articulos arti ON arti.arti_id = deor.arti_id ");
            cadSQL.append(" INNER JOIN orden_compra orco ON orco.orco_id = deor.orco_id ");
            cadSQL.append(" FROM detalle_orden deor");
            cadSQL.append(" WHERE deor.deor_id = ? ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, id, ps);
            rs = ps.executeQuery();

            if (rs.next()) {
                datosDetalleOrden = new DetalleOrdenDTO();
                datosDetalleOrden.setId(rs.getString("arti_id"));
                datosDetalleOrden.setIdOrdenCompra(rs.getString("orco_id"));
                datosDetalleOrden.setIdArticulo(rs.getString("arti_id"));
                datosDetalleOrden.setCantidadArt(rs.getString("deor_cantidad"));
                datosDetalleOrden.setRegistradoPor(rs.getString("deor_registradopor"));
                datosDetalleOrden.setFechaRegistro(rs.getString("deor_fecharegistro"));
                datosDetalleOrden.setReferenciaArt(rs.getString("arti_referencia"));
                datosDetalleOrden.setLoteArt(rs.getString("arti_lote"));
                datosDetalleOrden.setDescripcionArt(rs.getString("arti_descripcion"));
                datosDetalleOrden.setUnidadMedida(rs.getString("arti_unidadMedida"));
                datosDetalleOrden.setEstadoArt(rs.getString("arti_estado"));
                datosDetalleOrden.setIdOrdenCompra(rs.getString("orco_id"));
                datosDetalleOrden.setIdGasto(rs.getString("gast_id"));
                datosDetalleOrden.setFechaOrdenCompra(rs.getString("orco_fecha"));
                datosDetalleOrden.setEstadoOrdenCompra(rs.getString("orco_estado"));
                datosDetalleOrden.setConfirmadoOrdenCompra(rs.getString("orco_confirmado"));

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

        return datosDetalleOrden;
    }

}
