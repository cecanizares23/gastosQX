/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery(document).ready(function() {
    jQuery("#divBuscar").show();
    jQuery("#actividad").prop('disabled', true);
    jQuery("#divVerActividades").hide();
    jQuery("#divRegistrarAvance").hide();


    ajaxCnk.listarProyectoPorIdUsuario(idUsuarioSesion, {
        callback: function(data) {
            console.log("dataProy ", data);
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

function listarProyectoUsua() {
    ajaxCnk.listarProyectoPorIdUsuario(idUsuarioSesion, {
        callback: function(data) {
            console.log("dataProy ", data);
            if (data !== null) {

                dwr.util.removeAllOptions("proyecto");
                dwr.util.addOptions("proyecto", [{
                        id: '',
                        nombreProyecto: 'Seleccione un Proyecto'
                    }], 'id', 'proyecto');
                dwr.util.addOptions("proyecto", data, 'id', 'nombreProyecto');
                jQuery("#proyecto").val(jQuery("#listarProyecto").val());
            }
        },
        timeout: 20000
    });
}

function verPantallaAvance(id) {
    jQuery("#divVerActividades").hide();
    jQuery("#divBuscar").hide();
    jQuery("#divRegistrarAvance").show();
    jQuery("#actividad").val(id);
    llenarPorcentajeAvanzadoPorIdActividad(id);
}

var listadoActividades = [];
var mapaListadoActividades = [
    function(data) {
        if (data.avance == true) {
            return '<td><div class="text-center"><button class="btn-success id="btnRegistrarAvance" onclick="verPantallaAvance(' + data.id + ')" ><i class="el-icon-plus"></i></button></div></td>';
        }
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return '<td><div class="text-center">' + data.id + '</div></td>';
    },
    function(data) {
        return '<td><div class="text-center">' + data.porcentajeActividad + '%</div></td>';
    },
    function(data) {
        if (data.codigoPadre == null)
            return '<td><div class="text-center"> </div></td>';
        else
            return '<td><div class="text-center">' + data.codigoPadre + '</div></td>';
    },
    function(data) {
        if (data.porcentajeAvance == null) {
            return '<td><div class="text-center">0%</div></td>';
        } else {
            return '<td><div class="text-center">' + data.porcentajeAvance + '%</div></td>';
        }
    },
    function(data) {
        return '<td><div class="text-center">' + data.fechaInicio + '</div></td>';
    },
    function(data) {
        return '<td><div class="text-center">' + data.fechaFin + '</div></td>';
    },
    function(data) {
        if (data.avance == true)
            return '<td><button class="btn-primary status-active" title="Active" data-toggle="modal" onclick="consultarAvancePorIdActividad(' + data.id + ')" data-target="#modalLarge"><i class="el-icon-lines"></i></Button></td>';
    }    
];

var listadoActAvance = [];
function listarActividadesRegistrarAvance() {
    ajaxCnk.listarActividadesRegistrarAvance(jQuery("#proyecto").val(), {
        callback: function(data) {
            if (data !== null) {
                jQuery("#divVerActividades").show();
                listado = data;
                jQuery("#actividad").prop('disabled', false);
                dwr.util.removeAllOptions("actividad");
                dwr.util.addOptions("actividad", [{
                        id: '',
                        nombreActividad: 'Seleccione una Actividad'
                    }], 'id', 'nombreActividad');

                dwr.util.addOptions("actividad", data, 'id', 'nombreActividad');

            } else if (data === null) {
                jQuery("#codigoPadre").prop('disabled', true);
            }
        },
        timeout: 20000
    });

}

function listarActividadPorIdProyectoEstadoAbierta() {
    ajaxCnk.listarActividadPorIdProyectoEstadoAbierta(jQuery("#listarProyecto").val(), {
        callback: function(data) {
            if (data !== null) {
                jQuery("#divVerActividades").show();
                dwr.util.removeAllRows("listadoActividadesTabla");
                listadoActividades = data;
                dwr.util.addRows("listadoActividadesTabla", listadoActividades, mapaListadoActividades, {
                    escapeHtml: false
                });
                listarProyectoUsua();
                setTimeout('listarActividadesRegistrarAvance();', '500');
            } else {
                console.log("datalis1 ", data);
                jQuery("#divVerActividades").hide();
                notificacion("warning", "Este proyecto no tiene actividades", "alert");
            }
        },
        timeout: 20000
    });

}

function llenarPorcentajeAvanzadoPorIdActividad(id) {
    var idActividad;
    if (jQuery("#actividad").val() != id)
        idActividad = jQuery("#actividad").val();
    else
        idActividad = id;
    ajaxCnk.consultarActividadPorId(idActividad, {
        callback: function(data) {
            console.log("dataid ", data);
            if (data != null) {
                if (data.porcentajeAvance != null) {
                    jQuery("#porcentajeAvanzado").val(data.porcentajeAvance);
                } else {
                    jQuery("#porcentajeAvanzado").val("0");
                }

            }
        },
        timeout: 20000
    });
}

var listadoHistoricoAvance = [];
var mapaListadoHistoricoAvance = [
    function(data) {
        return data.contador;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return data.fechaDigitacion;
    },
    function(data) {
        return data.fechaInicioActividad;
    },
    function(data) {
        return data.fechaFinActividad;
    },
    function(data) {
        return '<td>' + data.valorAvance + '%</td>';
    },
    function(data) {
        return data.porcentajeActividad;
    },
    function(data) {
        return data.descripcionAvance;
    },
    function(data) {
        return data.unidad;
    },
    function(data) {
        return data.cantidadAproximada;
    },
    function(data) {
        return data.contratista;
    },
    function(data) {
        return data.modificacion;
    },
    function(data) {
        return data.problemasPresentados;
    }

];

function consultarAvancePorIdActividad(id) {
    var idActividad = id;
    console.log("idPadre ", idActividad);
    ajaxCnk.consultarAvancePorIdActividad(idActividad, {
        callback: function(data) {
            console.log("data avance ", data);
            if (data !== null) {
                if (data.length == 0) {
                    //notificacion("warning","Esta actividad no tiene avances Registrados","alert");
                    jQuery("#siAvance").hide();
                    jQuery("#noAvance").show();
                    return;
                }
                jQuery("#siAvance").show();
                jQuery("#noAvance").hide();
                dwr.util.removeAllRows("listadoHistoricoAvance");
                listadoHistoricoAvance = data;

                dwr.util.addRows("listadoHistoricoAvance", listadoHistoricoAvance, mapaListadoHistoricoAvance, {
                    escapeHtml: false
                });
                console.log("nombreAvance", data[0].nombreActividad);
                jQuery("#nombreAvance").text(data[0].nombreActividad);

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

function registrarAvanceActividad1() {

    var files = document.getElementById("file").files;

    for (var i = 0; i < files.length; i++) {
        var img;
        img = new FormData();
        img.append('file', jQuery('#file')[0].files[i]);

        jQuery.ajax({
            url: 'Uploader',
            data: img,
            processData: false,
            contentType: false,
            async: false,
            type: 'Post',
            success: function(data) {

            }
        }).done(function(html) {
            //location.reload();

        });
    }
}

function registrarAvanceActividad() {
    jQuery("#btnRegistrarAvance").prop('disabled', true);
    $('#spinner').fadeIn();
    var avanceActividad = {
        idActividad: jQuery("#actividad").val(),
        valorAvance: jQuery("#porcentajeAvance").val(),
        descripcionAvance: jQuery("#descripcionAvance").val(),
        unidad: jQuery("#unidad").val(),
        fechaDigitacion: hoy,
        cantidadAproximada: jQuery("#cantidadAproximada").val(),
        contratista: jQuery("#contratista").val(),
        modificacion: jQuery("#modificacion").val(),
        problemasPresentados: jQuery("#problemasPresentados").val()

    };

    var idActividad = jQuery("#actividad").val();

    if (jQuery("#porcentajeAvance").val() > 100 || jQuery("#porcentajeAvance").val() <= 0) {
        notificacion("danger", "El avance de la actividad no puede ser mayor a 100 o menor ó igual a 0", "alert");
        $('#spinner').fadeOut();
        jQuery("#btnRegistrarAvance").prop('disabled', false);
        return;
    }

    var files = document.getElementById("file").files;
    console.log("files ", files);



    var totalTamanios = 0;
    var totalTamaniosKb = 0;

    for (var i = 0; i < files.length; i++) {
        var tamanios = jQuery("#file")[0].files[i].size;
        console.log("tamanios ", tamanios);
        totalTamanios = (parseFloat(totalTamanios) + parseFloat(tamanios));
        console.log("totalTamanios ", totalTamanios);
        totalTamaniosKb = totalTamanios / 1024;
        console.log("totalTamaniosKb ", totalTamaniosKb);
    }
    var cincoMB = 5120;
    if (parseFloat(totalTamaniosKb) > parseFloat(cincoMB)) {
        notificacion("warning", "La sumatoria del tamaño de los archivos seleccionados supera la permitididad de 5MB, por favor dismunuya el tamaño de los archivos!.", "alert");
        jQuery("#btnRegistrarAvance").prop('disabled', false);
        $('#spinner').fadeOut();
        return;
    }

    for (var i = 0; i < files.length; i++) {
        if (jQuery('#file')[0].files[i].type === "image/jpeg" || jQuery('#file')[0].files[i].type === "image/png" ||
                jQuery('#file')[0].files[i].type === "video/mpg" || jQuery('#file')[0].files[i].type === "video/mpeg" ||
                jQuery('#file')[0].files[i].type === "video/mp4") {
            console.log("tipo de archivo permitido");
            console.log("tipo de archivo " + jQuery('#file')[0].files[i].type);
        } else {
            notificacion("danger", "Tipo de archivo no permitido", 'alert');
            jQuery("#btnRegistrarAvance").prop('disabled', false);
            $('#spinner').fadeOut();
            return;
        }
    }

    if ($("#file").val() != "") {
        notificacion("warning", "El cargue de los archivos puede durar un minuto.", "alert");
    }
    
    ajaxCnk.registrarPorcenjeAvanceActividad(avanceActividad, {
        callback: function(data) {
            if (data.condicion == 1) {
                notificacion("info", data.mensaje, "alert");
                return;
            }
            if (data.condicion == 2) {
                console.log("!!!!!!!!! ", $("#file").val());

                for (var i = 0; i < files.length; i++) {
                    var img;
                    img = new FormData();
                    img.append('file', jQuery('#file')[0].files[i]);
                    jQuery.ajax({
                        url: 'Uploader',
                        data: img,
                        processData: false,
                        contentType: false,
                        async: false,
                        type: 'Post',
                        success: function(data) {
                        }
                    }).done(function(html) {
                        //location.reload();
                    });
                }

                setTimeout('notificacion("success", "el avance se ha registrado con éxito", "alert");', '1000');
                ajaxCnk.consultarActividadPorId(jQuery("#actividad").val(), {
                    callback: function(data) {
                        if (data != null) {
                            console.log("entra al suma actualizar estado");
                            var porcentajeAvance = data.porcentajeAvance;
                            parseFloat(porcentajeAvance);
                            console.log("##############", porcentajeAvance);
                            if (porcentajeAvance >= 100) {
                                ajaxCnk.actualizarEstadoActividad(idActividad, {
                                    callback: function(data) {
                                        if (data !== null) {
                                            console.log("se actualizo estado actividad");
                                            notificacion("success", "Se ha completado el 100% de esta actividad. ESTADO CERRADA", "alert");
                                        }
                                    },
                                    timeout: 20000
                                });
                            }
                        }
                    },
                    timeout: 20000
                });
                limpiarFormularioRegistro();
            } else if (data.condicion == 3) {
                notificacion("info", data.mensaje, "alert");
                //notificacion("danger", "se ha generado un error en la consulta", "alert");
            }
            $('#spinner').fadeOut();
            jQuery("#btnRegistrarAvance").prop('disabled', false);
        },
        timeout: 120000
    });
    //desactivar();

}

function volver() {

    limpiarFormularioRegistro();
    listarActividadPorIdProyectoEstadoAbierta();
    jQuery("#divRegistrarAvance").hide();
    jQuery("#divBuscar").show();
}

function desactivar() {
    jQuery("#proyecto").prop("disabled", true);
    jQuery("#actividad").prop("disabled", true);
    jQuery("#porcentajeAvance").prop("disabled", true);
    jQuery("#descripcionAvance").prop("disabled", true);
    jQuery("#unidad").prop("disabled", true);
    jQuery("#cantidadAproximada").prop("disabled", true);
    jQuery("#contratista").prop("disabled", true);
    jQuery("#modificacion").prop("disabled", true);
    jQuery("#problemasPresentados").prop("disabled", true);
    jQuery("#btnRegistrarAvance").prop("disabled", true);
}

function limpiarFormularioRegistro() {
    //jQuery("#proyecto").val("");
    jQuery("#actividad").val("");
    jQuery("#porcentajeAvance").val("");
    jQuery("#descripcionAvance").val("");
    jQuery("#unidad").val("");
    jQuery("#cantidadAproximada").val("");
    jQuery("#contratista").val("");
    jQuery("#modificacion").val("");
    jQuery("#problemasPresentados").val("");
    jQuery("#file").val("");
    jQuery("#porcentajeAvanzado").val("");
    jQuery("#btnRegistrarAvance").prop('disabled', false);
}

function activar() {
    jQuery("#proyecto").prop("disabled", false);
    jQuery("#actividad").prop("disabled", false);
    jQuery("#porcentajeAvance").prop("disabled", false);
    jQuery("#descripcionAvance").prop("disabled", false);
    jQuery("#unidad").prop("disabled", false);
    jQuery("#cantidadAproximada").prop("disabled", false);
    jQuery("#contratista").prop("disabled", false);
    jQuery("#modificacion").prop("disabled", false);
    jQuery("#problemasPresentados").prop("disabled", false);
    jQuery("#btnRegistrarAvance").prop("disabled", false);
}

function notificacion(tipo, msj, id) {
    $(".alert").alert('close');
    $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
    setTimeout('$(".alert").alert("close");', '7000');
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

            registrarAvanceActividad();
            //jQuery("#btnAbrirModal").click();

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

function soloLetrasCaracteresEspeciales(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = " áéíóúabcdefghijklmnñopqrstuvwxyz!#$%&/()=?¡¿+*,.-_;";
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
