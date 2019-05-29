/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */
package co.IngCarlos.gastosQX.mvc.mediador;

import co.IngCarlos.gastosQX.common.connection.ContextDataResourceNames;
import co.IngCarlos.gastosQX.common.connection.DataBaseConnection;
import co.IngCarlos.gastosQX.common.util.Constantes;
import co.IngCarlos.gastosQX.common.util.EnvioEmail;
import co.IngCarlos.gastosQX.common.util.Formato;
import co.IngCarlos.gastosQX.common.util.Generales;
import co.IngCarlos.gastosQX.common.util.LoggerMessage;
import co.IngCarlos.gastosQX.mvc.dao.ArticulosDAO;
import co.IngCarlos.gastosQX.mvc.dao.DatosUsuarioDAO;
import co.IngCarlos.gastosQX.mvc.dao.DetalleGastosDAO;
import co.IngCarlos.gastosQX.mvc.dao.EspecialidadDAO;
import co.IngCarlos.gastosQX.mvc.dao.GastosDAO;
import co.IngCarlos.gastosQX.mvc.dao.MedicoDAO;
import co.IngCarlos.gastosQX.mvc.dao.ProcedimientoDAO;
import co.IngCarlos.gastosQX.mvc.dao.TipoDocumentoDAO;
import co.IngCarlos.gastosQX.mvc.dao.TipoUsuarioDAO;
import co.IngCarlos.gastosQX.mvc.dao.UsuarioDAO;
import co.IngCarlos.gastosQX.mvc.dao.UsuarioSeguridadDAO;
import co.IngCarlos.gastosQX.mvc.dto.ArticulosDTO;
import co.IngCarlos.gastosQX.mvc.dto.BodyMensajeDTO;
import co.IngCarlos.gastosQX.mvc.dto.DatosUsuarioDTO;
import co.IngCarlos.gastosQX.mvc.dto.DetalleGastosDTO;
import co.IngCarlos.gastosQX.mvc.dto.EspecialidadDTO;
import co.IngCarlos.gastosQX.mvc.dto.GastosDTO;
import co.IngCarlos.gastosQX.mvc.dto.MedicoDTO;
import co.IngCarlos.gastosQX.mvc.dto.ProcedimientoDTO;
import co.IngCarlos.gastosQX.mvc.dto.RegistroDTO;
import co.IngCarlos.gastosQX.mvc.dto.RespuestaDTO;
import co.IngCarlos.gastosQX.mvc.dto.TipoDocumentoDTO;
import co.IngCarlos.gastosQX.mvc.dto.TipoUsuarioDTO;
import co.IngCarlos.gastosQX.mvc.dto.UsuarioDTO;
import co.IngCarlos.gastosQX.mvc.dto.UsuarioSeguridadDTO;
import co.IngCarlos.gastosQX.responsemensaje.dto.ResponseMensajeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.crypto.provider.ARCFOURCipher;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import org.directwebremoting.WebContextFactory;
import org.json.JSONObject;
import util.ServicioEmail;

/**
 *
 * @author carlos
 */
public class MediadorAppGastos {

    /**
     *
     */
    private final static MediadorAppGastos instancia = null;
    private final LoggerMessage logMsg;

    /**
     *
     */
    public MediadorAppGastos() {
        logMsg = LoggerMessage.getInstancia();
    }

