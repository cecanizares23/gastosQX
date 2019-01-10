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
import co.IngCarlos.gastosQX.mvc.dto.DatosUsuarioDTO;
import co.IngCarlos.gastosQX.mvc.dto.UsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.directwebremoting.WebContextFactory;

/**
 *
 * @author carlos
 */
public class UsuarioDAO {

    /**
     *
     * @param conexion
     * @param usuario1
     * @param usuario
     * @return
     * @throws SQLException
     */
    public boolean registrarUsuario(Connection conexion, UsuarioDTO usuario1, String usuario) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        System.out.println("registroDAO " + usuario1.toStringJson());
        System.out.println("registroDAOusa " + usuario);

        try {
            cadSQL = new StringBuilder();

            cadSQL.append(" INSERT INTO usuario(tido_id, tius_id, usua_estado,usua_fechanaci, usua_nombre, usua_apellido,"
                    + "usua_documento, usua_celular, usua_correo, usua_direccion, usua_telefono, usua_registradopor)");
            cadSQL.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);

            AsignaAtributoStatement.setString(1, usuario1.getIdTipoDocumento(), ps);
            AsignaAtributoStatement.setString(2, usuario1.getTipoUsuario(), ps);
            AsignaAtributoStatement.setString(3, usuario1.getEstado(), ps);            
            AsignaAtributoStatement.setString(4, usuario1.getFechaNacimiento(), ps);
            AsignaAtributoStatement.setString(5, usuario1.getNombre(), ps);
            AsignaAtributoStatement.setString(6, usuario1.getApellido(), ps);            
            AsignaAtributoStatement.setString(7, usuario1.getDocumento(), ps);
            AsignaAtributoStatement.setString(8, usuario1.getCelular(), ps);
            AsignaAtributoStatement.setString(9, usuario1.getCorreo(), ps);
            AsignaAtributoStatement.setString(10, usuario1.getDireccion(), ps);
            AsignaAtributoStatement.setString(11, usuario1.getTelefono(), ps);
            AsignaAtributoStatement.setString(12, usuario, ps);

            nRows = ps.executeUpdate();
            if (nRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    registroExitoso = true;
                    usuario1.setId(rs.getString(1));
                }
                rs.close();
                rs = null;
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
     * @param datosUsuario
     * @return
     */
    public boolean editarUsuario(Connection conexion, UsuarioDTO datosUsuario) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        UsuarioDTO datos = datosUsuario;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();

