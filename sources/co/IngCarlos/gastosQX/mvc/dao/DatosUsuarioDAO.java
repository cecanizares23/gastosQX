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
import co.IngCarlos.gastosQX.mvc.dto.DatosUsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



/**
 *
 * @author carlos
 */
public class DatosUsuarioDAO {

    /**
     *
     * @param conexion
     * @param usuario
     * @return
     */
    public DatosUsuarioDTO consultarDatosUsuarioLogueado(Connection conexion, String usuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        DatosUsuarioDTO datosUsuario = null;
        StringBuilder cadSQL = null;
        try {
            System.out.print("llega a dao consultarusuariologeado");
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT usua.usua_id, usua.usua_nombre, usua.usua_imagenperfil, usua.usua_registradopor, tido.tido_descripcion, ");
            cadSQL.append(" usua.usua_fecharegistro, usse.usse_usuario, tius.tius_id, tius.tius_descripcion, usua.usua_celular, ");//muni.muni_nombre, ");
            cadSQL.append(" usua.usua_documento, usua.usua_correo, usua.usua_apellido, usua.usua_fechanaci, usua.usua_estado,");// depa.depa_nombre, gene.gene_descripcion, ");
            cadSQL.append(" usua.usua_celular, usua.usua_direccion, usua.usua_telefono ");
            cadSQL.append(" FROM usuario usua ");
            cadSQL.append(" INNER JOIN usuario_seguridad usse ON usse.usua_id = usua.usua_id");
            cadSQL.append(" INNER JOIN tipo_documento tido ON tido.tido_id = usua.tido_id");
            cadSQL.append(" INNER JOIN tipo_usuario tius ON tius.tius_id = usua.tius_id");
            //cadSQL.append(" INNER JOIN municipio muni ON muni.muni_id = usua.muni_id ");
            //cadSQL.append(" INNER JOIN departamento depa ON depa.depa_id = muni.depa_id ");
            //cadSQL.append(" INNER JOIN genero gene ON gene.gene_id = usua.gene_id ");
            cadSQL.append(" WHERE usse.usse_usuario=?");
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, usuario, ps);
            rs = ps.executeQuery();

            if (rs.next()) {
                datosUsuario = new DatosUsuarioDTO();
                datosUsuario.setIdUsuario(rs.getString("usua_id"));
                datosUsuario.setNombre(rs.getString("usua_nombre") + " " + rs.getString("usua_apellido"));
                datosUsuario.setImagenPerfil(rs.getString("usua_imagenperfil"));
                datosUsuario.setRegistradoPor(rs.getString("usua_registradopor"));
                datosUsuario.setFechaRegistro(rs.getString("usua_fecharegistro"));
                datosUsuario.setUsuario(rs.getString("usse_usuario"));
                datosUsuario.setIdTipoUsuario(rs.getString("tius_id"));
                datosUsuario.setTipoUsuario(rs.getString("tius_descripcion"));
                datosUsuario.setCelular(rs.getString("usua_celular"));
                //datosUsuario.setMunicipio(rs.getString("muni_nombre"));
                datosUsuario.setTipoDocumento(rs.getString("tido_descripcion"));
                datosUsuario.setDocumento(rs.getString("usua_documento"));
                datosUsuario.setCorreo(rs.getString("usua_correo"));
                datosUsuario.setApellido(rs.getString("usua_apellido"));
                datosUsuario.setFechaNacimieno(rs.getString("usua_fechanaci"));
                datosUsuario.setEstado(rs.getString("usua_estado"));
                //datosUsuario.setDepartamento(rs.getString("depa_nombre"));
                //datosUsuario.setGenero(rs.getString("gene_descripcion"));
                datosUsuario.setCelular(rs.getString("usua_celular"));
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
        return datosUsuario;
    }

    /**
     *
     * @param conexion
     * @param documento
     * @return
     */
    public DatosUsuarioDTO recuperarContrasenia(Connection conexion, String documento) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        DatosUsuarioDTO datosUsuario = null;
        StringBuilder cadSQL = null;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT usua.usua_id, usua.usua_correo, usse.usse_clave FROM usuario usua ");
            cadSQL.append(" INNER JOIN usuario_seguridad usse ON   usse.usua_id = usua.usua_id ");
            cadSQL.append(" WHERE  usua_documento = ?");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, documento, ps);
            rs = ps.executeQuery();

            if (rs.next()) {
                datosUsuario = new DatosUsuarioDTO();
                datosUsuario.setIdUsuario(rs.getString("usua_id"));
                datosUsuario.setCorreo(rs.getString("usua_correo"));
                datosUsuario.setClave(rs.getString("usse_clave"));
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
        return datosUsuario;
    }

}
