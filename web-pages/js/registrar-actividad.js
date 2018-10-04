/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//globales
var idProyecto = jQuery("#proyecto").val();

//jQuery(document).bind("cut copy paste", function(e) {
//  e.preventDefault();
// });

jQuery("jbox_n_default").hide();

jQuery(document).ready(function() {

    jQuery("#tituloRegistrarRequisito").hide();
    jQuery("#agregarRequisito").hide();
    jQuery("#divIdActividad").hide();
    jQuery("#estado").val("");
    jQuery("#btnLimpiar").hide();
    jQuery("#divFechaIniPadre").hide();
    jQuery("#divFechaFinPadre").hide();

    ajaxCnk.listarProyecto({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("proyecto");
                dwr.util.addOptions("proyecto", [{
                        id: '',
                        nombreProyecto: 'Seleccione un Proyecto'
                    }], 'id', 'nombreProyecto');
                dwr.util.addOptions("proyecto", data, 'id', 'nombreProyecto');
                var idProyecto = idProyectoJava;
                jQuery("#proyecto").val(parseInt(idProyecto)).trigger("change");
                console.log("id222222222 ", parseInt(idProyecto));
                setTimeout('listarCodigoPadres();', '500');

            }
        },
        timeout: 20000
    });
});

function listarCodigoPadres() {

    ajaxCnk.listarActividadesPorIdProyectoAbiertaSinAvance(jQuery("#proyecto").val(), {
        callback: function(data) {
            if (data !== null) {
                jQuery("#listadoRequisitos").prop('disabled', false);
                jQuery("#codigoPadre").prop('disabled', false);
                dwr.util.removeAllOptions("codigoPadre");
                dwr.util.addOptions("codigoPadre", [{
                        id: '',
                        nombreActividad: 'Seleccione un Padre'
                    }], 'id', 'nombreActividad');
                dwr.util.addOptions("codigoPadre", data, 'id', 'nombreActividad');
                //jQuery("#proyecto").prop('disabled',true);                
            } else if (data === null) {
                jQuery("#codigoPadre").prop('disabled', true);
            }
            listarRequisitoPadres();
        },
        timeout: 20000
    });

}

function listarRequisitoPadres() {
    ajaxCnk.listarActividadesParaRequisitoPadres($("#proyecto").val(), {
        callback: function(data) {
            if (data != null) {
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
            }
        },
        timeout: 20000
    });
}

function listarRequisitoHijas() {
    ajaxCnk.listarActividadesParaRequisitoHija($("#proyecto").val(), $("#codigoPadre").val(), {
        callback: function(data) {
            if (data != null) {
                dwr.util.removeAllOptions("listadoRequisitos");
                dwr.util.addOptions("listadoRequisitos", [{
                        id: '',
                        nombreActividad: 'Seleccione un Actividad'
                    }], 'id', 'nombreActividad');
                dwr.util.addOptions("listadoRequisitos", data, 'id', 'nombreActividad');
                $("#fechaInicio").val("");
            } else {
                dwr.util.removeAllOptions("listadoRequisitos");
                dwr.util.addOptions("listadoRequisitos", [{
                        id: '',
                        nombreActividad: 'No hay Requisitos'
                    }], 'id', 'nombreActividad');
                dwr.util.addOptions("listadoRequisitos", data, 'id', 'nombreActividad');
                jQuery("#listadoRequisitos").prop('disabled', true);
                $("#fechaInicio").val("");
            }
        },
        timeout: 20000
    });
}

function llenarFechaInicio(id){
    console.log(id);
    ajaxCnk.consultarActividadPorId(id,{
        callback: function(data){
            console.log(data);
            if(data != null){
                jQuery("#fechaInicio").val(data.fechaIniNuevaActividad);
            }
        },
        timeout: 20000
    });
}

