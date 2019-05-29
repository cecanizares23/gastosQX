<%-- 
    Document   : listar-gastos
    Created on : 27-sep-2018, 14:22:40
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
    <h5><strong>LISTAR GASTOS</strong></h5>
</div><br>

<div class="col-md-12">
    <div id="alert"></div>
</div>

<div class="row" id="divBtnAgregar">
    <div class="col-md-12">
        <div class="col-md-2">            
            <button class="btn btn-primary" id="btnAgregar" onclick="javascript:cargarPagina('registrar-gastos.jsp');"><i class="el-icon-plus bs_ttip"></i>  AGREGAR </button>             
        </div>
        <div class="col-md-3">                        
            <input type="text" id="buscarGasto" class="form-control" maxlength="50"/>
            <div class="input-group date" id="dp_component">
                <span class="input-group-addon"><i class="icon_calendar"></i></span>
                <input type="text" class="form-control" id="fecha" required />                
            </div>  
        </div>
        <div class="col-md-3">
            <select id="buscarPor" class="form-control" required>                                                                                
                <option value="1">Consecutivo</option>
                <option value="2">Cedula Paciente</option>
                <option value="3">Fecha</option>
            </select>
        </div>
        <div class="col-md-2">
            <a class="btn btn-primary" id="btnBuscar" onclick="javascript:buscarGasto();">Buscar</a>
        </div><br>
    </div>
</div>

<div class="row" id="tablaGastos">
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
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">CONSECUTIVO</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">PACIENTE</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">CEDULA</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">PROCEDIMIENTO</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">FECHA</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">ESTADO</th> 
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">EDITAR</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">VER DETALLE</th>
                                </tr>
                            </thead>
                            <tbody id="listadoGastosTabla">


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
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">GASTO: </h4>
                <strong><h3 id="labelIdGasto"></h3></strong>
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
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">REFERENCIA</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">LOTE</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">DESCRIPCION</th>
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">UND MEDIDA</th>                                                
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">CANT.</th>                                                                                                      
                                            </tr>
                                        </thead>
                                        <tbody id="listadoDetalleGasto">


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

<!--html del editar gasto-->
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
                <input type="text" class="form-control" id="fechaGasto" required />                
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
                <!--<button class="btn btn-primary" id="btnEditar" onclick="javascript:validar('form_validation');">Guardar</button>-->
                <a class="btn btn-primary" id="btnHabilita" onclick="javascript:habilita();">Editar</a>
                <a class="btn btn-primary" id="btnEditarGasto" onclick="javascript:editarEncabezadoGasto();">Guardar</a>
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
<!--Fin HTML Editar gasto-->

