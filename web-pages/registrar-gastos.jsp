<%-- 
    Document   : registrar-gastos
    Created on : 27-sep-2018, 14:22:21
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
    <h5><strong>REGISTRAR ENCABEZADO DEL GASTO</strong></h5>
</div>

<form id="form_validation" name="form_validation" action="autocomplete:off" novalidate>
    <div class="row"> 
        <div id="alert"></div>
        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Procedimiento:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_id bs_ttip"></i></span>
                <select id="procedimiento" class="form-control" required></select>
            </div>
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Paciente:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="paciente" class="form-control" maxlength="50" onkeypress="return soloLetras(event);" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Cedula:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="cedula" class="form-control" maxlength="50" onkeypress="return soloNumeros(event);" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Fecha:</label>                                
            <div class="input-group date" id="dp_component">
                <span class="input-group-addon"><i class="icon_calendar"></i></span>
                <input type="text" class="form-control" id="fecha" required />                
            </div>                                
        </div>

        <div class="col-md-6 form-group" id="divId" hidden="false">
            <label for="val_first_name" class="req">id:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="idGasto" class="form-control" maxlength="50" onkeypress="return soloNumeros(event);" disabled/>                
            </div>            
        </div>

        <div class="col-md-12 form-group" id="divButtonRegistrar"><br><br>
            <div class="col-md-9 form-group">
                <a class="btn btn-primary" id="btnAgregar" data-toggle="modal" data-target="#modalLarge" onclick="javascript:listarArticulos();">AGREGRAR</a>
                <a class="btn btn-success" id="btnConfirmar" onclick="javascript:confirmarGasto();">CONFIRMAR</a>
            </div>
            <div class="col-md-3 form-group">
                <button class="btn btn-primary" id="btnRegistrar" onclick="javascript:validar('form_validation');">Guardar</button>
                <a class="btn btn-primary" id="btnHabilita" onclick="javascript:habilita();">Editar</a>
                <a class="btn btn-primary" id="btnEditar" onclick="javascript:editarEncabezadoGasto();">Guardar</a>
                <a class="btn btn-primary" id="btnVolver" onclick="javascript:cargarPagina('listar-gastos.jsp');">Volver</a>
            </div>
        </div>
    </div>
</form> 

<form id="form_validation1" name="form_validation1" action="autocomplete:off" novalidate>
    <div class="row"> 

        <div class="col-md-12" id="divDetalleGasto">

            <div class="col-md-12" id="divFormDetalle">
                <div class="row">
                    <div class="col-md-6 form-group">
                        <label for="val_first_name" class="req">Referencia:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                            <input type="text" id="referencia" class="form-control" maxlength="50" onblur="validarReferencia(this.value)" required disabled/>                
                        </div>            
                    </div>

                    <div class="col-md-6 form-group">
                        <label for="val_first_name" class="req">Lote:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                            <input type="text" id="lote" class="form-control" maxlength="50" required disabled/>
                        </div>            
                    </div>

                    <div class="col-md-6 form-group">
                        <label for="val_first_name" class="req">Descripción:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                            <input type="text" id="descripcion" class="form-control" maxlength="50"  required disabled/>
                        </div>            
                    </div>

                    <div class="col-md-6 form-group">
                        <label for="val_first_name" class="req">Unidad de Medida:</label>
                        <div class="input-group date">
                            <span class="input-group-addon"><i class="social_flickr_square bs_ttip"></i></span>
                            <select id="unidad" class="form-control" required disabled>                                    
                                <option value="">-Seleccione uno-</option>
                                <option value="UNIDAD">Unidad</option>
                                <option value="KIT">Kit</option>
                                <option value="COMBO">Combo</option>
                            </select>
                        </div>
                    </div>

                    <div class="col-md-6 form-group">            
                        <label for="val_last_name" class="req">Cantidad:</label>
                        <div class="input-group date">
                            <span class="input-group-addon"><i class="el-icon-phone-alt"></i></span>
                            <input type="text" id="cantidad" class="form-control" onkeypress="return soloNumeros(event);" maxlength="10" required/>
                        </div>
                    </div>

                    <div class="col-md-6 form-group" hidden="true">            
                        <label for="val_last_name" class="req">idArticulo:</label>
                        <div class="input-group date">
                            <span class="input-group-addon"><i class="el-icon-phone-alt"></i></span>
                            <input type="text" id="idArticulo" class="form-control" onkeypress="return soloNumeros(event);" maxlength="10" required/>
                        </div>
                    </div>

                    <div class="col-md-12 form-group" id="divButtonRegistrar"><br><br>
                        <div class="col-md-9 form-group">                            
                        </div>
                        <div class="col-md-3 form-group">                            
                            <a class="btn btn-primary" id="btnGuardar" onclick="javascript:registrarDetalleGastoTemp();"><i class="icon_floppy_alt bs_ttip"></i></a>
                            <a class="btn btn-primary" id="btnCancelar" onclick="javascript:editarEncabezadoGasto();"><i class="el-icon-remove bs_ttip"></i></a>                            
                        </div>
                    </div>  

                </div>            
            </div>

        </div>
    </div>
