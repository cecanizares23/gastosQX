/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//globales
var muni;
var depa;
var idCliente = "";

jQuery(document).ready(function() {

    jQuery("#mensajeDocumento").hide();
    jQuery("#mensajeNit").hide();

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

    ajaxCnk.listarTipoDocumento({
        callback: function(data) {
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

    ajaxCnk.listarFormaJuridica({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("formaJuridica");
                dwr.util.addOptions("formaJuridica", [{
                        id: '',
                        descripcion: 'Seleccione la forma juridica'
                    }], 'id', 'descripcion');
                dwr.util.addOptions("formaJuridica", data, 'id', 'descripcion');
                //jQuery('#tipoDocumento option[value=1]').hide();
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarTipoEmpresa({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("naturalezaEmpresa");
                dwr.util.addOptions("naturalezaEmpresa", [{
                        id: '',
                        descripcion: 'Seleccione la Naturaleza'
                    }], 'id', 'descripcion');
                dwr.util.addOptions("naturalezaEmpresa", data, 'id', 'descripcion');
                //jQuery('#tipoDocumento option[value=1]').hide();
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarRegimen({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("regimen");
                dwr.util.addOptions("regimen", [{
                        id: '',
                        descripcion: 'Seleccione Regimen'
                    }], 'id', 'descripcion');
                dwr.util.addOptions("regimen", data, 'id', 'descripcion');
                //jQuery('#tipoDocumento option[value=1]').hide();
            }
        },
        timeout: 20000
    });

});

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

function registrarCliente() {
    $("#btnRegistrar").prop('disabled', true);
    var cliente = {
        nombreCliente: jQuery("#razonSocial").val(),
        idTipoDocumento: jQuery("#tipoDocumento").val(),
        documentoCliente: jQuery("#documento").val(),
        correoCliente: jQuery("#correo").val(),
        representateLegal: jQuery("#representanteLegal").val(),
        idRegimen: jQuery("#regimen").val(),
        idTipoEmpresa: jQuery("#naturalezaEmpresa").val(),
        idDepartamento: jQuery("#departamento").val(),
        idMunicipio: jQuery("#municipio").val(),
        idFormaJuridica: jQuery("#formaJuridica").val(),
        celularCliente: jQuery("#celular").val(),
        direccionCliente: jQuery("#direccion").val(),
        telefonoCliente: jQuery("#telefonoFijo").val(),
    };

    //validaUsuario();
    console.log("cliente", cliente);

    ajaxCnk.registrarCliente(cliente, {
        callback: function(data) {
            console.log("data ", data);
            if (data !== null) {

                if (jQuery("#file").val() == "") {
                    notificacion("success", "el Cliente se ha registrado con éxito", "alert");
                    limpiarFormularioRegistro();
                } else {
                    var img;
                    img = new FormData();
                    img.append('file', jQuery('#file')[0].files[0]);
                    if (jQuery('#file')[0].files[0].type === "image/jpeg" || jQuery('#file')[0].files[0].type === "image/png") {

                        jQuery.ajax({
                            url: 'SubirImagenPerfil',
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
                    } else {


                        notificaError("error ", "Archivo no permitido", 'bg-warning');
                    }
                    notificacion("success", "el Cliente se ha registrado con éxito", "alert");
                    limpiarFormularioRegistro();
                }

            } else {
                notificacion("danger", "se ha generado un error", "alert");
            }
        },
        timeout: 20000
    });
    //desactivar();
}



function validaDocumentoCliente(documento) {
    var nit = $('#documento').val();
    var letra = '-';
    var datosDocumentoUsuario = {
        documentoCliente: documento
    };
    if (documento == "") {
        return false;
    }
    ajaxCnk.validarDocumentoCliente(datosDocumentoUsuario, {
        callback: function(data) {
            console.log("validarUser ", data);
            if (data == true) {
                jQuery("#mensajeDocumento").show();
                jQuery("#btnRegistrar").prop("disabled", true);
                jQuery("#documento").focus();
            }
            else if (data == false) {
                jQuery("#btnRegistrar").prop("disabled", false);
                jQuery("#mensajeDocumento").hide();
            }
        },
        timeout: 20000

    });

    if (documento == nit && $('#tipoDocumento').val() === '1') {
        var valor = nit.charAt(nit.length - 2);
        if (valor == letra) {
        } else {
            jQuery("#mensajeNit").show();
            jQuery("#documento").val("");
            jQuery("#documento").focus();

        }
    }

}

function claseDocumento() {
    if ($('#tipoDocumento').val() === '1') {
        return paraNit(event);
    } else {
        return soloNumeros(event);
    }
}

function volver() {
    limpiarFormularioRegistro();
    cargarPagina("listar-usuarios.jsp");
}

function desactivar() {
    jQuery("#razonSocial").prop("disabled", true);
    jQuery("#tipoDocumento").prop("disabled", true);
    jQuery("#documento").prop("disabled", true);
    jQuery("#email").prop("disabled", true);
    jQuery("#representanteLegal").prop("disabled", true);
    jQuery("#regimen").prop("disabled", true);
    jQuery("#naturalezaEmpresa").prop("disabled", true);
    jQuery("#departamento").prop("disabled", true);
    jQuery("#municipio").prop("disabled", true);
    jQuery("#formaJuridica").prop("disabled", true);
    jQuery("#celular").prop("disabled", true);
    jQuery("#direccion").prop("disabled", true);
    jQuery("#telefonoFijo").prop("disabled", true);
    jQuery("#btnRegistrar").prop("disabled", true);
}

function limpiarFormularioRegistro() {
    jQuery("#razonSocial").val("");
    jQuery("#tipoDocumento").val("");
    jQuery("#documento").val("");
    jQuery("#correo").val("");
    jQuery("#representanteLegal").val("");
    jQuery("#regimen").val("");
    jQuery("#naturalezaEmpresa").val("");
    jQuery("#departamento").val("");
    jQuery("#municipio").val("");
    jQuery("#formaJuridica").val("");
    jQuery("#celular").val("");
    jQuery("#direccion").val("");
    jQuery("#telefonoFijo").val("");
    $("#btnRegistrar").prop('disabled', false);
}

function  montarImg() {

    var data = new FormData();
    data.append('file', $('#file')[0].files[0]);
    if ($('#file')[0].files[0].type === "image/jpeg" || $('#file')[0].files[0].type === "image/png") {
        jQuery.ajax({
            url: 'Uploader',
            data: data,
            processData: false,
            contentType: false,
            type: 'POST',
            success: function() {
                ajaxDoctorpin.cambiarImagen({
                    callback: function(data) {
                        if (data) {
                            //location.reload();
                            notificacion("success", "SE ACTUALIZO LA IMAGEN, Se ha actualizado la imagen del perfil con éxito.", "alert");
                        } else {
                            notificacion("danger", "ERROR AL CAMBIAR LA IMAGEN, No se pudo cambiar la imagen de perfil.", "alert");
                        }
                    },
                    timeout: 20000
                });
            }
        });
    } else {
        notificacion("danger", "ERROR AL CAMBIAR LA IMAGEN.El tipo de archivo que intenta agregar no esta permitido.", "alert");
    }
}

function notificacion(tipo, msj, id) {
    $(".alert").alert('close');
    $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
    setTimeout('$(".alert").alert("close");', '5000');
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
            registrarCliente();
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

function paraNit(e) {
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