<script>
    jQuery(document).ready(function () {
        listarGastos();
        $("#dp_component").hide();
        $("#form_validation").hide();
        $("#form_validation1").hide();
        $("#divTablaDetalle").hide();
        $("#btnEditarGasto").hide();
        $("#btnConfirmar").hide();
        $("#buscarPor").change(function () {
            if ($(this).val() === "3") {
                $("#dp_component").show();
                $("#buscarGasto").hide();
            } else {
                $("#dp_component").hide();
                $("#buscarGasto").show();
            }
        });

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

    var listadoGastos = [];
    var mapaListadoGastos = [
        function (data) {
            return '<div class="text-center"><td>' + data.id + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.paciente + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.cedula + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.descripcionProcedimiento + '</td></div>';
        },
        function (data) {
            return '<div class="text-center"><td>' + data.fecha + '</td></div>';
        },
        function (data) {
            if (data.estado === "0") {
                return '<div class="text-center"><td> NO CONFIRMADO </td></div>';
            }
            if (data.estado === "1") {
                return '<div class="text-center"><td> CONFIRMADO </td></div>';
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

    function listarGastos() {
        ajaxGastos.listarGastos({
            callback: function (data) {
                if (data !== null) {
                    $("#dp_component").hide();
                    $("#buscarGasto").show();
                    $("#fecha").val("");
                    $("#buscarGasto").val("");
                    $("#buscarPor").val("");
                    //$("#tablaReportes").dataTable().fnDestroy();                    
                    dwr.util.removeAllRows("listadoGastosTabla");
                    listadoGastos = data;
                    dwr.util.addRows("listadoGastosTabla", listadoGastos, mapaListadoGastos, {
                        escapeHtml: false
                    });
                } else {
                    jQuery("#tablaGastos").hide();
                }
            },
            timeout: 20000
        });
    }

    var listadoDetalleGastos = [];
    var mapaListadoDetalleGastos = [
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
        }
    ];

    function listarDetalleGastoXIdGasto(idGasto) {
        ajaxGastos.listarDetalleGastoXIdGasto(idGasto, {
            callback: function (data) {
                $("#dp_component").hide();
                $("#buscarGasto").show();
                $("#fecha").val("");
                $("#buscarGasto").val("");
                $("#buscarPor").val("");
                if (data !== null) {
                    dwr.util.removeAllRows("listadoDetalleGasto");
                    listadoDetalleGastos = data;
                    dwr.util.addRows("listadoDetalleGasto", listadoDetalleGastos, mapaListadoDetalleGastos, {
                        escapeHtml: false
                    });
                    $("#labelIdGasto").text(idGasto);
                }
            },
            timeout: 20000
        });
    }

    function buscarGasto() {
        console.log("buscarPor ", $("#buscarPor").val());
        console.log("buscar ", $("#buscarGasto").val());
        console.log("fecha ", $("#fecha").val());
        if ($("#buscarGasto").val() === "" && $("#fecha").val() === "") {
            listarGastos();
            notificacion("danger", "Por favor digite el valor a buscar.", "alert");
            return;
        } else {
            if ($("#buscarPor").val() === "1") {
                buscarXId($("#buscarGasto").val());
            } else if ($("#buscarPor").val() === "2") {
                console.log("cedula");
                buscarXCedula($("#buscarGasto").val());
            } else if ($("#buscarPor").val() === "3") {
                buscarXFecha($("#fecha").val());
            }
        }
    }

    function buscarXId(valor) {
        console.log("--------- ", valor);
        ajaxGastos.ConsultarGastosXId1(valor, {
            callback: function (data) {
                console.log("....", data);
                $("#dp_component").hide();
                $("#buscarGasto").show();
                $("#fecha").val("");
                $("#buscarGasto").val("");
                $("#buscarPor").val("");
                if (data !== null) {
                    dwr.util.removeAllRows("listadoGastosTabla");
                    listadoGastos = data;
                    dwr.util.addRows("listadoGastosTabla", listadoGastos, mapaListadoGastos, {
                        escapeHtml: false
                    });
                } else {
                    notificacion("danger", "No se encontró ningún resultado por el valor digitado!", "alert");
                }
            },
            timeout: 20000
        });
    }

    function buscarXCedula(cedula) {
        ajaxGastos.buscarGastoCedulaPaciente(cedula, {
            callback: function (data) {
                console.log("....", data);
                $("#dp_component").hide();
                $("#buscarGasto").show();
                $("#fecha").val("");
                $("#buscarGasto").val("");
                $("#buscarPor").val("");
                if (data !== null) {
                    dwr.util.removeAllRows("listadoGastosTabla");
                    listadoGastos = data;
                    dwr.util.addRows("listadoGastosTabla", listadoGastos, mapaListadoGastos, {
                        escapeHtml: false
                    });
                } else {
                    notificacion("danger", "No se encontró ningún resultado por la cedula digitada!", "alert");
                }
            },
            timeout: 20000
        });
    }

    function buscarXFecha(fecha) {
        ajaxGastos.buscarGastoFecha(fecha, {
            callback: function (data) {
                $("#dp_component").hide();
                $("#buscarGasto").show();
                $("#fecha").val("");
                $("#buscarGasto").val("");
                $("#buscarPor").val("");
                console.log("....", data);
                if (data !== null) {
                    dwr.util.removeAllRows("listadoGastosTabla");
                    listadoGastos = data;
                    dwr.util.addRows("listadoGastosTabla", listadoGastos, mapaListadoGastos, {
                        escapeHtml: false
                    });
                } else {
                    notificacion("danger", "No se encontró ningún resultado por la fecha digitada!", "alert");
                }
            },
            timeout: 20000
        });
    }

    //funciones sobre el editar gasto

    var listadoDetalleGastos1 = [];
    var mapaDetalleGastos1 = [
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

    function editarGastos(idGasto) {
        ajaxGastos.ConsultarGastosXId(idGasto, {
            callback: function (data) {
                if (data !== null) {
                    $("#form_validation").show();
                    $("#divBtnAgregar").hide();
                    $("#tablaGastos").hide();                    
                    $("#procedimiento").val(data.idProcedimiento);
                    $("#paciente").val(data.paciente);
                    $("#cedula").val(data.cedula);
                    $("#fechaGasto").val(data.fecha);
                    $("#idGasto").val(data.id);
                    $("#procedimiento").prop("disabled", true);
                    $("#paciente").prop("disabled", true);
                    $("#cedula").prop("disabled", true);
                    $("#fechaGasto").prop("disabled", true);
                    ajaxGastos.listarDetalleGastoXIdGasto(idGasto, {
                        callback: function (data) {
                            if (data !== null) {
                                $("#divTablaDetalle").show();
                                $("#btnConfirmar").show();
                                dwr.util.removeAllRows("listadoDetalle");
                                listadoDetalleGastos1 = data;
                                dwr.util.addRows("listadoDetalle", listadoDetalleGastos1, mapaDetalleGastos1, {
                                    escapeHtml: false
                                });
                                //$("#labelIdGasto").text(idGasto);
                            }
                        },
                        timeout: 20000
                    });
                }
            },
            timeout: 20000
        });

    }

    function habilita() {
        $("#procedimiento").prop("disabled", false);
        $("#paciente").prop("disabled", false);
        $("#cedula").prop("disabled", false);
        $("#fechaGasto").prop("disabled", false);
        $("#btnHabilita").hide();
        $("#btnEditarGasto").show();
    }
    
    function desabilita() {
        $("#procedimiento").prop("disabled", true);
        $("#paciente").prop("disabled", true);
        $("#cedula").prop("disabled", true);
        $("#fechaGasto").prop("disabled", true);
        $("#btnHabilita").show();
        $("#btnEditarGasto").hide();
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
    
    function editarEncabezadoGasto() {
        //$("#btnRegistrar").prop('disabled', true);
        var gastoEncabezado = {
            idProcedimiento: jQuery("#procedimiento").val(),
            paciente: jQuery("#paciente").val(),
            cedula: jQuery("#cedula").val(),
            fecha: jQuery("#fechaGasto").val(),
            id: jQuery("#idGasto").val()
        };
        
        ajaxGastos.actualizarGastos(gastoEncabezado, {
            callback: function (data) {
                if (data !== null) {
                    notificacion("success", "el encabezado se ha editado con éxito", "alert");
                    desabilita();
                    jQuery("#btnHabilita").show();
                    jQuery("#btnEditarGasto").hide();
                    jQuery("#btnAgregar").show();
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
            },
            timeout: 20000 
        });             
    }

    //hasta aqui va el jquery de editar gastos

    function notificacion(tipo, msj, id) {
        $(".alert").alert('close');
        $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
        setTimeout('$(".alert").alert("close");', '3000');
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