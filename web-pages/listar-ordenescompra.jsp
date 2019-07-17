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
                <option value="3">Fecha</option>
            </select>
        </div>
        <div class="col-md-2">
            <a class="btn btn-primary" id="btnBuscar" onclick="javascript:buscarGasto();">Buscar</a>
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
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">CONSECUTIVO</th>                                    
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
                <button type="butt on" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
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
                                                <th data-toggle="true" class="footable-visible footable-first-column text-center">ELIMINAR</th>
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

<script>
    jQuery(document).ready(function () {
        
    });
</script>