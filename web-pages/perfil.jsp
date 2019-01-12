<%-- 
    Document   : perfil
    Created on : 12/01/2019, 10:58:20 AM
    Author     : Ing. Carlos
--%>

<%@page import="co.IngCarlos.gastosQX.common.util.Formato"%>
<%@page import="co.IngCarlos.gastosQX.common.util.Constantes"%>
<%@page import="co.IngCarlos.gastosQX.mvc.dto.DatosUsuarioDTO"%>
<%@ page import="java.util.*,java.io.*"%>
<%@page session='true'%>

<%
    DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");
    System.out.println("DatosUsuario" + datosUsuario.toStringJson());
%>

<script>
    var idUsuarioSesion = <%=datosUsuario.getIdUsuario()%>;
</script>

<script type="text/javascript" src="js/perfil.js"></script>

<!-- Inicio Cuerpo -->       

<div class="row">

    <div class="menu_wrapper">
        <ul class="nav navbar-nav">
            <li class="active"><a onclick="javascript:miCuenta();"><i class="el-icon-adult"></i>&nbsp&nbspMi Cuenta</a></li>
            <li class="active"><a onclick="javascript:editarPerfil();"><i class="el-icon-pencil"></i>&nbsp&nbspEditar Perfil</a></li>
            <li class="active"><a onclick="javascript:cambioContrasenia();"><i class="el-icon-unlock-alt"></i>&nbsp&nbsp Cambiar Contraseña</a></li>
        </ul>                        
    </div>

    <div class="col-md-12">
        <div id="alert"></div>
    </div>

</div>

<div class="container" id="miCuenta">
    <div class="container">
        <div class="col-sm-12">
            <div class="col-sm-6">
                <h5><strong>MI CUENTA</strong></h5>
                <P>Datos Registrados.</P>
            </div>
            <div class="col-sm-6">
                <p><%= datosUsuario.getTipoUsuario()%>, <b><%= datosUsuario.getNombre()%></b></p>
                <p>Usuario: <b><%= datosUsuario.getUsuario()%></b></p>
                <p>Tipo De Usario: <b><%= datosUsuario.getTipoUsuario()%></b></p>
            </div>
        </div>        
    </div><hr/>

    <div class="col-sm-12">
        <div class="col-sm-6 form-group">                                                            
            <h5><strong>Nombres:</strong></h5>
            <label><strong><%= datosUsuario.getNombre()%></strong></label>
        </div>
        <div class="col-sm-6 form-group">
            <h5><strong>Apellidos:</strong></h5>
            <label><strong><%= datosUsuario.getApellido()%></strong></label>
        </div>                        
        <div class="col-sm-6 form-group">                            
            <h5><strong>Tipo Documento:</strong></h5>                                                       
            <label><strong><%= datosUsuario.getTipoDocumento()%></strong></label>                            
        </div>
        <div class="col-sm-6 form-group">                            
            <h5><strong>Documento:</strong></h5>                                                        
            <label><strong><%= datosUsuario.getDocumento()%></strong></label>                            
        </div>

        <div class="col-sm-6 form-group">                            
            <h5><strong>Email:</strong></h5>                                                        
            <label><strong><%= datosUsuario.getCorreo()%></strong></label>                            
        </div>
        <div class="col-sm-6 form-group">                            
            <h5><strong>Fecha Nacimiento:</strong></h5>                                                        
            <label><strong><%= Formato.formatoFechaMostrar(datosUsuario.getFechaNacimieno())%></strong></label>                            
        </div>
        <div class="col-sm-6 form-group">                            
            <h5><strong>Estado:</strong></h5>                                                                                                      
            <label><strong><%= datosUsuario.getEstado()%></strong></label>                                 
        </div>
        <div class="col-sm-6 form-group">                            
            <h5><strong>Tipo Usuario:</strong></h5>                                                        
            <label><strong><%= datosUsuario.getTipoUsuario()%></strong></label>                            
        </div>
        <div class="col-sm-6 form-group">                            
            <h5><strong>Departamento:</strong></h5>                                                        
            <label><strong><%= datosUsuario.getDepartamento()%></strong></label>                            
        </div>
        <div class="col-sm-6 form-group">                            
            <h5><strong>Municipio:</strong></h5>                                                        
            <label><strong><%= datosUsuario.getMunicipio()%></strong></label>                            
        </div>
        <div class="col-sm-6 form-group">                            
            <h5><strong>Genero:</strong></h5>                                                        
            <label><strong><%= datosUsuario.getGenero()%></strong></label>                            
        </div>
        <div class="col-sm-6 form-group">                            
            <h5><strong>Telefono Movil:</strong></h5>                                                        
            <label><strong><%= datosUsuario.getCelular()%></strong></label>                            
        </div>
        <div class="col-sm-6 form-group">                            
            <h5><strong>Direccion:</strong></h5>                                                        
            <label><strong><%= datosUsuario.getDireccion()%></strong></label>                            
        </div>
        <div class="col-sm-6 form-group">                            
            <h5><strong>Telefono Fijo:</strong></h5>                                                        
            <label><strong><%= datosUsuario.getTelefono()%></strong></label>                            
        </div>

    </div>                                        
