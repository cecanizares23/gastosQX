/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery(document).ready(function() {
    jQuery("#miCuenta").show();
    jQuery("#editarPerfil").hide();
    jQuery("#cambioContrasenia").hide();

    ajaxCnk.listarDepartamento({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("departamento");
                dwr.util.addOptions("departamento", [{
                        id: '',
                        nombre: ''
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
                jQuery('#tipoDocumento option[value=1]').hide();
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarGenero({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("genero");
                dwr.util.addOptions("genero", [{
                        id: '',
                        descripcion: 'Seleccione género'
                    }], 'id', 'descripcion');
                dwr.util.addOptions("genero", data, 'id', 'descripcion');
            }
        },
        timeout: 20000
    });

    ajaxCnk.listarCargos({
        callback: function(data) {
            if (data !== null) {
                dwr.util.removeAllOptions("tipoUsuario");
                dwr.util.addOptions("tipoUsuario", [{
                        id: '',
                        descripcion: 'Seleccione cargo'
                    }], 'id', 'descripcion');
                dwr.util.addOptions("tipoUsuario", data, 'id', 'descripcion');
            }
        },
        timeout: 20000
    });

});

function miCuenta() {
    jQuery("#miCuenta").show();
    jQuery("#editarPerfil").hide();
    jQuery("#cambioContrasenia").hide();
    limpiarFormularioRegistro();
    activar();
}

function editarPerfil() {
    jQuery("#miCuenta").hide();
    jQuery("#editarPerfil").show();
    jQuery("#cambioContrasenia").hide();
    console.log("%%%%%%%% ",idUsuarioSesion);
    editarUsuario(idUsuarioSesion);
}

function cambioContrasenia() {
    jQuery("#miCuenta").hide();
    jQuery("#editarPerfil").hide();
    jQuery("#cambioContrasenia").show();
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
                        nombre: ' '
                    }], 'id', 'nombre');
                dwr.util.addOptions("municipio", data, 'id', 'nombre');
                jQuery("#municipio").val(parseInt(muni)).trigger("change");
            }
        },
        timeout: 20000
    });
}

function editarUsuario(id) {
    disponible = true;
    ajaxCnk.consultarUsuarioPorId(id, {
        callback: function(data) {

            if (data !== null) {
                console.log("editar ", data);
                idUsuario = id;
                jQuery("#tituloEditar").show();
                jQuery("#divEditarUsuario").show();
                jQuery("#tituloGestion").hide();
                jQuery("#divTablaUsuarios").hide();
                listarMunicipioEditar(data.idDepartamento);
                jQuery('#nombreUsuario').val(data.nombre);
                jQuery('#apellidoUsuario').val(data.apellido);
                jQuery('#tipoDocumento').val(data.idTipoDocumento);
                jQuery('#documento').val(data.documento);
                jQuery('#email').val(data.correo);
                jQuery('#fechaNacimiento').val(data.fechaNacimiento);
                jQuery('#estado').val(data.estado);
                jQuery('#tipoUsuario').val(data.idTipoUsuario);
                depa = data.idDepartamento;
                jQuery('#departamento').val(depa).trigger("change");
                muni = data.idMunicipio;
                jQuery('#municipio').val(muni).trigger("change");
                jQuery('#genero').val(data.idGenero);
                jQuery('#telefonoMovil').val(data.celular);
                jQuery('#direccion').val(data.direccion);
                jQuery('#telefonoFijo').val(data.telefono);
                //jQuery('#direccion2').val(data.direccion1);
                jQuery('#usuario').val(data.usuario);
            }
        },
        timeout: 20000
    });
}

function registrarEditarUsuario() {
    var usuario = {
        id: idUsuario,
        nombre: jQuery("#nombreUsuario").val(),
        apellido: jQuery("#apellidoUsuario").val(),
        idTipoDocumento: jQuery("#tipoDocumento").val(),
        documento: jQuery("#documento").val(),
        correo: jQuery("#email").val(),
        fechaNacimiento: jQuery("#fechaNacimiento").val(),
        estado: jQuery("#estado").val(),
        idTipoUsuario: jQuery("#tipoUsuario").val(),
        municipio: jQuery("#municipio").val(),
        genero: jQuery("#genero").val(),
        celular: jQuery("#telefonoMovil").val(),
        direccion: jQuery("#direccion").val(),
        telefono: jQuery("#telefonoFijo").val()
                //direccion1: jQuery("#direccion2").val()
    };

    console.log("usuario ", usuario);

    ajaxCnk.editarUsuario(usuario, {
        callback: function(data) {
            if (data !== null) {
                notificacion("success", "el usuario se ha editado con exito", "alert");
            } else {
                notificacion("danger", "se ha generado un error", "alert");
            }
        },
        timeout: 20000
    });
    desactivar();
}

