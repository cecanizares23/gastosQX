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
                                    <th data-toggle="true" class="footable-visible footable-first-column">DOCUMENTO</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column">NOMBRE</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column">CORREO</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column">USUARIO</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column">ESTADO</th> 
                                    <th data-toggle="true" class="footable-visible footable-first-column">EDITAR</th>                                  
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

<script>
    jQuery(document).ready(function () {
        listarUsuarios();
    });

    var listadoUsuario = [];
    var mapaListadoUsuarios = [
        function (data) {
            return data.documento;
        },
        function (data) {
            return data.nombre;
        },
        function (data) {
            return data.correo;
        },
        function (data) {
            return data.usuario;
        },
        function (data) {
            if (data.estado == 0) {
                return "Inactivo";
            } else if (data.estado == 1) {
                return "Activo";
            }
        },
        function (data) {
            return '<td><button id="btnEditar" class="btn-primary status-active" onclick="cargarPagina(\'' +  + '\');">Editar</button></td>';
        }
    ];

    function listarUsuarios() {
        ajaxGastos.listarUsuarios({
            callback: function (data) {
                if (data !== null) {
                    console.log("---- ", data);
                    //$("#tablaReportes").dataTable().fnDestroy();                    
                    dwr.util.removeAllRows("listado");
                    listadoUsuario = data;
                    dwr.util.addRows("listado", listadoUsuario, mapaListadoUsuarios, {
                        escapeHtml: false
                    });
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

                ejecutarPostValidate();

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
