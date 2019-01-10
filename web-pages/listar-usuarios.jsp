<%-- 
    Document   : listar-usuarios
    Created on : 27-sep-2018, 14:19:13
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
    <h5><strong>LISTADO DE USUARIOS</strong></h5>
</div>

<div class="col-md-12">
    <div id="alert"></div>
</div>

<div class="row" id="tablaReportes">
    <div class="col-md-12">

        <div class="col-md-12">            
            <div class="row">
                <div class="col-md-12">
                    <div class="table-responsive" >
                        <table class="table table-yuk2 toggle-arrow-tiny tablet breakpoint footable-loaded footable" id="footable_demo" data-filter="#textFilter" data-page-size="5">
                            <thead>
                                <tr>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">DOCUMENTO</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">NOMBRE</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">CORREO</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">USUARIO</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">ESTADO</th> 
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">EDITAR</th>                                  
                                </tr>
                            </thead>
                            <tbody id="listado">


                            </tbody>
                            <tfoot class="hide-if-no-paging">
                            <div class="row">
                                <div class="col-sm-9 col-sm-offset-3">
                                </div>
                            </div>
                            </tfoot>
                        </table>
                    </div>
                </div>                                                                
            </div>
        </div>

    </div>
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
                <input type="text" class="form-control" id="fechaNacimiento" disabled="true" required />                
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
                <input type="text" id="usuario" class="form-control" onkeypress="return soloLetras(event);" onblur="validaUsuario(this.value)" maxlength="11" required disabled="true"/> <!--onkeypress="return soloLetras()(event);"-->
            </div>
        </div>

        <div class="col-md-6 form-group" id="divIdUsuario">            
            <label for="val_last_name" class="req">Teléfono Movil:</label>
            <div class="input-group date">
                <span class="input-group-addon"><i class="el-icon-phone-alt"></i></span>
                <input type="text" id="idUsuario" class="form-control" onkeypress="return soloNumeros(event);" hidden="false" required/>
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
                <a class="btn btn-primary" id="btnVolver" onclick="javascript:volver();">Volver</a>
            </div>

        </div>

        <div class="col-md-12">
            <div id="alert"></div>
        </div>

    </div>
</form>