function llenarFechasCodigoPadre() {
    ajaxCnk.consultarActividadPorCodigoPadre(jQuery("#codigoPadre").val(), {
        callback: function(data) {
            if (data !== null) {
                jQuery("#fechaInicioPadre").val(data.fechaInicio);
                jQuery("#fechaFinPadre").val(data.fechaFin);
                jQuery("#divFechaIniPadre").show();
                jQuery("#divFechaFinPadre").show();
                listarRequisitoHijas();
            } else {
                jQuery("#fechaInicioPadre").val("");
                jQuery("#fechaFinPadre").val("");
                jQuery("#divFechaIniPadre").hide();
                jQuery("#divFechaFinPadre").hide();
            }
        },
        timeout: 20000
    });
}

function registrarActividad() {
    $("#btnRegistrarActividad").prop('disabled', true);
    var actividad = {
        idProyecto: jQuery("#proyecto").val(),
        nombreActividad: jQuery("#nombreActividad").val(),
        porcentajeActividad: jQuery("#porcentajeActividad").val(),
        fechaInicio: jQuery("#fechaInicio").val(),
        //fechaFin: jQuery("#fechaFin").val(),
        duracionDias: jQuery("#duracionDias").val(),
        codigoPadre: jQuery("#codigoPadre").val(),
        requisito: jQuery("#listadoRequisitos").val()

    };

    if (jQuery("#porcentajeActividad").val() > 100) {
        notificacion("danger", "El porcentaje de esta actividad no puede ser mayor al 100%, ó igual o inferior a 0", "alert");
        jQuery("#porcentajeActividad").focus();
        $("#btnRegistrarActividad").prop('disabled', false);
        return;
    }

    if (jQuery("#porcentajeActividad").val() <= 0) {
        notificacion("danger", "El porcentaje de esta actividad no puede ser menor o igual a 0%", "alert");
        jQuery("#porcentajeActividad").focus();
        $("#btnRegistrarActividad").prop('disabled', false);
        return;
    }

    var fechaInicialPadre = jQuery("#fechaInicioPadre").val();
    var fechaFinalPadre = jQuery("#fechaFinPadre").val();
    var fechaInicialActividad = jQuery("#fechaInicio").val();

    console.log("fechaInicialActividad111 ", fechaInicialActividad);
    console.log("fechaInicialPadre111 ", fechaInicialPadre);
    console.log("fechaInicialActividad111 ", fechaInicialActividad);

    console.log("fechaInicialActividad ", Date.parse(fechaInicialActividad));
    console.log("fechaInicialPadre ", Date.parse(fechaInicialPadre));
    console.log("fechaInicialActividad ", Date.parse(fechaInicialActividad));

    if (Date.parse(fechaInicialActividad) < Date.parse(fechaInicialPadre)) {
        notificacion("danger", "La fecha inicial de la actividad no puede ser menor a la inicial de la actividad Padre", "alert");
        $("#btnRegistrarActividad").prop('disabled', false);
        return;
    }

    ajaxCnk.consultarProyectoPorId(jQuery("#proyecto").val(), {
        callback: function(data) {
            if (data != null) {

                var fechaInicialProyecto = data.fechaInicial;
                var fechaFinalProyecto = data.fechaFinal;

                console.log("fechasProyecto ", fechaInicialProyecto, " - ", fechaFinalProyecto);
                console.log("fechasActividad ", fechaInicialActividad);

                if (Date.parse(fechaInicialActividad) > Date.parse(fechaFinalProyecto)) {
                    console.log("entra al if de validacion fecha Inicial Actividad con fechas de proyecto");
                    notificacion("danger", "La fecha inicial de la actividad es superior a la final del proyecto", "alert");
                    $("#btnRegistrarActividad").prop('disabled', false);
                    return;
                }

                if (Date.parse(fechaInicialActividad) < Date.parse(fechaInicialProyecto)) {
                    console.log("entra al if de validacion fecha Inicial Actividad con fechas de proyecto");
                    notificacion("danger", "La fecha inicial de la actividad es inferior a la inicial del proyecto", "alert");
                    $("#btnRegistrarActividad").prop('disabled', false);
                    return;
                }

                if (jQuery("#codigoPadre").val() == "") {
                    ajaxCnk.sumaPorcentajeCodigoPadreIsNullRegistrar(jQuery("#proyecto").val(), {
                        callback: function(data) {
                            console.log("dentraSumatoria ", data, " ", jQuery("#proyecto").val());
                            if (data != null) {

                                console.log("dentraSumarPadrenull");

                                var sumaPadreIsNull = data.sumaPorcentajeNull;
                                var porcentajeAct = jQuery("#porcentajeActividad").val();
                                var sumaPadreIsNullTotal = parseFloat(sumaPadreIsNull) + parseFloat(porcentajeAct);

                                console.log("sumaPadreIsNullTotal ", sumaPadreIsNullTotal);

                                if (sumaPadreIsNullTotal > 100) {
                                    notificacion("danger", "El % digitado supera  el 100%, si desea que esta actividad Padre tenga este porcentaje modifique el % de las padres anteriores.", "alert");
                                    jQuery("#porcentajeActividad").focus();
                                    $("#btnRegistrarActividad").prop('disabled', false);
                                    console.log("sumapadrenull mayor a 100");
                                } else {

                                    //validaUsuario();
                                    console.log("registrarrrrrrrrr");
                                    jQuery("#btnLimpiar").show();
                                    ajaxCnk.registrarActividad(actividad, {
                                        callback: function(data) {
                                            if (data !== null) {
                                                console.log("data---___--- ", data);

                                                if (data === "") {
                                                    console.log("%%%%%%%%");
                                                    notificacion("warning", "la actividad supera el limite de dias con relacion al Proyecto!. Por favor Modifiquela.", "alert");
                                                    $("#btnRegistrarActividad").prop('disabled', false);
                                                    jQuery("#duracionDias").focus();
                                                    return;
                                                }

                                                notificacion("success", "la actividad se ha registrado con éxito", "alert");
                                                jQuery("#idActividad").val(data);

                                                desactivar();
                                                jQuery("#btnAbrirModal").click();

                                            } else {
                                                notificacion("danger", "se ha generado un error", "alert");
                                            }
                                        },
                                        timeout: 20000
                                    });
                                    //desactivar();
                                    //jQuery("#btnAbrirModal").click();
                                }

                            } else {
                                notificacion("warning", "Esta en el else de ajax del porcentaje de los códigos padre null", "alert");
                            }
                        }
                    });
                } else {
                    console.log("idActividad ", $("#idActividad").val());
                    ajaxCnk.sumaPorcentajePorCodigoPadre(jQuery("#codigoPadre").val(), jQuery("#idActividad").val(), {
                        callback: function(data) {
                            if (data != null) {

                                var sumaPadre = data.sumaPorcentajePadre;
                                console.log("sumaPadre " + sumaPadre);
                                var porcentajeAct = jQuery("#porcentajeActividad").val();
                                console.log("porcentajeAct " + porcentajeAct);
                                var sumaPadreTotal = parseFloat(sumaPadre) + parseFloat(porcentajeAct);
                                console.log("sumaPadreTotal " + sumaPadreTotal);


                                if (sumaPadreTotal > 100) {
                                    notificacion("danger", "El % digitado no cumple con el % faltante o supera el 100% de la actividad padre, modifiquelo por favor.", "alert");
                                    jQuery("#porcentajeActividad").focus();
                                    $("#btnRegistrarActividad").prop('disabled', false);
                                } else {

                                    //validaUsuario();                                
                                    $("#btnRegistrarActividad").prop('disabled', true);
                                    jQuery("#btnLimpiar").show();
                                    ajaxCnk.registrarActividad(actividad, {
                                        callback: function(data) {
                                            if (data !== null) {
                                                console.log("data---___---1 ", data);
                                                if (data === "") {
                                                    console.log("%%%%%%%%");
                                                    notificacion("warning", "la actividad supera el limite de dias con relacion a la actividad padre!. Por favor Modifiquela.", "alert");
                                                    jQuery("#duracionDias").focus();
                                                    $("#btnRegistrarActividad").prop('disabled', false);
                                                    return;
                                                }
                                                notificacion("success", "la actividad se ha registrado con éxito", "alert");
                                                jQuery("#idActividad").val(data);

                                                desactivar();
                                                jQuery("#btnAbrirModal").click();

                                            } else {
                                                notificacion("danger", "se ha generado un error", "alert");
                                            }
                                        },
                                        timeout: 20000
                                    });

                                }

                            } else {
                                notificacion("warning", "Esta en el else de ajax del porcentaje de los códigos padre null", "alert");
                            }
                        }
                    });
                }

            }
        },
        timeout: 20000
    });

}