</form>

<div class="col-md-12" id="divTablaDetalle">            
    <div class="row">
        <div class="col-md-12">
            <div class="table-responsive" >
                <table class="table table-yuk2 toggle-arrow-tiny tablet breakpoint footable-loaded footable" id="footable_demo1" data-filter="#textFilter" data-page-size="5">
                    <thead>
                        <tr>
                            <th data-toggle="true" class="footable-visible footable-first-column text-center">REFERENCIA</th>
                            <th data-toggle="true" class="footable-visible footable-first-column text-center">LOTE</th>
                            <th data-toggle="true" class="footable-visible footable-first-column text-center">DESCRIPCION</th>
                            <th data-toggle="true" class="footable-visible footable-first-column text-center">UND MEDIDA</th>
                            <th data-toggle="true" class="footable-visible footable-first-column text-center">CANT.</th>
                            <th data-toggle="true" class="footable-visible footable-first-column text-center">ELIMINAR</th>
                        </tr>
                    </thead>
                    <tbody id="listadoDetalle">


                    </tbody>
                </table>
            </div>
        </div>                                                                
    </div>
</div> 

<!--Modal Large-->
<div class="modal fade" id="modalLarge">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Buscar Articulos</h4>
            </div>
            <div class="modal-body">
                <div class="col-md-12">
                    <div class="col-md-3">                        
                        <input type="text" id="buscar" class="form-control" maxlength="50"/>                                                
                    </div>
                    <div class="col-md-3">
                        <select id="buscarPor" class="form-control" required>                                                                
                            <option value="1">Referencia</option>
                            <option value="2">Descripcion</option>                                    
                        </select>
                    </div>
                    <div class="col-md-2">
                        <a class="btn btn-primary" id="btnBuscar" onclick="javascript:buscarArticulo();">Buscar</a>
                    </div><br>
                    <div class="col-md-12">            
                        <div class="row">
                            <div class="col-md-12">
                                <div class="table-responsive" >
                                    <table class="table table-yuk2 toggle-arrow-tiny tablet breakpoint footable-loaded footable" id="footable_demo" data-filter="#textFilter" data-page-size="5">
                                        <thead>
                                            <tr>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">REFERENCIA</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">LOTE</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">DESCRIPCION</th>                                                
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">CANT.</th>      
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">UND MEDIDA</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">MAX</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">MIN</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">AGREGAR</th>
                                            </tr>
                                        </thead>
                                        <tbody id="listado">


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

