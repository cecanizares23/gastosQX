<%-- 
    Document   : listar-procedimientos
    Created on : 27-sep-2018, 14:21:28
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

<div class="row">
    <h5><strong>PROCEDIMIENTOS</strong></h5>
</div><br>

<div class="col-md-12">
    <div id="alert"></div>
</div>

<div class="row" id="divBtnAgregar">
    <div class="col-md-12">
        <div class="input-group">            
            <button class="btn btn-primary" id="btnAgregar" onclick="javascript:verAgregar();"><i class="el-icon-plus bs_ttip"></i>  AGREGAR </button>             
        </div>
    </div>
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
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">CODIGO</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">DESCRIPCION</th>
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
        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Codigo:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="codigo" class="form-control" maxlength="5" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Descripcion:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="descripcion" class="form-control" maxlength="50" onkeypress="return soloLetras(event);" required/>
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
        
        <div class="col-md-6 form-group" id="divIdProcedimiento">
            <label for="val_last_name" class="req">Descripcion:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="idProcedimiento" class="form-control" maxlength="50"  required/>
            </div>
        </div>

        <div class="col-md-12 form-group" id="divButtonRegistrar"><br><br>
            <div class="col-md-9 form-group"></div>
            <div class="col-md-3 form-group">
                <button class="btn btn-primary" id="btnRegistrar" onclick="javascript:registrarProcedimiento();">Guardar</button>
                <a class="btn btn-primary" id="btnVolver" onclick="javascript:cargarPagina('listar-procedimientos.jsp');">Volver</a>
            </div>
        </div>
        
        <div class="col-md-12 form-group" id="divButtonEditar"><br><br>
            <div class="col-md-9 form-group"></div>
            <div class="col-md-3 form-group">
                <button class="btn btn-primary" id="btnRegistraEditar" onclick="javascript:validarEditar('form_validation');">Guardar</button>
                <a class="btn btn-primary" id="btnVolver" onclick="javascript:cargarPagina('listar-procedimientos.jsp');">Volver</a>
            </div>
        </div>

        <div class="col-md-12">
            <div id="alert"></div>
        </div>
    </div>
</form>

