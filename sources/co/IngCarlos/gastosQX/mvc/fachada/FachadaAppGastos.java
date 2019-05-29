/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */
package co.IngCarlos.gastosQX.mvc.fachada;

import co.IngCarlos.gastosQX.mvc.dto.ArticulosDTO;
import co.IngCarlos.gastosQX.mvc.dto.DatosUsuarioDTO;
import co.IngCarlos.gastosQX.mvc.dto.DetalleGastosDTO;
import co.IngCarlos.gastosQX.mvc.dto.EspecialidadDTO;
import co.IngCarlos.gastosQX.mvc.dto.GastosDTO;
import co.IngCarlos.gastosQX.mvc.dto.MedicoDTO;
import co.IngCarlos.gastosQX.mvc.dto.ProcedimientoDTO;
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

    /**
     *
     * @param datosProcedimiento
     * @return
     */
    @RemoteMethod
    public boolean registrarProcedimiento(ProcedimientoDTO datosProcedimiento) {
        return MediadorAppGastos.getInstancia().registrarProcedimiento(datosProcedimiento);
    }

    /**
     *
     * @param datosProcedimiento
     * @return
     */
    @RemoteMethod
    public boolean actualizarProcedimiento(ProcedimientoDTO datosProcedimiento) {
        return MediadorAppGastos.getInstancia().actualizarProcedimiento(datosProcedimiento);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean eliminarProcedimiento(String id) {
        return MediadorAppGastos.getInstancia().eliminarProcedimiento(id);
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public ArrayList<ProcedimientoDTO> listarProcedimiento() {
        return MediadorAppGastos.getInstancia().listarProcedimiento();
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean activarEstadoProcedimiento(String id) {
        return MediadorAppGastos.getInstancia().activarEstadoProcedimiento(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean inactivarEstadoProcedimiento(String id) {
        return MediadorAppGastos.getInstancia().inactivarEstadoProcedimiento(id);
    }

    /**
     *
     * @return @param id
     */
    @RemoteMethod
    public ProcedimientoDTO ConsultarProcedimientoXId(String id) {
        return MediadorAppGastos.getInstancia().ConsultarProcedimientoXId(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean activarEstadoEspecialidad(String id) {
        return MediadorAppGastos.getInstancia().activarEstadoEspecialidad(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean inactivarEstadoEspecialidad(String id) {
        return MediadorAppGastos.getInstancia().inactivarEstadoEspecialidad(id);
    }

    /**
     *
     * @return @param id
     */
    @RemoteMethod
    public EspecialidadDTO ConsultarEspecialidadXId(String id) {
        return MediadorAppGastos.getInstancia().ConsultarEspecialidadXId(id);
    }

    /**
     *
     * @param datosGastos
     * @return
     */
    @RemoteMethod
    public String registrarGastos(GastosDTO datosGastos) {
        return MediadorAppGastos.getInstancia().registrarGastos(datosGastos);
    }

    /**
     *
     * @param datosGastos
     * @return
     */
    @RemoteMethod
    public boolean actualizarGastos(GastosDTO datosGastos) {
        System.out.println("datosGastos " + datosGastos.toStringJson());
        return MediadorAppGastos.getInstancia().actualizarGastos(datosGastos);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean eliminarGastos(String id) {
        return MediadorAppGastos.getInstancia().eliminarGastos(id);
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public ArrayList<GastosDTO> listarGastos() {
        return MediadorAppGastos.getInstancia().listarGastos();
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean activarEstadoGastos(String id) {
        return MediadorAppGastos.getInstancia().activarEstadoGastos(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean inactivarEstadoGastos(String id) {
        return MediadorAppGastos.getInstancia().inactivarEstadoGastos(id);
    }

    /**
     *
     * @return @param id
     */
    @RemoteMethod
    public GastosDTO ConsultarGastosXId(String id) {
        return MediadorAppGastos.getInstancia().ConsultarGastosXId(id);
    }

    /**
     *
     * @param datosArticulos
     * @return
     */
    @RemoteMethod
    public String registrarArticulo(ArticulosDTO datosArticulos) {
        return MediadorAppGastos.getInstancia().registrarArticulo(datosArticulos);
    }

    /**
     *
     * @param datosArticulos
     * @return
     */
    @RemoteMethod
    public boolean validarReferencia(ArticulosDTO datosArticulos) {
        return MediadorAppGastos.getInstancia().validarReferencia(datosArticulos);
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public ArrayList<ArticulosDTO> listarTodosLosArticulos() {
        return MediadorAppGastos.getInstancia().listarTodosLosArticulos();
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean activarEstadoArticulo(String id) {
        return MediadorAppGastos.getInstancia().activarEstadoArticulo(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @RemoteMethod
    public boolean inactivarEstadoArticulo(String id) {
        return MediadorAppGastos.getInstancia().inactivarEstadoArticulo(id);
    }

    /**
     *
     * @return @param id
     */
    @RemoteMethod
    public ArticulosDTO ConsultarArticulosXId(String id) {
        return MediadorAppGastos.getInstancia().ConsultarArticulosXId(id);
    }

    /**
     *
     * @param datosArticulos
     * @return
     */
    @RemoteMethod
    public boolean actualizarArticulo(ArticulosDTO datosArticulos) {
        return MediadorAppGastos.getInstancia().actualizarArticulo(datosArticulos);
    }

    /**
     *
     * @param condicion
     * @return
     */
    @RemoteMethod
    public ArrayList<ArticulosDTO> buscarPorReferencia(String condicion) {
        System.out.println("condicionRefe " + condicion);
        return MediadorAppGastos.getInstancia().buscarPorReferencia(condicion);
    }

    /**
     *
     * @param condicion
     * @return
     */
    @RemoteMethod
    public ArrayList<ArticulosDTO> buscarPorDescripcion(String condicion) {
        System.out.println("condicionDescrip " + condicion);
        return MediadorAppGastos.getInstancia().buscarPorDescripcion(condicion);
    }

    /**
     *
     * @param datosGastoDetalle
     * @return
     */
    @RemoteMethod
    public String registrarDetalleGasto(DetalleGastosDTO datosGastoDetalle) {
        return MediadorAppGastos.getInstancia().registrarDetalleGasto(datosGastoDetalle);
    }

    /**
     *
     * @param idGasto
     * @return
     */
    @RemoteMethod
    public ArrayList<DetalleGastosDTO> listarDetalleGastoXIdGasto(String idGasto) {
        System.out.println("condicionRefe " + idGasto);
        return MediadorAppGastos.getInstancia().listarDetalleGastoXIdGasto(idGasto);
    }

    /**
     *
     * @param id
     * @param cantidad
     * @param idArticulo
     * @return
     */
    @RemoteMethod
    public String eliminarDetalleGasto(String id, String cantidad, String idArticulo) {
        return MediadorAppGastos.getInstancia().eliminarDetalleGasto(id, cantidad, idArticulo);
    }

    /**
     *
     * @param condicion
     * @return
     */
    @RemoteMethod
    public ArrayList<GastosDTO> buscarGastoFecha(String condicion) {
        return MediadorAppGastos.getInstancia().buscarGastoFecha(condicion);
    }

    /**
     *
     * @param condicion
     * @return
     */
    @RemoteMethod
    public ArrayList<GastosDTO> buscarGastoCedulaPaciente(String condicion) {
        return MediadorAppGastos.getInstancia().buscarGastoCedulaPaciente(condicion);
    }

    /**
     *
     * @param condicion
     * @return
     */
    @RemoteMethod
    public ArrayList<GastosDTO> ConsultarGastosXId1(String condicion) {
        return MediadorAppGastos.getInstancia().ConsultarGastosXId1(condicion);
    }

}
