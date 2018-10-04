/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery(document).ready(function() {
    jQuery("#tituloGestionActividad").show();
    jQuery("#divListarProyectos").show();
    jQuery("#tablaActividades").hide();
    jQuery("#divIdActividad").show();
    jQuery("#tituloEditarActividad").hide();
    jQuery("#divEditarActividad").hide();
    jQuery("#tituloRegistrarRequisito").hide();
    jQuery("#agregarRequisito").hide();
    jQuery("#divIdActividad").hide();
    $("#divTablaRequisitosAsociados").hide();
    $("#btnEliminar1").hide();
    ListarProyectoEditar();
    console.log("$%&/() ", idProyectoPara);
    if (idProyectoPara !== "") {
        setTimeout('$("#listarProyecto").val(idProyectoPara);', '500');
    }

    ajaxCnk.listarProyecto({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("listarProyecto");
                dwr.util.addOptions("listarProyecto", [{
                        id: '',
                        nombreProyecto: 'Seleccione un Proyecto'
                    }], 'id', 'nombreProyecto');
                dwr.util.addOptions("listarProyecto", data, 'id', 'nombreProyecto');
            }
        },
        timeout: 20000
    });

});

function ListarProyectoEditar() {
    ajaxCnk.listarProyecto({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("proyecto");
                dwr.util.addOptions("proyecto", [{
                        id: '',
                        nombreProyecto: 'Seleccione un Proyecto'
                    }], 'id', 'nombreProyecto');
                dwr.util.addOptions("proyecto", data, 'id', 'nombreProyecto');
            }
        },
        timeout: 20000
    });
}

function listar() {
    jQuery("#tituloGestionActividad").show();
    jQuery("#divListarProyectos").hide();
    jQuery("#tablaActividades").show();
    console.log("Proyecto ", jQuery("#proyecto").val());
    listarActividadesPorProyecto();
}

var listadoActividades = [];
var mapaListadoActividades = [
    function(data) {
        return  data.contador;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return data.id;
    },
    function(data) {
        if (data.estadoActividad == 1) {
            return "Abierta";
        } else if (data.estadoActividad == 0) {
            return "Cerrada";
        }
    },
    function(data) {
        return data.fechaInicio;
    },
    function(data) {
        return data.fechaFin;
    },
    function(data) {
        return data.porcentajeActividad;
    },
    function(data) {
        return data.duracionDias;
    },
    function(data) {
        return data.duracionSemanas;
    },
    function(data) {
        return data.codigoPadre;
    },
    function(data) {
        return '<td><button id="btnVerRequisitos" class="btn-info status-active" data-toggle="modal" data-target="#modalDefault" onclick="listarRequisitos(' + data.id + ')">ver</button></td>';
    },
    function(data) {
        return '<td><button id="btnEditar" class="btn-primary status-active" onclick="editarActividad(' + data.id + ')">Editar</button></td>';
    },
    function(data) {
        return '<td><button class="btn-danger" id="btnEliminar" onclick="enviarId(' + data.id + ')">Eliminar</button></td>';
        //return '<td><button id="btnEliminar" class="btn-danger status-active" onclick="eliminarActividad(' + data.id + ')">Eliminar</button></td>';
    }
];

var idProyecto = jQuery("#listarProyecto").val();
function listarActividadesPorProyecto() {
    if (jQuery("#listarProyecto").val() == "") {
        notificacion("info", "Debe seleccionar un proyecto para ver las actividades registradas.", "alert");
        return;
    }
    ajaxCnk.listarActividadesXProyecto(jQuery("#listarProyecto").val(), {
        callback: function(data) {
            if (data !== null) {
                jQuery("#tituloGestionActividad").show();
                jQuery("#divListarProyectos").hide();
                jQuery("#tablaActividades").show();
                //console.log("33333 ggggggggggg ", data[0].nombreProyecto);
                jQuery("#nameProyecto").text(data[0].nombreProyecto);
                idProyecto = data[0].idProyecto;
                dwr.util.removeAllRows("listadoActividadesPorProyecto");
                listadoActividades = data;
                dwr.util.addRows("listadoActividadesPorProyecto", listadoActividades, mapaListadoActividades, {
                    escapeHtml: false

                });
                //borrarColumna('datatable-default');
            } else {
                notificacion("warning", "En este momento este proyecto no tiene actividades registradas", "alert");
                jQuery("#tituloGestionActividad").show();
                jQuery("#divListarProyectos").show();
                jQuery("#tablaActividades").hide();
            }
        },
        timeout: 20000
    });
}

