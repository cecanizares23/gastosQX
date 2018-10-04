/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//globales
var muni;
var depa;
var idProyecto = "";
var interventor = 2;

jQuery(document).ready(function() {

    $("#divCorreoReporteDiarioUno").hide();
    $("#divCorreoReporteSemanalUno").hide();
    $("#divCorreoReporteDiarioDos").hide();
    $("#divCorreoReporteSemanalDos").hide();
    $("#divCorreoReporteDiarioTres").hide();
    $("#divCorreoReporteSemanalTres").hide();
    $("#divCelularReporteDiarioUno").hide();
    $("#divCelularReporteSemanalUno").hide();
    $("#divCelularReporteDiarioDos").hide();
    $("#divCelularReporteSemanalDos").hide();
    $("#divCelularReporteDiarioTres").hide();
    $("#divCelularReporteSemanalTres").hide();
    jQuery("#divIdCliente").hide();
    jQuery("#divIdInterventor").hide();
    jQuery("#tituloEditarProyecto").hide();
    jQuery("#divEditarProyectos").hide();
    jQuery("#tituloGestionProyectos").show();
    jQuery("#divTablaProyectos").show();
    listarProyectos();

    ajaxCnk.listarDepartamento({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("departamento");
                dwr.util.addOptions("departamento", [{
                        id: '',
                        nombre: 'Seleccione Departamento'
                    }], 'id', 'nombre');
                dwr.util.addOptions("departamento", data, 'id', 'nombre');
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarClientes({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("s2_basic");
                dwr.util.addOptions("s2_basic", [{
                        id: '',
                        nombreCliente: 'Seleccione Cliente'
                    }], 'id', 'nombreCliente');
                dwr.util.addOptions("s2_basic", data, 'id', 'nombreCliente');
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarClientes({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("s2_basic1");
                dwr.util.addOptions("s2_basic1", [{
                        id: '',
                        documentoCliente: 'Seleccione Documento'
                    }], 'id', 'documentoCliente');
                dwr.util.addOptions("s2_basic1", data, 'id', 'documentoCliente');
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarUsuariosInterventor(interventor, {
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("s2_basic2");
                dwr.util.addOptions("s2_basic2", [{
                        id: '',
                        nombre: 'Seleccione Nombre'
                    }], 'id', 'nombre');
                dwr.util.addOptions("s2_basic2", data, 'id', 'nombre');
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarUsuariosInterventor(interventor, {
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("s2_basic3");
                dwr.util.addOptions("s2_basic3", [{
                        id: '',
                        documento: 'Seleccione Documento'
                    }], 'id', 'documento');
                dwr.util.addOptions("s2_basic3", data, 'id', 'documento');
            }
        },
        timeout: 20000
    });

});

function listarConceptos() {
    ajaxCnk.listarConceptosActivos({
        callback: function(data) {
            if (data != null) {
                dwr.util.removeAllOptions("concepto");
                dwr.util.addOptions("concepto", [{
                        id: '',
                        descripcion: 'Seleccione Concepto'
                    }], 'id', 'descripcion');
                dwr.util.addOptions("concepto", data, 'id', 'descripcion');
            }
        },
        timeout: 20000
    });
}

function agregarCorreoRepDiario() {
    var correoRepDiario = jQuery("#destinoRepDiario").val();
    var caract = new RegExp(/^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/);

    if (correoRepDiario == "") {
        notificacion("warning", "El campo Correo Reporte Diario se encuentra vacio, por favor digite un correo.", "alert");
        return;
    }

    if (caract.test(correoRepDiario) == false) {
        notificacion("warning", "Lo que se ha digitado no corresponde a un correo electronico", "alert");
        return;
    }

    if (jQuery("#correoReporteDiarioUno").text() == "") {
        console.log("####### ", jQuery("#correoReporteDiarioUno").text());
        console.log("correoRepDiario ", correoRepDiario);
        jQuery("#correoReporteDiarioUno").html(correoRepDiario);
        jQuery("#divCorreoReporteDiarioUno").show();
        jQuery("#destinoRepDiario").val("");
    } else if (jQuery("#correoReporteDiarioDos").text() == "") {
        if ($("#correoReporteDiarioUno").text() == correoRepDiario) {
            notificacion("warning", "Este correo ya ha sido digitado.", "alert");
            jQuery("#destinoRepDiario").val("");
            return;
        }
        jQuery("#correoReporteDiarioDos").html(correoRepDiario);
        jQuery("#divCorreoReporteDiarioDos").show();
        jQuery("#destinoRepDiario").val("");
    } else if (jQuery("#correoReporteDiarioTres").text() == "") {
        if ($("#correoReporteDiarioUno").text() == correoRepDiario || $("#correoReporteDiarioDos").text() == correoRepDiario) {
            notificacion("warning", "Este correo ya ha sido digitado.", "alert");
            jQuery("#destinoRepDiario").val("");
            return;
        }
        jQuery("#correoReporteDiarioTres").html(correoRepDiario);
        jQuery("#divCorreoReporteDiarioTres").show();
        jQuery("#destinoRepDiario").val("");
    } else {
        notificacion("danger", "Ya se han agregado los tres correos permitidos.", "alert");
    }

}

function eliminarCorreoReporteDiarioUno() {
    jQuery("#correoReporteDiarioUno").html("");
    jQuery("#divCorreoReporteDiarioUno").hide();
}

function eliminarCorreoReporteDiarioDos() {
    jQuery("#correoReporteDiarioDos").html("");
    jQuery("#divCorreoReporteDiarioDos").hide();
}

function eliminarCorreoReporteDiarioTres() {
    jQuery("#correoReporteDiarioTres").html("");
    jQuery("#divCorreoReporteDiarioTres").hide();
}

function agregarCorreoRepSemanal() {
    var correoRepSemanal = jQuery("#destinoRepSemanal").val();
    var caract = new RegExp(/^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/);

    if (correoRepSemanal == "") {
        notificacion("warning", "El campo Correo Reporte Semanal se encuentra vacio, por favor digite un correo.", "alert");
        return;
    }

    if (caract.test(correoRepSemanal) == false) {
        notificacion("warning", "Lo que se ha digitado no corresponde a un correo electronico", "alert");
        return;
    }

    if (jQuery("#correoReporteSemanalUno").text() == "") {
        console.log("####### ", jQuery("#correoReporteSemanalUno").text());
        console.log("correoRepSemanal ", correoRepSemanal);
        jQuery("#correoReporteSemanalUno").html(correoRepSemanal);
        jQuery("#divCorreoReporteSemanalUno").show();
        jQuery("#destinoRepSemanal").val("");
    } else if (jQuery("#correoReporteSemanalDos").text() == "") {
        if ($("#correoReporteSemanalUno").text() == correoRepSemanal) {
            notificacion("warning", "Este correo ya ha sido digitado.", "alert");
            jQuery("#destinoRepSemanal").val("");
            return;
        }
        jQuery("#correoReporteSemanalDos").html(correoRepSemanal);
        jQuery("#divCorreoReporteSemanalDos").show();
        jQuery("#destinoRepSemanal").val("");
    } else if (jQuery("#correoReporteSemanalTres").text() == "") {
        if ($("#correoReporteSemanalUno").text() == correoRepSemanal || $("#correoReporteSemanalDos").text() == correoRepSemanal) {
            notificacion("warning", "Este correo ya ha sido digitado.", "alert");
            jQuery("#destinoRepSemanal").val("");
            return;
        }
        jQuery("#correoReporteSemanalTres").html(correoRepSemanal);
        jQuery("#divCorreoReporteSemanalTres").show();
        jQuery("#destinoRepSemanal").val("");
    } else {
        notificacion("danger", "Ya se han agregado los tres correos permitidos.", "alert");
    }

}

function eliminarCorreoReporteSemanalUno() {
    jQuery("#correoReporteSemanalUno").html("");
    jQuery("#divCorreoReporteSemanalUno").hide();
}

function eliminarCorreoReporteSemanalDos() {
    jQuery("#correoReporteSemanalDos").html("");
    jQuery("#divCorreoReporteSemanalDos").hide();
}

function eliminarCorreoReporteSemanalTres() {
    jQuery("#correoReporteSemanalTres").html("");
    jQuery("#divCorreoReporteSemanalTres").hide();
}

function agregarCelularRepDiario() {
    var celularRepDiario = jQuery("#celularRepDiario").val();

    if (celularRepDiario == "") {
        notificacion("warning", "El campo Celular Reporte Diario se encuentra vacio, por favor digite un numero de celular.", "alert");
        return;
    }

    if (jQuery("#celularReporteDiarioUno").text() == "") {
        console.log("####### ", jQuery("#celularReporteDiarioUno").text());
        console.log("celularRepDiario ", celularRepDiario);
        jQuery("#celularReporteDiarioUno").html(celularRepDiario);
        jQuery("#divCelularReporteDiarioUno").show();
        jQuery("#celularRepDiario").val("");
    } else if (jQuery("#celularReporteDiarioDos").text() == "") {
        if ($("#celularReporteDiarioUno").text() == celularRepDiario) {
            notificacion("warning", "Este numero de celular ya ha sido digitado.", "alert");
            jQuery("#celularRepDiario").val("");
            return;
        }
        jQuery("#celularReporteDiarioDos").html(celularRepDiario);
        jQuery("#divCelularReporteDiarioDos").show();
        jQuery("#celularRepDiario").val("");
    } else if (jQuery("#celularReporteDiarioTres").text() == "") {
        if ($("#celularReporteDiarioUno").text() == celularRepDiario || $("#celularReporteDiarioDos").text() == celularRepDiario) {
            notificacion("warning", "Este numero de celular ya ha sido digitado.", "alert");
            jQuery("#celularRepDiario").val("");
            return;
        }
        jQuery("#celularReporteDiarioTres").html(celularRepDiario);
        jQuery("#divCelularReporteDiarioTres").show();
        jQuery("#celularRepDiario").val("");
    } else {
        notificacion("danger", "Ya se han agregado los tres numeros de celular permitidos.", "alert");
    }

}

function eliminarCelularReporteDiarioUno() {
    jQuery("#celularReporteDiarioUno").html("");
    jQuery("#divCelularReporteDiarioUno").hide();
}

function eliminarCelularReporteDiarioDos() {
    jQuery("#celularReporteDiarioDos").html("");
    jQuery("#divCelularReporteDiarioDos").hide();
}

function eliminarCelularReporteDiarioTres() {
    jQuery("#celularReporteDiarioTres").html("");
    jQuery("#divCelularReporteDiarioTres").hide();
}

function agregarCelularRepSemanal() {
    var celularRepSemanal = jQuery("#celularRepSemanal").val();

    if (celularRepSemanal == "") {
        notificacion("warning", "El campo Celular Reporte Semanal se encuentra vacio, por favor digite un numero de celular.", "alert");
        return;
    }

    if (jQuery("#celularReporteSemanalUno").text() == "") {
        console.log("####### ", jQuery("#celularReporteSemanalUno").text());
        console.log("celularRepSemanal ", celularRepSemanal);
        jQuery("#celularReporteSemanalUno").html(celularRepSemanal);
        jQuery("#divCelularReporteSemanalUno").show();
        jQuery("#celularRepSemanal").val("");
    } else if (jQuery("#celularReporteSemanalDos").text() == "") {
        if ($("#celularReporteSemanalUno").text() == celularRepSemanal) {
            notificacion("warning", "Este numero de celular ya ha sido digitado.", "alert");
            jQuery("#celularRepSemanal").val("");
            return;
        }
        jQuery("#celularReporteSemanalDos").html(celularRepSemanal);
        jQuery("#divCelularReporteSemanalDos").show();
        jQuery("#celularRepSemanal").val("");
    } else if (jQuery("#celularReporteSemanalTres").text() == "") {
        if ($("#celularReporteSemanalUno").text() == celularRepSemanal || $("#celularReporteSemanalDos").text() == celularRepSemanal) {
            notificacion("warning", "Este numero de celular ya ha sido digitado.", "alert");
            jQuery("#celularRepSemanal").val("");
            return;
        }
        jQuery("#celularReporteSemanalTres").html(celularRepSemanal);
        jQuery("#divCelularReporteSemanalTres").show();
        jQuery("#celularRepSemanal").val("");
    } else {
        notificacion("danger", "Ya se han agregado los tres numeros de celular permitidos.", "alert");
    }
}

function eliminarCelularReporteSemanalUno() {
    jQuery("#celularReporteSemanalUno").html("");
    jQuery("#divCelularReporteSemanalUno").hide();
}

function eliminarCelularReporteSemanalDos() {
    jQuery("#celularReporteSemanalDos").html("");
    jQuery("#divCelularReporteSemanalDos").hide();
}

function eliminarCelularReporteSemanalTres() {
    jQuery("#celularReporteSemanalTres").html("");
    jQuery("#divCelularReporteSemanalTres").hide();
}

function listarMunicipioEditar(idDepartamento) {
    ajaxCnk.listarMunicipioPorDepartamentoSelecionado(idDepartamento, {
        callback: function(data) {
            if (data !== null) {
                console.log("idDepartamento ", idDepartamento);
                console.log("data ", data);
                dwr.util.removeAllOptions("municipio");
                dwr.util.addOptions("municipio", [{
                        id: '',
                        nombre: 'Seleccione Municipio'
                    }], 'id', 'nombre');
                dwr.util.addOptions("municipio", data, 'id', 'nombre');
                jQuery("#municipio").val(parseInt(muni)).trigger("change");
            }
        },
        timeout: 20000
    });
}

var listadoProyectos = [];
var mapaListadoProyectos = [
    function(data) {
        return data.documentoCliente;
    },
    function(data) {
        return data.nombreCliente;
    },
    function(data) {
        return data.nombreProyecto;
    },
    function(data) {
        return '<td>$' + data.presupuesto + '</td>';
    },
    function(data) {
        return data.usuarioNombre;
    },
    function(data) {
        return '<td><button id="btnEditar" class="btn-primary status-active" onclick="editarProyecto(' + data.id + ')">Editar</button></td>';
    },
    function(data) {
        if (data.estado == 1) {
            return '<td><div class="text-center"><button id="btnRegistroActividad" class="btn-primary status-active" onclick="cargarListarActividades(' + data.id + ')"><i class="el-icon-plus-sign bs_ttip"></i></button></div></td>';
        } else {
            return '<td><div class="text-center"><button id="btnRegistroActividad" class="btn-primary status-active" onclick="cargarListarActividades(' + data.id + ')" disabled><i class="el-icon-plus-sign bs_ttip"></i></button></div></td>';
        }
    },
    function(data) {
        if (data.estado == 1)
            return '<td><button id="btnEstadoActivo" class="btn-success status-active" onclick="actualizarEstadoInactivar(' + data.id + ')" data-toggle="modal" data-target="#modalDefault">Activar</button></td>';
        else
            return '<td><button id="btnEstadoInactivo" class="btn-danger status-active" onclick="activarProyecto(' + data.id + ')">Inactivar</button></td>';
    },
    function(data) {
        if (data.estado == 0) {
            return '<td><div class="text-center"><button id="btnListarCon" class="btn-primary status-active" onclick="javascript:listarConceptoXIdProyecto(' + data.id + ')" data-toggle="modal" data-target="#modalDefault1"><i class="el-icon-lines bs_ttip"></i></button></div></td>';
        }
    }

];
var idProyectoAct;
function actualizarEstadoInactivar(id) {
    idProyectoAct = id;
    listarConceptos();
}

function inactivarProyecto() {
    console.log("idProyectoAct ", idProyectoAct);
    if ($("#concepto").val() == "") {
        notificacion('danger', 'Debe seleccionador al menos un concepto', 'alertModal');
        return;
    }
    var datos = {
        idConcepto: $("#concepto").val(),
        idProyecto: idProyectoAct
    };
    console.log("datos ", datos);
    ajaxCnk.registrarConceptoProyecto(datos, {
        callback: function(data) {
            if (data == true) {
                notificacion("success", "El proyecto se ha cerrado con exito.", "alert");
                $("#btnCerrarModalConcepto").click();
                listarProyectos();
            } else {
                ajaxCnk.eliminarRegistroCierreProyecto(idProyectoAct, {
                    callback: function(data) {
                        if (data == true) {
                            notificacion("danger", "Ha ocurrido un error al intentar cerrar el proyecto.", "alertModal");
                        }
                    },
                    timeout: 20000
                })
            }

        },
        timeout: 20000
    });
}

function activarProyecto(id) {
    console.log("idProy ", id);
    ajaxCnk.abrirProyecto(id, {
        callback: function(data) {
            if (data == true) {
                notificacion("success", "El proyecto se ha abierto con exito.", "alert");
                listarProyectos();
            }
        },
        timeout: 20000
    });
}

var listadoConcepto = [];
var mapaListadoConcepto = [
    function(data) {
        return data.nombreProyecto;
    },
    function(data) {
        return '<td><div class="text-center">' + data.idConcepto + '</div><td>';
    },
    function(data) {
        return data.fechaRegistro;
    },
    function(data) {
        return data.descripcionConcepto;
    }
];

function listarConceptoXIdProyecto(id) {
    ajaxCnk.consultarConceptoProyectoXIdProyecto(id, {
        callback: function(data) {
            if (data !== null) {
                console.log("data111 ", data);
                dwr.util.removeAllRows("listadoConceptos1");
                listadoConcepto = data;
                dwr.util.addRows("listadoConceptos1", listadoConcepto, mapaListadoConcepto, {
                    escapeHtml: false
                });
            }
        },
        timeout: 20000
    });
}

function listarProyectos() {
    ajaxCnk.listarProyectoTodosProyectos({
        callback: function(data) {
            if (data !== null) {
                console.log("$$$$", data);
                dwr.util.removeAllRows("listadoTablaProyectos");
                listadoProyectos = data;
                dwr.util.addRows("listadoTablaProyectos", listadoProyectos, mapaListadoProyectos, {
                    escapeHtml: false
                });
                //borrarColumna('datatable-default');
            }
        },
        timeout: 20000
    });
}

function cargarListarActividades(id) {
    console.log("%%%%%%% ", id);
    cargarPagina('gestion-actividad.jsp?idProyecto=' + id);
}

function desabilitarNombreCliente() {
    console.log("document");
    if ($("#s2_basic1").val() != "") {
        var idCliente = $("#s2_basic1").val();
        jQuery("#s2_basic").val(idCliente).trigger('change.select2');
        buscarClientePorId();
    }
}

function desabilitarDocumentoCliente() {
    console.log("nombre");
    if ($("#s2_basic").val() != "") {
        var idCliente = $("#s2_basic").val();
        jQuery("#s2_basic1").val(idCliente).trigger('change.select2');
        buscarClientePorId();
    }
}

function buscarClientePorId() {
    var idCliente = "";
    if ($("#s2_basic").val() != "") {
        idCliente = $("#s2_basic").val();
        jQuery("#s2_basic1").val(idCliente);
    }
    else if ($("#s2_basic1").val() != "") {
        idCliente = $("#s2_basic1").val();
        jQuery("#s2_basic").val(idCliente);
    }
    console.log("idCliente ", idCliente);
    ajaxCnk.buscarClientePorId(idCliente, {
        callback: function(data) {
            console.log("data ", data);
            if (data !== null) {
                jQuery("#nit").val(data.documentoCliente);
                jQuery("#RazonSocial").val(data.nombreCliente);
                jQuery("#idCliente").val(data.id);
                jQuery("#nombreProyecto").focus();
            } else {
                notificacion("danger", "No se han encontrado clientes con este # documento o nombre", "alert");
                jQuery("#s2_basic").focus();
                jQuery("#s2_basic").val("");
            }
        },
        timeout: 20000
    });
}

function desabilitarNombreInterventor() {
    console.log("document");
    if ($("#s2_basic3").val() != "") {
        var idCliente = $("#s2_basic3").val();
        jQuery("#s2_basic2").val(idCliente).trigger('change.select2');
        buscarUsuarioInterventorPorId();
    }
}

function desabilitarDocumentoInterventor() {
    console.log("nombre");
    if ($("#s2_basic2").val() != "") {
        var idCliente = $("#s2_basic2").val();
        jQuery("#s2_basic3").val(idCliente).trigger('change.select2');
        buscarUsuarioInterventorPorId();
    }
}

function buscarUsuarioInterventorPorId() {
    var idInterventor = "";
    if ($("#s2_basic2").val() != "") {
        idInterventor = $("#s2_basic2").val();
        jQuery("#s2_basic3").val(idInterventor);
    }
    else if ($("#s2_basic3").val() != "") {
        idInterventor = $("#s2_basic3").val();
        jQuery("#s2_basic2").val(idInterventor);
    }
    ajaxCnk.buscarUsuarioInterventorPorId(idInterventor, interventor, {
        callback: function(data) {
            console.log("data ", data);
            if (data !== null) {
                jQuery("#documento").val(data.documento);
                jQuery("#nombreInterventor").val(data.nombre);
                jQuery("#idInterventor").val(data.idUsuario);
                jQuery("#documentoInterventor").val("");
                //jQuery("#documentoInterventor").prop("disabled", true);
                jQuery("#documento").focus();
                //jQuery("#btnBuscarInterventor").hide();
            } else {
                notificacion("danger", "No se ha encontrado un interventor con este # documento o nombre", "alert");
                jQuery("#s2_basic2").focus();
                jQuery("#s2_basic2").val("");
            }
        },
        timeout: 20000
    });
}

function editarProyecto(id) {
    disponible = true;
    ajaxCnk.consultarProyectoPorId(id, {
        callback: function(data) {

            if (data !== null) {

                console.log("editar ", data);
                idProyecto = id;
                console.log("idProyecto ", idProyecto);
                console.log("dataConsulProy ", data);
                jQuery("#btnGuardarProyecto").prop("disabled", false);
                jQuery("#divIdCliente").hide();
                jQuery("#divIdInterventor").hide();
                jQuery("#tituloEditarProyecto").show();
                jQuery("#divEditarProyectos").show();
                jQuery("#tituloGestionProyectos").hide();
                jQuery("#divTablaProyectos").hide();
                listarMunicipioEditar(data.idDepartamento);
                jQuery('#idCliente').val(data.idCliente);
                jQuery('#nit').val(data.documentoCliente);
                jQuery('#RazonSocial').val(data.nombreCliente);
                jQuery('#nombreProyecto').val(data.nombreProyecto);
                jQuery('#presupuesto').val(data.presupuesto);
                jQuery('#licenciaContruccion').val(data.licenciaConstruccion);
                if (data.planManejoAmbiental == true) {
                    console.log("planManejoAmbiental ", data.planManejoAmbiental);
                    jQuery("#planMA").prop('checked', true);
                } else {
                    //jQuery("#planMA").val("");
                    jQuery("#planMA").prop('checked', false);
                }
                if (data.planSeguridadIndustrial == true) {
                    console.log("planSeguridadIndustrial", data.planSeguridadIndustrial);
                    jQuery("#planSI").prop('checked', true);
                } else {
                    //jQuery("#planSI").val("");
                    jQuery("#planSI").prop('checked', false);
                }
                if (data.sabados == true) {
                    jQuery("#sabados").prop('checked', true);
                } else {
                    jQuery("#sabados").prop('checked', false);
                }
                if (data.domingos == true) {
                    jQuery("#domingos").prop('checked', true);
                } else {
                    jQuery("#domingos").prop('checked', false)
                }
                if (data.festivos == true) {
                    jQuery("#festivos").prop('checked', true);
                } else {
                    jQuery("#festivos").prop('checked', false);
                }
                jQuery('#fechaIniProy').val(data.fechaInicial);
                jQuery('#diasDuracion').val(data.diasDuracionProyecto);
                jQuery('#idInterventor').val(data.idUsuario);
                jQuery('#documento').val(data.usuarioDocumento);
                jQuery('#nombreInterventor').val(data.usuarioNombre);
                depa = data.idDepartamento;
                jQuery('#departamento').val(depa).trigger("change");
                muni = data.idMunicipio;
                jQuery('#municipio').val(muni).trigger("change");
                if (data.destinoRepDiario != null) {
                    $("#correoReporteDiarioUno").html(data.destinoRepDiario);
                    $("#divCorreoReporteDiarioUno").show();
                }

                if (data.destinoRepDiarioUno != null) {
                    $("#correoReporteDiarioDos").html(data.destinoRepDiarioUno);
                    $("#divCorreoReporteDiarioDos").show();
                }

                if (data.destinoRepDiarioDos != null) {
                    $("#correoReporteDiarioTres").html(data.destinoRepDiarioDos);
                    $("#divCorreoReporteDiarioTres").show();
                }

                if (data.destinoRepSemanal != null) {
                    $("#correoReporteSemanalUno").html(data.destinoRepSemanal);
                    $("#divCorreoReporteSemanalUno").show();
                }

                if (data.destinoRepSemanalUno != null) {
                    $("#correoReporteSemanalDos").html(data.destinoRepSemanalUno);
                    $("#divCorreoReporteSemanalDos").show();
                }

                if (data.destinoRepSemanalDos != null) {
                    $("#correoReporteSemanalTres").html(data.destinoRepSemanalDos);
                    $("#divCorreoReporteSemanalTres").show();
                }

                if (data.celularReporteDiario != null) {
                    $("#celularReporteDiarioUno").html(data.celularReporteDiario);
                    $("#divCelularReporteDiarioUno").show();
                }

                if (data.celularReporteDiarioUno != null) {
                    $("#celularReporteDiarioDos").html(data.celularReporteDiarioUno);
                    $("#divCelularReporteDiarioDos").show();
                }

                if (data.celularReporteDiarioDos != null) {
                    $("#celularReporteDiarioTres").html(data.celularReporteDiarioDos);
                    $("#divCelularReporteDiarioTres").show();
                }

                if (data.celularReporteSemanal != null) {
                    $("#celularReporteSemanalUno").html(data.celularReporteSemanal);
                    $("#divCelularReporteSemanalUno").show();
                }

                if (data.celularReporteSemanalUno != null) {
                    $("#celularReporteSemanalDos").html(data.celularReporteSemanalUno);
                    $("#divCelularReporteSemanalDos").show();
                }

                if (data.celularReporteSemanalDos != null) {
                    $("#celularReporteSemanalTres").html(data.celularReporteSemanalDos);
                    $("#divCelularReporteSemanalTres").show();
                }

                jQuery('#nombreFalla').val(data.nombreFallaReporte);
                jQuery('#celularFalla').val(data.celularFallaReporte);
                //activar();

                ajaxCnk.listarActividadXIdProyecto(idProyecto, {
                    callback: function(data) {
                        var listado = [];
                        listado = data;
                        console.log("entraatividades---------- ", listado);
                        if (listado.length != 0) {

                            console.log("----ingresaVerdadero ", listado);
                            for (var i = 0; i < listado.length; i++) {
                                if (listado[i].porcentajeAvance !== null) {
                                    $("#divBuscarCliente").hide();
                                    notificacion("warning", "Este proyecto ya contiene actividades con avances registrados, solo podra modificar el interventor y demas campos. ", "alert");
                                    //jQuery("#presupuesto").prop("disabled", true);
                                    //jQuery("#licenciaContruccion").prop("disabled", true);
                                    jQuery("#planSI").prop("disabled", true);
                                    jQuery("#planMA").prop("disabled", true);
                                    jQuery("#sabados").prop("disabled", true);
                                    jQuery("#domingos").prop("disabled", true);
                                    jQuery("#festivos").prop("disabled", true);
                                    jQuery("#fechaIniProy").prop("disabled", true);
                                    jQuery("#diasDuracion").prop("disabled", true);
                                }
                            }
                        } else {
                            console.log("ingresafalso")
                            jQuery("#presupuesto").prop("disabled", false);
                            jQuery("#licenciaContruccion").prop("disabled", false);
                            jQuery("#planSI").prop("disabled", false);
                            jQuery("#planMA").prop("disabled", false);
                            jQuery("#sabados").prop("disabled", false);
                            jQuery("#domingos").prop("disabled", false);
                            jQuery("#festivos").prop("disabled", false);
                            jQuery("#fechaIniProy").prop("disabled", false);
                            jQuery("#diasDuracion").prop("disabled", false);
                            $(".alert").alert("close");
                        }

                    },
                    timeout: 20000
                });
            }
        },
        timeout: 20000
    });
}

var planMA;
var planSI;
function registrarEditarProyecto() {

    if (jQuery("#planMA").prop('checked')) {
        planMA = 1;
    } else {
        planMA = 0;
    }

    if (jQuery("#planSI").prop('checked')) {
        planSI = 1;
    } else {
        planSI = 0;
    }

    if (jQuery("#sabados").prop('checked')) {
        sabados = 1;
    } else {
        sabados = 0;
    }

    if (jQuery("#domingos").prop('checked')) {
        domingos = 1;
    } else {
        domingos = 0;
    }

    if (jQuery("#festivos").prop('checked')) {
        festivos = 1;
    } else {
        festivos = 0;
    }

    var proyecto = {
        id: idProyecto,
        idCliente: jQuery("#idCliente").val(),
        idUsuario: jQuery("#idInterventor").val(),
        nombreProyecto: jQuery("#nombreProyecto").val(),
        idDepartamento: jQuery("#departamento").val(),
        idMunicipio: jQuery("#municipio").val(),
        presupuesto: jQuery("#presupuesto").val(),
        licenciaConstruccion: jQuery("#licenciaContruccion").val(),
        planManejoAmbiental: planMA,
        planSeguridadIndustrial: planSI,
        sabados: sabados,
        domingos: domingos,
        festivos: festivos,
        destinoRepSemanal: jQuery("#correoReporteSemanalUno").text(),
        destinoRepDiario: jQuery("#correoReporteDiarioUno").text(),
        fechaInicial: jQuery("#fechaIniProy").val(),
        diasDuracionProyecto: jQuery("#diasDuracion").val(),
        celularReporteDiario: jQuery("#celularReporteDiarioUno").text(),
        celularReporteSemanal: jQuery("#celularReporteSemanalUno").text(),
        nombreFallaReporte: jQuery("#nombreFalla").val(),
        celularFallaReporte: jQuery("#celularFalla").val(),
        destinoRepDiarioUno: $("#correoReporteDiarioDos").text(),
        destinoRepDiarioDos: $("#correoReporteDiarioTres").text(),
        destinoRepSemanalUno: $("#correoReporteSemanalDos").text(),
        destinoRepSemanalDos: $("#correoReporteSemanalTres").text(),
        celularReporteDiarioUno: $("#celularReporteDiarioDos").text(),
        celularReporteDiarioDos: $("#celularReporteDiarioTres").text(),
        celularReporteSemanalUno: $("#celularReporteSemanalDos").text(),
        celularReporteSemanalDos: $("#celularReporteSemanalTres").text()
    };            
    
    var fechaIniProyecto = jQuery("#fechaIniProy").val();
    var fechaFinProyecto = jQuery("#fechaFinProy").val();
    if (Date.parse(fechaIniProyecto) > Date.parse(fechaFinProyecto)) {
        notificacion("danger", "La fecha Inicial no puede ser mayor a la final", "alert");
        return;
    }
    
    if($("#correoReporteDiarioUno").text() == "" && $("#correoReporteDiarioDos").text() == "" && $("#correoReporteDiarioTres").text() == ""){
        notificacion("danger","Debe ingresar al menos un correo de destino para el reporte diario","alert");
        return;
    }
    
    if($("#correoReporteSemanalUno").text() == "" && $("#correoReporteSemanalDos").text() == "" && $("#correoReporteSemanalTres").text() == ""){
        notificacion("danger","Debe ingresar al menos un correo de destino para el reporte semanal","alert");
        return;
    }
    
    if($("#celularReporteDiarioUno").text() == "" && $("#celularReporteDiarioDos").text() == "" && $("#celularReporteDiarioTres").text() == ""){
        notificacion("danger","Debe ingresar al menos un numero de celular destino para el reporte diario","alert");
        return;
    }
    
    if($("#celularReporteSemanalUno").text() == "" && $("#celularReporteSemanalDos").text() == "" && $("#celularReporteSemanalTres").text() == ""){
        notificacion("danger","Debe ingresar al menos un numero de celular destino para el reporte diario","alert");
        return;
    }

    ajaxCnk.editarProyecto(proyecto, {
        callback: function(data) {
            console.log("dataEditar ", data);
            if (data !== null) {
                notificacion("success", "el proyecto se ha editado con éxito", "alert");
                limpiarFormularioRegistro();
                setTimeout('cargarPagina("gestion-proyecto.jsp");', '5000');
                jQuery("#btnGuardarProyecto").prop("disabled", true);
            } else {
                notificacion("danger", "se ha generado un error", "alert");
            }
        },
        timeout: 20000
    });
    //desactivar();
    //limpiarFormularioRegistro();



}

function limpiarFormularioRegistro() {
    jQuery("#nit").val("");
    jQuery("#RazonSocial").val("");
    jQuery("#nombreProyecto").val("");
    jQuery("#presupuesto").val("");
    jQuery("#PlanSI").val("");
    jQuery("#planMA").val("");
    jQuery("#fechaIniProy").val("");
    jQuery("#diasDuracion").val("");
    jQuery("#documento").val("");
    jQuery("#nombreInterventor").val("");
    jQuery("#departamento").val("");
    jQuery("#municipio").val("");
    jQuery("#destinoRepDiario").val("");
    jQuery("#destinoRepSemanal").val("");
    jQuery("#celularRepDiario").val("");
    jQuery("#celularRepSemanal").val("");
    jQuery("#nombreFalla").val("");
    jQuery("#celularFalla").val("");
    jQuery('#licenciaContruccion').val("");
}

function desactivar() {
    jQuery("#nombreProyecto").prop("disabled", true);
    jQuery("#presupuesto").prop("disabled", true);
    jQuery("#licenciaContruccion").prop("disabled", true);
    jQuery("#PlanSI").prop("disabled", true);
    jQuery("#planMA").prop("disabled", true);
    jQuery("#fechaIniProy").prop("disabled", true);
    jQuery("#diasDuracion").prop("disabled", true);
    jQuery("#departamento").prop("disabled", true);
    jQuery("#municipio").prop("disabled", true);
    jQuery("#destinoRepDiario").prop("disabled", true);
    jQuery("#destinoRepSemanal").prop("disabled", true);
    jQuery("#celularRepDiario").prop("disabled", true);
    jQuery("#celularRepSemanal").prop("disabled", true);
    jQuery("#nombreFalla").prop("disabled", true);
    jQuery("#celularFalla").prop("disabled", true);
    jQuery("#btnGuardarProyecto").prop("disabled", true);
}

function activar() {
    jQuery("#nombreProyecto").prop("disabled", false);
    jQuery("#presupuesto").prop("disabled", false);
    jQuery("#licenciaContruccion").prop("disabled", false);
    jQuery("#PlanSI").prop("disabled", false);
    jQuery("#planMA").prop("disabled", false);
    jQuery("#fechaIniProy").prop("disabled", false);
    jQuery("#diasDuracion").prop("disabled", false);
    jQuery("#departamento").prop("disabled", false);
    jQuery("#municipio").prop("disabled", false);
    jQuery("#destinoRepDiario").prop("disabled", false);
    jQuery("#destinoRepSemanal").prop("disabled", false);
    jQuery("#celularRepDiario").prop("disabled", false);
    jQuery("#celularRepSemanal").prop("disabled", false);
    jQuery("#nombreFalla").prop("disabled", false);
    jQuery("#celularFalla").prop("disabled", false);
    jQuery("#btnGuardarProyecto").prop("disabled", false);
}

function volver() {
    jQuery("#divIdCliente").hide();
    jQuery("#divIdInterventor").hide();
    jQuery("#tituloEditarProyecto").hide();
    jQuery("#divEditarProyectos").hide();
    jQuery("#tituloGestionProyectos").show();
    jQuery("#divTablaProyectos").show();
    limpiarFormularioRegistro();
    listarProyectos();
}

function limpiar() {
    limpiarFormularioRegistro();
    activar();
}

function notificacion(tipo, msj, id) {
    $(".alert").alert('close');
    $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
    setTimeout('$(".alert").alert("close");', '5000');
    $('html, body').animate({
        scrollTop: $("#alert").offset().top
    }, 2000);
}

function validar(formulario) {

    $('#' + formulario).validate({
        highlight: function(label) {
            jQuery(label).closest('.form-group').removeClass('has-success').addClass('has-error');
        },
        success: function(label) {
            jQuery(label).closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function(error, element) {
            var placement = element.closest('.input-group');
            if (!placement.get(0)) {
                placement = element;
            }
            if (error.text() !== '') {
                placement.after(error);
            }
        },
        submitHandler: function() {

            registrarEditarProyecto();
        }

    });
}

$(function() {
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

$(function() {
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
    console.log("entra a la funcion");
    var key = e.which || e.keyCode;
    console.log("key", key);
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
    console.log("key", key);
    if ((key < 63 && key != 8 && key != 32) || key > 122) {
        e.preventDefault();
    }
}