function desactivar() {
    jQuery("#nombreUsuario").prop("disabled", true);
    jQuery("#apellidoUsuario").prop("disabled", true);
    jQuery("#tipoDocumento").prop("disabled", true);
    jQuery("#documento").prop("disabled", true);
    jQuery("#email").prop("disabled", true);
    jQuery("#fechaNacimiento").prop("disabled", true);
    jQuery("#estado").prop("disabled", true);
    jQuery("#tipoUsuario").prop("disabled", true);
    jQuery("#departamento").prop("disabled", true);
    jQuery("#municipio").prop("disabled", true);
    jQuery("#genero").prop("disabled", true);
    jQuery("#telefonoMovil").prop("disabled", true);
    jQuery("#direccion").prop("disabled", true);
    jQuery("#telefonoFijo").prop("disabled", true);
    //jQuery("#direccion2").prop("disabled", true);
    jQuery("#usuario").prop("disabled", true);
    jQuery("#contrasenia").prop("disabled", true);
    jQuery("#contrasenia1").prop("disabled", true);
    jQuery("#btnRegistrar").prop("disabled", true);
}

function activar() {
    jQuery("#nombreUsuario").prop("disabled", false);
    jQuery("#apellidoUsuario").prop("disabled", false);
    jQuery("#tipoDocumento").prop("disabled", false);
    jQuery("#documento").prop("disabled", false);
    jQuery("#email").prop("disabled", false);
    jQuery("#fechaNacimiento").prop("disabled", false);
    jQuery("#estado").prop("disabled", false);
    jQuery("#tipoUsuario").prop("disabled", false);
    jQuery("#departamento").prop("disabled", false);
    jQuery("#municipio").prop("disabled", false);
    jQuery("#genero").prop("disabled", false);
    jQuery("#telefonoMovil").prop("disabled", false);
    jQuery("#direccion").prop("disabled", false);
    jQuery("#telefonoFijo").prop("disabled", false);
    //jQuery("#direccion2").prop("disabled", false);
    jQuery("#usuario").prop("disabled", false);
    jQuery("#contrasenia").prop("disabled", false);
    jQuery("#contrasenia1").prop("disabled", false);
    jQuery("#btnRegistrar").prop("disabled", false);
}

function limpiarFormularioRegistro() {
    jQuery("#nombreUsuario").val("");
    jQuery("#apellidoUsuario").val("");
    jQuery("#tipoDocumento").val("");
    jQuery("#documento").val("");
    jQuery("#email").val("");
    jQuery("#fechaNacimiento").val("");
    jQuery("#estado").val("");
    jQuery("#tipoUsuario").val("");
    jQuery("#departamento").val("");
    jQuery("#municipio").val("");
    jQuery("#genero").val("");
    jQuery("#telefonoMovil").val("");
    jQuery("#direccion").val("");
    jQuery("#telefonoFijo").val("");
    //jQuery("#direccion2").val("");
    jQuery("#usuario").val("");

}

function cambiarContraseniaUsuario() {

    var datos = {
        clave: jQuery('#contraseniaAnterior').val(),
        nuevaClave: jQuery('#nuevaContrasenia').val()
    };
    ajaxCnk.cambiarContrasenia(datos, {
        callback: function(data) {
            if (data === true) {
                notificacion("success", "CONTRASEÑA ACTUALIZADA", "alert");
                jQuery('#formContrasenia input[type="password"]').each(function() {
                    jQuery(this).val("");
                });
                cargarPagina('perfil.jsp');
            } else {
                notificacion("danger", "ERROR AL CAMBIAR LA CONTRASEÑA", "alert");
            }
        }, timeout: 20000
    });
}

function notificacion(tipo, msj, id) {
    $(".alert").alert('close');
    $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
    setTimeout('$(".alert").alert("close");', '10000');
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

            cambiarContraseniaUsuario();

        }

    });
}

function validar2(formulario) {

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

            registrarEditarUsuario();

        }

    });
}

$(function() {
    // wysiwg editor
    yukon_wysiwg.p_forms_validation();
    // multiselect
    yukon_select2.p_forms_validation();
    // validation
    yukon_parsley_validation.p_forms_validation();
});

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

function validarUsuarios(e) {

    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = "abcdefghijklmnopqrstuvwxyz,0,1,2,3,4,5,6,7,8,9,|,°,!,#,$,%,&,/,(,),=,?,¿,¡,*,+,-,_";
    especiales = "8-33-37-39-46";
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