</div>                              

<div class="container" id="editarPerfil">
    <div class="row">
        <h5><strong>EDITAR PERFIL</strong></h5>                        
    </div><br>                 

    <form id="form_validation" action="autocomplete:off" novalidate>
        <div class="row">
            <div class="col-md-6 form-group">
                <label for="val_first_name" class="req">Nombres:</label>
                <div class="input-group">
                    <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                    <input type="text" id="nombreUsuario" required class="form-control" onkeypress="return soloLetras(event);"/>
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_last_name" class="req">Apellidos:</label>
                <div class="input-group">
                    <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                    <input type="text" id="apellidoUsuario" required class="form-control" onkeypress="return soloLetras(event);"/>
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_first_name" class="req">Tipo Doumento:</label>
                <div class="input-group">
                    <span class="input-group-addon"><i class="icon_id bs_ttip"></i></span>
                    <select id="tipoDocumento" class="form-control" required></select>
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_last_name" class="req"># Documento:</label>
                <div class="input-group">
                    <span class="input-group-addon"><i class="icon_id-2 bs_ttip"></i></span>
                    <input type="text" id="documento" required class="form-control" disabled onkeypress="return soloNumeros(event);" maxlength="10"/>
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_last_name" class="req">Email:</label>
                <div class="input-group">
                    <span class="input-group-addon"><i class="icon_mail bs_ttip"></i></span>
                    <input type="email" id="email" required class="form-control" />
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_last_name" class="req">Fecha Nacimiento:</label>                                
                <div class="input-group date" id="dp_component">
                    <span class="input-group-addon"><i class="icon_calendar"></i></span>
                    <input type="text" class="form-control" id="fechaNacimiento" required>                
                </div>                                
            </div>
            <div class="col-md-6 form-group">
                <label for="val_first_name" class="req">Estado:</label>
                <div class="input-group date" id="dp_component">
                    <span class="input-group-addon"><i class="social_flickr_square bs_ttip"></i></span>
                    <select id="estado" class="form-control" required>
                        <option value="">-Seleccione uno-</option>
                        <option value="1">Activo</option>
                        <option value="0">Inactivo</option>
                    </select>
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_first_name" class="req">Tipo Usuario:</label>
                <div class="input-group date" id="dp_component">
                    <span class="input-group-addon"><i class="icon_group bs_ttip"></i></span>
                    <select id="tipoUsuario" class="form-control" required></select>
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_first_name" class="req">Departamento:</label>
                <div class="input-group date" id="dp_component">
                    <span class="input-group-addon"><i class="el-icon-caret-right"></i></span>
                    <select id="departamento" class="form-control" required></select>
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_first_name" class="req">Municipio:</label>
                <div class="input-group date" id="dp_component">
                    <span class="input-group-addon"><i class="el-icon-chevron-right"></i></span>
                    <select id="municipio" class="form-control" required></select>
                </div>
            </div>
            <div class="col-md-6 form-group" class="req">
                <label for="val_first_name">Genero:</label>
                <div class="input-group date" id="dp_component">
                    <span class="input-group-addon"><i class="el-icon-universal-access bs_ttip"></i></span>
                    <select id="genero" class="form-control" required></select>
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_last_name" class="req">Telefono Movil:</label>
                <div class="input-group date" id="dp_component">
                    <span class="input-group-addon"><i class="el-icon-phone-alt"></i></span>
                    <input type="text" id="telefonoMovil" required class="form-control"/>
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_last_name" class="req">Direccion:</label>
                <div class="input-group date" id="dp_component">
                    <span class="input-group-addon"><i class="el-icon-road bs_ttip"></i></span>
                    <input type="text" id="direccion" required class="form-control"/>
                </div>
            </div>
            <div class="col-md-6 form-group">
                <label for="val_last_name" class="req">Telefono Fijo:</label>
                <div class="input-group date" id="dp_component">
                    <span class="input-group-addon"><i class="el-icon-phone bs_ttip"></i></span>
                    <input type="text" id="telefonoFijo" required class="form-control"/>
                </div>
            </div>
            <div class="col-md-6 form-group">

            </div>
            <div class="col-md-6 form-group">
                <label for="val_last_name" class="req">Usuario:</label>
                <div class="input-group date" id="dp_component">
                    <span class="input-group-addon"><i class="el-icon-torso bs_ttip"></i></span>
                    <input type="text" id="usuario" required class="form-control" disabled/>
                </div>
            </div>

            <div class="col-md-6 form-group">

            </div>
            <div class="col-md-6 form-group"><br><br>
                <div class="col-md-5 form-group"></div>
                <button class="btn btn-primary" id="btnRegistrar" onclick="javascript:validar2('form_validation');">Guardar</button>            
                <button class="btn btn-primary" onclick="javascript:miCuenta();">Volver</button>
            </div>

        </div>
    </form>

