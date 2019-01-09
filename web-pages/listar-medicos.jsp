<%-- 
    Document   : listar-medicos
    Created on : 27-sep-2018, 14:20:47
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
    <h5><strong>LISTADO DE MEDICOS</strong></h5>
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
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">CELULAR</th>
                                    <th data-toggle="true" class="footable-visible footable-first-column text-center">ESTADO</th> 
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

<form id="form_validation" name="form_validation" action="autocomplete:off" novalidate>
    <div class="row">       

        <div class="alert alert-danger" id="mensajeDocumento">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <strong>Ya hay un medico registrado con este numero de documento!</strong>
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Nombres:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="nombreMedico" class="form-control" maxlength="50" onkeypress="return soloLetras(event);" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Apellidos:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="apellidoMedico" class="form-control" maxlength="50" onkeypress="return soloLetras(event);" required/>
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
                <input type="text" id="documento" class="form-control" onkeypress="javascript:soloNumeros(event);" onblur="validarDocumentoMedico(this.value)" maxlength="12" required/>
            </div>
        </div>                

        <!--<div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Fecha Nacimiento:</label>                                
            <div class="input-group date" id="dp_component">
                <span class="input-group-addon"><i class="icon_calendar"></i></span>
                <input type="text" class="form-control" id="fechaNacimiento" required />                
            </div>                                
        </div>-->

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
            <label for="val_last_name" class="req">Direccion residencia:</label>
            <div class="input-group date">
                <span class="input-group-addon"><i class="el-icon-phone-alt"></i></span>
                <input type="text" id="direccion" class="form-control" required/>
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
            <label for="val_first_name" class="req">Especialidad:</label>
            <div class="input-group date">
                <span class="input-group-addon"><i class="icon_group bs_ttip"></i></span>
                <select id="especialidad" class="form-control" required></select>
            </div>
        </div>                                                                        

        <div class="col-md-12 form-group"><br><br>
            <div class="col-md-9 form-group"></div>
            <div class="col-md-3 form-group">
                <button class="btn btn-primary" id="btnRegistrar" onclick="javascript:validar('form_validation');">Guardar</button>
                <a class="btn btn-primary" onclick="javascript:cargarPagina('listar-usuarios.jsp');">Volver</a>
            </div>

        </div>        

    </div>
</form>

<script>
    jQuery(document).ready(function () {
        listarMedicos();
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

         ajaxGastos.listarTodasEspecialidades({
            callback: function (data) {
                if (data !== null) {
                    dwr.util.removeAllOptions("especialidad");
                    dwr.util.addOptions("especialidad", [{
                            id: '',
                            descripcion: 'Seleccione especialidad'
                        }], 'id', 'descripcion');
                    dwr.util.addOptions("especialidad", data, 'id', 'descripcion');
                }
            },
            timeout: 20000
        });

    });
    
     var listadoMedicos = [];
    var mapaListadoMedicos = [
        function (data) {
            return data.cedula;
        },
        function (data) {
            return data.nombres;
        },
        function (data) {
            return data.email;
        },
        function (data) {
            return data.celular;
        },
        function (data) {
            if (data.estado === 0) {
                return "Inactivo";
            } else if (data.estado === 1) {
                return "Activo";
            }
        },
        function (data) {
            if (data.id === "1")
                return '<td><button id="btnEditar" class="btn-primary status-active" disabled="true" onclick="cargarEditar(' + data.id + ');">Editar</button></td>';
            else
                return '<td><button id="btnEditar" class="btn-primary status-active" onclick="cargarEditar(' + data.id + ');">Editar</button></td>';
        }
    ];

    function listarMedicos() {
        ajaxGastos.listarMedicos({
            callback: function (data) {
                if (data !== null) {
                    //$("#tablaReportes").dataTable().fnDestroy();                    
                    dwr.util.removeAllRows("listado");
                    listadoMedicos = data;
                    console.log("ingres");                    
                    dwr.util.addRows("listado", listadoMedicos, mapaListadoMedicos, {
                        escapeHtml: false
                    });
                }
            },
            timeout: 20000
        });
    }
</script>