var listadoRequisitos = [];
var mapaListadoRequisitos = [
    function(data) {
        return data.id;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return data.fechaInicio;
    },
    function(data) {
        return data.fechaFin;
    }
];

var idActividad;
function listarRequisitos(id) {
    idActividad = id;
    ajaxCnk.listarRequisitosPorIdActividad(idActividad, {
        callback: function(data) {
            console.log('muestra data', data);
            if (data !== null) {
                jQuery("#tablaRequisitos").show();
                jQuery("#mensaje").hide();
                console.log("$$$$", data);
                dwr.util.removeAllRows("listadoRequisitosPorProyecto");
                listadoRequisitos = data;
                dwr.util.addRows("listadoRequisitosPorProyecto", listadoRequisitos, mapaListadoRequisitos, {
                    escapeHtml: false
                });
                //borrarColumna('datatable-default');
            } else {
                jQuery("#tablaRequisitos").hide();
                jQuery("#mensaje").show();
            }
        },
        timeout: 20000
    });
}

var idActividadMisma;
function editarActividad(id) {
    disponible = true;
    ajaxCnk.consultarActividadPorId(id, {
        callback: function(data) {
            console.log('editar', data);
            if (data !== null) {
                if (data.estadoActividad == 0) {
                    console.log("ingresa por el true ", data.estadoActividad);
                    idActividad = id;
                    idActividadMisma = data.id;
                    console.log("data1111111111111 ", data);
                    desactivar();
                    jQuery("#codigoPadre").prop('disabled', true);
                    jQuery("#proyecto").prop("disabled", true);
                    jQuery("#btnRegistrarActividad").prop('disabled', true);
                    jQuery("#divFechaInicioPadre").hide();
                    jQuery("#divFechaFinPadre").hide();
                    jQuery("#tituloGestionActividad").hide();
                    jQuery("#divListarProyectos").hide();
                    jQuery("#tablaActividades").hide();
                    jQuery("#tituloEditarActividad").show();
                    jQuery("#divEditarActividad").show();
                    jQuery("#tituloRegistrarRequisito").hide();
                    jQuery("#agregarRequisito").hide();

                    jQuery('#proyecto').val(data.idProyecto);
                    jQuery('#estado').val(data.estadoActividad);
                    jQuery('#nombreActividad').val(data.nombreActividad);
                    jQuery('#porcentajeActividad').val(data.porcentajeActividad);
                    jQuery('#fechaInicio').val(data.fechaInicio);
                    //jQuery('#fechaFin').val(data.fechaFin);
                    jQuery('#duracionDias').val(data.duracionDias);
                    listarCodigoPadres();
                    setTimeout(" jQuery('#codigoPadre').val(" + data.codigoPadre + ");", "1000");
                    ajaxCnk.consultarActividadPorCodigoPadre(data.codigoPadre, {
                        callback: function(data) {
                            if (data !== null) {
                                jQuery("#fechaInicioPadre").val(data.fechaInicio);
                                jQuery("#fechaFinPadre").val(data.fechaFin);
                            }
                        },
                        timeout: 20000
                    });
                    jQuery('#idActividad').val(data.id);
                    //activar();
                } else {
                    console.log("ingresa por el else ", data.estadoActividad);
                    idActividad = id;
                    listarRequisitosAsociados(id);
                    idActividadMisma = data.id;
                    console.log("data2222222222222222 ", data);
                    jQuery("#tituloGestionActividad").hide();
                    jQuery("#divListarProyectos").hide();
                    jQuery("#tablaActividades").hide();
                    jQuery("#tituloEditarActividad").show();
                    jQuery("#divEditarActividad").show();
                    jQuery("#tituloRegistrarRequisito").hide();
                    jQuery("#agregarRequisito").hide();

                    jQuery('#proyecto').val(data.idProyecto);
                    jQuery('#estado').val(data.estadoActividad);
                    jQuery('#nombreActividad').val(data.nombreActividad);
                    jQuery('#porcentajeActividad').val(data.porcentajeActividad);
                    jQuery('#fechaInicio').val(data.fechaInicio);
                    //jQuery('#fechaFin').val(data.fechaFin);
                    jQuery('#duracionDias').val(data.duracionDias);
                    listarCodigoPadres();

                    ajaxCnk.consultarActividadPorCodigoPadre(data.codigoPadre, {
                        callback: function(data) {
                            if (data !== null) {
                                jQuery("#fechaInicioPadre").val(data.fechaInicio);
                                jQuery("#fechaFinPadre").val(data.fechaFin);
                            }
                        },
                        timeout: 20000
                    });
                    jQuery('#idActividad').val(data.id);
                    activar();
                    jQuery("#proyecto").prop("disabled", true);
                    //setTimeout('jQuery("#codigoPadre").prop("disabled", true)','700');
                    if (data.codigoPadre != null) {
                        console.log('data.codigoPadre ', data.codigoPadre);
                        setTimeout(" jQuery('#codigoPadre').val(" + data.codigoPadre + ");", "1000");
                        jQuery('#porcentajeActividad').prop('disabled', true);
                    }
                }

            }
        },
        timeout: 20000
    });
}