<script>
    jQuery(document).ready(function () {
        listarUsuarios();
        $("#form_validation").hide();

        ajaxGastos.listarTipoDocumento({
            callback: function (data) {
                if (data !== null) {
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
            callback: function (data) {
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

    var listadoUsuario = [];
    var mapaListadoUsuarios = [
        function (data) {
            return '<div class="text-center"><td>' + data.documento + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.nombre + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.correo + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.usuario + '</td></div>';
        },
        function (data) {
            if (data.estado == 0) {
                return '<div class="text-center"><td><button class="btn btn-success status-active" onclick="activarUsuario(' + data.id + ')">Activar</button></td></div>';
            }
            if (data.estado == 1) {
                if (data.id > 1) {
                    return '<div class="text-center"><td><button class="btn btn-danger status-active" onclick="inactivarUsuario(' + data.id + ')">Inactivar</button></td></div>';
                } else {
                    return '<div class="text-center"><td><button class="btn btn-danger status-active" disabled onclick="inactivarUsuario(' + data.id + ')">Inactivar</button></td></div>';
                }

            }
        },
        function (data) {
            if (data.id === "1")
                return '<div class="text-center"><td><button id="btnEditar" class="btn btn-primary status-active" disabled="true" onclick="cargarEditar(' + data.id + ');">Editar</button></td></div>';
            else
            if (data.estado === "1")
                return '<div class="text-center"><td><button id="btnEditar" class="btn btn-primary status-active" onclick="cargarEditar(' + data.id + ');">Editar</button></td></div>';
            else
                return '<div class="text-center"><td><button id="btnEditar" class="btn btn-primary status-active" disabled onclick="cargarEditar(' + data.id + ');">Editar</button></td></div>';

        }
    ];

    function listarUsuarios() {
        ajaxGastos.listarUsuarios({
            callback: function (data) {
                if (data !== null) {
                    //$("#tablaReportes").dataTable().fnDestroy();                    
                    dwr.util.removeAllRows("listado");
                    listadoUsuario = data;
                    console.log("ingres");
                    for (var i = 0; i < listadoUsuario.length; i++) {
                        if (listadoUsuario[i].id === "1") {
                            //$("#btnEditar").prop('disabled', true);
                            $("#btnEditar").prop('disabled', true);
                        }
                    }
                    dwr.util.addRows("listado", listadoUsuario, mapaListadoUsuarios, {
                        escapeHtml: false
                    });
                }
            },
            timeout: 20000
        });
    }

    function cargarEditar(id) {
        $("#btnRegistrar").prop('disabled', false);
        $("#form_validation").show();
        $("#tablaReportes").hide();
        $("#mensajeUsuario").hide();
        $("#mensajeDocumento").hide();
        $("#divIdUsuario").hide();
        ajaxGastos.consultarUsuario(id, {
            callback: function (data) {
                if (data !== null) {
                    $("#nombreUsuario").val(data.nombre);
                    $("#apellidoUsuario").val(data.apellido);
                    $("#tipoDocumento").val(data.tipoDocumento);
                    $("#documento").val(data.documento);
                    $("#fechaNacimiento").val(data.fechaNacimiento);
                    $("#email").val(data.correo);
                    $("#estado").val(data.estado);
                    $("#tipoUsuario").val(data.idTipoUsuario);
                    $("#usuario").val(data.usuario);
                    $("#telefonoMovil").val(data.celular);
                    $("#idUsuario").val(id);
                }
            },
            timeout: 20000
        });
    }

    function editarUsuarios() {

        var usuarioSeguridad = {
            usuario: jQuery("#usuario").val()
        };
        var usuario = {
            nombre: jQuery("#nombreUsuario").val(),
            apellido: jQuery("#apellidoUsuario").val(),
            idTipoDocumento: jQuery("#tipoDocumento").val(),
            documento: jQuery("#documento").val(),
            correo: jQuery("#email").val(),
            fechaNacimiento: jQuery("#fechaNacimiento").val(),
            estado: jQuery("#estado").val(),
            idTipoUsuario: jQuery("#tipoUsuario").val(),
            celular: jQuery("#telefonoMovil").val(),
            id: jQuery("#idUsuario").val()
        };

        //validaUsuario();

        ajaxGastos.editarUsuario(usuario, usuarioSeguridad, {
            callback: function (data) {

                if (data !== null) {
                    notificacion("success", "el usuario se ha editado con éxito", "alert");
                    limpiarFormularioRegistro();
                    setTimeout('$("#btnVolver").click();','2000');
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
            },
            timeout: 20000
        });
        //desactivar();        
    }

    function activarUsuario(idUsuario) {
        ajaxGastos.activarEstadoUsuario(idUsuario, {
            callback: function (data) {
                if (data) {
                    //listarUsuarios();
                    cargarPagina('listar-usuarios.jsp');
                } else {
                    notificacion("danger", "No se pudo actualizar el estado.", "alert");
                }
            },
            timeout: 20000
        });
    }

    function inactivarUsuario(idUsuario) {
        ajaxGastos.inactivarEstadoUsuario(idUsuario, {
            callback: function (data) {
                if (data) {
                    listarUsuarios();
                } else {
                    notificacion("danger", "No se pudo actualizar el estado.", "alert");
                }
            },
            timeout: 20000
        });
    }

    function volver() {
        $("#form_validation").hide();
        $("#tablaReportes").show();
        listarUsuarios();
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
        setTimeout('$(".alert").alert("close");', '10000');
    }

    var nextFunction = null;
    function validar(formulario) {
        nextFunction = 1;
        $('#' + formulario).validate({
            highlight: function (label) {
                jQuery(label).closest('.form-group').removeClass('has-success').addClass('has-error');
            },
            success: function (label) {
                jQuery(label).closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function (error, element) {
                var placement = element.closest('.input-group');
                if (!placement.get(0)) {
                    placement = element;
                }
                if (error.text() !== '') {
                    placement.after(error);
                }
            },
            submitHandler: function () {

                editarUsuarios();

            }

        });
    }

    function ejecutarPostValidate() {
        if (nextFunction == 1) {
            listarReporte();
        }
        nextFunction = null;

    }

    $(function () {
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

    $(function () {
        // wysiwg editor
        yukon_wysiwg.p_forms_validation();
        // multiselect
        yukon_select2.p_forms_validation();
        // validation
        yukon_parsley_validation.p_forms_validation();
    });

    function soloNumeros(e) {
        console.log("entra a la funcion");
        var key = e.which || e.keyCode;
        console.log("key", key);
        if ((key < 48 && key != 8) || key > 57) {
            e.preventDefault();
        }
    }

    function soloLetras(e) {
        var key = e.which || e.keyCode;
        console.log("key", key);
        if ((key < 63 && key != 8 && key != 32) || key > 122) {
            e.preventDefault();
        }
    }

</script>
