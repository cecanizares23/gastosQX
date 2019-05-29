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
        System.out.println("datosGastos " + datosGastos.toStringJson() + " usuario " + usuario);
        try {

            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE gastos SET proc_id = ?, gast_paciente = ?, gast_cedula = ?, gast_fecha = ?, gast_estado = ?, gast_registradopor = ? "
                    + "WHERE gast_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosGastos.getIdProcedimiento(), ps);
            AsignaAtributoStatement.setString(2, datosGastos.getPaciente(), ps);
            AsignaAtributoStatement.setString(3, datosGastos.getCedula(), ps);
            AsignaAtributoStatement.setString(4, datosGastos.getFecha(), ps);
            AsignaAtributoStatement.setString(5, Constantes.ESTADO_ACTIVO, ps);
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
            cadSQL.append(" SELECT gast.gast_id, gast.proc_id, gast.gast_paciente, gast.gast_cedula, gast.gast_fecha, gast.gast_estado, gast.gast_registradopor, gast.gast_fecharegistro, ");
            cadSQL.append(" proc.proc_codigo, proc.proc_descripcion ");
            cadSQL.append(" FROM gastos gast");
            cadSQL.append(" INNER JOIN procedimiento proc ON proc.proc_id = gast.proc_id ");
            cadSQL.append(" ORDER BY gast.gast_id DESC ");

            ps = conexion.prepareStatement(cadSQL.toString());
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
                datosGastos.setDescripcionProcedimiento(rs.getString("proc_descripcion"));
                datosGastos.setCodigoProcedimiento(rs.getString("proc_codigo"));
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
     * @param confirmado
     * @return
     */
    public boolean activarEstadoGastos(Connection conexion, String id, String confirmado) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE gastos SET gast_estado = ? WHERE gast_id = ?");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, confirmado, ps);
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
     * @param condicion
     * @return
     */
    public GastosDTO ConsultarGastosXId(Connection conexion, String condicion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        GastosDTO datosGastos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT gast.gast_id, gast.proc_id, gast.gast_paciente, gast.gast_cedula, gast.gast_fecha, gast.gast_estado, ");
            cadSQL.append(" gast.gast_registradopor, gast.gast_fecharegistro, proc.proc_codigo, proc.proc_descripcion ");
            cadSQL.append(" FROM gastos gast ");
            cadSQL.append(" INNER JOIN procedimiento proc ON proc.proc_id = gast.proc_id ");
            cadSQL.append(" WHERE gast.gast_id = ? ");
            //cadSQL.append(" ORDER BY gast.gast_id DESC ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, condicion, ps);
            rs = ps.executeQuery();

            if (rs.next()) {
                datosGastos = new GastosDTO();
                datosGastos.setId(rs.getString("gast_id"));
                datosGastos.setIdProcedimiento(rs.getString("proc_id"));
                datosGastos.setPaciente(rs.getString("gast_paciente"));
                datosGastos.setCedula(rs.getString("gast_cedula"));
                datosGastos.setFecha(rs.getString("gast_fecha"));
                datosGastos.setEstado(rs.getString("gast_estado"));
                datosGastos.setRegistradoPor(rs.getString("gast_registradopor"));
                datosGastos.setFechaRegistro(rs.getString("gast_fecharegistro"));
                datosGastos.setCodigoProcedimiento(rs.getString("proc_codigo"));
                datosGastos.setDescripcionProcedimiento(rs.getString("proc_descripcion"));

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

    /**
     *
     * @param conexion
     * @param condicion
     * @return
     */
    public ArrayList<GastosDTO> buscarGastoFecha(Connection conexion, String condicion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<GastosDTO> listado = null;
        GastosDTO datosGastos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT gast.gast_id, gast.proc_id, gast.gast_paciente, gast.gast_cedula, gast.gast_fecha, gast.gast_estado, ");
            cadSQL.append(" gast.gast_registradopor, gast.gast_fecharegistro, proc.proc_codigo, proc.proc_descripcion ");
            cadSQL.append(" FROM gastos gast ");
            cadSQL.append(" INNER JOIN procedimiento proc ON proc.proc_id = gast.proc_id ");
            cadSQL.append(" WHERE gast.gast_fecha LIKE '%" + condicion + "%' ");
            cadSQL.append(" ORDER BY gast.gast_id DESC ");
            //cadSQL.append(" WHERE arti.arti_referencia LIKE '%" + condicion + "%'");

            ps = conexion.prepareStatement(cadSQL.toString());
            //AsignaAtributoStatement.setString(1, condicion, ps);
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
                datosGastos.setDescripcionProcedimiento(rs.getString("proc_descripcion"));

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
     * @param condicion
     * @return
     */
    public ArrayList<GastosDTO> buscarGastoCedulaPaciente(Connection conexion, String condicion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<GastosDTO> listado = null;
        GastosDTO datosGastos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT gast.gast_id, gast.proc_id, gast.gast_paciente, gast.gast_cedula, gast.gast_fecha, gast.gast_estado, ");
            cadSQL.append(" gast.gast_registradopor, gast.gast_fecharegistro, proc.proc_codigo, proc.proc_descripcion ");
            cadSQL.append(" FROM gastos gast ");
            cadSQL.append(" INNER JOIN procedimiento proc ON proc.proc_id = gast.proc_id ");
            cadSQL.append(" WHERE gast.gast_cedula LIKE '%" + condicion + "%' ");
            cadSQL.append(" ORDER BY gast.gast_id DESC ");
            //cadSQL.append(" WHERE arti.arti_referencia LIKE '%" + condicion + "%'");

            ps = conexion.prepareStatement(cadSQL.toString());
            //AsignaAtributoStatement.setString(1, condicion, ps);
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
                datosGastos.setDescripcionProcedimiento(rs.getString("proc_descripcion"));

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
     * @param condicion
     * @return
     */
    public ArrayList<GastosDTO> ConsultarGastosXId1(Connection conexion, String condicion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<GastosDTO> listado = null;
        GastosDTO datosGastos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT gast.gast_id, gast.proc_id, gast.gast_paciente, gast.gast_cedula, gast.gast_fecha, gast.gast_estado, ");
            cadSQL.append(" gast.gast_registradopor, gast.gast_fecharegistro, proc.proc_codigo, proc.proc_descripcion ");
            cadSQL.append(" FROM gastos gast ");
            cadSQL.append(" INNER JOIN procedimiento proc ON proc.proc_id = gast.proc_id ");
            cadSQL.append(" WHERE gast.gast_id = ? ");
            //cadSQL.append(" WHERE arti.arti_referencia LIKE '%" + condicion + "%'");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, condicion, ps);
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
                datosGastos.setDescripcionProcedimiento(rs.getString("proc_descripcion"));

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

}