var listadoRequisitosAsociados = [];
var mapaListadoRequisitosAsociados = [
    function(data) {
        return '<td><div class="text-center">' + data.id + '</div></td>';
    },
    function(data) {
        return '<td><div class="text-center">' + data.nombreActividad + '</div></td>';
    },
    function(data) {
        return '<td><div class="text-center">' + data.fechaInicio + '</div></td>';
    },
    function(data) {
        return '<td><div class="text-center">' + data.fechaFin + '</div></td>';
    },
    function(data) {
        return '<td><div class="text-center"><button class="btn btn-danger id="btnEliminarRequisito" onclick="eliminarRequisito(' + data.id + ')" data-target="#modalSmall1"><i class="el-icon-error bs_ttip"></i></button></div></td>';
        //return '<td><button id="btnEliminar" class="btn-danger status-active" onclick="eliminarActividad(' + data.id + ')">Eliminar</button></td>';
    }
];

function listarRequisitosAsociados(id) {
    console.log("idATDFDDG ", id);
    ajaxCnk.listarRequisitosPorIdActividad(id, {
        callback: function(data) {
            console.log('muestra data', data);
            if (data !== null) {
                jQuery("#divTablaRequisitosAsociados").show();
                dwr.util.removeAllRows("listadoRequisitosAsociados");
                listadoRequisitosAsociados = data;
                dwr.util.addRows("listadoRequisitosAsociados", listadoRequisitosAsociados, mapaListadoRequisitosAsociados, {
                    escapeHtml: false
                });
                //borrarColumna('datatable-default');
            } else {
                jQuery("#divTablaRequisitosAsociados").hide();
                //jQuery("#mensaje").show();
            }
        },
        timeout: 20000
    });
}

function enviarId(id) {
    idActividad = id;
    ajaxCnk.consultarActividadPorId(id, {
        callback: function(data) {
            if (data != null) {
                console.log('data.porcentajeavance ', data);
                var porcentajeAvanzado = data.porcentajeAvance;
                console.log('porcentajeAvanzado ', porcentajeAvanzado);
                if (porcentajeAvanzado !== null) {
                    notificacion('danger', 'Esta actividad ya ha avanzado, por esto no se puede eliminar', 'alert');
                    return;
                }
            }
        },
        timeout: 20000
    });
    $("#btnEliminar1").click();
}