function registrarRequisito() {
    console.log("###### $$$$ ", jQuery("#listadoRequisitos").val());
    if (jQuery("#listadoRequisitos").val() !== "") {

        var requisito = {
            idActividad: jQuery("#idActividad").val(),
            requisito: jQuery("#listadoRequisitos").val()
        };

        //validaUsuario();
        console.log("cliente", requisito);

        ajaxCnk.registrarRequisito(requisito, {
            callback: function(data) {
                console.log("datos de requisito ", data);
                if (data !== null) {
                    notificacion("success", "el requisito se ha registrado con éxito", "alert");
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
                jQuery("#listadoRequisitos").val("");
            },
            timeout: 20000
        });
    } else {
        notificacion("danger", "Debe seleccionar un requisito", "alert");
        $("#listadoRequisitos").focus();
    }

    //limpiarFormularioRegistro();
    //cargarPagina('gestion-actividad.jsp');
}

var idProyecto;
function volver() {
    idProyecto = $("#proyecto").val();
    limpiarFormularioRegistro();
    cargarPagina('gestion-actividad.jsp?idProyecto=' + idProyecto);
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
    jQuery("#btnLimpiar").show();
}

function limpiarFormularioRegistro() {
    //jQuery("#proyecto").val("");
    jQuery("#estado").val("");
    jQuery("#nombreActividad").val("");
    jQuery("#porcentajeActividad").val("");
    jQuery("#fechaInicio").val("");
    //jQuery("#fechaFin").val("");
    jQuery("#duracionDias").val("");
    jQuery("#codigoPadre").val("");
    jQuery("#fechaInicioPadre").val("");
    jQuery("#fechaFinPadre").val("");
    $("#btnRegistrarActividad").prop('disabled', false);
}

function limpiar() {
    limpiarFormularioRegistro();
    activar();
    jQuery("#btnLimpiar").hide();
    jQuery("#btnRegistrarActividad").show();
    cargarPagina('registrar-actividad.jsp');
}

function limpiarEnRequisito() {
    limpiarFormularioRegistro();
    activar();
    jQuery("#tituloRegistrarRequisito").hide();
    jQuery("#agregarRequisito").hide();
    jQuery("#btnVolverRegistroActividad").prop('disabled', false);
    jQuery("#btnRegistrarActividad").prop('disabled', false);
    cargarPagina('registrar-actividad.jsp');
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
            console.log('muestra listado', data);
            console.log("idproyacto ", jQuery("#proyecto").val(), "idActivdad ", jQuery("#idActividad").val());
            if (data !== null) {
                jQuery("#listadoRequisitos").prop('disabled', false);
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
                jQuery("#btnRegistrarRequisito").show();
            }
        },
        timeout: 20000
    });

}

var listadoRequisitos = [];
function listarRequisitosPorIdActividad() {
    ajaxCnk.listadoRequisitosPorIdActividad(jQuery("#idActividad").val(), {
        callback: function(data) {
            if (data != null) {
                listadoRequisitos = data;
                for (i = 0; i < listadoRequisitos.le; i++) {

                }
            }
        },
        timeout: 20000
    });
}

function notificacion(tipo, msj, id) {
    $(".alert").alert('close');
    $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
    setTimeout('$(".alert").alert("close");', '8000');
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
        registrarActividad();
    } else if (nextFunction == 2) {
        registrarRequisito();
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

function soloLetras11(e) {
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

$(function() {
    // jBox
    yukon_jBox.p_components_notifications_popups('########');
})