    /**
     *
     * @return
     */
    public static synchronized MediadorAppGastos getInstancia() {
        return instancia == null ? new MediadorAppGastos() : instancia;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * LOS METODOS APARTIR DE AQUI NO HAN SIDO VALIDADOS
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     *
     * @param datosUsuario1
     * @param datosUsuarioSeguridad
     * @return
     */
    public RegistroDTO registrarUsuario(UsuarioDTO datosUsuario1, UsuarioSeguridadDTO datosUsuarioSeguridad) {

        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroUsuario = false;
        boolean registroUsuarioSeguridad = false;
        String codigoEjecutivo = null;
        String rangoMinimo = null;
        String rangoMaximo = null;
        RegistroDTO registroExitoso = null;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new RegistroDTO();
            String fecha = Formato.formatoFecha(datosUsuario1.getFechaNacimiento());
            datosUsuario1.setFechaNacimiento(fecha);

            registroUsuario = new UsuarioDAO().registrarUsuario(conexion, datosUsuario1, datosUsuario.getUsuario());

            registroUsuarioSeguridad = new UsuarioSeguridadDAO().registrarUsuarioSeguridad(conexion, datosUsuario1, datosUsuarioSeguridad, datosUsuario.getUsuario());

            if (registroUsuario != false && registroUsuarioSeguridad != false) {

                String[] to = {datosUsuario1.getCorreo()};
                String estado = "CONFIRMACIÓN: SE CREÓ LA CUENTA EN LA PLATAFORMA Gastos QX CSJ";
                String body = "Cordial Saludo.\n\n\n"
                        + "En el presente correo se confirma el registro en la plataforma Medipin, con los siguientes datos: \n\n"
                        + "Nombre: " + datosUsuario1.getNombre() + "\n"
                        + "NIT: " + datosUsuario1.getDocumento() + "\n"
                        + "Correo: " + datosUsuario1.getCorreo() + "\n"
                        + "Usuario: " + datosUsuarioSeguridad.getUsuario() + "\n"
                        + "Clave Medipin: " + datosUsuarioSeguridad.getClave() + "\n\n"
                        + "Para ingresar a la plataforma GastosQX ingrese en el siguiente enlace: www.algo.com \n"
                        + "Este correo se generÃ³ automÃ¡ticamente por lo tanto no se debe responder al mismo.";

                System.out.println("datosUsuario1 " + datosUsuario1.toStringJson());
                System.out.println("TO " + to + "estado " + estado + "body " + body);

                //ServicioEmail s = new ServicioEmail(Constantes.CORREO, Constantes.CLAVE_CORREO);
                boolean enviar = EnvioEmail.sendFromGMail(Constantes.CORREO, Constantes.CLAVE_CORREO, to, estado, body);

                //s.enviarEmail(datosUsuario1.getCorreo(), estado, body);
                if (enviar == true) {
                    conexion.commit();
                    registroExitoso.setCondicion(true);
                    registroExitoso.setMensaje("Registro Exitoso");
                    logMsg.loggerMessageDebug(" |  Se registrÃ³ la revisiÃ³n exitosamente");
                } else {
                    conexion.rollback();
                    registroExitoso = null;
                    throw new Exception("Error ::: al enviar el email");
                }

            } else {
                conexion.rollback();
                registroExitoso = null;
            }
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param datosUsuario
     * @return
     */
    public boolean validarUsuario(UsuarioSeguridadDTO datosUsuario) {

        HttpSession session = WebContextFactory.get().getSession();
        DataBaseConnection dbcon = null;
        Connection conexion = null;

        boolean validarUsuario = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);

            validarUsuario = new UsuarioSeguridadDAO().validarUsuarioSeguridad(conexion, datosUsuario);

            if (validarUsuario != false) {
                conexion.commit();
                validarUsuario = true;
            } else {
                conexion.rollback();
            }
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return validarUsuario;
    }

    /**
     *
     * @return
     */
    public ArrayList<UsuarioDTO> listarUsuarioSeguridad() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<UsuarioDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new UsuarioDAO().listarUsuarioSeguridad(conexion);
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param idUsuario
     * @return
     */
    public UsuarioDTO consultarUsuario(String idUsuario) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        UsuarioDTO datos = null;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            datos = new UsuarioDAO().consultarUsuario(conexion, idUsuario);
            //String fecha = Formato.formatoFechaMostrar(datos.getFechaNacimiento());            
            datos.setFechaNacimiento(Formato.formatoFechaMostrar(datos.getFechaNacimiento()));
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }

        return datos;
    }

    /**
     *
     * @return
     */
    public ArrayList<UsuarioDTO> listarUsuarios() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<UsuarioDTO> listado = null;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new UsuarioDAO().listarUsuarios(conexion);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean eliminarUsuario(String id) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean deleteExitoso = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            deleteExitoso = new UsuarioDAO().eliminarUsuario(conexion, id);

            conexion.close();
            conexion = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            deleteExitoso = false;
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                deleteExitoso = false;
            }
        }
        return deleteExitoso;
    }

    /**
     *
     * @return
     */
    public boolean cambiarImagen() {
        HttpSession session = WebContextFactory.get().getSession();
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean cambiarImagen = false;
        DatosUsuarioDTO datos = new DatosUsuarioDTO();
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            datos = (DatosUsuarioDTO) session.getAttribute("datosUsuario");
            datos.setImagenPerfil((String) session.getAttribute("img"));
            cambiarImagen = new UsuarioDAO().cambiarImagen(conexion, datos);
            if (cambiarImagen != false) {
                conexion.commit();
                cambiarImagen = true;
            } else {
                conexion.rollback();
                cambiarImagen = false;
            }
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return cambiarImagen;
    }

    /**
     *
     * @param datosUsuario1
     * @param datosUsuarioSeguridad
     * @return
     */
    public RegistroDTO editarUsuario(UsuarioDTO datosUsuario1, UsuarioSeguridadDTO datosUsuarioSeguridad) {

        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean editarUsuario = false;
        boolean editarNombreUsuario = false;
        RegistroDTO registroExitoso = null;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new RegistroDTO();
            String fecha = Formato.formatoFecha(datosUsuario1.getFechaNacimiento());
            datosUsuario1.setFechaNacimiento(fecha);
            System.out.println("mediador ::: " + datosUsuario1.toStringJson() + " ::: " + datosUsuarioSeguridad.toStringJson());

            if (!"".equals(datosUsuario1.getId())) {
                editarUsuario = new UsuarioDAO().editarUsuario(conexion, datosUsuario1);
                editarNombreUsuario = new UsuarioSeguridadDAO().editarNombreUsuarioSeguridad(conexion, datosUsuario1, datosUsuarioSeguridad);
            }

            if (editarUsuario != false) {
                conexion.commit();
                registroExitoso.setCondicion(true);
                registroExitoso.setMensaje("Registro Exitoso");
            } else {
                conexion.rollback();
                registroExitoso = null;
            }
            conexion.close();
            conexion = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     * @param idUsuario
     * @return
     */
    public boolean activarEstadoUsuario(String idUsuario) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new UsuarioDAO().activarEstadoUsuario(conexion, idUsuario, Constantes.ESTADO_ACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     * @param idUsuario
     * @return
     */
    public boolean inactivarEstadoUsuario(String idUsuario) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new UsuarioDAO().inactivarEstadoUsuario(conexion, idUsuario, Constantes.ESTADO_INACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @return
     */
    public ArrayList<TipoDocumentoDTO> listarTipoDocumento() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<TipoDocumentoDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new TipoDocumentoDAO().listarTipoDocumento(conexion, Constantes.ESTADO_ACTIVO);
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param datosTipoDocumento
     * @return
     */
    public boolean registrarTipoDocumento(TipoDocumentoDTO datosTipoDocumento) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new TipoDocumentoDAO().registrarTipoDocumento(conexion, datosTipoDocumento, datosUsuario.getUsuario());
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }

        return registroExitoso;
    }

    /**
     * @param datosTipoDocumento
     * @return
     */
    public boolean actualizarTipoDocumento(TipoDocumentoDTO datosTipoDocumento) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new TipoDocumentoDAO().actualizarTipoDocumento(conexion, datosTipoDocumento, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean eliminarTipoDocumento(String id) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean deleteExitoso = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            deleteExitoso = new TipoDocumentoDAO().eliminarTipoDocumento(conexion, id, Constantes.ESTADO_ACTIVO);

            conexion.close();
            conexion = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            deleteExitoso = false;
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                deleteExitoso = false;
            }
        }
        return deleteExitoso;
    }

    /**
     *
     * @return
     */
    public ArrayList<TipoDocumentoDTO> listarTodosLosTipoDocumento() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<TipoDocumentoDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new TipoDocumentoDAO().listarTodosLosTipoDocumento(conexion);
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @return
     */
    public ArrayList<TipoUsuarioDTO> listarCargos() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<TipoUsuarioDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new TipoUsuarioDAO().listarCargos(conexion, Constantes.ESTADO_ACTIVO);
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param datosCargo
     * @return
     */
    public boolean registrarCargo(TipoUsuarioDTO datosCargo) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new TipoUsuarioDAO().registrarCargo(conexion, datosCargo, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     * @param datosCargo
     * @return
     */
    public boolean actualizarCargo(TipoUsuarioDTO datosCargo) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new TipoUsuarioDAO().actualizarCargo(conexion, datosCargo, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean eliminarCargo(String id) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean deleteExitoso = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            deleteExitoso = new TipoUsuarioDAO().eliminarCargo(conexion, id, Constantes.ESTADO_INACTIVO);

            conexion.close();
            conexion = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            deleteExitoso = false;
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                deleteExitoso = false;
            }
        }
        return deleteExitoso;
    }

    /**
     *
     * @return
     */
    public ArrayList<TipoUsuarioDTO> listarTodosLosCargos() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<TipoUsuarioDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new TipoUsuarioDAO().listarTodosLosCargos(conexion);
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param documento
     * @return
     */
    public DatosUsuarioDTO recuperarContrasenia(String documento) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        DatosUsuarioDTO datosUsuario = null;
        String nuevaContrasenia = Generales.EMPTYSTRING;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            datosUsuario = new DatosUsuarioDAO().recuperarContrasenia(conexion, documento);

            if (datosUsuario == null) {
                throw new Exception("no se encotraron datos con con esa cedula");
            }

            String usauId = datosUsuario.getIdUsuario();

            nuevaContrasenia = UUID.randomUUID().toString().substring(0, 6);

            if (!new UsuarioSeguridadDAO().generarContrasenia(conexion, usauId, nuevaContrasenia)) {
                throw new Exception("no se actualiza la coontrasaÃ±a");
            }

            datosUsuario.setClave(nuevaContrasenia);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }

        return datosUsuario;
    }

    /**
     *
     * @return @param id
     */
    public UsuarioDTO consultarUsuarioPorId(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        UsuarioDTO datos = null;

        try {
            HttpSession session = WebContextFactory.get().getSession();
            DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            datos = new UsuarioDAO().consultarUsuarioPorId(conexion, id);
            String fecha = Formato.formatoFechaMostrar(datos.getFechaNacimiento());
            datos.setFechaNacimiento(fecha);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }

        return datos;
    }

    /**
     *
     * @param datosUsuario
     * @return
     */
    public boolean actualizarUsuarioPerfil(UsuarioDTO datosUsuario) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            HttpSession session = WebContextFactory.get().getSession();
            DatosUsuarioDTO datosUsuarioLogueado = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

            datosUsuario.setIdUsuario(datosUsuarioLogueado.getIdUsuario());

            if (!new UsuarioDAO().actualizarUsuarioPerfil(conexion, datosUsuario)) {
                throw new Exception("Error, no se pudo actualizar el usuario");
            }

            registroExitoso = true;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param datosUsuarioSeguridad
     * @return
     */
    public boolean cambiarContrasenia(UsuarioSeguridadDTO datosUsuarioSeguridad) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;

        boolean editarUsuarioSeguridad = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            HttpSession session = WebContextFactory.get().getSession();
            DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

            datosUsuarioSeguridad.setIdUsuario(datosUsuario.getIdUsuario());

            if (!new UsuarioSeguridadDAO().cambiarContrasenia(conexion, datosUsuarioSeguridad)) {
                throw new Exception("Error, no se pudo actualizar la contraseÃ±a");
            }

            editarUsuarioSeguridad = true;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return editarUsuarioSeguridad;
    }

    /**
     *
     * @param datosUsuario
     * @return
     */
    public boolean validarDocumento(UsuarioDTO datosUsuario) {

        HttpSession session = WebContextFactory.get().getSession();
        DataBaseConnection dbcon = null;
        Connection conexion = null;

        boolean validarUsuario = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);

            validarUsuario = new UsuarioDAO().validarDocumento(conexion, datosUsuario);

            if (validarUsuario) {
                conexion.commit();

            } else {
                conexion.rollback();
            }
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return validarUsuario;
    }

    /**
     *
     * @param mensaje
     * @return
     */
    public ResponseMensajeDTO enviarMensaje(BodyMensajeDTO mensaje) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        String urlRequest = Generales.EMPTYSTRING;
        String respuesta = Generales.EMPTYSTRING;
        ResponseMensajeDTO respuestaMensaje = null;

        // Obtener objeto URL
        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            urlRequest = "https://api.infobip.com/sms/1/text/single";
            URL url = new URL(urlRequest);

            // Establecer conexion a la URL indicada
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Basic  QXZhbnpvOk9jdHVicmUuMjAxNw==");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            logMsg.loggerMessageDebug("Request Body|\n" + mensaje.toStringJson());

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(mensaje.toStringJson());
            wr.flush();
            wr.close();

            if (conn.getResponseCode() != 200) {
                throw new Exception("Mensaje API Rejection : Error " + conn.getResponseCode() + " : " + conn.getResponseMessage());
            } else {
                registroExitoso = true;
            }

            // =====================================================================================================================================================
            //
            // Obtener respuesta de la conexion
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String responseJSON = "";

            // Leer la respuesta obtenida
            while ((respuesta = rd.readLine()) != null) {
                responseJSON += respuesta;
            }
            rd.close();

            // Imprimir la respuesta JSON para DEBUG
            logMsg.loggerMessageDebug("|Response|Mensaje API|\n" + new JSONObject(responseJSON).toString(3));
            responseJSON = new JSONObject(responseJSON).toString(3);
            respuestaMensaje = new ObjectMapper().readValue(responseJSON, ResponseMensajeDTO.class);

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return respuestaMensaje;
    }

    /**
     *
     * @param mensaje
     * @return
     */
    public ResponseMensajeDTO enviarCodigo(BodyMensajeDTO mensaje) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ResponseMensajeDTO respuesta = null;
        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            if (true) {
                mensaje.setFrom("InfoSMS");
                mensaje.setTo("57" + mensaje.getTo());
                mensaje.setKeyword("verificacion");
                mensaje.setText("El reporte diario del proyecto: " + mensaje.getText() + "no ha sido enviado.");

                if (Constantes.ENVIO_MENSAJE) {
                    ResponseMensajeDTO responseMensaje = enviarMensaje(mensaje);
                    if (!responseMensaje.getMessages().get(0).getStatus().getGroupName().equals(Constantes.RESPONSE_MENSAJE_ENVIADO)) {
                        logMsg.loggerMessageDebug(" | false");
                        logMsg.loggerMessageDebug(" | hubo un error enviando mensaje");
                    } else {
                        logMsg.loggerMessageDebug(" | true");
                        logMsg.loggerMessageDebug(" |  Se envio  mensaje");
                    }
                } else {
                    logMsg.loggerMessageDebug(" | true");
                    logMsg.loggerMessageDebug(" |  Se envio  mensaje");
                }
            } else {
                //datosRespuesta.setResult("false");
                // datosRespuesta.setMensaje("No se encontro codigo de verificacion en el usuario: " + datosUsuario.toStringJson());
            }

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return respuesta;
    }

    /**
     *
     * @param datosMedico
     * @return
     */
    public boolean registrarMedico(MedicoDTO datosMedico) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new MedicoDAO().registrarMedico(conexion, datosMedico, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param datosMedico
     * @return
     */
    public boolean actualizarMedico(MedicoDTO datosMedico) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new MedicoDAO().actualizarMedico(conexion, datosMedico, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean eliminarMedico(String id) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean deleteExitoso = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            deleteExitoso = new MedicoDAO().eliminarMedico(conexion, id, Constantes.ESTADO_INACTIVO);

            conexion.close();
            conexion = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            deleteExitoso = false;
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                deleteExitoso = false;
            }
        }
        return deleteExitoso;
    }

    /**
     *
     * @return
     */
    public ArrayList<MedicoDTO> listarMedicos() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<MedicoDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new MedicoDAO().listarMedicos(conexion);
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @return @param id
     */
    public MedicoDTO ConsultarMedicoXId(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        MedicoDTO datos = null;

        try {
            HttpSession session = WebContextFactory.get().getSession();
            DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            datos = new MedicoDAO().ConsultarMedicoXId(conexion, id);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }

        return datos;
    }

    /**
     *
     * @param datosEspecialidad
     * @return
     */
    public boolean registrarEspecialidad(EspecialidadDTO datosEspecialidad) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new EspecialidadDAO().registrarEspecialidad(conexion, datosEspecialidad, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param datosEspecialidad
     * @return
     */
    public boolean actualizarEspecialidad(EspecialidadDTO datosEspecialidad) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new EspecialidadDAO().actualizarEspecialidad(conexion, datosEspecialidad, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @return
     */
    public ArrayList<EspecialidadDTO> listarTodasEspecialidades() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<EspecialidadDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new EspecialidadDAO().listarTodasEspecialidades(conexion, Constantes.ESTADO_ACTIVO);
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean eliminarEspecialidad(String id) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean deleteExitoso = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            deleteExitoso = new EspecialidadDAO().eliminarEspecialidad(conexion, id);

            conexion.close();
            conexion = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            deleteExitoso = false;
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                deleteExitoso = false;
            }
        }
        return deleteExitoso;
    }

    /**
     *
     * @param datosMedico
     * @return
     */
    public boolean validarDocumentoMedico(MedicoDTO datosMedico) {

        HttpSession session = WebContextFactory.get().getSession();
        DataBaseConnection dbcon = null;
        Connection conexion = null;

        boolean validarUsuario = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);

            validarUsuario = new MedicoDAO().validarDocumentoMedico(conexion, datosMedico);

            if (validarUsuario) {
                conexion.commit();

            } else {
                conexion.rollback();
            }
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return validarUsuario;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean activarEstadoMedico(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new MedicoDAO().activarEstadoMedico(conexion, id, Constantes.ESTADO_ACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean inactivarEstadoMedico(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new MedicoDAO().inactivarEstadoMedico(conexion, id, Constantes.ESTADO_INACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param datosProcedimiento
     * @return
     */
    public boolean registrarProcedimiento(ProcedimientoDTO datosProcedimiento) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new ProcedimientoDAO().registrarProcedimiento(conexion, datosProcedimiento, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param datosProcedimiento
     * @return
     */
    public boolean actualizarProcedimiento(ProcedimientoDTO datosProcedimiento) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            registroExitoso = new ProcedimientoDAO().actualizarProcedimiento(conexion, datosProcedimiento, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean eliminarProcedimiento(String id) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean deleteExitoso = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            deleteExitoso = new ProcedimientoDAO().eliminarProcedimiento(conexion, id);

            conexion.close();
            conexion = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            deleteExitoso = false;
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                deleteExitoso = false;
            }
        }
        return deleteExitoso;
    }

    /**
     *
     * @return
     */
    public ArrayList<ProcedimientoDTO> listarProcedimiento() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<ProcedimientoDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new ProcedimientoDAO().listarProcedimiento(conexion, Constantes.ESTADO_ACTIVO);
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean activarEstadoProcedimiento(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new ProcedimientoDAO().activarEstadoProcedimiento(conexion, id, Constantes.ESTADO_ACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean inactivarEstadoProcedimiento(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new ProcedimientoDAO().inactivarEstadoProcedimiento(conexion, id, Constantes.ESTADO_INACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public ProcedimientoDTO ConsultarProcedimientoXId(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ProcedimientoDTO datos = null;

        try {
            HttpSession session = WebContextFactory.get().getSession();
            DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            datos = new ProcedimientoDAO().ConsultarProcedimientoXId(conexion, id);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }

        return datos;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean activarEstadoEspecialidad(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new EspecialidadDAO().activarEstadoEspecialidad(conexion, id, Constantes.ESTADO_ACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean inactivarEstadoEspecialidad(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new EspecialidadDAO().inactivarEstadoEspecialidad(conexion, id, Constantes.ESTADO_INACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public EspecialidadDTO ConsultarEspecialidadXId(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        EspecialidadDTO datos = null;

        try {
            HttpSession session = WebContextFactory.get().getSession();
            DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            datos = new EspecialidadDAO().ConsultarEspecialidadXId(conexion, id);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }

        return datos;
    }

    /**
     *
     * @param datosGastos
     * @return
     */
    public String registrarGastos(GastosDTO datosGastos) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            datosGastos.setFecha(Formato.formatoFecha(datosGastos.getFecha()));
            datosGastos.setEstado(Constantes.NO_CONFIRMADO);
            registroExitoso = new GastosDAO().registrarGastos(conexion, datosGastos, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return datosGastos.getId();
    }

    /**
     *
     * @param datosGastos
     * @return
     */
    public boolean actualizarGastos(GastosDTO datosGastos) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            datosGastos.setFecha(Formato.formatoFecha(datosGastos.getFecha()));
            System.out.println("fecha " + datosGastos.getFecha());
            registroExitoso = new GastosDAO().actualizarGastos(conexion, datosGastos, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean eliminarGastos(String id) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean deleteExitoso = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            deleteExitoso = new GastosDAO().eliminarGastos(conexion, id);

            conexion.close();
            conexion = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            deleteExitoso = false;
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                deleteExitoso = false;
            }
        }
        return deleteExitoso;
    }

    /**
     *
     * @return
     */
    public ArrayList<GastosDTO> listarGastos() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<GastosDTO> listado = null;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new GastosDAO().listarGastos(conexion);

            for (int i = 0; i < listado.size(); i++) {
                listado.get(i).setFecha(Formato.formatoFechaMostrar(listado.get(i).getFecha()));
            }

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean activarEstadoGastos(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new GastosDAO().activarEstadoGastos(conexion, id, Constantes.CONFIRMADO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean inactivarEstadoGastos(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new GastosDAO().inactivarEstadoGastos(conexion, id, Constantes.ESTADO_INACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public GastosDTO ConsultarGastosXId(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        GastosDTO datos = null;

        try {
            HttpSession session = WebContextFactory.get().getSession();
            DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            datos = new GastosDAO().ConsultarGastosXId(conexion, id);
            datos.setFecha(Formato.formatoFechaMostrar(datos.getFecha()));

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }

        return datos;
    }

    /**
     *
     * @param datosArticulos
     * @return
     */
    public String registrarArticulo(ArticulosDTO datosArticulos) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            //datosArticulos.setFecha(Formato.formatoFecha(datosGastos.getFecha()));
            //datosArticulos.setEstado(Constantes.ESTADO_ACTIVO);
            datosArticulos.setCantidad(Constantes.ZERO);
            datosArticulos.setEstado(Constantes.ESTADO_ACTIVO);
            registroExitoso = new ArticulosDAO().registrarArticulo(conexion, datosArticulos, datosUsuario.getUsuario());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return datosArticulos.getId();
    }

    /**
     *
     * @param datosArticulo
     * @return
     */
    public boolean actualizarArticulo(ArticulosDTO datosArticulo) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);
            //datosArticulo.setFecha(Formato.formatoFecha(datosGastos.getFecha()));   
            //System.out.println("fecha " + datosArticulo.getFecha());
            registroExitoso = new ArticulosDAO().actualizarArticulo(conexion, datosArticulo, datosUsuario.getUsuario());
            System.out.println("datos " + datosArticulo.toStringJson());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean eliminarArticulo(String id) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean deleteExitoso = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            deleteExitoso = new ArticulosDAO().eliminarArticulo(conexion, id);

            conexion.close();
            conexion = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            deleteExitoso = false;
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                deleteExitoso = false;
            }
        }
        return deleteExitoso;
    }

    /**
     *
     * @param datosArticulo
     * @return
     */
    public boolean validarReferencia(ArticulosDTO datosArticulo) {

        HttpSession session = WebContextFactory.get().getSession();
        DataBaseConnection dbcon = null;
        Connection conexion = null;

        boolean validarUsuario = false;

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);

            validarUsuario = new ArticulosDAO().validarReferencia(conexion, datosArticulo);

            if (validarUsuario) {
                conexion.commit();

            } else {
                conexion.rollback();
            }
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return validarUsuario;
    }

    /**
     *
     * @return
     */
    public ArrayList<ArticulosDTO> listarTodosLosArticulos() {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<ArticulosDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new ArticulosDAO().listarTodosLosArticulos(conexion);
            for (int i = 0; i < listado.size(); i++) {
                if ("1".equals(listado.get(i).getUnidadMedidad())) {
                    listado.get(i).setUnidadMedidad("Unidad");
                }
                if ("2".equals(listado.get(i).getUnidadMedidad())) {
                    listado.get(i).setUnidadMedidad("Kit");
                }
                if ("3".equals(listado.get(i).getUnidadMedidad())) {
                    listado.get(i).setUnidadMedidad("Combo");
                }
            }
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean activarEstadoArticulo(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new ArticulosDAO().activarEstadoArticulo(conexion, id, Constantes.ESTADO_ACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean inactivarEstadoArticulo(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            registroExitoso = new ArticulosDAO().inactivarEstadoArticulo(conexion, id, Constantes.ESTADO_INACTIVO);
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return registroExitoso;
    }

    /**
     *
     * @param id
     * @return
     */
    public ArticulosDTO ConsultarArticulosXId(String id) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArticulosDTO datos = null;

        try {
            HttpSession session = WebContextFactory.get().getSession();
            DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            datos = new ArticulosDAO().ConsultarArticulosXId(conexion, id);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }

        return datos;
    }

    /**
     *
     * @param condicion
     * @return
     */
    public ArrayList<ArticulosDTO> buscarPorReferencia(String condicion) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<ArticulosDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new ArticulosDAO().buscarPorReferencia(conexion, condicion);
            for (int i = 0; i < listado.size(); i++) {
                if ("1".equals(listado.get(i).getUnidadMedidad())) {
                    listado.get(i).setUnidadMedidad("Unidad");
                }
                if ("2".equals(listado.get(i).getUnidadMedidad())) {
                    listado.get(i).setUnidadMedidad("Kit");
                }
                if ("3".equals(listado.get(i).getUnidadMedidad())) {
                    listado.get(i).setUnidadMedidad("Combo");
                }
            }
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param condicion
     * @return
     */
    public ArrayList<ArticulosDTO> buscarPorDescripcion(String condicion) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<ArticulosDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new ArticulosDAO().buscarPorDescripcion(conexion, condicion);

            for (int i = 0; i < listado.size(); i++) {
                if ("1".equals(listado.get(i).getUnidadMedidad())) {
                    listado.get(i).setUnidadMedidad("Unidad");
                }
                if ("2".equals(listado.get(i).getUnidadMedidad())) {
                    listado.get(i).setUnidadMedidad("Kit");
                }
                if ("3".equals(listado.get(i).getUnidadMedidad())) {
                    listado.get(i).setUnidadMedidad("Combo");
                }
            }
            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param datosGastoDetalle
     * @return
     */
    public String registrarDetalleGasto(DetalleGastosDTO datosGastoDetalle) {
        HttpSession session = WebContextFactory.get().getSession();
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean registroExitoso = false;
        boolean actualizarCantidad = false;
        ArticulosDTO datosArticulo = null;
        int cantidadArticulo = 0;
        int resta = 0;
        int cantidadDescontar = 0;
        String operacion = "";

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            conexion.setAutoCommit(false);

            datosArticulo = new ArticulosDAO().ConsultarArticulosXId(conexion, datosGastoDetalle.getIdArticulos());

            if ("0".equals(datosArticulo.getCantidad())) {
                registroExitoso = false;
                operacion = "0";
                System.out.println("la cantidad del articulo seleccionado es 0 no hay existencias en el inventario");
            } else {
                cantidadArticulo = Integer.parseInt(datosArticulo.getCantidad());
                cantidadDescontar = Integer.parseInt(datosGastoDetalle.getCantidad());
                resta = cantidadArticulo - cantidadDescontar;
                System.out.println("el resultado de Resta es ::::: " + resta);
                if (resta < 0) {
                    operacion = "1";
                    System.out.println("Resta es negativo por lo cual la operacion no puede ejecutarse");
                } else {
                    System.out.println("valores a actualizar cantidad y idArticulo  ::::: " + String.valueOf(resta) + " ::: " + datosGastoDetalle.getIdArticulos());
                    actualizarCantidad = new ArticulosDAO().actualizarCantidad(conexion, String.valueOf(resta), datosGastoDetalle.getIdArticulos());
                    System.out.println("se actualizo cantidad con exito ? :::::: " + actualizarCantidad);
                    if (actualizarCantidad == true) {
                        registroExitoso = new DetalleGastosDAO().registrarDetalleGasto(conexion, datosGastoDetalle, datosUsuario.getUsuario());
                        System.out.println("el registro del detalle a sido :::::: " + registroExitoso);
                        if (registroExitoso == true) {
                            operacion = "2";
                        }

                    }

                }

            }

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return operacion;
    }

    /**
     *
     * @param idGasto
     * @return
     */
    public ArrayList<DetalleGastosDTO> listarDetalleGastoXIdGasto(String idGasto) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<DetalleGastosDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            listado = new DetalleGastosDAO().listarDetalleGastoXIdGasto(conexion, idGasto);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param id
     * @param cantidad
     * @param idArticulo
     * @return
     */
    public String eliminarDetalleGasto(String id, String cantidad, String idArticulo) {

        DataBaseConnection dbcon = null;
        Connection conexion = null;
        boolean deleteExitoso = false;
        boolean actualizarCantidad = false;
        ArticulosDTO datosArticulos = null;

        int actual = 0;
        int suma = 0;
        int valor = 0;

        String eliminado = "";

        try {

            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);
            //deleteExitoso = new DetalleGastosDAO().eliminarDetalleGasto(conexion, id);

            datosArticulos = new ArticulosDAO().ConsultarArticulosXId(conexion, idArticulo);
            System.out.println("datos articulo en mediador ::: " + datosArticulos.toStringJson());
            actual = Integer.valueOf(datosArticulos.getCantidad());
            System.out.println("actual ::: " + actual);
            valor = Integer.valueOf(cantidad);
            System.out.println("valor ::: " + valor);
            suma = actual + valor;

            deleteExitoso = new DetalleGastosDAO().eliminarDetalleGasto(conexion, id);

            if (deleteExitoso == true) {
                actualizarCantidad = new ArticulosDAO().actualizarCantidad(conexion, Integer.toString(suma), idArticulo);
                if (actualizarCantidad == true) {
                    eliminado = "1";
                }
            } else {
                eliminado = "0";
            }

            conexion.close();
            conexion = null;

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            deleteExitoso = false;
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                deleteExitoso = false;
            }
        }
        return eliminado;
    }

    /**
     *
     * @param condicion
     * @return
     */
    public ArrayList<GastosDTO> buscarGastoFecha(String condicion) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<GastosDTO> listado = null;

        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            condicion = Formato.formatoFecha(condicion);
            System.out.println("condicion :::::::: " + condicion);

            listado = new GastosDAO().buscarGastoFecha(conexion, condicion);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param condicion
     * @return
     */
    public ArrayList<GastosDTO> buscarGastoCedulaPaciente(String condicion) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<GastosDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            listado = new GastosDAO().buscarGastoCedulaPaciente(conexion, condicion);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

    /**
     *
     * @param condicion
     * @return
     */
    public ArrayList<GastosDTO> ConsultarGastosXId1(String condicion) {
        DataBaseConnection dbcon = null;
        Connection conexion = null;
        ArrayList<GastosDTO> listado = null;
        try {
            dbcon = DataBaseConnection.getInstance();
            conexion = dbcon.getConnection(ContextDataResourceNames.MYSQL_GASTOS_JDBC);

            listado = new GastosDAO().ConsultarGastosXId1(conexion, condicion);

            conexion.close();
            conexion = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    conexion = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (SQLException e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
            }
        }
        return listado;
    }

}