function eliminarActividad() {
    console.log("resiveid ", idActividad);
    disponible = true;
    ajaxCnk.eliminarActividad(idActividad, {
        callback: function(data) {
            console.log('muestra ppppppppp', data);
            if (data !== true) {
                console.log('muestra ppppppppp', data);
                notificacion("danger", "No puedes eliminar esta actividad", "alert");
            } else {
                notificacion("success", "Actividad eliminada satisfactoriamente", "alert");
                //cargarPagina('gestion-actividad.jsp');
                ajaxCnk.listarActividadesXProyecto(idProyecto, {
                    callback: function(data) {
                        if (data !== null) {
                            jQuery("#tituloGestionActividad").show();
                            jQuery("#divListarProyectos").hide();
                            jQuery("#tablaActividades").show();
                            //console.log("33333 ggggggggggg ", data[0].nombreProyecto);
                            jQuery("#nameProyecto").text(data[0].nombreProyecto);
                            idProyecto = data[0].idProyecto;
                            dwr.util.removeAllRows("listadoActividadesPorProyecto");
                            listadoActividades = data;
                            dwr.util.addRows("listadoActividadesPorProyecto", listadoActividades, mapaListadoActividades, {
                                escapeHtml: false

                            });
                            //borrarColumna('datatable-default');
                        } else {
                            notificacion("warning", "En este momento este proyecto no tiene actividades registradas", "alert");
                            jQuery("#tituloGestionActividad").show();
                            jQuery("#divListarProyectos").show();
                            jQuery("#tablaActividades").hide();
                        }
                    },
                    timeout: 20000
                });
            }
        },
        timeout: 20000
    });
}

function eliminarRequisito(id) {
    console.log("11111 ", idActividad);
    ajaxCnk.listarRequisitosXIdActividadXRequisito(id, idActividad, {
        callback: function(data) {
            if (data != null) {
                var idRequisito = data.id;
                console.log("222222 ", idRequisito);
                ajaxCnk.eliminarRequisito(idRequisito, {
                    callback: function(data) {
                        if (data == true) {
                            notificacion("warning", "Requisito desasociado satisfactoriamente", "alert");
                            ajaxCnk.listarRequisitosPorIdActividad(idActividad, {
                                callback: function(data) {
                                    console.log('muestra data', data);
                                    if (data !== null) {
                                        jQuery("#divTablaRequisitosAsociados").show();
                                        dwr.util.removeAllRows("listadoRequisitosAsociados");
                                        listadoRequisitosAsociados = data;
                                        dwr.util.addRows("listadoRequisitosAsociados", listadoRequisitosAsociados, mapaListadoRequisitosAsociados, {
                                            escapeHtml: false
                                        });
                                        //borrarColumna('datatable-default');
                                    } else {
                                        jQuery("#divTablaRequisitosAsociados").hide();
                                        //jQuery("#mensaje").show();
                                    }
                                },
                                timeout: 20000
                            });
                        }
                    },
                    timeout: 20000
                });

            }
        },
        timeout: 20000
    });

}

function volver() {
    limpiarFormularioRegistro();
    //cargarPagina('gestion-actividad.jsp');
    jQuery("#tituloGestionActividad").show();
    jQuery("#divListarProyectos").show();
    jQuery("#tablaActividades").show();
    jQuery("#tituloEditarActividad").hide();
    jQuery("#divEditarActividad").hide();
    $("#divTablaRequisitosAsociados").hide();
    listarActividadesPorProyecto();
}

function llenarFechasCodigoPadre() {
    ajaxCnk.consultarActividadPorCodigoPadre(jQuery("#codigoPadre").val(), {
        callback: function(data) {
            if (data !== null) {
                jQuery("#fechaInicioPadre").val(data.fechaInicio);
                jQuery("#fechaFinPadre").val(data.fechaFin);
            } else {
                jQuery("#fechaInicioPadre").val("");
                jQuery("#fechaFinPadre").val("");
            }
        },
        timeout: 20000
    });
}

