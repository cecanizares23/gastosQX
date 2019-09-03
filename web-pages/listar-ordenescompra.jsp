<%-- 
    Document   : listar-ordenescompra
    Created on : 27-sep-2018, 14:23:04
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
    <h5><strong>ORDENES DE COMPRA</strong></h5>
</div>

<div class="col-md-12">
    <div id="alert"></div>
</div>

<div class="row" id="divBtnAgregar">
    <div class="col-md-12">
        <div class="col-md-12">            
            <button class="btn btn-primary" id="btnAgregar" onclick="javascript:habilitarRegistro();"><i class="el-icon-plus bs_ttip"></i>  AGREGAR </button>             
        </div><br><br>
        <div class="col-md-3">                        
            <input type="text" id="buscarGasto" class="form-control" maxlength="50" onkeypress="return soloNumeros(event);"/>
            <div class="input-group date" id="dp_component">
                <span class="input-group-addon"><i class="icon_calendar"></i></span>
                <input type="text" class="form-control" id="fecha" required />                
            </div>  
        </div>
        <div class="col-md-3">
            <select id="buscarPor" class="form-control" onchange="javascript:onChangeBuscar();" required>  
                <option>-Seleccione una opcion-</option>
                <option value="1">Consecutivo</option>                
                <option value="2">Fecha</option>
            </select>
        </div>
        <div class="col-md-2">
            <button class="btn btn-primary" id="btnBuscarOrden" onclick="javascript:listarOrdenesCompra();">Buscar</button>
        </div><br>
    </div>
</div>

<div class="row" id="tablaOrdenCompra">
    <div class="col-md-12">
    </div>
    <div class="col-md-12">
        <div class="col-md-12">            
            <div class="row">
                <div class="col-md-12">
                    <div class="table-responsive" >
                        <table class="table table-yuk2 toggle-arrow-tiny tablet breakpoint footable-loaded footable" id="footable_demo" data-filter="#textFilter" data-page-size="5">
                            <thead>
                                <tr>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">ID</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">FECHA</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">ESTADO</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">CONFIRMADO</th>                                                
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">EDITAR</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">VER DETALLE</th>
                                </tr>
                            </thead>
                            <tbody id="listadoOrdenCompra">


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

<!--Modal Large-->
<div class="modal fade" id="modalLarge">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="butt on" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">ORDEN COMPRA: </h4>
                <strong><h3 id="labelIdOrdenCompra"></h3></strong>
            </div>
            <div class="modal-body">
                <div class="col-md-12">                    
                    <div class="col-md-12">            
                        <div class="row">
                            <div class="col-md-12">
                                <div class="table-responsive" id="table">
                                    <table class="table table-yuk2 toggle-arrow-tiny tablet breakpoint footable-loaded footable" id="footable_demo" data-filter="#textFilter" data-page-size="5">
                                        <thead>
                                            <tr>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">ID</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">FECHA</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">ESTADO</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">CONFIRMADO</th>                                                
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">EDITAR</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">VER DETALLE</th>
                                            </tr>
                                        </thead>
                                        <tbody id="listadoDetalleOrdenCompra">


                                        </tbody>

                                    </table>
                                </div>
                            </div>                                                                
                        </div>
                    </div>
                </div>
            </div><br>                
            <div class="modal-footer">
                <button type="button" class="btn btn-default btn-sm" data-dismiss="modal" id="btnCerrarModal">Close</button>

            </div>
        </div>
    </div>
</div>

<!--Formulario Registro-->
<form id="form_validation" name="form_validation" action="autocomplete:off" novalidate>
    <div class="row"> 
        <div id="alert"></div>
        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Fecha:</label>                                              
            <div class="input-group date" id="dp_component1">
                <span class="input-group-addon"><i class="icon_calendar"></i></span>
                <input type="text" class="form-control" id="fechaRegistro" required />                           
            </div>
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Estado:</label>            
            <select id="buscarPor" class="form-control" onchange="javascript:onChangeBuscar();" required>  
                <option>-Seleccione una opcion-</option>
                <option value="1">Activo</option>                
                <option value="0">Inactivo</option>
            </select>                    
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Confirmado:</label>            
            <select id="buscarPor" class="form-control" onchange="javascript:onChangeBuscar();" required>  
                <option>-Seleccione una opcion-</option>
                <option value="1">SI</option>                
                <option value="0">NO</option>
            </select>                    
        </div>
       
        <div class="col-md-6 form-group" id="divId" hidden="true">
            <label for="val_first_name" class="req">id:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="idGasto" class="form-control" maxlength="50" onkeypress="return soloNumeros(event);" disabled/>                
            </div>            
        </div>

        <div class="col-md-12 form-group" id="divButtonRegistrar"><br><br>
            <div class="col-md-9 form-group">                
            </div>
            <div class="col-md-3 form-group">
                <button class="btn btn-primary" id="btnRegistrar" onclick="javascript:validar('form_validation');">Guardar</button>                
                <a class="btn btn-primary" id="btnVolver" onclick="javascript:volverReady();">Volver</a>
            </div>
        </div>
    </div>
