<%-- 
    Document   : registrar-articulos
    Created on : 9/03/2019, 09:21:56 AM
    Author     : Ing. Carlos Cañizares
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
    <h5><strong>REGISTRAR ARTICULOS</strong></h5>
</div>

<div class="col-md-12">
    <div id="alert"></div>
</div>

<form id="form_validation" name="form_validation" action="autocomplete:off" novalidate>
    <div class="row">
        
        <div class="alert alert-danger" id="mensajeReferencia">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
            <strong>Ya hay un Articulo registrado con esta referencia!</strong>
        </div>
        
        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Referencia:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="referencia" class="form-control" maxlength="50" onblur="validarReferencia(this.value)" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Lote:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="lote" class="form-control" maxlength="50" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Descripción:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="descripcion" class="form-control" maxlength="50"  required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Unidad de Medida:</label>
            <div class="input-group date">
                <span class="input-group-addon"><i class="social_flickr_square bs_ttip"></i></span>
                <select id="unidad" class="form-control" required>                                    
                    <option value="">-Seleccione uno-</option>
                    <option value="UNIDAD">Unidad</option>
                    <option value="KIT">Kit</option>
                    <option value="COMBO">Combo</option>
                </select>
            </div>
        </div>

        <div class="col-md-6 form-group">            
            <label for="val_last_name" class="req">Cantidad Maxima:</label>
            <div class="input-group date">
                <span class="input-group-addon"><i class="el-icon-phone-alt"></i></span>
                <input type="text" id="cantidadMax" class="form-control" onkeypress="return soloNumeros(event);" maxlength="10" required/>
            </div>
        </div>

        <div class="col-md-6 form-group">            
            <label for="val_last_name" class="req">Cantidad Minima:</label>
            <div class="input-group date">
                <span class="input-group-addon"><i class="el-icon-phone-alt"></i></span>
                <input type="text" id="cantidadMin" class="form-control" onkeypress="return soloNumeros(event);" maxlength="10" required/>
            </div>
        </div>

        <div class="col-md-12 form-group"><br><br>
            <div class="col-md-9 form-group"></div>
            <div class="col-md-3 form-group">
                <button class="btn btn-primary" id="btnRegistrar" onclick="javascript:validar('form_validation');">Guardar</button>
                <a class="btn btn-primary" onclick="javascript:cargarPagina('listar-articulos.jsp');">Volver</a>
            </div>
        </div>   

    </div>
</form>

<script>

    jQuery(document).ready(function () {
        jQuery("#mensajeReferencia").hide();
    });    

    function registrarArticulo() {
        $("#btnRegistrar").prop('disabled', true);
        var articulo = {
            referencia: jQuery("#referencia").val(),
            lote: jQuery("#lote").val(),
            descripcion: jQuery("#descripcion").val(),
            unidadMedidad: jQuery("#unidad").val(),
            cantidadMax: jQuery("#cantidadMax").val(),
            cantidadMin: jQuery("#cantidadMin").val()
        };

        //validaUsuario();        
        ajaxGastos.registrarArticulo(articulo, {
            callback: function (data) {
                if (data !== null) {
                    notificacion("success", "el Articulo se ha registrado con éxito", "alert");
                    limpiarFormularioRegistro();
                    //cargarPagina("listar-usuarios.jsp");
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
            },
            timeout: 20000
        });
        //desactivar();        
    }

    function validarReferencia(referencia) {
        var datosReferencia = {
            referencia: referencia
        };
        if (referencia == "") {
            return false;
        }
        ajaxGastos.validarReferencia(datosReferencia, {
            callback: function (data) {

                if (data == true) {
                    jQuery("#mensajeReferencia").show();
                    setTimeout('jQuery("#mensajeReferencia").hide();', '4000');
                    jQuery("#btnRegistrar").prop("disabled", true);
                    jQuery("#referencia").focus();
                    setTimeout('jQuery("#referencia").val("");', '1000');
                } else if (data == false) {
                    jQuery("#btnRegistrar").prop("disabled", false);
                    jQuery("#mensajeReferencia").hide();
                }
            },
            timeout: 20000

        });

    }

    function volver() {
        limpiarFormularioRegistro();
        cargarPagina("listar-articulos.jsp");
    }

    function desactivar() {
        jQuery("#referencia").prop("disabled", true);
        jQuery("#lote").prop("disabled", true);
        jQuery("#descripcion").prop("disabled", true);
        jQuery("#unidad").prop("disabled", true);
        jQuery("#cantidadMax").prop("disabled", true);
        jQuery("#cantidadMin").prop("disabled", true);
    }

    function limpiarFormularioRegistro() {
        jQuery("#referencia").val("");
        jQuery("#lote").val("");
        jQuery("#descripcion").val("");
        jQuery("#unidad").val("");
        jQuery("#cantidadMax").val("");
        jQuery("#cantidadMin").val("");
    }

    function notificacion(tipo, msj, id) {
        $(".alert").alert('close');
        $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
        setTimeout('$(".alert").alert("close");', '3000');
    }
    
    function validar(formulario) {

        $('#' + formulario).validate({
            highlight: function (label) {
                jQuery(label).closest('.form-group').removeClass('has-success').addClass('has-error');
            },
            success: function (label) {
                jQuery(label).closest('.form-group').removeClass('has-error');
                label.remove();
            },
            errorPlacement: function (error, element) {
                var placement = element.closest('.input-group');
                if (!placement.get(0)) {
                    placement = element;
                }
                if (error.text() !== '') {
                    placement.after(error);
                }
            },
            submitHandler: function () {

                registrarArticulo();

            }

        });
    }

    $(function () {
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

    $(function () {
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

        var key = e.which || e.keyCode;
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
        if ((key < 63 && key != 8 && key != 32) || key > 122) {
            e.preventDefault();
        }
    }

</script>