function registrarEditarActividad() {
    $("#btnRegistrarActividad").prop('disabled', true);
    var actividad = {
        idProyecto: jQuery("#proyecto").val(),
        estadoActividad: jQuery("#estado").val(),
        nombreActividad: jQuery("#nombreActividad").val(),
        porcentajeActividad: jQuery("#porcentajeActividad").val(),
        fechaInicio: jQuery("#fechaInicio").val(),
        //fechaFin: jQuery("#fechaFin").val(),
        duracionDias: jQuery("#duracionDias").val(),
        codigoPadre: jQuery("#codigoPadre").val(),
        id: jQuery("#idActividad").val(),
        cerrada: jQuery("#estado").val()
    };

    if ($("#estado").val() == "0") {
        ajaxCnk.consultarActividadPorId($("#idActividad").val(), {
            callback: function(data) {
                if (data != null) {
                    var porcentajeAvance = data.porcentajeAvance;
                    if (porcentajeAvance > 0) {
                        notificacion("warning", "Esta actividad ya posee avances, por favor terminela", "alert");
                        $("#btnRegistrarActividad").prop('disabled', false);
                        return;
                    }
                }
            },
            timeout: 20000
        });
        //return;
    }

    if (jQuery("#porcentajeActividad").val() > 100 || jQuery("#porcentajeActividad").val() <= 0) {
        notificacion("danger", "El porcentaje de esta actividad no puede ser mayor al 100%, ó igual o inferior a 0", "alert");
        jQuery("#porcentajeActividad").focus();
        $("#btnRegistrarActividad").prop('disabled', false);
        return;
    }

    var fechaInicialPadre = jQuery("#fechaInicioPadre").val();
    var fechaFinalPadre = jQuery("#fechaFinPadre").val();
    var fechaInicialActividad = jQuery("#fechaInicio").val();
    var fechaFinalActividad = jQuery("#fechaFin").val();

    if (Date.parse(fechaInicialActividad) > Date.parse(fechaFinalActividad)) {
        notificacion("danger", "La fecha inicial de la actividad no puede ser mayor a la final", "alert");
        $("#btnRegistrarActividad").prop('disabled', false);
        return;
    }

    if (Date.parse(fechaInicialActividad) > Date.parse(fechaFinalPadre) || Date.parse(fechaInicialActividad) < Date.parse(fechaInicialPadre)) {
        console.log("entra al if de validacion fechaInicialActividad con fecha ini de padre");
        notificacion("danger", "La fecha inicial de la actividad es superior a la final del padre ó es inferior a la inicial del padre", "alert");
        $("#btnRegistrarActividad").prop('disabled', false);
        return;
    }

    ajaxCnk.consultarProyectoPorId(jQuery("#proyecto").val(), {
        callback: function(data) {
            if (data != null) {

                var fechaInicialProyecto = data.fechaInicial;
                var fechaFinalProyecto = data.fechaFinal;

                console.log("fechasProyecto ", fechaInicialProyecto, " - ", fechaFinalProyecto);
                console.log("fechasActividad ", fechaInicialActividad, " - ", fechaFinalActividad);

                if (Date.parse(fechaInicialActividad) > Date.parse(fechaFinalProyecto) || Date.parse(fechaInicialActividad) < Date.parse(fechaInicialProyecto)) {
                    console.log("entra al if de validacion fecha Inicial Actividad con fechas de proyecto");
                    notificacion("danger", "La fecha inicial de la actividad es superior a la final del proyecto ó es inferior a la inicial del proyecto", "alert");
                    $("#btnRegistrarActividad").prop('disabled', false);
                    return;
                }

                if (jQuery("#codigoPadre").val() == "") {
                    console.log("EntraCodigoPadreVacio");
                    ajaxCnk.sumaPorcentajeCodigoPadreIsNull(jQuery("#proyecto").val(), jQuery("#idActividad").val(), {
                        callback: function(data) {
                            console.log("sumaPorcentajeCodigoPadreIsNull", data);
                            if (data != null) {

                                console.log("dentraSumarPadrenull");

                                var sumaPadreIsNull = data.sumaPorcentajeNull;
                                var porcentajeAct = jQuery("#porcentajeActividad").val();
                                var sumaPadreIsNullTotal = parseFloat(sumaPadreIsNull) + parseFloat(porcentajeAct);

                                console.log("sumaPadreIsNullTotal ", sumaPadreIsNullTotal);

                                if (sumaPadreIsNullTotal > 100) {
                                    notificacion("danger", "El % digitado no cumple con el % faltante o supera el 100% del proyecto, modifiquelo por favor.", "alert");
                                    $("#btnRegistrarActividad").prop('disabled', false);
                                    jQuery("#porcentajeActividad").focus();

                                } else {

                                    ajaxCnk.editarActividad(actividad, {
                                        callback: function(data) {
                                            console.log("dataPadreVacio " + data);
                                            if (data !== null) {
                                                notificacion("success", "La actividad se ha editado con exito", "alert");
                                                desactivar();
                                                jQuery("#btnAbrirModal").click();

                                            } else {
                                                if (data === null) {
                                                    console.log("%%%%%%%%");
                                                    notificacion("warning", "la actividad Padre supera el limite de dias con relacion al Proyecto!. Por favor Modifiquela.", "alert");
                                                    $("#btnRegistrarActividad").prop('disabled', false);
                                                    jQuery("#duracionDias").focus();
                                                    return;
                                                }
                                            }
                                        },
                                        timeout: 20000
                                    });
                                }

                            } else {
                                notificacion("warning", "Esta en el else de ajax del porcentaje de los codigos padre null", "alert");
                            }
                        }
                    });
                } else {
                    console.log("ingresa por el padre lleno");
                    ajaxCnk.sumaPorcentajePorCodigoPadre(jQuery("#codigoPadre").val(), jQuery("#idActividad").val(), {
                        callback: function(data) {
                            console.log("sumaPorcentajePorCodigoPadre", data);
                            if (data != null) {

                                var sumaPadre = data.sumaPorcentajePadre;
                                var porcentajeAct = jQuery("#porcentajeActividad").val();
                                var sumaPadreTotal = parseFloat(sumaPadre) + parseFloat(porcentajeAct);

                                if (sumaPadreTotal > 100) {
                                    notificacion("danger", "El % digitado no cumple con el % faltante o supera el 100% de la actividad padre, modifiquelo por favor.", "alert");
                                    $("#btnRegistrarActividad").prop('disabled', false);
                                    jQuery("#porcentajeActividad").focus();
                                } else {

                                    ajaxCnk.editarActividad(actividad, {
                                        callback: function(data) {
                                            console.log("dataPadreLleno ", data);
                                            if (data !== null) {
                                                notificacion("success", "La actividad se ha editado con exito", "alert");
                                                desactivar();
                                                jQuery("#btnAbrirModal").click();
                                            } else {
                                                if (data === null) {
                                                    notificacion("warning", "la actividad supera el limite de dias con relacion a la actividad padre!. Por favor Modifiquela.", "alert");
                                                    $("#btnRegistrarActividad").prop('disabled', false);
                                                    jQuery("#duracionDias").focus();
                                                    return;
                                                }
                                            }
                                        },
                                        timeout: 20000
                                    });
                                }

                            } else {
                                notificacion("warning", "Esta en el else de ajax del porcentaje de los codigos padre null", "alert");
                            }
                        }
                    });
                }

            }
        },
        timeout: 20000
    });

}

