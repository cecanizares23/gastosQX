<%-- 
    Document   : listar-articulos
    Created on : 16/03/2019, 11:50:47 AM
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
    <h5><strong>LISTADO ARTICULOS</strong></h5>
</div><br>

<div class="col-md-12">
    <div id="alert"></div>
</div>

<div class="row" id="tablaArticulos">
    <div class="col-md-12">

        <div class="col-md-12">            
            <div class="row">
                <div class="col-md-12">
                    <div class="table-responsive" >
                        <table class="table table-yuk2 toggle-arrow-tiny tablet breakpoint footable-loaded footable" id="footable_demo" data-filter="#textFilter" data-page-size="5">
                            <thead>
                                <tr>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">REFERENIA</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">LOTE</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">DESCRIPCION</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">CANT.</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">UND MEDIDA</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">MAX</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">MIN</th>
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
        
        <div class="alert alert-danger" id="mensajeReferencia">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <strong>Ya hay un Articulo registrado con esta referencia!</strong>
        </div>
        
        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Referencia:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="referencia" class="form-control" maxlength="50" onblur="validarReferencia(this.value)" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Lote:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="lote" class="form-control" maxlength="50" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Descripción:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="descripcion" class="form-control" maxlength="50"  required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Unidad de Medida:</label>
            <div class="input-group date">
                <span class="input-group-addon"><i class="social_flickr_square bs_ttip"></i></span>
                <select id="unidad" class="form-control" required>                                    
                    <option value="">-Seleccione uno-</option>
                    <option value="UNIDAD">Unidad</option>
                    <option value="KIT">Kit</option>
                    <option value="COMBO">Combo</option>
                </select>
            </div>
        </div>

        <div class="col-md-6 form-group">            
            <label for="val_last_name" class="req">Cantidad Maxima:</label>
            <div class="input-group date">
                <span class="input-group-addon"><i class="el-icon-phone-alt"></i></span>
                <input type="text" id="cantidadMax" class="form-control" onkeypress="return soloNumeros(event);" maxlength="10" required/>
            </div>
        </div>

        <div class="col-md-6 form-group">            
            <label for="val_last_name" class="req">Cantidad Minima:</label>
            <div class="input-group date">
                <span class="input-group-addon"><i class="el-icon-phone-alt"></i></span>
                <input type="text" id="cantidadMin" class="form-control" onkeypress="return soloNumeros(event);" maxlength="10" required/>
            </div>
        </div>

        <div class="col-md-12 form-group"><br><br>
            <div class="col-md-9 form-group"></div>
            <div class="col-md-3 form-group">
                <button class="btn btn-primary" id="btnRegistrar" onclick="javascript:validar('form_validation');">Guardar</button>
                <a class="btn btn-primary" onclick="javascript:cargarPagina('listar-medicos.jsp');">Volver</a>
            </div>
        </div>   

    </div>
</form>

<script>
    
    jQuery(document).ready(function () {
        $("#form_validation").hide();   
        listarArticulos();
    });
    
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
            if (data.estado == 0){
                return '<div class="text-center"><td>' + data.cantidad + '</td></div>';
            }
            if (data.estado == 1){
                if(data.cantidad == 0){
                    return '<div class="text-center"><td><div class="tdRed">' + data.cantidad + '</div></td></div>';
                }
                if(data.cantidad <= data.cantidadMin){
                    return '<div class="text-center"><td><div class="tdYellow">' + data.cantidad + '</div></td></div>';
                }
                if(data.cantidad > data.cantidadMin){}
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
            if (data.estado == 0) {
                return '<div class="text-center"><td><button class="btn btn-success status-active" onclick="activarArticulo(' + data.id + ')">Activar</button></td></div>';
            }
            if (data.estado == 1) {
                return '<div class="text-center"><td><button class="btn btn-danger status-active" onclick="inactivarArticulo(' + data.id + ')">Inactivar</button></td></div>';
            }
        },
        function (data) {
            if (data.estado === "1")
                return '<div class="text-center"><td><button id="btnEditar" class="btn btn-primary status-active" onclick="cargarEditarEspecialidad(' + data.id + ');">Editar</button></td></div>';
            else
                return '<div class="text-center"><td><button id="btnEditar" class="btn btn-primary status-active" disabled onclick="cargarEditarProcedimiento(' + data.id + ');">Editar</button></td></div>';
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
    
    function activarArticulo(id) {
        ajaxGastos.activarEstadoArticulo(id, {
            callback: function (data) {
                if (data) {
                    //listarUsuarios();
                    cargarPagina('listar-articulos.jsp');
                } else {
                    notificacion("danger", "No se pudo actualizar el estado.", "alert");
                }
            },
            timeout: 20000
        });
    }

    function inactivarArticulo(id) {
        ajaxGastos.inactivarEstadoArticulo(id, {
            callback: function (data) {
                if (data) {
                    //listarProcedimientos();
                    cargarPagina('listar-articulos.jsp');
                } else {
                    notificacion("danger", "No se pudo actualizar el estado.", "alert");
                }
            },
            timeout: 20000
        });
    }
    
</script>