</div>

<div class="container" id="cambioContrasenia">
    <div class="row">
        <h5><strong>CAMBIAR CONTRASEÑA</strong></h5>                        
    </div><br>

    <form id="form_validation1" name="form_validation1" action="autocomplete:off" novalidate>
        <div class="row">

            <div class="col-md-4 form-group">

            </div>

            <div class="col-md-4 form-group">
                <label for="val_last_name">Contraseña Actual:</label>
                <input type="password" id="contraseniaAnterior" class="form-control" placeholder="Contraseña actual" maxlength="10" minlength="6" name="contraseniaAnterior" title="Ingrese contraseña, Mínimo 6 y máximo 10 caracteres" onkeypress="return validarUsuarios(event);" required/><br>
                <label for="val_last_name">Nueva Contraseña</label>
                <input type="password" id="nuevaContrasenia" class="form-control" placeholder="Nueva contraseña" maxlength="10" minlength="6" name="nuevaContrasenia" title="Ingrese contraseña, Mínimo 6 y máximo 10 caracteres" onkeypress="return validarUsuarios(event);" required/><br>
                <label for="val_last_name">Confirmar Contraseña:</label>
                <input type="password" id="confNnuevaContrasenia" class="form-control" name="confNnuevaContrasenia" placeholder="Comprobar contraseña" equalTo="#nuevaContrasenia" maxlength="10" minlength="6" title="Las contraseñas no coinciden" onkeypress="return validarUsuarios(event);" required/><br>
                <div class="text-center">
                    <button class="btn btn-primary" onclick="javascript:validar('form_validation1');">Cambiar</button>                                    
                    <button class="btn btn-primary" onclick="javascript:miCuenta();">Volver</button>
                </div>                                
            </div>

            <div class="col-md-4 form-group">

            </div>                                                                                                                                                                     

        </div>
    </form>