function listarCodigoPadres() {
    console.log("idMisma ", idActividadMisma);
    ajaxCnk.listarActividadesPorIdProyectoAbiertaSinAvanceNiMisma(jQuery("#proyecto").val(), idActividadMisma, {
        callback: function(data) {
            if (data !== null) {
                jQuery("#codigoPadre").prop('disabled', false);
                dwr.util.removeAllOptions("codigoPadre");
                dwr.util.addOptions("codigoPadre", [{
                        id: '',
                        nombreActividad: 'Seleccione un Padre'
                    }], 'id', 'nombreActividad');
                dwr.util.addOptions("codigoPadre", data, 'id', 'nombreActividad');
                //jQuery("#proyecto").prop('disabled',true);
                //jQuery("#codigoPadre").val(data.codigoPadre)
            } else if (data === null) {
                jQuery("#codigoPadre").prop('disabled', true);
            }
        },
        timeout: 20000
    });

}

function cargarExcel() {
    //consultarUltimoRegistroActividad();
    //idProyecto = jQuery("#proyecto").val();
    console.log("%%%%%% " + idProyecto);
    cargarPagina('cargar-excel.jsp?idProyecto=' + idProyecto);
}

function cargarActividad() {
    console.log("cargarActiIdProy ", idProyecto);
    cargarPagina('registrar-actividad.jsp?idProyecto=' + idProyecto);
}

function desactivar() {
    jQuery("#proyecto").prop("disabled", true);
    jQuery("#estado").prop("disabled", true);
    jQuery("#nombreActividad").prop("disabled", true);
    jQuery("#porcentajeActividad").prop("disabled", true);
    jQuery("#fechaInicio").prop("disabled", true);
    //jQuery("#fechaFin").prop("disabled", true);
    jQuery("#duracionDias").prop("disabled", true);
    jQuery("#codigoPadre").prop("disabled", true);
}

function cerrarModal() {
    jQuery("#btnRegistrarActividad").hide();
}

