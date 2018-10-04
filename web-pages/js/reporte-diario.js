/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery(document).ready(function() {
        jQuery("#cerrarReporte").hide();
        jQuery("#seleccionarProyecto").show();
        jQuery("#modalPlanDePagoImprimir").hide();
        jQuery("#divContenedorArchivos").hide();
        jQuery("#btnModalLarge").hide();
        jQuery("#btnVizualizarPdf").prop('disabled', true);
        jQuery("#btnCerrarEnviar").prop('disabled', true);
        jQuery("#divSeleccionarImagenes").hide();

        ajaxCnk.listarProyectoPorIdUsuario(idUsuarioJava, {
            callback: function(data) {
                if (data !== null) {
                    dwr.util.removeAllOptions("proyecto");
                    dwr.util.addOptions("proyecto", [{
                            id: '',
                            nombreProyecto: 'Seleccione Proyecto'
                        }], 'id', 'nombreProyecto');
                    dwr.util.addOptions("proyecto", data, 'id', 'nombreProyecto');
                }
            },
            timeout: 20000
        });

    });

    function abrirReporte() {
        var fecha = hoy;
        var nombreUsuario = nombreUsuarioJava;
        var valorProyecto = $("#proyecto").val();
        var estaTiempo = $("#estadoTiempo").val();
        estaTiempo = estaTiempo.replace(/\s/g, "_");
        cargarPagina('pdf-reporte-diario.jsp?valorProyecto=' + valorProyecto + '&estaTiempo=' + estaTiempo + '&nombreUsuario=' + nombreUsuario + '&fecha=' + fecha);
    }

    function listarArchivos() {
        console.log("dentraAqui");

        if (jQuery("#proyecto").val() == "") {
            console.log("primer if");
            notificacion("warning", "Debe seleccionar un proyecto!", "alert");
            return;
        }
        if (jQuery("#estadoTiempo").val() == "") {
            console.log("segundo if");
            notificacion("warning", "Debe digitar el estado del tiempo!", "alert");
            return;
        }
        var listadoArchivos = [];
        ajaxCnk.listarImagenes(jQuery("#proyecto").val(), {
            callback: function(data) {
                listadoArchivos = data;
                console.log("data ", data);
                if (listadoArchivos == "") {
                    notificacion("warning", "No hay avances registrados hoy, para el proyecto seleccionado. Por  favor registre avances en las actividades del proyecto", "alert");
                    return;
                }
                if (data !== null) {
                    console.log("listadoArchivos ", listadoArchivos);
                    jQuery("#btnModalLarge").click();
                    for (var i = 0; i < listadoArchivos.length; i++) {
                        jQuery("#divSeleccionarImagenes").show();
                        jQuery("#seleccionarProyecto").hide();
                        // jQuery("#archivo").prop('src', listadoArchivos[i]);
                        var idCheckbox = listadoArchivos[i].nombreArchivo;
                        idCheckbox = idCheckbox.toString().replace(/\./g, "_");
                        var valueCheckBox = listadoArchivos[i].nombreArchivo;
                        valueCheckBox = valueCheckBox.toString().replace(/\./g, "-");
                        console.log("idChecbox ", idCheckbox);
                        jQuery("#divContenedorArchivos").show();
                        jQuery("#divContenedorArchivos").append('<li><input type="checkbox" class="icheck" value="' + valueCheckBox + '" onclick="javascript:pesoImagen(this);" id="' + idCheckbox + '" style="alignment: center;"><a class="img_wrapper" disabled="true" id="btnImagenes"><img src="<%=request.getContextPath()%>/imgAvanceActividad?imagen=' + listadoArchivos[i].nombreArchivo + '" alt=""></a></li>');
                        jQuery("#btnImagenes").prop("disabled", true);
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

    var acumulador = 0;
    var arrayImagenes = [];
    var tamanio;
    var tamanioKb = 0;
    var tamanioMb = 0;
    var nombreProyecto;
    var valorProyecto;
    var estadoTiempo;
    var nombreUsuario;
    var idAvance;
    var fecha;
    var tamanioPdf = 0;

    function pesoImagen(p) {

        console.log("p.id ", p.id);
        console.log("p.value ", p.value);
        console.log("p ", p);
        console.log("&&& ", jQuery("#" + p.id).prop('checked'));

        if (jQuery("#" + p.id).prop('checked')) {

            console.log("p.value ", p.value);

            if (jQuery("#file").val() == "") {
                notificacion("warning", "Debe seleccionar el video, para adjuntar al correo.", "alert1");
                jQuery("#file").focus();
                jQuery("#" + p.id).prop('checked', false);
                return;
            } else {
                jQuery("#btnVizualizarPdf").prop('disabled', true);
                var files = document.getElementById("file").files;


                console.log("&&& ", jQuery("#" + p.id).prop('checked'));

                if (jQuery("#" + p.id).prop('checked')) {
                    arrayImagenes.push(p.value);
                }

            }
        } else {
            console.log("//////// ", p.id);
            if (!jQuery("#" + p.id).prop('checked')) {
                for (var i = 0; i <= arrayImagenes.length; i++) {
                    if (p.value === arrayImagenes[i]) {
                        arrayImagenes.splice(i, 1);
                    }
                }
            }
        }
        jQuery("#btnVizualizarPdf").prop("disabled", false);
    }

    function generarPdf() {

        ajaxCnk.reporteFechaActual($("#proyecto").val(), {
            callback: function(data) {
                if (data == null) {

                    ajaxCnk.listarAvancesDiarios(jQuery("#proyecto").val(), {
                        callback: function(data) {
                            if (data !== null) {

                                nombreProyecto = data[0].nombreProyecto;
                                nombreProyecto = nombreProyecto.replace(/\s/g, "_");

                                var files = document.getElementById("file").files;
                                idAvance = data[data.length - 1].id;

                                for (var i = 0; i < files.length; i++) {
                                    tamanio = jQuery("#file")[0].files[i].size;
                                    console.log("tamanio ", tamanio);
                                    tamanioKb = parseFloat(tamanio) / 1024;
                                    tamanioMb = (tamanioKb * 100) / 25000;
                                    tamanioMb = tamanioMb.toFixed(2);
                                }

                                var datosReporteDiario = {
                                    idProyecto: jQuery("#proyecto").val(),
                                    estadoTiempoRepDiario: jQuery("#estadoTiempo").val(),
                                    fechaGeneraReporte: hoy,
                                    nombrePdf: idAvance + "ReporteDiario.pdf",
                                    idAvance: idAvance
                                };

                                console.log("Entra por registrar reporte diario");

                                ajaxCnk.registrarReporteDiario(datosReporteDiario, {
                                    callback: function(data) {
                                        if (data !== null) {

                                            for (var i = 0; i < files.length; i++) {

                                                var img;
                                                img = new FormData();
                                                img.append('file', jQuery('#file')[0].files[i]);

                                                if (jQuery('#file')[0].files[i].type === "video/mpg" || jQuery('#file')[0].files[i].type === "viedo/mpeg" ||
                                                        jQuery('#file')[0].files[i].type === "video/mp4") {

                                                    jQuery.ajax({
                                                        url: 'ServletSubirVideoReporteDiario',
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

                                                    valorProyecto = jQuery("#proyecto").val();
                                                    estadoTiempo = jQuery("#estadoTiempo").val();
                                                    estadoTiempo = estadoTiempo.replace(/\s/g, "_");
                                                    nombreUsuario = "<%=nombreUsuario%>";
                                                    fecha = hoy;
                                                    console.log("generar ", arrayImagenes);
                                                    verPdf(valorProyecto, estadoTiempo, nombreProyecto, nombreUsuario, fecha, idAvance, arrayImagenes);

                                                    ajaxCnk.listarPdf(idAvance, {
                                                        callback: function(data) {
                                                            if (data !== null) {
                                                                tamanioPdf = parseFloat(data.tamanioPdf) / 1024;
                                                                tamanioPdf = (parseFloat(tamanioPdf) * 100) / 25000;
                                                                tamanioPdf = tamanioPdf.toFixed(2);
                                                                tamanioMb = parseFloat(tamanioMb) + parseFloat(tamanioPdf);

                                                                jQuery("#barraProgreso").css("width", tamanioMb.toString() + "%");
                                                                jQuery("#valorProgreso").text(tamanioMb.toString() + "%");
                                                            }
                                                        },
                                                        timeout: 20000
                                                    });

                                                } else {
                                                    notificacion("danger", "Tipo de archivo seleccionado no permitido!", "alert1");
                                                    jQuery("files").focus();
                                                    return;
                                                }

                                            }

                                            notificacion("success", "Registro Exitoso!", "alert1");
                                        }
                                    },
                                    timeout: 20000
                                });

                            }
                        },
                        timeOut: 20000
                    });

                } else {
                    ajaxCnk.ultimoIdDeReporteDiario({
                        callback: function(data) {
                            if (data !== null) {
                                var idReporte = data.id;
                                ajaxCnk.listarAvancesDiarios(jQuery("#proyecto").val(), {
                                    callback: function(data) {
                                        if (data !== null) {

                                            nombreProyecto = data[0].nombreProyecto;
                                            nombreProyecto = nombreProyecto.replace(/\s/g, "_");

                                            var files = document.getElementById("file").files;
                                            idAvance = data[data.length - 1].id;

                                            for (var i = 0; i < files.length; i++) {
                                                tamanio = jQuery("#file")[0].files[i].size;
                                                console.log("tamanio ", tamanio);
                                                tamanioKb = parseFloat(tamanio) / 1024;
                                                tamanioMb = (tamanioKb * 100) / 25000;
                                                tamanioMb = tamanioMb.toFixed(2);
                                            }

                                            var datosReporteDiario = {
                                                idProyecto: jQuery("#proyecto").val(),
                                                estadoTiempoRepDiario: jQuery("#estadoTiempo").val(),
                                                fechaGeneraReporte: hoy,
                                                nombrePdf: idAvance + "ReporteDiario.pdf",
                                                idAvance: idAvance
                                            };

                                            ajaxCnk.editarReporteDiario(datosReporteDiario, idReporte, {
                                                callback: function(data) {
                                                    if (data !== null) {
                                                        for (var i = 0; i < files.length; i++) {

                                                            console.log("Entra por editar reporte diario");

                                                            var img;
                                                            img = new FormData();
                                                            img.append('file', jQuery('#file')[0].files[i]);

                                                            if (jQuery('#file')[0].files[i].type === "video/mpg" || jQuery('#file')[0].files[i].type === "viedo/mpeg" ||
                                                                    jQuery('#file')[0].files[i].type === "video/mp4") {

                                                                jQuery.ajax({
                                                                    url: 'ServletSubirVideoReporteDiario',
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

                                                                //notificacion("danger", "No puedes registrar mas de un reporte ", "alert1");                                                                

                                                                valorProyecto = jQuery("#proyecto").val();
                                                                estadoTiempo = jQuery("#estadoTiempo").val();
                                                                estadoTiempo = estadoTiempo.replace(/\s/g, "_");
                                                                nombreUsuario = "<%=nombreUsuario%>";
                                                                fecha = hoy;
                                                                console.log("generar ", arrayImagenes);
                                                                console.log("idAvance editar ", idAvance);
                                                                verPdf(valorProyecto, estadoTiempo, nombreProyecto, nombreUsuario, fecha, idAvance, arrayImagenes);

                                                                console.log("idAvance " + idAvance);

                                                                ajaxCnk.listarPdf(idAvance, {
                                                                    callback: function(data) {
                                                                        if (data !== null) {
                                                                            tamanioPdf = parseFloat(data.tamanioPdf) / 1024;
                                                                            tamanioPdf = (parseFloat(tamanioPdf) * 100) / 25000;
                                                                            tamanioPdf = tamanioPdf.toFixed(2);
                                                                            tamanioMb = parseFloat(tamanioMb) + parseFloat(tamanioPdf);

                                                                            jQuery("#barraProgreso").css("width", tamanioMb.toString() + "%");
                                                                            jQuery("#valorProgreso").text(tamanioMb.toString() + "%");
                                                                        }
                                                                    },
                                                                    timeout: 20000
                                                                });

                                                            } else {
                                                                console.log("else editar");
                                                                notificacion("danger", "Tipo de archivo seleccionado no permitido!", "alert1");
                                                                jQuery("files").focus();
                                                                return;
                                                            }

                                                        }

                                                        notificacion("success", "Registro Exitoso!", "alert1");
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
            },
            timeout: 20000
        });

    }

    function enviaPdf() {
        var idProyecto = $("#proyecto").val();
        ajaxCnk.consultarCorreoPorId($("#proyecto").val(), {
            callback: function(data) {
                if (data !== null) {
                    var correo = data.id;
                    ajaxCnk.listarAvancesDiarios(idProyecto, {
                        callback: function(data) {
                            if (data !== null) {
                                var idAvance = data[data.length - 1].id;
                                $.ajax({
                                    url: 'servletEnviarPdf',
                                    data: {
                                        idAvance: idAvance,
                                        correo: correo
                                    },
                                    type: 'POST'
                                });
                                //notificacion("success", "Correo enviado satisfactoriamente", "alert1");
                                setTimeout('', '2000');

                                ajaxCnk.ultimoIdDeReporteDiario({
                                    callback: function(data) {
                                        if (data !== null) {
                                            var idReporte = data.id;
                                            ajaxCnk.actualizarEstadoReporte(idReporte, {
                                                callback: function(data) {
                                                    if (data !== null) {
                                                        notificacion("success", "Se ha cerrado y enviado con exito el reporte diario.", "alert1");
                                                        setTimeout('location.reload();', '3000');
                                                    }
                                                },
                                                timeout: 20000
                                            })
                                        }
                                    },
                                    timeout: 20000
                                });

                            } else {
                                notificacion("warning", "no hay avances en este proyecto", "alert");
                            }

                        },
                        timeout: 20000
                    });
                } else {
                    notificacion("warning", "no hay avances en este proyecto", "alert");
                }
            },
            timeout: 20000
        });

    }

    function eliminarReporte() {
        ajaxCnk.ultimoIdDeReporteDiario({
            callback: function(data) {
                if (data !== null) {
                    var idReporte = data.id;
                    ajaxCnk.eliminarReporte(idReporte, {
                        callback: function(data) {
                            if (data !== null) {
                                setTimeout('location.reload();', '500');
                            }
                        },
                        timeout: 20000
                    });
                }
            },
            timeout: 20000
        });
    }

    function registrarReporteDiario() {

        ajaxCnk.reporteFechaActual($("#proyecto").val(), {
            callback: function(data) {
                if (data == null) {
                    ajaxCnk.consultarProyectoPorId($("#proyecto").val(), {
                        callback: function(data) {
                            if (data !== null) {
                                ajaxCnk.listarAvancesDiarios($("#proyecto").val(), {
                                    callback: function(data) {
                                        console.log("listaAvances", data);
                                        if (data !== null) {

                                            cerrar();
                                            var fecha = hoy;
                                            var valorProyecto = $("#proyecto").val();
                                            var estaTiempo = $("#estadoTiempo").val();
                                            estaTiempo = estaTiempo.replace(/\s/g, "_");
                                            console.log("sale a estaTiempo", estaTiempo);
                                            var nomProyecto = data[0].nombreProyecto;
                                            nomProyecto.replace(/\s/g, "_");
                                            var nombreUsuario = "<%=nombreUsuario%>";
                                            console.log("sale a verpdf", nombreUsuario);
                                            var idAvance = data[data.length - 1].id;
                                            console.log("sale a idAvance", idAvance);
                                            verPdf(valorProyecto, estaTiempo, nomProyecto, nombreUsuario, fecha, idAvance);

                                            var datosReporteDiario = {
                                                idProyecto: $("#proyecto").val(),
                                                estadoTiempoRepDiario: $("#estadoTiempo").val(),
                                                fechaGeneraReporte: hoy,
                                                nombrePdf: idAvance + "ReporteDiario.pdf"
                                            };

                                            ajaxCnk.registrarReporteDiario(datosReporteDiario, {
                                                callback: function(data) {
                                                    console.log("registra reporte", data);
                                                    if (data !== null) {
                                                        notificacion("success", "registro exitoso en la base de datos", "alert");
                                                    }
                                                },
                                                timeout: 20000
                                            });
                                        } else {
                                            notificacion("warning", "no hay avances en este Proyecto para el dia de hoy", "alert");
                                        }
                                    },
                                    timeout: 20000
                                });
                            }
                        },
                        timeout: 20000
                    });

                } else {
                    notificacion("danger", "No puedes registrar mas de un reporte ", "alert");
                }
            },
            timeout: 20000
        });
    }

    function verPdf(idProyecto, estadoTiempo, nombreProyecto, nombreUsuario, fecha, idAvance, arrayImagenes) {
        console.log("ver pdf ", idProyecto, estadoTiempo, nombreProyecto, nombreUsuario, fecha, idAvance, arrayImagenes);
        jQuery("#btnCallModalImprimirPlan").click();
        var object = "<iframe style=\"height: 470px;\" src='<%= request.getContextPath()%>/DescargarPdf?idProyecto=" + idProyecto + "&estadoTiempo=" +
                estadoTiempo + "&nombreProyecto=" + nombreProyecto + "&nombreUsuario=" + nombreUsuario + "&fecha=" + fecha + "&idAvance=" + idAvance +
                "&arrayImagenes=" + arrayImagenes + "' class=\"col-xs-12\"></iframe>";
        jQuery("#modalPlanDePagoImprimir").show();
        jQuery("#modalVisualizar").html(object);
    }

    function cerrarReporte() {
        jQuery("#cerrarReporte").hide();
        jQuery("#seleccionarProyecto").show();
    }

    function cerrar() {
        jQuery("#seleccionarProyecto").hide();
        jQuery("#cerrarReporte").hide();
    }

    function cierraModal() {
        //cerrarReporte();
        //jQuery("#modalPlanDePagoImprimir").hide();
        jQuery("#btnCerrarEnviar").prop('disabled', false);
        notificacion("success", "ya se ha pre-vizualizado el reporte diario, se desea cerrarlo de click en el boton Cerrar Enviar", "alert");
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
                registrarReporteDiario();
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
        yukon_clock_picker.init();         // chained selects
        yukon_chained_selects.init();
        // password show/hide         yukon_pwd_show_hide.init();
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
        letras = " áéíóúabcdefghijklmnñopqrstuvwxyz ";
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
