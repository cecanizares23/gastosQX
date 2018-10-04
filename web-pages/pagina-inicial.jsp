<%-- 
    Document   : pagina-principal
    Created on : 27/10/2017, 02:35:26 PM
    Author     : carlos
--%>

<%@page import="co.IngCarlos.gastosQX.mvc.dto.*"%>
<%@ page import="java.util.*,java.io.*"%>
<%@page session='true'%>

<%
    DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");
%>

<div class="row">

    <div class="col-md-12">
        <div id="alert"></div>
    </div>

    <div class="col-lg-12">
        <img src="assets/img/cnktussueños.png" class="img-responsive">                
    </div>
</div>

<script>

    jQuery(document).ready(function() {
       
    });

    var hoy = new Date();
    var dd = hoy.getDate();
    var mm = hoy.getMonth() + 1; //hoy es 0!
    var yyyy = hoy.getFullYear();

    if (dd < 10) {
        dd = '0' + dd;
    }

    if (mm < 10) {
        mm = '0' + mm;
    }

    hoy = dd + '/' + mm + '/' + yyyy;

    /**function alarmasPmt() {
        var listadoPmtActivos = [];
        ajaxCnk.listarPmtXEstadoActivo({
            callback: function(data) {
                if (data !== null) {
                    listadoPmtActivos = data;
                    for (var i = 0; i < listadoPmtActivos.length; i++) {
                        var fechaVencimientoPmt = listadoPmtActivos[i].fechaFinalPmt;
                        console.log("fechaVencimientoPmt ", fechaVencimientoPmt);
                        console.log("hoy ", hoy);
                        var nombreProyecto = listadoPmtActivos[i].nombreProyecto;
                        if (hoy == fechaVencimientoPmt) {
                            console.log("entra a actualizar estado");
                            ajaxCnk.actualizarEstadoPmt(listadoPmtActivos[i].id, {
                                callback: function(data) {
                                    if (data !== null) {
                                        console.log("// ", data.mensaje);
                                        var msj = data.mensaje;
                                        console.log("## ", msj);
                                        setTimeout('notificacion("success",\'' + msj+ '\', "alert");','2000');
                                        notificacion("success", msj, "alert");
                                        //setTimeout("cargarPagina('gestion-pmt.jsp');", "3000");                                        
                                    }
                                },
                                timeout: 20000
                            });
                        } else {
                            var actual = new Date(hoy);
                            var vencimientoPmt = new Date(fechaVencimientoPmt);

                            var resta = vencimientoPmt - actual;
                            var dias = Math.floor(resta / (1000 * 60 * 60 * 24));

                            if (dias <= 5) {
                                notificacion("warning", "¡Quedan 5 dias o menos!, para vencerse el seguro PMT del proyecto:" + listadoPmtActivos[i].nombreProyecto, "alert");
                                //setTimeout('cargarPagina("gestion-pmt.jsp");', '3000');
                            }

                        }
                    }
                }
            },
            timeout: 20000
        });
    }**/

    function notificacion(tipo, msj, id) {
        $(".alert").alert('close');
        $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
        setTimeout('$(".alert").alert("close");', '10000');
    }

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