</div>
<!--Fin cuerpo-->                
<script>
    jQuery(document).ready(function() {
    jQuery("#miCuenta").show();
    jQuery("#editarPerfil").hide();
    jQuery("#cambioContrasenia").hide();

    ajaxCnk.listarDepartamento({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("departamento");
                dwr.util.addOptions("departamento", [{
                        id: '',
                        nombre: ''
                    }], 'id', 'nombre');
                dwr.util.addOptions("departamento", data, 'id', 'nombre');
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarTipoDocumento({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("tipoDocumento");
                dwr.util.addOptions("tipoDocumento", [{
                        id: '',
                        descripcion: 'Seleccione tipo de documento'
                    }], 'id', 'descripcion');
                dwr.util.addOptions("tipoDocumento", data, 'id', 'descripcion');
                jQuery('#tipoDocumento option[value=1]').hide();
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarGenero({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("genero");
                dwr.util.addOptions("genero", [{
                        id: '',
                        descripcion: 'Seleccione género'
                    }], 'id', 'descripcion');
                dwr.util.addOptions("genero", data, 'id', 'descripcion');
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarCargos({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("tipoUsuario");
                dwr.util.addOptions("tipoUsuario", [{
                        id: '',
                        descripcion: 'Seleccione cargo'
                    }], 'id', 'descripcion');
                dwr.util.addOptions("tipoUsuario", data, 'id', 'descripcion');
            }
        },
        timeout: 20000
    });

});

function miCuenta() {
    jQuery("#miCuenta").show();
    jQuery("#editarPerfil").hide();
    jQuery("#cambioContrasenia").hide();
    limpiarFormularioRegistro();
    activar();
}

function editarPerfil() {
    jQuery("#miCuenta").hide();
    jQuery("#editarPerfil").show();
    jQuery("#cambioContrasenia").hide();
    console.log("%%%%%%%% ",idUsuarioSesion);
    editarUsuario(idUsuarioSesion);
}

function cambioContrasenia() {
    jQuery("#miCuenta").hide();
    jQuery("#editarPerfil").hide();
    jQuery("#cambioContrasenia").show();
}

function listarMunicipioEditar(idDepartamento) {
    ajaxCnk.listarMunicipioPorDepartamentoSelecionado(idDepartamento, {
        callback: function(data) {
            if (data !== null) {
                console.log("idDepartamento ", idDepartamento);
                console.log("data ", data);
                dwr.util.removeAllOptions("municipio");
                dwr.util.addOptions("municipio", [{
                        id: '',
                        nombre: ' '
                    }], 'id', 'nombre');
                dwr.util.addOptions("municipio", data, 'id', 'nombre');
                jQuery("#municipio").val(parseInt(muni)).trigger("change");
            }
        },
        timeout: 20000
    });
}

function editarUsuario(id) {
    disponible = true;
    ajaxCnk.consultarUsuarioPorId(id, {
        callback: function(data) {

            if (data !== null) {
                console.log("editar ", data);
                idUsuario = id;
                jQuery("#tituloEditar").show();
                jQuery("#divEditarUsuario").show();
                jQuery("#tituloGestion").hide();
                jQuery("#divTablaUsuarios").hide();
                listarMunicipioEditar(data.idDepartamento);
                jQuery('#nombreUsuario').val(data.nombre);
                jQuery('#apellidoUsuario').val(data.apellido);
                jQuery('#tipoDocumento').val(data.idTipoDocumento);
                jQuery('#documento').val(data.documento);
                jQuery('#email').val(data.correo);
                jQuery('#fechaNacimiento').val(data.fechaNacimiento);
                jQuery('#estado').val(data.estado);
                jQuery('#tipoUsuario').val(data.idTipoUsuario);
                depa = data.idDepartamento;
                jQuery('#departamento').val(depa).trigger("change");
                muni = data.idMunicipio;
                jQuery('#municipio').val(muni).trigger("change");
                jQuery('#genero').val(data.idGenero);
                jQuery('#telefonoMovil').val(data.celular);
                jQuery('#direccion').val(data.direccion);
                jQuery('#telefonoFijo').val(data.telefono);
                //jQuery('#direccion2').val(data.direccion1);
                jQuery('#usuario').val(data.usuario);
            }
        },
        timeout: 20000
    });
}

function registrarEditarUsuario() {
    var usuario = {
        id: idUsuario,
        nombre: jQuery("#nombreUsuario").val(),
        apellido: jQuery("#apellidoUsuario").val(),
        idTipoDocumento: jQuery("#tipoDocumento").val(),
        documento: jQuery("#documento").val(),
        correo: jQuery("#email").val(),
        fechaNacimiento: jQuery("#fechaNacimiento").val(),
        estado: jQuery("#estado").val(),
        idTipoUsuario: jQuery("#tipoUsuario").val(),
        municipio: jQuery("#municipio").val(),
        genero: jQuery("#genero").val(),
        celular: jQuery("#telefonoMovil").val(),
        direccion: jQuery("#direccion").val(),
        telefono: jQuery("#telefonoFijo").val()
                //direccion1: jQuery("#direccion2").val()
    };

    console.log("usuario ", usuario);

    ajaxCnk.editarUsuario(usuario, {
        callback: function(data) {
            if (data !== null) {
                notificacion("success", "el usuario se ha editado con exito", "alert");
            } else {
                notificacion("danger", "se ha generado un error", "alert");
            }
        },
        timeout: 20000
    });
    desactivar();
}

function desactivar() {
    jQuery("#nombreUsuario").prop("disabled", true);
    jQuery("#apellidoUsuario").prop("disabled", true);
    jQuery("#tipoDocumento").prop("disabled", true);
    jQuery("#documento").prop("disabled", true);
    jQuery("#email").prop("disabled", true);
    jQuery("#fechaNacimiento").prop("disabled", true);
    jQuery("#estado").prop("disabled", true);
    jQuery("#tipoUsuario").prop("disabled", true);
    jQuery("#departamento").prop("disabled", true);
    jQuery("#municipio").prop("disabled", true);
    jQuery("#genero").prop("disabled", true);
    jQuery("#telefonoMovil").prop("disabled", true);
    jQuery("#direccion").prop("disabled", true);
    jQuery("#telefonoFijo").prop("disabled", true);
    //jQuery("#direccion2").prop("disabled", true);
    jQuery("#usuario").prop("disabled", true);
    jQuery("#contrasenia").prop("disabled", true);
    jQuery("#contrasenia1").prop("disabled", true);
    jQuery("#btnRegistrar").prop("disabled", true);
}

function activar() {
    jQuery("#nombreUsuario").prop("disabled", false);
    jQuery("#apellidoUsuario").prop("disabled", false);
    jQuery("#tipoDocumento").prop("disabled", false);
    jQuery("#documento").prop("disabled", false);
    jQuery("#email").prop("disabled", false);
    jQuery("#fechaNacimiento").prop("disabled", false);
    jQuery("#estado").prop("disabled", false);
    jQuery("#tipoUsuario").prop("disabled", false);
    jQuery("#departamento").prop("disabled", false);
    jQuery("#municipio").prop("disabled", false);
    jQuery("#genero").prop("disabled", false);
    jQuery("#telefonoMovil").prop("disabled", false);
    jQuery("#direccion").prop("disabled", false);
    jQuery("#telefonoFijo").prop("disabled", false);
    //jQuery("#direccion2").prop("disabled", false);
    jQuery("#usuario").prop("disabled", false);
    jQuery("#contrasenia").prop("disabled", false);
    jQuery("#contrasenia1").prop("disabled", false);
    jQuery("#btnRegistrar").prop("disabled", false);
}

function limpiarFormularioRegistro() {
    jQuery("#nombreUsuario").val("");
    jQuery("#apellidoUsuario").val("");
    jQuery("#tipoDocumento").val("");
    jQuery("#documento").val("");
    jQuery("#email").val("");
    jQuery("#fechaNacimiento").val("");
    jQuery("#estado").val("");
    jQuery("#tipoUsuario").val("");
    jQuery("#departamento").val("");
    jQuery("#municipio").val("");
    jQuery("#genero").val("");
    jQuery("#telefonoMovil").val("");
    jQuery("#direccion").val("");
    jQuery("#telefonoFijo").val("");
    //jQuery("#direccion2").val("");
    jQuery("#usuario").val("");

}

function cambiarContraseniaUsuario() {

    var datos = {
        clave: jQuery('#contraseniaAnterior').val(),
        nuevaClave: jQuery('#nuevaContrasenia').val()
    };
    ajaxCnk.cambiarContrasenia(datos, {
        callback: function(data) {
            if (data === true) {
                notificacion("success", "CONTRASEÑA ACTUALIZADA", "alert");
                jQuery('#formContrasenia input[type="password"]').each(function() {
                    jQuery(this).val("");
                });
                cargarPagina('perfil.jsp');
            } else {
                notificacion("danger", "ERROR AL CAMBIAR LA CONTRASEÑA", "alert");
            }
        }, timeout: 20000
    });
}

function notificacion(tipo, msj, id) {
    $(".alert").alert('close');
    $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
    setTimeout('$(".alert").alert("close");', '10000');
}

function validar(formulario) {

    $('#' + formulario).validate({
        highlight: function(label) {
            jQuery(label).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function(label) {
            jQuery(label).closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function(error, element) {
            var placement = element.closest('.input-group');
            if (!placement.get(0)) {
                placement = element;
            }
            if (error.text() !== '') {
                placement.after(error);
            }
        },
        submitHandler: function() {

            cambiarContraseniaUsuario();

        }

    });
}

function validar2(formulario) {

    $('#' + formulario).validate({
        highlight: function(label) {
            jQuery(label).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function(label) {
            jQuery(label).closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function(error, element) {
            var placement = element.closest('.input-group');
            if (!placement.get(0)) {
                placement = element;
            }
            if (error.text() !== '') {
                placement.after(error);
            }
        },
        submitHandler: function() {

            registrarEditarUsuario();

        }

    });
}

$(function() {
    // wysiwg editor
    yukon_wysiwg.p_forms_validation();
    // multiselect
    yukon_select2.p_forms_validation();
    // validation
    yukon_parsley_validation.p_forms_validation();
});

$(function() {
    // select2
    yukon_select2.p_forms_extended();
    // datepicker
    yukon_datepicker.p_forms_extended();
    // date range picker
    yukon_date_range_picker.p_forms_extended();
    // rangeSlider
    yukon_rangeSlider.p_forms_extended();
    // textarea autosize
    yukon_textarea_autosize.init();
    // masked inputs
    yukon_maskedInputs.p_forms_extended();
    // maxlength for textareas
    yukon_textarea_maxlength.p_forms_extended();
    // multiuploader
    yukon_uploader.p_forms_extended();
    // 2col multiselect
    yukon_2col_multiselect.init();
    // clock picker
    yukon_clock_picker.init();
    // chained selects
    yukon_chained_selects.init();
    // password show/hide
    yukon_pwd_show_hide.init();
    // password strength metter
    yukon_pwd_strength_metter.init();
    // checkboxes & radio buttons
    yukon_icheck.init();
    // selectize.js
    yukon_selectize.p_forms_extended();
    // wysiwg editor
    yukon_wysiwg.p_forms_extended();
});

function soloNumeros1(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = "0,1,2,3,4,5,6,7,8,9";
    especiales = "8-37-39-46";
    tecla_especial = false;
    for (var i in especiales) {
        if (key === especiales[i]) {
            tecla_especial = true;
            break;
        }
    }
    if (letras.indexOf(tecla) === -1 && !tecla_especial) {
        return false;
    }
}

function soloNumeros(e) {
    console.log("entra a la funcion");
    var key = e.which || e.keyCode;
    console.log("key", key);
    if ((key < 48 && key != 8) || key > 57) {
        e.preventDefault();
    }
}

function soloLetras1(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = " áéíóúabcdefghijklmnñopqrstuvwxyz";
    especiales = "8-37-39-46";
    tecla_especial = false;
    for (var i in especiales) {
        if (key === especiales[i]) {
            tecla_especial = true;
            break;
        }
    }
    if (letras.indexOf(tecla) === -1 && !tecla_especial) {
        return false;
    }
}

function soloLetras(e) {
    var key = e.which || e.keyCode;
    console.log("key", key);
    if ((key < 63 && key != 8 && key != 32) || key > 122) {
        e.preventDefault();
    }
}

function validarUsuarios(e) {

    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = "abcdefghijklmnopqrstuvwxyz,0,1,2,3,4,5,6,7,8,9,|,°,!,#,$,%,&,/,(,),=,?,¿,¡,*,+,-,_";
    especiales = "8-33-37-39-46";
    tecla_especial = false;
    for (var i in especiales) {
        if (key === especiales[i]) {
            tecla_especial = true;
            break;
        }
    }
    if (letras.indexOf(tecla) === -1 && !tecla_especial) {
        return false;
    }
}


</script>