<script>
    jQuery(document).ready(function () {
        jQuery("#btnEditar").hide();
        jQuery("#btnHabilita").hide();
        jQuery("#btnAgregar").hide();
        jQuery("#divTablaDetalle").hide();
        jQuery("#divFormDetalle").hide();
        jQuery("#btnConfirmar").hide();
        
        ajaxGastos.listarProcedimiento({
            callback: function (data) {
                if (data !== null) {
                    console.log("data ", data);
                    dwr.util.removeAllOptions("procedimiento");
                    dwr.util.addOptions("procedimiento", [{
                            id: '',
                            descripcion: 'Seleccione tipo de documento'
                        }], 'id', 'descripcion');
                    dwr.util.addOptions("procedimiento", data, 'id', 'descripcion');
                    
                }
            },
            timeout: 20000
        });
    });

    function registrarEncabezadoGasto() {
        $("#btnRegistrar").prop('disabled', true);
        var gastoEncabezado = {
            idProcedimiento: jQuery("#procedimiento").val(),
            paciente: jQuery("#paciente").val(),
            cedula: jQuery("#cedula").val(),
            fecha: jQuery("#fecha").val()
        };

        ajaxGastos.registrarGastos(gastoEncabezado, {
            callback: function (data) {

                if (data !== null) {
                    console.log("data11111111", data);
                    notificacion("success", "el encabezado se ha creado con éxito", "alert");
                    jQuery("#btnRegistrar").hide();
                    jQuery("#btnHabilita").show();
                    jQuery("#btnAgregar").show();
                    jQuery("#idGasto").val(data);
                    desabilita();
                    //cargarPagina("listar-usuarios.jsp");
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
            },
            timeout: 20000
        });
              
    }

    function editarEncabezadoGasto() {
        $("#btnRegistrar").prop('disabled', true);
        var gastoEncabezado = {
            idProcedimiento: jQuery("#procedimiento").val(),
            paciente: jQuery("#paciente").val(),
            cedula: jQuery("#cedula").val(),
            fecha: jQuery("#fecha").val(),
            id: jQuery("#idGasto").val()
        };
        
        ajaxGastos.actualizarGastos(gastoEncabezado, {
            callback: function (data) {
                if (data !== null) {
                    notificacion("success", "el encabezado se ha editado con éxito", "alert");
                    desabilita();
                    jQuery("#btnHabilita").show();
                    jQuery("#btnEditar").hide();
                    jQuery("#btnAgregar").show();
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
            },
            timeout: 20000
        });             
    }

    var listadoArticulos = [];
    var mapaListadoArticulos = [
        function (data) {
            return '<div class="text-center"><td>' + data.referencia + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.lote + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.descripcion + '</td></div>';
        },
        function (data) {
            if (data.estado == 0) {
                return '<div class="text-center"><td>' + data.cantidad + '</td></div>';
            }
            if (data.estado == 1) {
                if (data.cantidad == 0) {
                    return '<div class="text-center"><td><div class="tdRed">' + data.cantidad + '</div></td></div>';
                }
                if (data.cantidad <= data.cantidadMin) {
                    return '<div class="text-center"><td><div class="tdYellow">' + data.cantidad + '</div></td></div>';
                }
                if (data.cantidad > data.cantidadMin) {
                }
                return '<div class="text-center"><td><div class="tdGreen">' + data.cantidad + '</div></td></div>';
            }
        },
        function (data) {
            return '<div class="text-center"><td>' + data.unidadMedidad + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.cantidadMax + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.cantidadMin + '</td></div>';
        },
        function (data) {
            if (data.cantidad === "0")
                return '<div class="text-center"><td><button id="btnAgregar" class="btn btn-primary status-active" disabled onclick="seleccionarArticulo(' + data.id + ');"><i class="el-icon-plus-sign bs_ttip"></i></button></td></div>';
            else
                return '<div class="text-center"><td><button id="btnagregar" class="btn btn-primary status-active" onclick="seleccionarArticulo(' + data.id + ');"><i class="el-icon-plus-sign bs_ttip"></i></button></td></div>';
        }
    ];

    function listarArticulos() {
        ajaxGastos.listarTodosLosArticulos({
            callback: function (data) {

                console.log('lisArt ', data);
                if (data !== null) {
                    //$("#tablaReportes").dataTable().fnDestroy();                    
                    dwr.util.removeAllRows("listado");
                    listadoArticulos = data;
                    dwr.util.addRows("listado", listadoArticulos, mapaListadoArticulos, {
                        escapeHtml: false
                    });
                    $(".tdGreen").css({"background": "#5BFF33"});
                    $(".tdYellow").css({"background": "#F9FF33"});
                    $(".tdRed").css({"background": "#FF3333"});
                } else {
                    jQuery("#tablaReportes").hide();
                }
            },
            timeout: 20000
        });
    }

    function buscarArticulo() {
        console.log("buscarPor ", $("#buscarPor").val());
        console.log("buscar ", $("#buscar").val());
        if ($("#buscar").val() === "") {
            listarArticulos();
            return;
        }
        if ($("#buscarPor").val() === "1") {
            buscarReferencia();
        } else if ($("#buscarPor").val() === "2") {
            console.log("descripcion");
            buscarDescripcion();
        }
    }

    function buscarReferencia() {
        ajaxGastos.buscarPorReferencia($("#buscar").val(), {
            callback: function (data) {
                console.log("....", data);
                if (data !== null) {
                    dwr.util.removeAllRows("listado");
                    listadoArticulos = data;
                    dwr.util.addRows("listado", listadoArticulos, mapaListadoArticulos, {
                        escapeHtml: false
                    });
                    $(".tdGreen").css({"background": "#5BFF33"});
                    $(".tdYellow").css({"background": "#F9FF33"});
                    $(".tdRed").css({"background": "#FF3333"});
                }
            },
            timeout: 20000
        });
    }

    function buscarDescripcion() {
        ajaxGastos.buscarPorDescripcion($("#buscar").val(), {
            callback: function (data) {
                if (data !== null) {
                    dwr.util.removeAllRows("listado");
                    listadoArticulos = data;
                    dwr.util.addRows("listado", listadoArticulos, mapaListadoArticulos, {
                        escapeHtml: false
                    });
                    $(".tdGreen").css({"background": "#5BFF33"});
                    $(".tdYellow").css({"background": "#F9FF33"});
                    $(".tdRed").css({"background": "#FF3333"});
                }
            },
            timeout: 20000
        });
    }

    function seleccionarArticulo(id) {
        ajaxGastos.ConsultarArticulosXId(id, {
            callback: function (data) {
                console.log("detalle ", data);
                if (data !== null) {
                    jQuery("#referencia").val(data.referencia);
                    jQuery("#lote").val(data.lote);
                    jQuery("#descripcion").val(data.descripcion);
                    $("#unidad").val(data.unidadMedidad);
                    jQuery("#idArticulo").val(data.id);
                    jQuery("#divFormDetalle").show();
                    jQuery("#btnCerrarModal").click();
                    jQuery("#cantidad").val("");
                    jQuery("#cantidad").focus();
                    $("#divTablaDetalle").hide();
                    $("#divDetalleGasto").show();
                }
            },
            timeout: 20000
        });
    }

    function registrarDetalleGastoTemp() {
        if ($("#cantidad").val() === "" || $("#cantidad").val() === "0") {
            notificacion("danger", "El campo cantidad no puede vacio o en cero", "alert");
            $("#cantidad").focus();
            return;
        }
        var gastoDetalle = {
            idGastos: $("#idGasto").val(),
            idArticulos: $("#idArticulo").val(),
            cantidad: $("#cantidad").val()
        };
        ajaxGastos.registrarDetalleGasto(gastoDetalle, {
            callback: function (data) {
                if (data === "0") {
                    notificacion("danger", "La cantidad de este articulo es 0, no se puede agregar.", "alert");
                    $("#cantidad").focus();
                } else if (data === "1") {
                    notificacion("danger", "La Cantidad de este articulo quedaria negativa, por lo cual no se puede realizar esta operacion.", "alert");
                    $("#cantidad").val("");
                    $("#cantidad").focus();
                } else if (data === "2") {
                    notificacion("success", "Este detalle se ha agregado con exito.", "alert");
                    listarDetalleXIdGasto();
                }
            },
            timeout: 20000
        });
    }

    var listadoDetalleGastos = [];
    var mapaDetalleGastos = [
        function (data) {
            return '<div class="text-center"><td>' + data.referencia + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.lote + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.descripcionArt + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.unidadMedidad + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.cantidad + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td><button id="btnAgregar" class="btn btn-danger status-active" onclick="eliminarArticuloDetalle(' + data.id + ', ' + data.cantidad + ', ' + data.idArticulos + ');"><i class="el-icon-minus-sign bs_ttip"></i></button></td></div>';
        }
    ];

    function listarDetalleXIdGasto() {
        ajaxGastos.listarDetalleGastoXIdGasto($("#idGasto").val(), {
            callback: function (data) {
                if (data !== null) {
                    dwr.util.removeAllRows("listadoDetalle");
                    listadoDetalleGastos = data;
                    dwr.util.addRows("listadoDetalle", listadoDetalleGastos, mapaDetalleGastos, {
                        escapeHtml: false
                    });

                    $("#divTablaDetalle").show();
                    $("#divDetalleGasto").hide();
                    $("#btnConfirmar").show();
                } else {
                    $("#divTablaDetalle").hide();
                    $("#btnConfirmar").hide();
                }
            },
            timeout: 20000
        });
    }

    function eliminarArticuloDetalle(id, cantidad, idArticulo) {
        console.log("idDetalle ", id, "cantidad ", cantidad, "idArticulo ", idArticulo);
        ajaxGastos.eliminarDetalleGasto(id, cantidad, idArticulo, {
            callback: function (data) {
                if (data === "1") {
                    notificacion("success", "Este detalle se ha eliminado con exito.", "alert");
                    listarDetalleXIdGasto();
                } else {
                    notificacion("danger", "A ocurrido un error al eliminar el detalle", "alert");
                }
            },
            timeout: 20000
        });
    }

    function confirmarGasto() {
        ajaxGastos.activarEstadoGastos($("#idGasto").val(), {
            callback: function (data) {
                if (data === true) {
                    notificacion("success", "El gasto se ha confirmado con éxito!", "alert");
                    setTimeout("cargarPagina('registrar-gastos.jsp')", 3000);
                } else {
                    notificacion("danger", "Se ha producido un error al confirmar el gasto!", "alert");
                }
            },
            timeout: 20000
        });
    }

    function desabilita() {
        jQuery("#procedimiento").prop('disabled', true);
        jQuery("#paciente").prop('disabled', true);
        jQuery("#cedula").prop('disabled', true);
        jQuery("#fecha").prop('disabled', true);
    }

    function habilita() {
        jQuery("#procedimiento").prop('disabled', false);
        jQuery("#paciente").prop('disabled', false);
        jQuery("#cedula").prop('disabled', false);
        jQuery("#fecha").prop('disabled', false);
        jQuery("#btnEditar").show();
        jQuery("#btnHabilita").hide();
        jQuery("#btnAgregar").hide();
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