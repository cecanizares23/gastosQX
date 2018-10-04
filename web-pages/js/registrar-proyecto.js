/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//globales
var muni;
var depa;
var interventor = 2;

jQuery(document).ready(function() {

    jQuery("#divIdCliente").hide();
    jQuery("#divIdInterventor").hide();
    jQuery("#btnGuardarProyecto").prop("disabled", false);
    jQuery("#divIdProyecto").hide();
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
    //$("#presupuesto").mask("####.####");

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
    console.log("idInterventor ", idInterventor);
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

var planMA;
var planSI;
var sabados;
var domingos;
var festivos;

function registrarProyecto() {
    jQuery("#btnGuardarProyecto").prop("disabled", true);
    $('#spinner').fadeIn();
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
        //fechaFinal: jQuery("#fechaFinProy").val(),
        diasDuracionProyecto: jQuery("#diasDuracion").val(),
        celularReporteDiario: jQuery("#celularReporteDiarioUno").text(),
        celularReporteSemanal: jQuery("#celularReporteSemanalUno").text(),
        nombreFallaReporte: jQuery("#nombreFalla").val(),
        celularFallaReporte: jQuery("#celularFalla").val(),
        seleccionarCargueActividades: jQuery("#seleccionarRegistroActividades").val(),
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
        jQuery("#btnGuardarProyecto").prop("disabled", false);
        $('#spinner').fadeOut();
        return;
    }
    
    if($("#correoReporteDiarioUno").text() == "" && $("#correoReporteDiarioDos").text() == "" && $("#correoReporteDiarioTres").text() == ""){
        notificacion("danger","Debe ingresar al menos un correo de destino para el reporte diario","alert");
        jQuery("#btnGuardarProyecto").prop("disabled", false);
        $('#spinner').fadeOut();
        return;
    }
    
    if($("#correoReporteSemanalUno").text() == "" && $("#correoReporteSemanalDos").text() == "" && $("#correoReporteSemanalTres").text() == ""){
        notificacion("danger","Debe ingresar al menos un correo de destino para el reporte semanal","alert");
        jQuery("#btnGuardarProyecto").prop("disabled", false);
        $('#spinner').fadeOut();
        return;
    }
    
    if($("#celularReporteDiarioUno").text() == "" && $("#celularReporteDiarioDos").text() == "" && $("#celularReporteDiarioTres").text() == ""){
        notificacion("danger","Debe ingresar al menos un numero de celular destino para el reporte diario","alert");
        jQuery("#btnGuardarProyecto").prop("disabled", false);
        $('#spinner').fadeOut();
        return;
    }
    
    if($("#celularReporteSemanalUno").text() == "" && $("#celularReporteSemanalDos").text() == "" && $("#celularReporteSemanalTres").text() == ""){
        notificacion("danger","Debe ingresar al menos un numero de celular destino para el reporte diario","alert");
        jQuery("#btnGuardarProyecto").prop("disabled", false);
        $('#spinner').fadeOut();
        return;
    }

    //validaUsuario();
    console.log("cliente", proyecto);

    notificacion("warning", "el registro puede tardar algunos minutos", "alert");
    ajaxCnk.registrarProyecto(proyecto, {
        callback: function(data) {
            console.log("------_______----## ", data);
            if (data !== null) {
                //setTimeout('notificacion("success", "el proyecto se ha registrado con éxito", "alert");', '100');
                notificacion("success", "el proyecto se ha registrado con éxito", "alert");
                jQuery("#idProyecto").val(data);
                seleccionarRegistrarActividad();
                limpiarFormularioRegistro();
                jQuery("#btnGuardarProyecto").prop("disabled", false);
                
            } else {
                setTimeout('notificacion("danger", "se ha generado un error", "alert");', '100');
            }
             $('#spinner').fadeOut();
        },
        timeout: 120000
    });
    //desactivar();
}

var idProyecto;
function seleccionarRegistrarActividad() {

    console.log("dentra al seleccionar cargue", jQuery("#seleccionarRegistroActividades").val());

    if (jQuery("#seleccionarRegistroActividades").val() == 0) {
        console.log("igual a 0", jQuery("#seleccionarRegistroActividades").val());
        setTimeout('cargarPagina("registrar-actividad.jsp");', '5000');
        //cargarPagina("registrar-actividad.jsp");

    } else if (jQuery("#seleccionarRegistroActividades").val() == 1) {
        idProyecto = jQuery("#idProyecto").val();
        console.log("igual a 1", jQuery("#seleccionarRegistroActividades").val());
        console.log("##33333 ", jQuery("#idProyecto").val());
        setTimeout('cargarPagina("cargar-excel.jsp?idProyecto=" + idProyecto)', '5000');
        setTimeout('consultarUltimoRegistroActividad();', '5000');
        //cargarPagina("cargar-excel.jsp");
    }
}

function consultarUltimoRegistroActividad() {
    console.log("dentraUltimo");
    ajaxCnk.ConsultarUltimoRegistro({
        callback: function(data) {
            if (data !== null) {
                console.log("dentraUltimo2 ", data);
                var ultimo = data.id;
                var siguiente = parseInt(ultimo) + 1;
                console.log("$$$$$", ultimo);
                notificacion("info", "El ultimo código de Actividad Generado es " + ultimo + " los códigos de las actividades en el archivo deben continuar a partir del siguiente: " + siguiente, "alert");
            }
        },
        timeout: 20000
    });
}

function volver() {
    limpiarFormularioRegistro();
    activar();
    cargarPagina("gestion-proyecto.jsp");
}

function desactivar() {
    jQuery("#nombreProyecto").prop("disabled", true);
    jQuery("#presupuesto").prop("disabled", true);
    jQuery("#licenciaContruccion").prop("disabled", true);
    jQuery("#PlanSI").prop("disabled", true);
    jQuery("#planMA").prop("disabled", true);
    jQuery("#fechaIniProy").prop("disabled", true);
    jQuery("#fechaFinProy").prop("disabled", true);
    jQuery("#departamento").prop("disabled", true);
    jQuery("#municipio").prop("disabled", true);
    jQuery("#destinoRepDiario").prop("disabled", true);
    jQuery("#destinoRepSemanal").prop("disabled", true);
    jQuery("#celularRepDiario").prop("disabled", true);
    jQuery("#celularRepSemanal").prop("disabled", true);
    jQuery("#nombreFalla").prop("disabled", true);
    jQuery("#celularFalla").prop("disabled", true);
    jQuery("#btnGuardarProyecto").prop("disabled", true);
    jQuery("#seleccionarRegistroActividades").prop("disabled", true);
}

function activar() {
    jQuery("#nombreProyecto").prop("disabled", false);
    jQuery("#presupuesto").prop("disabled", false);
    jQuery("#licenciaContruccion").prop("disabled", false);
    jQuery("#PlanSI").prop("disabled", false);
    jQuery("#planMA").prop("disabled", false);
    jQuery("#fechaIniProy").prop("disabled", false);
    jQuery("#fechaFinProy").prop("disabled", false);
    jQuery("#departamento").prop("disabled", false);
    jQuery("#municipio").prop("disabled", false);
    jQuery("#destinoRepDiario").prop("disabled", false);
    jQuery("#destinoRepSemanal").prop("disabled", false);
    jQuery("#celularRepDiario").prop("disabled", false);
    jQuery("#celularRepSemanal").prop("disabled", false);
    jQuery("#nombreFalla").prop("disabled", false);
    jQuery("#celularFalla").prop("disabled", false);
    jQuery("#btnGuardarProyecto").prop("disabled", false);
    jQuery("#seleccionarRegistroActividades").prop("disabled", false);
}

function limpiarFormularioRegistro() {
    jQuery("#nit").val("");
    jQuery("#RazonSocial").val("");
    jQuery("#nombreProyecto").val("");
    jQuery("#presupuesto").val("");
    jQuery("#PlanSI").val("");
    jQuery("#planMA").val("");
    jQuery("#fechaIniProy").val("");
    jQuery("#fechaFinProy").val("");
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
    jQuery("#seleccionarRegistroActividades").val("");
    jQuery("#licenciaContruccion").val("");
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

            registrarProyecto();

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

function soloNumerosConGuion(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = "0,1,2,3,4,5,6,7,8,9,-";
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
