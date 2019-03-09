<%-- 
    Document   : registrar-gastos
    Created on : 27-sep-2018, 14:22:21
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
    <h5><strong>REGISTRAR ENCABEZADO DEL GASTO</strong></h5>
</div>

<form id="form_validation" name="form_validation" action="autocomplete:off" novalidate>
    <div class="row"> 

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Procedimiento:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_id bs_ttip"></i></span>
                <select id="procedimiento" class="form-control" required></select>
            </div>
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Paciente:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="paciente" class="form-control" maxlength="50" onkeypress="return soloLetras(event);" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_first_name" class="req">Cedula:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="cedula" class="form-control" maxlength="50" onkeypress="return soloNumeros(event);" required/>                
            </div>            
        </div>

        <div class="col-md-6 form-group">
            <label for="val_last_name" class="req">Fecha:</label>                                
            <div class="input-group date" id="dp_component">
                <span class="input-group-addon"><i class="icon_calendar"></i></span>
                <input type="text" class="form-control" id="fecha" required />                
            </div>                                
        </div>

        <div class="col-md-6 form-group" id="divId" hidden="true   ">
            <label for="val_first_name" class="req">id:</label>
            <div class="input-group">
                <span class="input-group-addon"><i class="icon_profile bs_ttip"></i></span>
                <input type="text" id="idGasto" class="form-control" maxlength="50" onkeypress="return soloNumeros(event);" disabled/>                
            </div>            
        </div>

        <div class="col-md-12 form-group" id="divButtonRegistrar"><br><br>
            <div class="col-md-9 form-group">
                <a class="btn btn-primary" id="btnAgregar" onclick="javascript:agregarDetalleGasto();">AGREGRAR</a>
            </div>
            <div class="col-md-3 form-group">
                <button class="btn btn-primary" id="btnRegistrar" onclick="javascript:validar('form_validation');">Guardar</button>
                <a class="btn btn-primary" id="btnHabilita" onclick="javascript:habilita();">Editar</a>
                <a class="btn btn-primary" id="btnEditar" onclick="javascript:editarEncabezadoGasto();">Guardar</a>
                <a class="btn btn-primary" id="btnVolver" onclick="javascript:cargarPagina('pagina-inicial.jsp');">Volver</a>
            </div>
        </div>                

        <div class="col-md-12">
            <div id="alert"></div>
        </div>
    </div>
</form>



<script>
    jQuery(document).ready(function () {
        jQuery("#btnEditar").hide();
        jQuery("#btnHabilita").hide();
        jQuery("#btnAgregar").hide();
        
        //jQuery("#paciente").prop('disabled',true);
        ajaxGastos.listarProcedimiento({
            callback: function (data) {
                if (data !== null) {
                    console.log("data ", data);
                    dwr.util.removeAllOptions("procedimiento");
                    dwr.util.addOptions("procedimiento", [{
                            id: '',
                            descripcion: 'Seleccione tipo de documento'
                        }], 'id', 'descripcion');
                    dwr.util.addOptions("procedimiento", data, 'id', 'descripcion');
                    //jQuery('#tipoDocumento option[value=1]').hide();
                }
            },
            timeout: 20000
        });
    });

    function registrarEncabezadoGasto() {
        $("#btnRegistrar").prop('disabled', true);
        var gastoEncabezado = {
            idProcedimiento: jQuery("#procedimiento").val(),
            paciente: jQuery("#paciente").val(),
            cedula: jQuery("#cedula").val(),
            fecha: jQuery("#fecha").val()
        };

        ajaxGastos.registrarGastos(gastoEncabezado, {
            callback: function (data) {

                if (data !== null) {
                    console.log("data11111111", data);
                    notificacion("success", "el usuario se ha registrado con éxito", "alert");
                    jQuery("#btnRegistrar").hide();
                    jQuery("#btnHabilita").show();
                    jQuery("#btnAgregar").show();
                    jQuery("#idGasto").val(data);
                    desabilita();
                    //cargarPagina("listar-usuarios.jsp");
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
            },
            timeout: 20000
        });
        //desactivar();        
    }
    
    function editarEncabezadoGasto() {
        $("#btnRegistrar").prop('disabled', true);
        var gastoEncabezado = {
            idProcedimiento: jQuery("#procedimiento").val(),
            paciente: jQuery("#paciente").val(),
            cedula: jQuery("#cedula").val(),
            fecha: jQuery("#fecha").val(),
            id: jQuery("#idGasto").val()
        };

        //validaUsuario();        
        ajaxGastos.actualizarGastos(gastoEncabezado, {
            callback: function (data) {
                if (data !== null) {
                    notificacion("success", "el encabezado se ha editado con éxito", "alert");     
                    desabilita();
                    jQuery("#btnHabilita").show();
                    jQuery("#btnEditar").hide();
                    jQuery("#btnAgregar").show();
                } else {
                    notificacion("danger", "se ha generado un error", "alert");
                }
            },
            timeout: 20000
        });
        //desactivar();        
    }

    function desabilita() {
        jQuery("#procedimiento").prop('disabled', true);
        jQuery("#paciente").prop('disabled', true);
        jQuery("#cedula").prop('disabled', true);
        jQuery("#fecha").prop('disabled', true);
    }

    function habilita() {
        jQuery("#procedimiento").prop('disabled', false);
        jQuery("#paciente").prop('disabled', false);
        jQuery("#cedula").prop('disabled', false);
        jQuery("#fecha").prop('disabled', false);
        jQuery("#btnEditar").show();
        jQuery("#btnHabilita").hide();
        jQuery("#btnAgregar").hide();
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

                registrarEncabezadoGasto();

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