</form> 

<script>
    jQuery(document).ready(function () {
        jQuery("#dp_component").hide();
        listarOrdenCompra();
        $("#btnBuscarOrden").prop('disabled', true);
        $("#form_validation").hide();
    });

    var listadoCompra = [];
    var mapaListadoOrdenesCompra = [
        function (data) {
            return '<div class="text-center"><td>' + data.id + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.fecha + '</td></div>';
        },
        function (data) {
            console.log("------", data.estado);
            if (data.estado === '0') {
                return '<div class="text-center"><td> INACTIVO </td></div>';
            } else if (data.estado === '1') {
                return '<div class="text-center"><td> ACTIVO </td></div>';
            }
        },
        function (data) {
            console.log("------", data.confirmado);
            if (data.confirmado === '0') {
                return '<div class="text-center"><td> NO </td></div>';
            } else if (data.confirmado === '1') {
                return '<div class="text-center"><td> SI </td></div>';
            }
        },
        function (data) {
            if (data.estado === "0") {
                return '<div class="text-center"><td><button class="btn btn-success status-active" onclick="editarGastos(' + data.id + ')"><i class="el-icon-edit bs_ttip"></i></button></td></div>';
            }
            if (data.estado === "1") {
                return '<div class="text-center"><td><button class="btn btn-success status-active" disabled onclick="editarGastos(' + data.id + ')"><i class="el-icon-edit bs_ttip"></i></button></td></div>';
            }
        },
        function (data) {
            return '<div class="text-center"><td><button id="btnEditar" class="btn btn-primary status-active" data-toggle="modal" data-target="#modalLarge" onclick="javascript:listarDetalleGastoXIdGasto(' + data.id + ');"><i class="el-icon-eye-open bs_ttip"></i></button></td></div>';
        }
    ];

    function listarOrdenCompra() {
        ajaxGastos.listarOrdenCompra({
            callback: function (data) {

                if (data !== null) {
                    //$("#tablaOrdenCompra").dataTable().fnDestroy();                    
                    dwr.util.removeAllRows("listadoOrdenCompra");
                    listadoCompra = data;
                    dwr.util.addRows("listadoOrdenCompra", listadoCompra, mapaListadoOrdenesCompra, {
                        escapeHtml: false
                    });
                } else {
                    jQuery("#tablaOrdenCompra").hide();
                }
            },
            timeout: 20000
        });
    }

    function listarOrdenCompraXId() {
        ajaxGastos.listarOrdenesCompraXId(jQuery("#buscarGasto").val(), {
            callback: function (data) {
                if (data !== null) {
                    //$("#tablaOrdenCompra").dataTable().fnDestroy();  
                    console.log("data ", data);
                    dwr.util.removeAllRows("listadoOrdenCompra");
                    listadoCompra = data;
                    dwr.util.addRows("listadoOrdenCompra", listadoCompra, mapaListadoOrdenesCompra, {
                        escapeHtml: false
                    });
                } else {
                    //jQuery("#tablaOrdenCompra").hide();
                    notificacion('danger', 'No hay orden de compra con este consecutivo, por favor digite otro.', 'alert');
                    listarOrdenCompra();
                    $("#buscarPor").val("");
                }
            },
            timeout: 20000
        });
    }

    function listarOrdenCompraXFecha() {
        ajaxGastos.listarOrdenCompraXFecha(jQuery("#fecha").val(), {
            callback: function (data) {
                if (data !== null) {
                    //$("#tablaOrdenCompra").dataTable().fnDestroy();                    
                    dwr.util.removeAllRows("listadoOrdenCompra");
                    listadoCompra = data;
                    dwr.util.addRows("listadoOrdenCompra", listadoCompra, mapaListadoOrdenesCompra, {
                        escapeHtml: false
                    });
                } else {
                    //jQuery("#tablaOrdenCompra").hide();
                    notificacion('danger', 'No hay orden de compra en esta fecha, por favor digite otra.', 'alert');
                    listarOrdenCompra();
                    $("#fecha").val("");
                }
            },
            timeout: 20000
        });
    }

    function onChangeBuscar() {
        if ($("#buscarPor").val() === "1") {
            $("#buscarGasto").show();
            $("#dp_component").hide();
            $("#btnBuscarOrden").prop('disabled', false);
        } else if ($("#buscarPor").val() === "2") {
            $("#buscarGasto").hide();
            $("#dp_component").show();
            $("#btnBuscarOrden").prop('disabled', false);
        }
    }

    function listarOrdenesCompra() {
        if ($("#buscarPor").val() === "1") {
            listarOrdenCompraXId();
        } else {
            listarOrdenCompraXFecha();
        }
    }
    
    function habilitarRegistro(){
        $("#divBtnAgregar").hide();
        $("#tablaOrdenCompra").hide();
        $("#form_validation").show();
    }
    
    function volverReady(){
        $("#divBtnAgregar").show();
        $("#tablaOrdenCompra").show();
        $("#form_validation").hide();
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

                registrarEncabezadoGasto();

            }

        });
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