            cadSQL.append(" UPDATE usuario SET tido_id = ?, tius_id = ?, usua_estado = ?, usua_fechanaci = ?, usua_nombre = ?,"
                    + " usua_apellido = ?, usua_documento = ?, usua_celular = ?, usua_correo = ? WHERE  usua_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datos.getIdTipoDocumento(), ps);
            AsignaAtributoStatement.setString(2, datos.getIdTipoUsuario(), ps);
            AsignaAtributoStatement.setString(3, datos.getEstado(), ps);            
            AsignaAtributoStatement.setString(4, datos.getFechaNacimiento(), ps);
            AsignaAtributoStatement.setString(5, datos.getNombre(), ps);
            AsignaAtributoStatement.setString(6, datos.getApellido(), ps);            
            AsignaAtributoStatement.setString(7, datos.getDocumento(), ps);
            AsignaAtributoStatement.setString(8, datos.getCelular(), ps);
            AsignaAtributoStatement.setString(9, datos.getCorreo(), ps);            
            AsignaAtributoStatement.setString(10, datos.getId(), ps);
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
     * @return
     */
    public ArrayList<UsuarioDTO> listarUsuarios(Connection conexion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UsuarioDTO> listado = null;
        UsuarioDTO datosUsuario = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT usua.usua_id, usua.tido_id, usua.tius_id, usua.usua_estado, usua.usua_fechanaci, usua.usua_nombre,");
            cadSQL.append(" usua.usua_documento, usua.usua_celular, usua.usua_correo, usua.usua_apellido, usse.usse_usuario, usua.usua_registradopor");
            cadSQL.append(" FROM usuario usua");
            cadSQL.append(" INNER JOIN usuario_seguridad usse ON usua.usua_id = usse.usua_id");

            ps = conexion.prepareStatement(cadSQL.toString());

            rs = ps.executeQuery();

            listado = new ArrayList();
            while (rs.next()) {
                datosUsuario = new UsuarioDTO();
                datosUsuario.setId(rs.getString("usua_id"));
                datosUsuario.setTipoDocumento(rs.getString("tido_id"));
                datosUsuario.setIdTipoUsuario(rs.getString("tius_id"));
                datosUsuario.setEstado(rs.getString("usua_estado"));                
                datosUsuario.setFechaNacimiento(rs.getString("usua_fechanaci"));
                datosUsuario.setNombre(rs.getString("usua_nombre") + " " + rs.getString("usua_apellido"));
                datosUsuario.setDocumento(rs.getString("usua_documento"));
                datosUsuario.setCelular(rs.getString("usua_celular"));
                datosUsuario.setCorreo(rs.getString("usua_correo"));                
                datosUsuario.setUsuario(rs.getString("usse_usuario"));
                datosUsuario.setRegistradoPor(rs.getString("usua_registradopor"));
                listado.add(datosUsuario);
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
     * @return
     */
    public ArrayList<UsuarioDTO> listarUsuarioSeguridad(Connection conexion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UsuarioDTO> listado = null;
        UsuarioDTO datos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT usu.usua_id, us.usse_usuario, usu.usua_nombre, usu.usua_documento, usu.usua_estado");
            cadSQL.append(" FROM promociones.usuario usu");
            cadSQL.append(" INNER JOIN promociones.usuario_seguridad us ON usu.usua_id = us.usua_id");
            ps = conexion.prepareStatement(cadSQL.toString());
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datos = new UsuarioDTO();
                datos.setId(rs.getString("usua_id"));
                datos.setUsuario(rs.getString("usse_usuario"));
                datos.setNombre(rs.getString("usua_nombre"));
                datos.setDocumento(rs.getString("usua_documento"));
                datos.setEstado(rs.getString("usua_estado"));
                listado.add(datos);
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
     * @param idUsuario
     * @return
     */
    public UsuarioDTO consultarUsuario(Connection conexion, String idUsuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        UsuarioDTO datos = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT us.usse_usuario, us.usse_clave, usu.usua_nombre, usu.usua_documento, usu.tido_id, usu.tius_id, usu.usua_celular, usu.usua_correo, ");
            cadSQL.append(" usu.usua_apellido, tius.tius_descripcion, usu.usua_fechanaci, usu.usua_estado ");
            cadSQL.append(" FROM usuario usu ");
            cadSQL.append(" INNER JOIN usuario_seguridad us ON usu.usua_id = us.usua_id ");           
            cadSQL.append(" INNER JOIN tipo_usuario tius ON usu.tius_id = tius.tius_id ");
            cadSQL.append(" WHERE usu.usua_id = ? ");
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, idUsuario, ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                datos = new UsuarioDTO();
                datos.setUsuario(rs.getString("usse_usuario"));
                datos.setClave(rs.getString("usse_clave"));
                datos.setNombre(rs.getString("usua_nombre"));
                datos.setDocumento(rs.getString("usua_documento"));
                datos.setTipoDocumento(rs.getString("tido_id"));
                datos.setIdTipoUsuario(rs.getString("tius_id"));
                datos.setCelular(rs.getString("usua_celular"));
                datos.setCorreo(rs.getString("usua_correo"));
                datos.setApellido(rs.getString("usua_apellido"));
                datos.setRol(rs.getString("tius_descripcion"));
                datos.setFechaNacimiento(rs.getString("usua_fechanaci"));
                datos.setEstado(rs.getString("usua_estado"));
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
        return datos;
    }

    /**
     *
     * @param conexion
     * @param id
     * @return
     */
    public boolean eliminarUsuario(Connection conexion, String id) {
        PreparedStatement ps = null;
        int nRows = 0;
        boolean deleteExitoso = false;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("DELETE us,usu FROM usuario_seguridad AS usu INNER JOIN usuario AS us WHERE us.usua_id=usu.usua_id AND us.usua_id LIKE ?");
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
     * @param datos
     * @return
     */
    public boolean cambiarImagen(Connection conexion, DatosUsuarioDTO datos) {
        HttpSession session = WebContextFactory.get().getSession();
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            datos = (DatosUsuarioDTO) session.getAttribute("datosUsuario");
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE usuario SET usua_imagenperfil = ? WHERE  usua_id = ?");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datos.getImagenPerfil(), ps);
            AsignaAtributoStatement.setString(2, datos.getIdUsuario(), ps);
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
     * @param idUsuario
     * @param estado
     * @return
     */
    public boolean activarEstadoUsuario(Connection conexion, String idUsuario, String estado) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE usuario SET usua_estado = ? WHERE usua_id = ?");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, estado, ps);
            AsignaAtributoStatement.setString(2, idUsuario, ps);
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
     * @param idUsuario
     * @param estado
     * @return
     */
    public boolean inactivarEstadoUsuario(Connection conexion, String idUsuario, String estado) {
        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append("UPDATE usuario SET usua_estado = ? WHERE usua_id = ?");
            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, estado, ps);
            AsignaAtributoStatement.setString(2, idUsuario, ps);
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
    public UsuarioDTO consultarUsuarioPorId(Connection conexion, String id) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        UsuarioDTO datosUsuario = null;
        StringBuilder cadSQL = null;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT usua.usua_id, usua.usua_estado, usua.usua_nombre, usua.usua_apellido, usua.usua_celular, usua.usua_documento, usua.usua_correo, ");
            cadSQL.append(" usua.usua_imagenperfil, usse.usse_usuario, tido.tido_id, tido.tido_descripcion, tius.tius_id, ");
            cadSQL.append(" tius.tius_descripcion, muni.muni_id, muni.muni_nombre, muni.depa_id, usua.usua_fechanaci, gene.gene_id, gene.gene_descripcion, ");
            cadSQL.append(" usua.usua_telefono, usua.usua_direccion, muni.depa_id ");
            cadSQL.append(" FROM usuario usua ");
            cadSQL.append(" INNER JOIN usuario_seguridad usse ON usse.usua_id = usua.usua_id  ");
            cadSQL.append(" INNER JOIN tipo_documento tido ON tido.tido_id = usua.tido_id ");
            cadSQL.append(" INNER JOIN tipo_usuario tius ON usua.tius_id = tius.tius_id ");
            cadSQL.append(" INNER JOIN municipio muni ON usua.muni_id = muni.muni_id ");
            cadSQL.append(" INNER JOIN departamento depa ON depa.depa_id = muni.depa_id ");
            cadSQL.append(" INNER JOIN genero gene ON gene.gene_id = usua.gene_id ");
            cadSQL.append(" WHERE usua.usua_id  = ? ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, id, ps);
            rs = ps.executeQuery();

            if (rs.next()) {

                datosUsuario = new UsuarioDTO();
                datosUsuario.setIdUsuario(rs.getString("usua_id"));
                datosUsuario.setEstado(rs.getString("usua_estado"));
                datosUsuario.setNombre(rs.getString("usua_nombre"));
                datosUsuario.setApellido(rs.getString("usua_apellido"));
                datosUsuario.setCelular(rs.getString("usua_celular"));
                datosUsuario.setDocumento(rs.getString("usua_documento"));
                datosUsuario.setCorreo(rs.getString("usua_correo"));
                datosUsuario.setImagenPerfil(rs.getString("usua_imagenperfil"));
                datosUsuario.setUsuario(rs.getString("usse_usuario"));
                datosUsuario.setIdTipoDocumento(rs.getString("tido_id"));
                datosUsuario.setTipoDocumento(rs.getString("tido_descripcion"));
                datosUsuario.setIdTipoUsuario(rs.getString("tius_id"));
                datosUsuario.setTipoUsuario(rs.getString("tius_descripcion"));
                datosUsuario.setIdMunicipio(rs.getString("muni_id"));
                datosUsuario.setMunicipio(rs.getString("muni_nombre"));
                datosUsuario.setIdDepartamento(rs.getString("depa_id"));
                datosUsuario.setFechaNacimiento(rs.getString("usua_fechanaci"));
                datosUsuario.setIdGenero(rs.getString("gene_id"));
                datosUsuario.setGenero(rs.getString("gene_descripcion"));
                datosUsuario.setTelefono(rs.getString("usua_telefono"));
                datosUsuario.setDireccion(rs.getString("usua_direccion"));
                datosUsuario.setIdDepartamento(rs.getString("depa_id"));

            }

            System.out.println("datosUsuarioDao " + datosUsuario.toStringJson());

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
     * @param conexion
     * @param datosUsuario
     * @return
     */
    public boolean actualizarUsuarioPerfil(Connection conexion, UsuarioDTO datosUsuario) {

        PreparedStatement ps = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean registroExitoso = false;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append(" UPDATE usuario SET usua_nombre = ?, tido_id = ?, usua_documento = ?, usua_celular = ?, usua_fechanaci = ?, muni_id = ?,  ");
            cadSQL.append(" usua_correo = ? ");
            cadSQL.append(" WHERE   usua_id = ?");

            ps = conexion.prepareStatement(cadSQL.toString(), Statement.RETURN_GENERATED_KEYS);
            AsignaAtributoStatement.setString(1, datosUsuario.getNombre(), ps);
            AsignaAtributoStatement.setString(2, datosUsuario.getIdTipoDocumento(), ps);
            AsignaAtributoStatement.setString(3, datosUsuario.getDocumento(), ps);
            AsignaAtributoStatement.setString(4, datosUsuario.getCelular(), ps);
            AsignaAtributoStatement.setString(5, datosUsuario.getFechaNacimiento(), ps);
            AsignaAtributoStatement.setString(6, datosUsuario.getIdMunicipio(), ps);
            AsignaAtributoStatement.setString(7, datosUsuario.getCorreo(), ps);
            AsignaAtributoStatement.setString(8, datosUsuario.getIdUsuario(), ps);

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
     * @param documento
     * @param interventor
     * @return
     */
    public UsuarioDTO buscarUsuarioInterventorPorDocumento(Connection conexion, String documento, String interventor) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        UsuarioDTO datosUsuario = null;
        StringBuilder cadSQL = null;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT usua.usua_id, usua.usua_estado, usua.usua_nombre, usua.usua_apellido, usua.usua_celular, usua.usua_documento, usua.usua_correo, ");
            cadSQL.append(" usua.usua_imagenperfil, usse.usse_usuario, tido.tido_id, tido.tido_descripcion, tius.tius_id, ");
            cadSQL.append(" tius.tius_descripcion, muni.muni_id, muni.muni_nombre, muni.depa_id, usua.usua_fechanaci, gene.gene_id, gene.gene_descripcion, ");
            cadSQL.append(" usua.usua_telefono, usua.usua_direccion, muni.depa_id ");
            cadSQL.append(" FROM usuario usua ");
            cadSQL.append(" INNER JOIN usuario_seguridad usse ON usse.usua_id = usua.usua_id  ");
            cadSQL.append(" INNER JOIN tipo_documento tido ON tido.tido_id = usua.tido_id ");
            cadSQL.append(" INNER JOIN tipo_usuario tius ON usua.tius_id = tius.tius_id ");
            cadSQL.append(" INNER JOIN municipio muni ON usua.muni_id = muni.muni_id ");
            cadSQL.append(" INNER JOIN departamento depa ON depa.depa_id = muni.depa_id ");
            cadSQL.append(" INNER JOIN genero gene ON gene.gene_id = usua.gene_id ");
            cadSQL.append(" WHERE usua.usua_documento  = ? AND tius.tius_id = ? AND tius.tius_estado = ? ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, documento, ps);
            AsignaAtributoStatement.setString(2, interventor, ps);
            AsignaAtributoStatement.setString(3, Constantes.ESTADO_ACTIVO, ps);
            rs = ps.executeQuery();

            if (rs.next()) {

                datosUsuario = new UsuarioDTO();
                datosUsuario.setIdUsuario(rs.getString("usua_id"));
                datosUsuario.setEstado(rs.getString("usua_estado"));
                datosUsuario.setNombre(rs.getString("usua_nombre"));
                datosUsuario.setApellido(rs.getString("usua_apellido"));
                datosUsuario.setCelular(rs.getString("usua_celular"));
                datosUsuario.setDocumento(rs.getString("usua_documento"));
                datosUsuario.setCorreo(rs.getString("usua_correo"));
                datosUsuario.setImagenPerfil(rs.getString("usua_imagenperfil"));
                datosUsuario.setUsuario(rs.getString("usse_usuario"));
                datosUsuario.setIdTipoDocumento(rs.getString("tido_id"));
                datosUsuario.setTipoDocumento(rs.getString("tido_descripcion"));
                datosUsuario.setIdTipoUsuario(rs.getString("tius_id"));
                datosUsuario.setTipoUsuario(rs.getString("tius_descripcion"));
                datosUsuario.setIdMunicipio(rs.getString("muni_id"));
                datosUsuario.setMunicipio(rs.getString("muni_nombre"));
                datosUsuario.setIdDepartamento(rs.getString("depa_id"));
                datosUsuario.setFechaNacimiento(rs.getString("usua_fechanaci"));
                datosUsuario.setIdGenero(rs.getString("gene_id"));
                datosUsuario.setGenero(rs.getString("gene_descripcion"));
                datosUsuario.setTelefono(rs.getString("usua_telefono"));
                datosUsuario.setDireccion(rs.getString("usua_direccion"));
                datosUsuario.setIdDepartamento(rs.getString("depa_id"));

            }

            System.out.println("datosUsuarioDao " + datosUsuario.toStringJson());

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
     * @param datosUsuario
     * @return
     * @throws SQLException
     */
    public boolean validarDocumento(Connection conexion, UsuarioDTO datosUsuario) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs = null;
        int nRows = 0;
        StringBuilder cadSQL = null;
        boolean validado = false;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT  usua_documento");
            cadSQL.append(" FROM usuario ");
            cadSQL.append(" WHERE usua_documento = ?");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, datosUsuario.getDocumento(), ps);

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
     * @param idInterventor
     * @param interventor
     * @return
     */
    public UsuarioDTO buscarUsuarioInterventorPorId(Connection conexion, String idInterventor, String interventor) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        UsuarioDTO datosUsuario = null;
        StringBuilder cadSQL = null;

        try {

            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT usua.usua_id, usua.usua_estado, usua.usua_nombre, usua.usua_apellido, usua.usua_celular, usua.usua_documento, usua.usua_correo, ");
            cadSQL.append(" usua.usua_imagenperfil, usse.usse_usuario, tido.tido_id, tido.tido_descripcion, tius.tius_id, ");
            cadSQL.append(" tius.tius_descripcion, muni.muni_id, muni.muni_nombre, muni.depa_id, usua.usua_fechanaci, gene.gene_id, gene.gene_descripcion, ");
            cadSQL.append(" usua.usua_telefono, usua.usua_direccion, muni.depa_id ");
            cadSQL.append(" FROM usuario usua ");
            cadSQL.append(" INNER JOIN usuario_seguridad usse ON usse.usua_id = usua.usua_id  ");
            cadSQL.append(" INNER JOIN tipo_documento tido ON tido.tido_id = usua.tido_id ");
            cadSQL.append(" INNER JOIN tipo_usuario tius ON usua.tius_id = tius.tius_id ");
            cadSQL.append(" INNER JOIN municipio muni ON usua.muni_id = muni.muni_id ");
            cadSQL.append(" INNER JOIN departamento depa ON depa.depa_id = muni.depa_id ");
            cadSQL.append(" INNER JOIN genero gene ON gene.gene_id = usua.gene_id ");
            cadSQL.append(" WHERE usua.usua_id  = ? AND tius.tius_id = ? AND tius.tius_estado = ? ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, idInterventor, ps);
            AsignaAtributoStatement.setString(2, interventor, ps);
            AsignaAtributoStatement.setString(3, Constantes.ESTADO_ACTIVO, ps);
            rs = ps.executeQuery();

            if (rs.next()) {

                datosUsuario = new UsuarioDTO();
                datosUsuario.setIdUsuario(rs.getString("usua_id"));
                datosUsuario.setEstado(rs.getString("usua_estado"));
                datosUsuario.setNombre(rs.getString("usua_nombre"));
                datosUsuario.setApellido(rs.getString("usua_apellido"));
                datosUsuario.setCelular(rs.getString("usua_celular"));
                datosUsuario.setDocumento(rs.getString("usua_documento"));
                datosUsuario.setCorreo(rs.getString("usua_correo"));
                datosUsuario.setImagenPerfil(rs.getString("usua_imagenperfil"));
                datosUsuario.setUsuario(rs.getString("usse_usuario"));
                datosUsuario.setIdTipoDocumento(rs.getString("tido_id"));
                datosUsuario.setTipoDocumento(rs.getString("tido_descripcion"));
                datosUsuario.setIdTipoUsuario(rs.getString("tius_id"));
                datosUsuario.setTipoUsuario(rs.getString("tius_descripcion"));
                datosUsuario.setIdMunicipio(rs.getString("muni_id"));
                datosUsuario.setMunicipio(rs.getString("muni_nombre"));
                datosUsuario.setIdDepartamento(rs.getString("depa_id"));
                datosUsuario.setFechaNacimiento(rs.getString("usua_fechanaci"));
                datosUsuario.setIdGenero(rs.getString("gene_id"));
                datosUsuario.setGenero(rs.getString("gene_descripcion"));
                datosUsuario.setTelefono(rs.getString("usua_telefono"));
                datosUsuario.setDireccion(rs.getString("usua_direccion"));
                datosUsuario.setIdDepartamento(rs.getString("depa_id"));

            }

            //System.out.println("datosUsuarioDao " + datosUsuario.toStringJson());

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
     * @param interventor
     * @return 
     */
    public ArrayList<UsuarioDTO> listarUsuariosInterventor(Connection conexion, String interventor) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UsuarioDTO> listado = null;
        UsuarioDTO datosUsuario = null;
        StringBuilder cadSQL = null;
        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT usua.usua_id, usua.tido_id, usua.tius_id, usua.usua_estado, usua.muni_id, usua.usua_fechanaci, usua.usua_nombre,");
            cadSQL.append(" usua.usua_documento, usua.usua_celular, usua.usua_correo, usua.usua_apellido, usua.gene_id, usse.usse_usuario, usua.usua_registradopor");
            cadSQL.append(" FROM usuario usua");
            cadSQL.append(" INNER JOIN usuario_seguridad usse ON usua.usua_id = usse.usua_id");
            cadSQL.append(" WHERE usua.tius_id = ? ");

            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, interventor, ps);

            rs = ps.executeQuery();

            listado = new ArrayList();
            while (rs.next()) {
                datosUsuario = new UsuarioDTO();
                datosUsuario.setId(rs.getString("usua_id"));
                datosUsuario.setTipoDocumento(rs.getString("tido_id"));
                datosUsuario.setIdTipoUsuario(rs.getString("tius_id"));
                datosUsuario.setEstado(rs.getString("usua_estado"));
                datosUsuario.setMunicipio(rs.getString("muni_id"));
                datosUsuario.setFechaNacimiento(rs.getString("usua_fechanaci"));
                datosUsuario.setNombre(rs.getString("usua_nombre") + " " + rs.getString("usua_apellido"));
                datosUsuario.setDocumento(rs.getString("usua_documento"));
                datosUsuario.setCelular(rs.getString("usua_celular"));
                datosUsuario.setCorreo(rs.getString("usua_correo"));
                datosUsuario.setGenero(rs.getString("gene_id"));
                datosUsuario.setUsuario(rs.getString("usse_usuario"));
                datosUsuario.setRegistradoPor(rs.getString("usua_registradopor"));
                listado.add(datosUsuario);
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
