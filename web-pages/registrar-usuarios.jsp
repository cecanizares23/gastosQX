<%-- 
    Document   : registrar-usuarios
    Created on : 27-sep-2018, 14:18:55
    Author     : Ing. Carlos Cañizares
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

<div class="row">
    <h5><strong>REGISTRAR USUARIO</strong></h5>
</div>

<form id="form_validation" name="form_validation" action="autocomplete:off" novalidate>
    <div class="row">       

        <div class="alert alert-danger" id="mensajeDocumento">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <strong>Ya hay un usuario registrado con este numero de documento!</strong>
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Nombres:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="nombreUsuario" class="form-control" maxlength="50" onkeypress="return soloLetras(event);" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Apellidos:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="apellidoUsuario" class="form-control" maxlength="50" onkeypress="return soloLetras(event);" required/>
            </div>
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Tipo Documento:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_id bs_ttip"></i></span>
                <select id="tipoDocumento" class="form-control" required></select>
            </div>
        </div>

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req"># Documento:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_id-2 bs_ttip"></i></span>
                <input type="text" id="documento" class="form-control" onkeypress="javascript:soloNumeros(event);" onblur="validaDocumento(this.value)" maxlength="12" required/>
            </div>
        </div>

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Fecha Nacimiento:</label>                                
            <div class="input-group date" id="dp_component">
                <span class="input-group-addon"><i class="icon_calendar"></i></span>
                <input type="text" class="form-control" id="fechaNacimiento" required />                
            </div>                                
        </div>

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Email:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_mail bs_ttip"></i></span>
                <input type="email" id="email" class="form-control" required maxlength="50"/>
            </div>
        </div>        

        <div class="col-md-6 form-group">            
            <label for="val_last_name" class="req">Teléfono Movil:</label>
            <div class="input-group date">
                <span class="input-group-addon"><i class="el-icon-phone-alt"></i></span>
                <input type="text" id="telefonoMovil" class="form-control" onkeypress="return soloNumeros(event);" maxlength="10" required/>
            </div>
        </div>        

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Estado:</label>
            <div class="input-group date" >
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
            <div class="input-group date">
                <span class="input-group-addon"><i class="icon_group bs_ttip"></i></span>
                <select id="tipoUsuario" class="form-control" required></select>
            </div>
        </div>                                                

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Usuario:</label>
            <div class="input-group date" id="dp_component">
                <span class="input-group-addon"><i class="el-icon-torso bs_ttip"></i></span>
                <input type="text" id="usuario" class="form-control" onkeypress="return soloLetras(event);" onblur="validaUsuario(this.value)" maxlength="11" required/> <!--onkeypress="return soloLetras()(event);"-->
            </div>
        </div>                                    

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Contraseña:</label>
            <div class="input-group date" id="dp_component">
                <span class="input-group-addon"><i class="el-icon-eye-open"></i></span>
                <input type="password" id="contrasenia" class="form-control" maxlength="100" required/>
            </div>
        </div>        

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Confirmar Contraseña:</label>
            <div class="input-group date" id="dp_component">
                <span class="input-group-addon"><i class="el-icon-eye-open"></i></span>
                <input type="password" id="contrasenia1" class="form-control" maxlength="100" equalTo="#contrasenia" required/>
            </div>
        </div>

        <div class="col-md-6 form-group">
            <div class="alert alert-danger" id="mensajeUsuario">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <strong>El usuario ya existe en la base de datos!</strong>
            </div>
        </div>


        <div class="col-md-12 form-group"><br><br>
            <div class="col-md-9 form-group"></div>
            <div class="col-md-3 form-group">
                <button class="btn btn-primary" id="btnRegistrar" onclick="javascript:validar('form_validation');">Guardar</button>
                <a class="btn btn-primary" onclick="javascript:cargarPagina('listar-usuarios.jsp');">Volver</a>
            </div>

        </div>

        <div class="col-md-12">
            <div id="alert"></div>
        </div>

    </div>
</form>

<!--Fin cuerpo de la pagina-->