function limpiarFormularioRegistro() {
    jQuery("#proyecto").val("");
    jQuery("#estado").val("");
    jQuery("#nombreActividad").val("");
    jQuery("#porcentajeActividad").val("");
    jQuery("#fechaInicio").val("");
    //jQuery("#fechaFin").val("");
    jQuery("#duracionDias").val("");
    jQuery("#codigoPadre").val("");
    jQuery("#fechaInicioPadre").val("");
    jQuery("#fechaFinPadre").val("");
}

function activar() {
    jQuery("#proyecto").prop("disabled", false);
    jQuery("#estado").prop("disabled", false);
    jQuery("#nombreActividad").prop("disabled", false);
    jQuery("#porcentajeActividad").prop("disabled", false);
    jQuery("#fechaInicio").prop("disabled", false);
    //jQuery("#fechaFin").prop("disabled", false);
    jQuery("#duracionDias").prop("disabled", false);
    jQuery("#codigoPadre").prop("disabled", false);
    jQuery("#btnRegistrarActividad").show();
    jQuery("#btnRegistrarActividad").prop('disabled', false);
}

function verRequisito() {
    jQuery("#tituloRegistrarRequisito").show();
    jQuery("#agregarRequisito").show();
    jQuery("#btnVolverRegistroActividad").prop('disabled', true);
    jQuery("#btnRegistrarActividad").prop('disabled', true);
    console.log("idProyecto ", jQuery("#proyecto").val());
    console.log("idActividad ", jQuery("#idActividad").val());

    ajaxCnk.listarActividadesDelProyecto(jQuery("#proyecto").val(), jQuery("#idActividad").val(), {
        callback: function(data) {
            console.log('muestra listado', data)
            if (data !== null) {
                dwr.util.removeAllOptions("listadoRequisitos");
                dwr.util.addOptions("listadoRequisitos", [{
                        id: '',
                        nombreActividad: 'Seleccione un Actividad'
                    }], 'id', 'nombreActividad');
                dwr.util.addOptions("listadoRequisitos", data, 'id', 'nombreActividad');
            } else {
                dwr.util.removeAllOptions("listadoRequisitos");
                dwr.util.addOptions("listadoRequisitos", [{
                        id: '',
                        nombreActividad: 'No hay Requisitos'
                    }], 'id', 'nombreActividad');
                dwr.util.addOptions("listadoRequisitos", data, 'id', 'nombreActividad');
                jQuery("#listadoRequisitos").prop('disabled', true);
                jQuery("#btnRegistrarRequisito").hide();
            }
        },
        timeout: 20000
    });

}

function registrarRequisito() {
    if (jQuery("#listadoRequisitos").val() !== "") {

        var requisito = {
            idActividad: jQuery("#idActividad").val(),
            requisito: jQuery("#listadoRequisitos").val()
        };

        //validaUsuario();
        console.log("cliente", requisito);

        ajaxCnk.registrarRequisito(requisito, {
            callback: function(data) {
                console.log("data ", data);
                if (data !== null) {
                    notificacion("success", "el requisito se ha registrado con exito", "alert");
                    //limpiarFormularioRegistro();
                    //activar();
                    //cargarPagina('gestion-actividad.jsp');
                    if ($('#listadoRequisitos').val() == '') {
                        var valor = $('#listadoRequisitos').val();
                        $("select").find("option[value='" + valor + "']").prop("hidden", false);
                    } else {
                        var valor = $('#listadoRequisitos').val();
                        $("select").find("option[value='" + valor + "']").prop("hidden", true);
                    }
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
                jQuery("#listadoRequisitos").val(" ");
            },
            timeout: 20000
        });
    } else {
        notificacion("warning", "Debe seleccionar un requisito", "alert");
        $("#listadoRequisitos").focus();
    }

    //limpiarFormularioRegistro();
    //cargarPagina('gestion-actividad.jsp');
}

function notificacion(tipo, msj, id) {
    $(".alert").alert('close');
    $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
    setTimeout('$(".alert").alert("close");', '10000');
    $('html, body').animate({
        scrollTop: $("#alert").offset().top
    }, 2000);
}

var nextFunction = null;
function validar(formulario) {
    nextFunction = 1;
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

            ejecutarPostValidate();

        }

    });
}

function ejecutarPostValidate() {
    if (nextFunction == 1) {
        registrarEditarActividad();
    }
    nextFunction = null;

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
    letras = "0,1,2,3,4,5,6,7,8,9,.";
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

