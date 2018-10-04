    jQuery(document).ready(function() {
        console.log("entra al ready pdf reporte diario");
        listarAvancesDiarios();
        listarModificacionesAvancesDiarios();
        listarProblemasAvancesDiarios();
        avancesTerminados();
        llenarReponsable();
        nombreImg();
    });


    /*$(function() {
     var doc = new jsPDF('p', 'pt', 'a4');
     
     
     var specialElementHandlers = {
     '#editor': function(element, renderer) {
     return true;
     },
     '.controls': function(element, renderer) {
     return true;
     }
     };
     
     
     $('#btnDescargar').click(function() {
     //doc.fromHTML(source, 15, 15, {
     doc.fromHTML($('#pdf').html(), 15, 15, {
     'width': 170,
     'elementHandlers': specialElementHandlers
     });
     doc.save('sample-file.pdf');
     });
     });*/



    function  nombreImg() {

        ajaxCnk.listarImagenes("<%=idProyecto%>", {
            callback: function(data) {
                if (data !== null) {

                    for (i = 0; i < data.length; i++) {

                        $("#imagenx").append('<img src="" id="' + i + '" width="50%" height="150">');
                        //$("#22Koala.jpg").prop('src', "/cnk/imgAvanceActividad?imagen=22Koala.jpg");

                        $("#" + i).attr('src', "<%= request.getContextPath()%>/imgAvanceActividad?imagen=" + data[i]);
                    }
                }
            },
            timeout: 20000
        });
    }

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
    var listadoAvance = [];
    var mapaListadoAvance = [
        function(data) {
            return data.nombreActividad;
        },
        function(data) {
            return data.unidad;
        },
        function(data) {
            return data.cantidadAproximada;
        },
        function(data) {
            return data.contratista;
        }
    ];
    function listarAvancesDiarios() {
        ajaxCnk.listarAvancesDiarios("<%=idProyecto%>", {
            callback: function(data) {
                if (data !== null) {
                    jQuery('#listadoTablaEjecDia');
                    dwr.util.removeAllRows("listadoTablaEjecDia");
                    listadoAvance = data;
                    dwr.util.addRows("listadoTablaEjecDia", listadoAvance, mapaListadoAvance, {
                        escapeHtml: false
                    });
                    //borrarColumna('datatable-default');
                }
                else {
                    alert("No tiene avances este día");
                }
            },
            timeout: 20000
        });
    }

    var listadoModificacionAvance = [];
    var mapaListadoModificacionAvance = [
        function(data) {
            if (data.modificacion != null) {
                return data.modificacion;
            }
            else {
                return 'No se produjeron cambios';
            }
        },
        function(data) {
            if (data.modificacion != null) {
                return 'X';
            }
        },
        function(data) {
            if (data.modificacion == null) {
                return 'X';
            }
        },
        function(data) {
            return "<%=estadoTiempo%>";
        }
    ];
    function listarModificacionesAvancesDiarios() {
        ajaxCnk.listarAvancesDiarios("<%=idProyecto%>", {
            callback: function(data) {
                if (data !== null) {
                    jQuery('#listadoTablaModificacion');
                    dwr.util.removeAllRows("listadoTablaModificacion");
                    listadoModificacionAvance = data;
                    dwr.util.addRows("listadoTablaModificacion", listadoModificacionAvance, mapaListadoModificacionAvance, {
                        escapeHtml: false
                    });
                    //borrarColumna('datatable-default');
                }
            },
            timeout: 20000
        });
    }

    var listadoProblemasAvance = [];
    var mapaListadoProblemasAvance = [
        function(data) {
            return data.id;
        },
        function(data) {
            return data.problemasPresentados;
        },
        function(data) {
            return;
        }
    ];
    function listarProblemasAvancesDiarios() {
        ajaxCnk.listarAvancesDiarios("<%=idProyecto%>", {
            callback: function(data) {
                if (data !== null) {
                    jQuery('#listadoTablaProbPres');
                    dwr.util.removeAllRows("listadoTablaProbPres");
                    listadoProblemasAvance = data;
                    dwr.util.addRows("listadoTablaProbPres", listadoProblemasAvance, mapaListadoProblemasAvance, {
                        escapeHtml: false
                    });
                    //borrarColumna('datatable-default');
                }
            },
            timeout: 20000
        });
    }

    var terminado = [];
    var mapaTerminado = [
        function(data) {
            if (data.sumaPorcentajeAvance == 100) {
                return data.nombreActividad;
            }
            else {
                return "";
            }

        },
        function(data) {
            if (data.sumaPorcentajeAvance < 100) {
                return data.nombreActividad;
            }
            else {
                return "";
            }
        }
    ];
    function avancesTerminados() {
        ajaxCnk.avancesDiariosTerminados("<%=idProyecto%>", {
            callback: function(data) {
                if (data !== null) {
                    jQuery('#listadoTablaTermActi');
                    dwr.util.removeAllRows("listadoTablaTermActi");
                    terminado = data;
                    dwr.util.addRows("listadoTablaTermActi", terminado, mapaTerminado, {
                        escapeHtml: false
                    });
                    //borrarColumna('datatable-default');
                }
            },
            timeout: 20000
        });
    }

    function llenarReponsable() {
        $("#proyecto").text("<%=nombreProyecto%>");
        $("#fecha").text(hoy);
        $("#interventor").text(" " + "<%=datosUsuario.getNombre()%>");
    }