<script>
    jQuery(document).ready(function () {
        $("#form_validation").hide();
        $("#divIdProcedimiento").hide();
        listarProcedimientos();
    });

    var listadoProcedimientos = [];
    var mapaListadoProcedimientos = [
        function (data) {
            return '<div class="text-center"><td>' + data.codigo + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.descripcion + '</td></div>';
        },
        function (data) {
            if (data.estado == 0) {
                return '<div class="text-center"><td><button class="btn btn-success status-active" onclick="activarProcedimiento(' + data.id + ')">Activar</button></td></div>';
            }
            if (data.estado == 1) {
                return '<div class="text-center"><td><button class="btn btn-danger status-active" onclick="inactivarProcedimiento(' + data.id + ')">Inactivar</button></td></div>';
            }
        },
        function (data) {            
            if (data.estado === "1")
                return '<div class="text-center"><td><button id="btnEditar" class="btn btn-primary status-active" onclick="cargarEditarProcedimiento(' + data.id + ');">Editar</button></td></div>';
            else
                return '<div class="text-center"><td><button id="btnEditar" class="btn btn-primary status-active" disabled onclick="cargarEditarProcedimiento(' + data.id + ');">Editar</button></td></div>';
        }
    ];
    function listarProcedimientos() {
        ajaxGastos.listarProcedimiento({
            callback: function (data) {
                console.log('lisProce ', data);
                if (data !== null) {
                    //$("#tablaReportes").dataTable().fnDestroy();                    
                    dwr.util.removeAllRows("listado");
                    listadoProcedimientos = data;
                    dwr.util.addRows("listado", listadoProcedimientos, mapaListadoProcedimientos, {
                        escapeHtml: false
                    });
                } else {
                    jQuery("#tablaReportes").hide();
                }
            },
            timeout: 20000
        });
    }

    function verAgregar() {
        $("#divBtnAgregar").hide();
        $("#tablaReportes").hide();
        $("#form_validation").show();
        $("#divButtonEditar").hide();
    }
    
    function registrarProcedimiento() {
            $("#btnRegistrar").prop('disabled', true);
            if($("#codigo").val() === ""){
                $("#codigo").focus();
                notificacion("danger", "El codigo no puede estar vacio", "alert");
                $("#btnRegistrar").prop('disabled', false);
                return;
            }else if($("#descripcion").val() === ""){
                $("#descripcion").focus();
                notificacion("danger", "La descripcion no puede estar vacia", "alert");
                $("#btnRegistrar").prop('disabled', false);
                return;
            }else if($("#estado").val() === ""){
                $("#estado").focus();
                notificacion("danger", "El estado no puede estar vacio", "alert");
                $("#btnRegistrar").prop('disabled', false);
                return;
            }
        var procedimiento = {
            codigo: $("#codigo").val(),
            descripcion: $("#descripcion").val(),
            estado: $("#estado").val()            
        };

        //validaUsuario();        
        ajaxGastos.registrarProcedimiento(procedimiento, {
            callback: function (data) {
                if (data !== null) {
                    notificacion("success", "el procedimiento se ha registrado con éxito", "alert");
                    limpiarFormulario();
                    setTimeout('$("#btnVolver").click();','2000');
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
            },
            timeout: 20000
        });
        //desactivar();        
    }
    
    function editarProcedimiento() {
        $("#btnRegistrar").prop('disabled', true);
        var procedimiento = {
            codigo: $("#codigo").val(),
            descripcion: $("#descripcion").val(),
            estado: $("#estado").val(),
            id: $("#idProcedimiento").val()
        };

        //validaUsuario();        
        ajaxGastos.actualizarProcedimiento(procedimiento, {
            callback: function (data) {
                if (data !== null) {
                    notificacion("success", "el procedimiento se ha actualizado con éxito", "alert");
                    limpiarFormulario();
                    setTimeout('$("#btnVolver").click();','2000');
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
            },
            timeout: 20000
        });
        //desactivar();        
    }
    
    function limpiarFormulario(){
        $("#prefijo").val("");
        $("#descripcion").val("");
        $("#estado").val("");
    }
    
    function cargarEditarProcedimiento(id){
        $("#divBtnAgregar").hide();
        $("#divButtonRegistrar").hide();
        $("#tablaReportes").hide();
        $("#form_validation").show();
        ajaxGastos.ConsultarProcedimientoXId(id,{
            callback: function(data){
                if(data !== null){
                    $("#codigo").val(data.codigo);
                    $("#descripcion").val(data.descripcion);
                    $("#estado").val(data.estado);
                    $("#idProcedimiento").val(id)
                }
            },
            timeout: 20000
        });
    }
    
    function activarProcedimiento(id) {
        ajaxGastos.activarEstadoProcedimiento(id, {
            callback: function (data) {
                if (data) {
                    //listarUsuarios();
                    cargarPagina('listar-procedimientos.jsp');
                } else {
                    notificacion("danger", "No se pudo actualizar el estado.", "alert");
                }
            },
            timeout: 20000
        });
    }

    function inactivarProcedimiento(id) {
        ajaxGastos.inactivarEstadoProcedimiento(id, {
            callback: function (data) {
                if (data) {
                    listarProcedimientos();
                } else {
                    notificacion("danger", "No se pudo actualizar el estado.", "alert");
                }
            },
            timeout: 20000
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
console.log("formulario ", formulario);
        $('#' + formulario).validate({            
            highlight: function(label) {
                console.log("ingresa 1");
                jQuery(label).closest('.form-group').removeClass('has-success').addClass('has-error');
            },
            success: function(label) {
                console.log("ingresa 2");
                jQuery(label).closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function(error, element) {
                console.log("ingresa 3");
                var placement = element.closest('.input-group');
                if (!placement.get(0)) {
                    placement = element;
                }
                if (error.text() !== '') {
                    placement.after(error);
                }
            },
            submitHandler: function() {                
                console.log("ingresa 4");
                registrarProcedimiento();

            }

        });
    }
    
    function validarEditar(formulario) {
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

                editarProcedimiento();

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