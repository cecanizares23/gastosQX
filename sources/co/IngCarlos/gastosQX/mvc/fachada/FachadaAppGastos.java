/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */
package co.IngCarlos.gastosQX.mvc.fachada;

import co.IngCarlos.gastosQX.mvc.dto.DatosUsuarioDTO;
import co.IngCarlos.gastosQX.mvc.dto.EspecialidadDTO;
import co.IngCarlos.gastosQX.mvc.dto.MedicoDTO;
import co.IngCarlos.gastosQX.mvc.dto.RegistroDTO;
import co.IngCarlos.gastosQX.mvc.dto.TipoDocumentoDTO;
import co.IngCarlos.gastosQX.mvc.dto.TipoUsuarioDTO;
import co.IngCarlos.gastosQX.mvc.dto.UsuarioDTO;
import co.IngCarlos.gastosQX.mvc.dto.UsuarioSeguridadDTO;
import co.IngCarlos.gastosQX.mvc.mediador.MediadorAppGastos;
import java.util.ArrayList;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.ScriptScope;

/**
 *
 * @author carlos
 */
@RemoteProxy(name = "ajaxGastos", scope = ScriptScope.SESSION)
public class FachadaAppGastos {

    /**
     *
     */
    public FachadaAppGastos() {
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public boolean servicioActivo() {
        return true;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * LOS METODOS APARTIR DE AQUI NO HAN SIDO VALIDADOS
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     *
     * @param datosUsuario
     * @param datosUsuarioSeguridad
     * @return
     */
    @RemoteMethod
    public RegistroDTO registrarUsuario(UsuarioDTO datosUsuario, UsuarioSeguridadDTO datosUsuarioSeguridad) {
        return MediadorAppGastos.getInstancia().registrarUsuario(datosUsuario, datosUsuarioSeguridad);

    }

    /**
     *
     * @param datosUsuario
     * @return
     */
    @RemoteMethod
    public boolean validarUsuario(UsuarioSeguridadDTO datosUsuario) {
        return MediadorAppGastos.getInstancia().validarUsuario(datosUsuario);
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public ArrayList<UsuarioDTO> listarUsuarios() {
        return MediadorAppGastos.getInstancia().listarUsuarios();
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public ArrayList<UsuarioDTO> listarUsuarioSeguridad() {
        return MediadorAppGastos.getInstancia().listarUsuarioSeguridad();
    }

    /**
     *
     * @param usuario
     * @return
     */
    @RemoteMethod
    public UsuarioDTO consultarUsuario(String usuario) {
        return MediadorAppGastos.getInstancia().consultarUsuario(usuario);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean eliminarUsuario(String id) {
        return MediadorAppGastos.getInstancia().eliminarUsuario(id);
    }

    /**
     * @return
     */
    @RemoteMethod
    public boolean cambiarImagen() {
        return MediadorAppGastos.getInstancia().cambiarImagen();
    }

    /**
     *
     * @param datosUsuario
     * @param datosUsuarioSeguridad
     * @return
     */
    @RemoteMethod
    public RegistroDTO editarUsuario(UsuarioDTO datosUsuario, UsuarioSeguridadDTO datosUsuarioSeguridad) {
        System.out.println("entra fachada");
        return MediadorAppGastos.getInstancia().editarUsuario(datosUsuario, datosUsuarioSeguridad);

    }

    /**
     *
     * @param idUsuario
     * @return
     */
    @RemoteMethod
    public boolean activarEstadoUsuario(String idUsuario) {
        return MediadorAppGastos.getInstancia().activarEstadoUsuario(idUsuario);
    }

    /**
     *
     * @param idUsuario
     * @return
     */
    @RemoteMethod
    public boolean inactivarEstadoUsuario(String idUsuario) {
        return MediadorAppGastos.getInstancia().inactivarEstadoUsuario(idUsuario);
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public ArrayList<TipoDocumentoDTO> listarTipoDocumento() {
        return MediadorAppGastos.getInstancia().listarTipoDocumento();
    }

    /**
     *
     * @param datosTipoDocumento
     * @return
     */
    @RemoteMethod
    public boolean registrarTipoDocumento(TipoDocumentoDTO datosTipoDocumento) {
        return MediadorAppGastos.getInstancia().registrarTipoDocumento(datosTipoDocumento);
    }

    /**
     *
     * @param datosTipoDocumento
     * @return
     */
    @RemoteMethod
    public boolean actualizarTipoDocumento(TipoDocumentoDTO datosTipoDocumento) {
        return MediadorAppGastos.getInstancia().actualizarTipoDocumento(datosTipoDocumento);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean eliminarTipoDocumento(String id) {
        return MediadorAppGastos.getInstancia().eliminarTipoDocumento(id);
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public ArrayList<TipoDocumentoDTO> listarTodosLosTipoDocumento() {
        return MediadorAppGastos.getInstancia().listarTodosLosTipoDocumento();
    }

    /**
     *
     * @param documento
     * @return
     */
    public DatosUsuarioDTO recuperarContrasenia(String documento) {
        return MediadorAppGastos.getInstancia().recuperarContrasenia(documento);
    }

    /**
     *
     * @return @param id
     */
    @RemoteMethod
    public UsuarioDTO consultarUsuarioPorId(String id) {
        return MediadorAppGastos.getInstancia().consultarUsuarioPorId(id);
    }

    /**
     *
     * @param datosUsuario
     * @return
     */
    @RemoteMethod
    public boolean actualizarUsuarioPerfil(UsuarioDTO datosUsuario) {
        return MediadorAppGastos.getInstancia().actualizarUsuarioPerfil(datosUsuario);
    }

    /**
     *
     * @param datosUsuarioSeguridad
     * @return
     */
    @RemoteMethod
    public boolean cambiarContrasenia(UsuarioSeguridadDTO datosUsuarioSeguridad) {
        return MediadorAppGastos.getInstancia().cambiarContrasenia(datosUsuarioSeguridad);

    }

    /**
     *
     * @param datosUsuario
     * @return
     */
    @RemoteMethod
    public boolean validarDocumento(UsuarioDTO datosUsuario) {
        return MediadorAppGastos.getInstancia().validarDocumento(datosUsuario);
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public ArrayList<TipoUsuarioDTO> listarCargos() {
        return MediadorAppGastos.getInstancia().listarCargos();
    }

    /**
     *
     * @param datosTipoUsuario
     * @return
     */
    @RemoteMethod
    public boolean registrarCargo(TipoUsuarioDTO datosTipoUsuario) {
        return MediadorAppGastos.getInstancia().registrarCargo(datosTipoUsuario);
    }

    /**
     *
     * @param datosTipoUsuario
     * @return
     */
    @RemoteMethod
    public boolean actualizarCargo(TipoUsuarioDTO datosTipoUsuario) {
        return MediadorAppGastos.getInstancia().actualizarCargo(datosTipoUsuario);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean eliminarCargo(String id) {
        return MediadorAppGastos.getInstancia().eliminarCargo(id);
    }

    /**
     *
     * @param datosMedico
     * @return
     */
    @RemoteMethod
    public boolean registrarMedico(MedicoDTO datosMedico) {
        return MediadorAppGastos.getInstancia().registrarMedico(datosMedico);
    }

    /**
     *
     * @param datosMedico
     * @return
     */
    @RemoteMethod
    public boolean actualizarMedico(MedicoDTO datosMedico) {
        return MediadorAppGastos.getInstancia().actualizarMedico(datosMedico);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean eliminarMedico(String id) {
        return MediadorAppGastos.getInstancia().eliminarMedico(id);
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public ArrayList<MedicoDTO> listarMedicos() {
        return MediadorAppGastos.getInstancia().listarMedicos();
    }

    /**
     *
     * @param datosEspecialidad
     * @return
     */
    @RemoteMethod
    public boolean registrarEspecialidad(EspecialidadDTO datosEspecialidad) {
        return MediadorAppGastos.getInstancia().registrarEspecialidad(datosEspecialidad);
    }

    /**
     *
     * @param datosEspecialidad
     * @return
     */
    @RemoteMethod
    public boolean actualizarEspecialidad(EspecialidadDTO datosEspecialidad) {
        return MediadorAppGastos.getInstancia().actualizarEspecialidad(datosEspecialidad);
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public ArrayList<EspecialidadDTO> listarTodasEspecialidades() {
        return MediadorAppGastos.getInstancia().listarTodasEspecialidades();
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean eliminarEspecialidad(String id) {
        return MediadorAppGastos.getInstancia().eliminarEspecialidad(id);
    }

    /**
     *
     * @param datosMedico
     * @return
     */
    @RemoteMethod
    public boolean validarDocumentoMedico(MedicoDTO datosMedico) {
        return MediadorAppGastos.getInstancia().validarDocumentoMedico(datosMedico);
    }
    
     /**
      * 
      * @param id
      * @return 
      */
    @RemoteMethod
    public boolean activarEstadoMedico(String id) {
        return MediadorAppGastos.getInstancia().activarEstadoMedico(id);
    }

    /**
     * 
     * @param id
     * @return 
     */
    @RemoteMethod
    public boolean inactivarEstadoMedico(String id) {
        return MediadorAppGastos.getInstancia().inactivarEstadoMedico(id);
    }
    
    /**
     *
     * @return @param id
     */
    @RemoteMethod
    public MedicoDTO ConsultarMedicoXId(String id) {
        return MediadorAppGastos.getInstancia().ConsultarMedicoXId(id);
    }
    
}