<script>

    //globales
    var idUsuario = "";

    jQuery(document).ready(function() {

        jQuery("#mensajeUsuario").hide();
        jQuery("#mensajeDocumento").hide();

        ajaxGastos.listarTipoDocumento({
            callback: function(data) {
                if (data !== null) {
                    console.log("data ",data);
                    dwr.util.removeAllOptions("tipoDocumento");
                    dwr.util.addOptions("tipoDocumento", [{
                            id: '',
                            descripcion: 'Seleccione tipo de documento'
                        }], 'id', 'descripcion');
                    dwr.util.addOptions("tipoDocumento", data, 'id', 'descripcion');
                    //jQuery('#tipoDocumento option[value=1]').hide();
                }
            },
            timeout: 20000
        });

        ajaxGastos.listarCargos({
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

    

    function validaUsuario(user) {
        var datosUsuario = {
            usuario: user
        };
        if (user == "") {
            return false;
        }
        ajaxGastos.validarUsuario(datosUsuario, {
            callback: function(data) {
                
                if (data == true) {
                    jQuery("#mensajeUsuario").show();
                    jQuery("#btnRegistrar").prop("disabled", true);
                    jQuery("#usuario").focus();
                    setTimeout('jQuery("#usuario").val("");','1000');
                }
                else if (data == false) {
                    jQuery("#btnRegistrar").prop("disabled", false);
                    jQuery("#mensajeUsuario").hide();
                }
            },
            timeout: 20000
        });
    }

    function validaDocumento(documento) {
        var datosDocumentoUsuario = {
            documento: documento
        };
        if (documento == "") {
            return false;
        }
        ajaxGastos.validarDocumento(datosDocumentoUsuario, {
            callback: function(data) {
                
                if (data == true) {
                    jQuery("#mensajeDocumento").show();
                    jQuery("#btnRegistrar").prop("disabled", true);
                    jQuery("#documento").focus();
                    setTimeout('jQuery("#documento").val("");','1000');
                }
                else if (data == false) {
                    jQuery("#btnRegistrar").prop("disabled", false);
                    jQuery("#mensajeDocumento").hide();
                }
            },
            timeout: 20000

        });

    }

    function registrarUsuario() {
        $("#btnRegistrar").prop('disabled', true);
        var usuarioSeguridad = {
            usuario: jQuery("#usuario").val(),
            clave: jQuery("#contrasenia").val()
        };
        var usuario = {
            nombre: jQuery("#nombreUsuario").val(),
            apellido: jQuery("#apellidoUsuario").val(),
            idTipoDocumento: jQuery("#tipoDocumento").val(),
            documento: jQuery("#documento").val(),
            correo: jQuery("#email").val(),
            fechaNacimiento: jQuery("#fechaNacimiento").val(),
            estado: jQuery("#estado").val(),
            tipoUsuario: jQuery("#tipoUsuario").val(),
            celular: jQuery("#telefonoMovil").val(),
            direccion: jQuery("#direccion").val(),
            telefono: jQuery("#telefonoFijo").val(),
            direccion1: jQuery("#direccion2").val()
        };        

        //validaUsuario();

        ajaxGastos.registrarUsuario(usuario, usuarioSeguridad, {
            callback: function(data) {

                if (data !== null) {
                    notificacion("success", "el usuario se ha registrado con éxito", "alert");
                    limpiarFormularioRegistro();
                    //cargarPagina("listar-usuarios.jsp");
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
            },
            timeout: 20000
        });
        //desactivar();        
    }

    function volver() {
        limpiarFormularioRegistro();
        cargarPagina("listar-usuarios.jsp");
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
        jQuery("#telefonoMovil").prop("disabled", true);
        jQuery("#direccion").prop("disabled", true);
        jQuery("#telefonoFijo").prop("disabled", true);
        jQuery("#direccion2").prop("disabled", true);
        jQuery("#usuario").prop("disabled", true);
        jQuery("#contrasenia").prop("disabled", true);
        jQuery("#contrasenia1").prop("disabled", true);
        jQuery("#btnRegistrar").prop("disabled", true);
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
        jQuery("#genero").val("");
        jQuery("#telefonoMovil").val("");
        jQuery("#direccion").val("");
        jQuery("#telefonoFijo").val("");
        jQuery("#direccion2").val("");
        jQuery("#usuario").val("");
        jQuery("#contrasenia").val("");
        jQuery("#contrasenia1").val("");
        $("#btnRegistrar").prop('disabled', false);
    }

    function notificacion(tipo, msj, id) {
        $(".alert").alert('close');
        $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
        setTimeout('$(".alert").alert("close");', '3000');
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

                registrarUsuario();

            }

        });
    }

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

    $(function() {
        // wysiwg editor
        yukon_wysiwg.p_forms_validation();
        // multiselect
        yukon_select2.p_forms_validation();
        // validation
        yukon_parsley_validation.p_forms_validation();
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
        
        var key = e.which || e.keyCode;        
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
        if ((key < 63 && key != 8 && key != 32) || key > 122) {
            e.preventDefault();
        }
    }

</script>
