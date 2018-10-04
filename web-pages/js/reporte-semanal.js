/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery(document).ready(function() {
    jQuery("#modalPlanDePagoImprimir").hide();
    jQuery("#divSeleccionarImagenes").hide();
    jQuery("#divSeleccionarProyecto").show();
    jQuery("#btnVizualizarPdf").prop('disabled', true);
    jQuery("#btnCerrarEnviar").prop('disabled', true);

    ajaxCnk.listarProyectoPorIdUsuario(idUsuarioJavaRepSem, {
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
    //jQuery("#verReporte<").show();
    idProyecto = jQuery("#proyecto").val();
    cargarPagina('pdf-reporte-semanal.jsp?idProyecto=' + idProyecto);
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

var dias = 0;
var semanas = 0;
var fechaIniProy;
var diasHoy;
var semanaActual = 0;
var idGestionTecnica;
function listarArchivosGestionTecnica() {
    ajaxCnk.consultarProyectoPorId(jQuery("#proyecto").val(), {
        callback: function(data) {
            if (data !== null) {

                dias = parseFloat(data.proyDuracionDias);
                semanas = dias / 7;
                fechaIniProy = new Date(data.fechaInicial);
                var hoy1 = new Date(hoy);
                diasHoy = Date.parse(hoy1) - Date.parse(fechaIniProy);
                diasHoy = Math.floor(diasHoy / (1000 * 60 * 60 * 24));
                semanaActual = diasHoy / 7;
                semanaActual = semanaActual.toFixed(2);
                semanaActual = Math.round(semanaActual);

                ajaxCnk.consultarGestionTecnicaXSemanaIdProyecto(jQuery("#proyecto").val(), semanaActual, {
                    callback: function(data) {
                        console.log("data consultar gestion ", data);
                        if (data !== null) {
                            idGestionTecnica = data.id;
                            console.log("entra consultar gestion", idGestionTecnica);

                            var listadoImagenes = [];
                            ajaxCnk.listarImagenesGestionTecnica(idGestionTecnica, {
                                callback: function(data) {
                                    listadoImagenes = data;
                                    console.log("entra listar image ", listadoImagenes);
                                    if (data !== null) {
                                        console.log("entra data != null ", listadoImagenes);
                                        for (var i = 0; i < listadoImagenes.length; i++) {
                                            console.log("listadoImagenes ", listadoImagenes);
                                            jQuery("#divSeleccionarImagenes").show();
                                            jQuery("#divSeleccionarProyecto").hide();
                                            var idCheckbox = listadoImagenes[i].nombreArchivo;
                                            idCheckbox = idCheckbox.toString().replace(/\./g, "_");
                                            var valueCheckBox = listadoImagenes[i].nombreArchivo;
                                            valueCheckBox = valueCheckBox.toString().replace(/\./g, "-");
                                            jQuery("#divContenedorArchivos").show();
                                            jQuery("#divContenedorArchivos").append('<li><input type="checkbox" class="icheck" value="' + valueCheckBox + '" onclick="javascript:pesoImagen(this);" id="' + idCheckbox + '" style="alignment: center;"><a class="img_wrapper" disabled="true" id="btnImagenes"><img src="<%=request.getContextPath()%>/ServletImagenGestionTecnica?imagen=' + listadoImagenes[i].nombreArchivo + '" alt=""></a></li>');
                                            jQuery("#btnImagenes").prop("disabled", true);
                                        }
                                    } else {
                                        console.log("23456787654");
                                    }
                                },
                                timeout: 20000
                            });

                        } else {
                            notificacion("danger", "La semana " + semanaActual + " no tiene registro de gestion tecnica, por favor registrelo.", "alert");
                            setTimeout("cargarPagina('registrar-gestion-tecnica.jsp');", "3000");
                        }
                    },
                    timeout: 20000
                });

            }
        },
        timeout: 20000
    });
}

var arrayImagenes = [];
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

var idProyecto;
var nombreProyecto;
var numeroInforme;
var fechaReporteDesde;
var fechaReporteHasta;

var acumuladoAnteriorObservado;
var acumuladoAnteriorEsperado;
var acumuladoAnteriorDiferencia;

var durantePeriodoObservado;
var durantePeriodoEsperado;
var durantePeriodoDiferencia;

var acumuladoActualObservado;
var acumuladoActualEsperado;
var acumuladoActualDiferencia;

var estadoObraAnterior;
var estadoObraAnteriorFechaTerminacion;
var estadoObraActual;
var estadoObraActualFechaTerminacion;
var estadoObraDiferencia;

var duracionObraContractualTotal;
var duracionObraContractualTranscurrida;
var duracionObraContractualFaltante;
var duracionObraContractualFaltantePorcentaje;

function generarPdf() {

    idProyecto = jQuery("#proyecto").val();

    var listado = [];
    var calculo = 0;
    var almacenado = 0;
    var fechaActual = new Date();
    var calculoFechaActual = 0;
    var tamanio = 0;
    var tamanioKb = 0;
    var tamanioMb = 0;
    var tamanioPdf = 0;

    ajaxCnk.ultimoIdDeReporte({
        callback: function(data) {
            if (data !== null) {
                var ultimoId = parseInt(data);
                var codigo = ultimoId + 1;
                numeroInforme = codigo;
            }
        },
        timeout: 20000
    });

    console.log("semanaActual ", semanaActual);
    ajaxCnk.ultimoReporteSemanalGenerado(semanaActual, {
        callback: function(data) {
            console.log("dataUltimoReporte ", data);

            if (data == null) {
                console.log("dataultirep == null ");
                ajaxCnk.consultarDatosReporteSemanalAnteriorPorIdProyecto(idProyecto, {
                    callback: function(data) {
                        console.log("_____---- ", data);
                        if (data !== null) {

                            console.log("Entra por la parte verdadera");

                            var fechaInicialProyecto = new Date(data.proyFechaIni);
                            var fechaFinalProyecto = new Date(data.proyFechaFin);
                            var duracionDias = data.proyDuracionDias;
                            console.log("duracionDias ", duracionDias, " -- ", data.proyDuracionDias);
                            var semanasProyecto = parseFloat(duracionDias) / 7;

                            var diasTranscurridos = 0;
                            var semanasTranscurridas = 0;

                            //datos Reporte semananal anterior
                            nombreProyecto = data.nombreProyecto;
                            fechaReporteDesde = data.fechaGeneraReporte;
                            fechaReporteHasta = hoy;
                            acumuladoAnteriorObservado = data.acumuladoAnteriorObservado;
                            acumuladoAnteriorEsperado = data.acumuladoAnteriorEsperado;
                            acumuladoAnteriorDiferencia = data.acumuladoAnteriorDiferencia;
                            estadoObraAnterior = data.estObraSemanasAnterior;
                            estadoObraAnteriorFechaTerminacion = data.estObraFechaTerminacionAnterior;

                            //aqui se calculan los datos de durante el periodo y acumulado actual

                            ajaxCnk.calcularAcumuladoActualObservado({
                                callback: function(data) {
                                    console.log("entra a calcular acumulado", data);
                                    if (data !== null) {
                                        listado = data;
                                        console.log("listado ", listado);
                                        for (i = 0; i < listado.length; i++) {
                                            calculo = (parseFloat(listado[i].porcentajeActividad) * parseFloat(listado[i].porcentajeAvance) / 100);
                                            almacenado = almacenado + calculo;
                                        }

                                        console.log("almacenado ", almacenado);

                                        acumuladoActualObservado = almacenado.toString();

                                        // se calcula el acumulado actual esperado

                                        ajaxCnk.consultarProyectoPorId(idProyecto, {
                                            callback: function(data) {
                                                if (data !== null) {
                                                    calculoFechaActual = Date.parse(fechaActual) - Date.parse(fechaInicialProyecto);
                                                    calculoFechaActual = Math.floor(calculoFechaActual / (1000 * 60 * 60 * 24));
                                                    acumuladoActualEsperado = (parseFloat(calculoFechaActual) / parseFloat(duracionDias)) * 100;
                                                    acumuladoActualEsperado = acumuladoActualEsperado.toFixed(2);
                                                    console.log("acumuladoActualEsperado ", acumuladoActualEsperado);

                                                    //otros calculos 

                                                    // se calcula actual diferencia
                                                    acumuladoActualDiferencia = parseFloat(acumuladoActualObservado) - parseFloat(acumuladoActualEsperado);

                                                    // calculos durante el periodo
                                                    durantePeriodoObservado = parseFloat(acumuladoActualObservado) - parseFloat(acumuladoAnteriorObservado);
                                                    durantePeriodoEsperado = parseFloat(acumuladoActualEsperado) - parseFloat(acumuladoAnteriorEsperado);
                                                    console.log("acumuladoActualEsperado ", acumuladoActualEsperado, " acumuladoAnteriorEsperado ", acumuladoAnteriorEsperado);
                                                    console.log("durantePeriodoEsperado ", durantePeriodoEsperado);
                                                    durantePeriodoDiferencia = parseFloat(durantePeriodoObservado) - parseFloat(durantePeriodoEsperado);


                                                    //calculo estado de la obra 
                                                    var fechaActual1 = new Date();
                                                    diasTranscurridos = Date.parse(fechaActual1) - Date.parse(fechaInicialProyecto);
                                                    diasTranscurridos = Math.floor(diasTranscurridos / (1000 * 60 * 60 * 24));
                                                    console.log("diasTranscurridos ", diasTranscurridos);
                                                    semanasTranscurridas = diasTranscurridos / 7;
                                                    console.log("semanasTranscurridas ", semanasTranscurridas);

                                                    estadoObraActual = ((parseFloat(acumuladoActualObservado)) - (parseFloat(semanasTranscurridas)));
                                                    estadoObraActual = estadoObraActual.toFixed(2);
                                                    estadoObraActualFechaTerminacion = hoy;
                                                    estadoObraDiferencia = ((parseFloat(estadoObraAnterior)) - (parseFloat(estadoObraActual)));
                                                    estadoObraDiferencia = estadoObraDiferencia.toFixed(2);

                                                    console.log("duracionObra", duracionObraContractualTotal);

                                                    //calculo duracion de la obra
                                                    duracionObraContractualTotal = semanasProyecto.toString();
                                                    duracionObraContractualTranscurrida = semanasTranscurridas.toFixed(2);
                                                    duracionObraContractualFaltante = parseFloat(duracionObraContractualTotal) - parseFloat(duracionObraContractualTranscurrida);
                                                    duracionObraContractualFaltante = duracionObraContractualFaltante.toFixed(2);
                                                    console.log("duracionObraContractualTotal ", duracionObraContractualTotal, " duracionObraContractualTranscurrida ", duracionObraContractualTranscurrida);
                                                    duracionObraContractualFaltantePorcentaje = -100 / (parseFloat(duracionObraContractualTotal) * parseFloat(duracionObraContractualTranscurrida)) + 100;
                                                    duracionObraContractualFaltantePorcentaje = duracionObraContractualFaltantePorcentaje.toFixed(2);
                                                    console.log("duracionObraContractualFaltantePorcentaje ", duracionObraContractualFaltantePorcentaje);

                                                    //registro de reporte semanal

                                                    console.log("fgh ", idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado,
                                                            acumuladoAnteriorEsperado, acumuladoAnteriorDiferencia, numeroInforme, acumuladoActualObservado,
                                                            acumuladoActualEsperado, acumuladoActualDiferencia, durantePeriodoObservado, durantePeriodoEsperado,
                                                            durantePeriodoDiferencia, estadoObraAnterior, estadoObraAnteriorFechaTerminacion, estadoObraActual,
                                                            estadoObraActualFechaTerminacion, estadoObraDiferencia, duracionObraContractualTotal, duracionObraContractualTranscurrida,
                                                            "###", duracionObraContractualFaltante, duracionObraContractualFaltantePorcentaje);

                                                    console.log("arrayImagenes ", arrayImagenes);

                                                    var files = document.getElementById("file").files;

                                                    var datosReporteSemanal = {
                                                        idProyecto: idProyecto,
                                                        acumuladoAnteriorObservado: acumuladoAnteriorObservado,
                                                        acumuladoAnteriorEsperado: acumuladoAnteriorEsperado,
                                                        acumuladoAnteriorDiferencia: acumuladoAnteriorDiferencia,
                                                        acumuladoActualObservado: acumuladoActualObservado,
                                                        acumuladoActualEsperado: acumuladoActualEsperado,
                                                        acumuladoActualDiferencia: acumuladoActualDiferencia,
                                                        durantePeriodoObservado: durantePeriodoObservado,
                                                        durantePeriodoEsperado: durantePeriodoEsperado,
                                                        durantePeriodoDiferencia: durantePeriodoDiferencia,
                                                        estObraSemanasAnterior: estadoObraAnterior,
                                                        estObraFechaTerminacionAnterior: estadoObraAnteriorFechaTerminacion,
                                                        estObraSemanaActual: estadoObraActual,
                                                        estObraFechaTerminacionActual: estadoObraActualFechaTerminacion,
                                                        estObraDiferenciaSemanas: estadoObraDiferencia,
                                                        duraObraContraActualTotal: duracionObraContractualTotal,
                                                        duraObraContraActualTranscurrido: duracionObraContractualTranscurrida,
                                                        duraObraContraActualFaltante: duracionObraContractualFaltante,
                                                        duraObraContraActualFaltantePorcent: duracionObraContractualFaltantePorcentaje,
                                                        fechaGeneraReporte: hoy,
                                                        numeroSemanaGenera: semanaActual,
                                                        numeroInforme: numeroInforme,
                                                        nombrePdf: "ReporteSemanal" + semanaActual + ".pdf"

                                                    };

                                                    console.log("datosRep ", datosReporteSemanal);

                                                    for (var i = 0; i < files.length; i++) {
                                                        tamanio = jQuery("#file")[0].files[i].size;
                                                        tamanioKb = parseFloat(tamanio) / 1024;
                                                        tamanioMb = (tamanioKb * 100) / 25000;
                                                        tamanioMb = tamanioMb.toFixed(2);
                                                    }

                                                    ajaxCnk.registrarReporteSemanal(jQuery("#proyecto").val(), datosReporteSemanal, {
                                                        callback: function(data) {
                                                            if (data !== null) {

                                                                for (var i = 0; i < files.length; i++) {

                                                                    var img;
                                                                    img = new FormData();
                                                                    img.append('file', jQuery('#file')[0].files[i]);

                                                                    if (jQuery('#file')[0].files[i].type === "video/mpg" || jQuery('#file')[0].files[i].type === "viedo/mpeg" ||
                                                                            jQuery('#file')[0].files[i].type === "video/mp4") {

                                                                        jQuery.ajax({
                                                                            url: 'ServletSubirVideoReporteSemanal',
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

                                                                        verPdf(idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado,
                                                                                acumuladoAnteriorEsperado, acumuladoAnteriorDiferencia, numeroInforme, acumuladoActualObservado,
                                                                                acumuladoActualEsperado, acumuladoActualDiferencia, durantePeriodoObservado, durantePeriodoEsperado,
                                                                                durantePeriodoDiferencia, estadoObraAnterior, estadoObraAnteriorFechaTerminacion, estadoObraActual,
                                                                                estadoObraActualFechaTerminacion, estadoObraDiferencia, duracionObraContractualTotal, duracionObraContractualTranscurrida,
                                                                                duracionObraContractualFaltante, duracionObraContractualFaltantePorcentaje, semanaActual, arrayImagenes);

                                                                        console.log("numeroInforme ", numeroInforme);

                                                                        ajaxCnk.listarPdfSemanal(semanaActual, {
                                                                            callback: function(data) {
                                                                                console.log("dataPdf ", data);
                                                                                if (data !== null) {

                                                                                    tamanioPdf = parseFloat(data.tamanioPdf) / 1024;
                                                                                    console.log("tamanioPdf1 ", tamanioPdf);
                                                                                    tamanioPdf = (parseFloat(tamanioPdf) * 100) / 25000;
                                                                                    console.log("tamanioPdf2 ", tamanioPdf);
                                                                                    tamanioMb = parseFloat(tamanioMb) + parseFloat(tamanioPdf);
                                                                                    tamanioMb = tamanioMb.toFixed(2);
                                                                                    console.log("tamanioMb ", tamanioMb);

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

                        } else {

                            console.log("Entra por la parte falsa");

                            idProyecto = jQuery("#proyecto").val();
                            console.log("idProyecto ", idProyecto);
                            ajaxCnk.consultarProyectoPorId(idProyecto, {
                                callback: function(data) {
                                    if (data !== null) {
                                        nombreProyecto = data.nombreProyecto;
                                        fechaReporteDesde = data.fechaInicial;
                                        fechaReporteHasta = hoy;
                                        acumuladoAnteriorObservado = "0";
                                        //console.log("nombreProyecto ", nombreProyecto, " fechaReporteDesde ", fechaReporteDesde,
                                        //" fechaReporteHasta ", fechaReporteHasta, " acumuladoAnteriorObservado ", acumuladoAnteriorObservado);

                                        // calculo acumulado anterior esperado
                                        var fechaInicialProyecto = new Date(data.fechaInicial);
                                        var fechaFinalProyecto = new Date(data.fechaFinal);
                                        var duracionDias = data.diasDuracionProyecto;
                                        var semanasProyecto = parseFloat(duracionDias) / 7;
                                        var diasTranscurridos = 0;
                                        var semanasTranscurridas = 0;

                                        //console.log("fechaInicialProyecto ", fechaInicialProyecto, " fechaFinalProyecto ", fechaFinalProyecto,
                                        //" duracionDias ", duracionDias, " semanasProyecto ", semanasProyecto, " diasTranscurridos ", diasTranscurridos,
                                        //" semanasTranscurridas ", semanasTranscurridas);

                                        if (fechaActual >= fechaFinalProyecto) {
                                            console.log("Entra 1");
                                            acumuladoAnteriorEsperado = "100";

                                            // calculo acumulado anterior diferencia segun la condicion.
                                            acumuladoAnteriorDiferencia = parseFloat(acumuladoAnteriorObservado) - parseFloat(acumuladoAnteriorEsperado);

                                        } else if (fechaActual < fechaInicialProyecto) {
                                            console.log("Entra 2");
                                            acumuladoAnteriorEsperado = "0";

                                            // calculo acumulado anterior diferencia segun la condicion.
                                            acumuladoAnteriorDiferencia = parseFloat(acumuladoAnteriorObservado) - parseFloat(acumuladoAnteriorObservado);

                                        } else {
                                            console.log("Entra 3");

                                            fechaActual = Date.parse(fechaActual) - Date.parse(fechaInicialProyecto);
                                            console.log("fechaActual ", fechaActual);
                                            fechaActual = Math.floor(fechaActual / (1000 * 60 * 60 * 24));
                                            console.log("fechaActual---- ", fechaActual);
                                            acumuladoAnteriorEsperado = (fechaActual / duracionDias) * 100;
                                            console.log("acumuladoAnteriorEsperado ", acumuladoAnteriorEsperado);
                                            acumuladoAnteriorEsperado = acumuladoAnteriorEsperado.toFixed(2);
                                            console.log("acumuladoAnteriorEsperado----- ", acumuladoAnteriorEsperado);

                                            // calculo acumulado anterior diferencia segun la condicion.
                                            acumuladoAnteriorDiferencia = parseFloat(acumuladoAnteriorObservado) - parseFloat(acumuladoAnteriorEsperado);
                                            console.log("acumuladoAnteriorDiferencia ", acumuladoAnteriorDiferencia);

                                            //aqui se calculan los datos de durante el periodo y acumulado actual

                                            //calculo acumulado actual observado
                                            ajaxCnk.calcularAcumuladoActualObservado({
                                                callback: function(data) {
                                                    if (data !== null) {
                                                        listado = data;
                                                        for (i = 0; i < listado.length; i++) {
                                                            calculo = (parseFloat(listado[i].porcentajeActividad) * parseFloat(listado[i].porcentajeAvance) / 100);
                                                            almacenado = almacenado + calculo;
                                                        }

                                                        console.log("alamacenado else ", almacenado);

                                                        acumuladoActualObservado = almacenado.toFixed(2);

                                                        //aqui el acumulado actual esperado es igual al anterior porque es la primera vez que se va a realizar 
                                                        //el registro del reporte diario.
                                                        acumuladoActualEsperado = acumuladoAnteriorEsperado;

                                                        //acumulado actual diferencia
                                                        acumuladoActualDiferencia = parseFloat(acumuladoActualObservado) - parseFloat(acumuladoActualEsperado);

                                                        // calculos durante el periodo
                                                        durantePeriodoObservado = parseFloat(acumuladoActualObservado) - parseFloat(acumuladoAnteriorObservado);

                                                        durantePeriodoEsperado = parseFloat(acumuladoAnteriorEsperado);
                                                        durantePeriodoDiferencia = parseFloat(durantePeriodoObservado) - parseFloat(durantePeriodoEsperado);

                                                        //calculo estado de la obra
                                                        var fechaActual1 = new Date();
                                                        console.log("fechaActual ", fechaActual1, " fechaInicialProyecto ", fechaInicialProyecto);
                                                        diasTranscurridos = Date.parse(fechaActual1) - Date.parse(fechaInicialProyecto);
                                                        diasTranscurridos = Math.floor(diasTranscurridos / (1000 * 60 * 60 * 24));
                                                        console.log("diasTranscurridos ", diasTranscurridos);
                                                        semanasTranscurridas = diasTranscurridos / 7;
                                                        console.log("semanasTranscurridas ", semanasTranscurridas);

                                                        estadoObraAnterior = "0";
                                                        estadoObraAnteriorFechaTerminacion = "NaN";

                                                        estadoObraActual = ((parseFloat(acumuladoActualObservado)) - (parseFloat(semanasTranscurridas)));
                                                        estadoObraActual = estadoObraActual.toFixed(2);
                                                        estadoObraActualFechaTerminacion = hoy;
                                                        estadoObraDiferencia = parseFloat(estadoObraActual);
                                                        estadoObraDiferencia = estadoObraDiferencia.toFixed(2);

                                                        //calculo duracion de la obra
                                                        duracionObraContractualTotal = semanasProyecto.toFixed(2);
                                                        duracionObraContractualTranscurrida = semanasTranscurridas.toFixed(2);
                                                        duracionObraContractualFaltante = parseFloat(duracionObraContractualTotal) - parseFloat(duracionObraContractualTranscurrida);
                                                        duracionObraContractualFaltante = duracionObraContractualFaltante.toFixed(2);
                                                        duracionObraContractualFaltantePorcentaje = -100 / (parseFloat(duracionObraContractualTotal) * parseFloat(duracionObraContractualTranscurrida)) + 100;
                                                        duracionObraContractualFaltantePorcentaje = duracionObraContractualFaltantePorcentaje.toFixed(2);

                                                        console.log("fgh ", idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado,
                                                                acumuladoAnteriorEsperado, acumuladoAnteriorDiferencia, numeroInforme, acumuladoActualObservado,
                                                                acumuladoActualEsperado, acumuladoActualDiferencia, durantePeriodoObservado, durantePeriodoEsperado,
                                                                durantePeriodoDiferencia, estadoObraAnterior, estadoObraAnteriorFechaTerminacion, estadoObraActual,
                                                                estadoObraActualFechaTerminacion, estadoObraDiferencia, duracionObraContractualTotal, duracionObraContractualTranscurrida,
                                                                "###", duracionObraContractualFaltante, duracionObraContractualFaltantePorcentaje);

                                                        console.log("arrayImagenes ", arrayImagenes);

                                                        var files = document.getElementById("file").files;

                                                        var datosReporteSemanal = {
                                                            idProyecto: idProyecto,
                                                            acumuladoAnteriorObservado: acumuladoAnteriorObservado,
                                                            acumuladoAnteriorEsperado: acumuladoAnteriorEsperado,
                                                            acumuladoAnteriorDiferencia: acumuladoAnteriorDiferencia,
                                                            acumuladoActualObservado: acumuladoActualObservado,
                                                            acumuladoActualEsperado: acumuladoActualEsperado,
                                                            acumuladoActualDiferencia: acumuladoActualDiferencia,
                                                            durantePeriodoObservado: durantePeriodoObservado,
                                                            durantePeriodoEsperado: durantePeriodoEsperado,
                                                            durantePeriodoDiferencia: durantePeriodoDiferencia,
                                                            estObraSemanasAnterior: estadoObraAnterior,
                                                            estObraFechaTerminacionAnterior: estadoObraAnteriorFechaTerminacion,
                                                            estObraSemanaActual: estadoObraActual,
                                                            estObraFechaTerminacionActual: estadoObraActualFechaTerminacion,
                                                            estObraDiferenciaSemanas: estadoObraDiferencia,
                                                            duraObraContraActualTotal: duracionObraContractualTotal,
                                                            duraObraContraActualTranscurrido: duracionObraContractualTranscurrida,
                                                            duraObraContraActualFaltante: duracionObraContractualFaltante,
                                                            duraObraContraActualFaltantePorcent: duracionObraContractualFaltantePorcentaje,
                                                            fechaGeneraReporte: hoy,
                                                            numeroSemanaGenera: semanaActual,
                                                            numeroInforme: numeroInforme,
                                                            nombrePdf: "ReporteSemanal" + semanaActual + ".pdf"

                                                        };

                                                        console.log("DatosReporte ", datosReporteSemanal);

                                                        for (var i = 0; i < files.length; i++) {
                                                            tamanio = jQuery("#file")[0].files[i].size;
                                                            tamanioKb = parseFloat(tamanio) / 1024;
                                                            tamanioMb = (tamanioKb * 100) / 25000;
                                                            tamanioMb = tamanioMb.toFixed(2);
                                                        }

                                                        ajaxCnk.registrarReporteSemanal(jQuery("#proyecto").val(), datosReporteSemanal, {
                                                            callback: function(data) {
                                                                if (data !== null) {

                                                                    for (var i = 0; i < files.length; i++) {

                                                                        var img;
                                                                        img = new FormData();
                                                                        img.append('file', jQuery('#file')[0].files[i]);

                                                                        if (jQuery('#file')[0].files[i].type === "video/mpg" || jQuery('#file')[0].files[i].type === "viedo/mpeg" ||
                                                                                jQuery('#file')[0].files[i].type === "video/mp4") {

                                                                            jQuery.ajax({
                                                                                url: 'ServletSubirVideoReporteSemanal',
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

                                                                            verPdf(idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado,
                                                                                    acumuladoAnteriorEsperado, acumuladoAnteriorDiferencia, numeroInforme, acumuladoActualObservado,
                                                                                    acumuladoActualEsperado, acumuladoActualDiferencia, durantePeriodoObservado, durantePeriodoEsperado,
                                                                                    durantePeriodoDiferencia, estadoObraAnterior, estadoObraAnteriorFechaTerminacion, estadoObraActual,
                                                                                    estadoObraActualFechaTerminacion, estadoObraDiferencia, duracionObraContractualTotal, duracionObraContractualTranscurrida,
                                                                                    duracionObraContractualFaltante, duracionObraContractualFaltantePorcentaje, semanaActual, arrayImagenes);

                                                                            ajaxCnk.listarPdfSemanal(semanaActual, {
                                                                                callback: function(data) {
                                                                                    if (data !== null) {
                                                                                        tamanioPdf = parseFloat(data.tamanioPdf) / 1024;
                                                                                        tamanioPdf = (parseFloat(tamanioPdf) * 100) / 25000;
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

                                                                }
                                                            },
                                                            timeout: 20000
                                                        });
                                                    }
                                                },
                                                timeout: 20000
                                            });
                                        }
                                    }
                                },
                                timeout: 20000
                            });
                        }
                    },
                    timeout: 20000
                });

            } else {
                console.log("------____-----");
                //notificacion("warning", "Ya se ha Generado el reporte de esta semana del proyecto seleccionado", "alert1");
                ajaxCnk.consultarDatosReporteSemanalAnteriorPorIdProyecto(idProyecto, {
                    callback: function(data) {
                        if (data !== null) {
                            console.log("Entra por la parte actualizar", data.id);
                            var idSemanal = data.id;

                            var fechaInicialProyecto = new Date(data.proyFechaIni);
                            var fechaFinalProyecto = new Date(data.proyFechaFin);
                            var duracionDias = data.proyDuracionDias;
                            console.log("duracionDias ", duracionDias, " -- ", data.proyDuracionDias);
                            var semanasProyecto = parseFloat(duracionDias) / 7;

                            var diasTranscurridos = 0;
                            var semanasTranscurridas = 0;

                            //datos Reporte semananal anterior
                            nombreProyecto = data.nombreProyecto;
                            fechaReporteDesde = data.fechaGeneraReporte;
                            fechaReporteHasta = hoy;
                            acumuladoAnteriorObservado = data.acumuladoAnteriorObservado;
                            acumuladoAnteriorEsperado = data.acumuladoAnteriorEsperado;
                            acumuladoAnteriorDiferencia = data.acumuladoAnteriorDiferencia;
                            estadoObraAnterior = data.estObraSemanasAnterior;
                            estadoObraAnteriorFechaTerminacion = data.estObraFechaTerminacionAnterior;

                            //aqui se calculan los datos de durante el periodo y acumulado actual

                            ajaxCnk.calcularAcumuladoActualObservado({
                                callback: function(data) {
                                    if (data !== null) {
                                        listado = data;
                                        console.log("listado ", listado);
                                        for (i = 0; i < listado.length; i++) {
                                            calculo = (parseFloat(listado[i].porcentajeActividad) * parseFloat(listado[i].porcentajeAvance) / 100);
                                            almacenado = almacenado + calculo;
                                        }

                                        console.log("almacenado ", almacenado);

                                        acumuladoActualObservado = almacenado.toString();

                                        // se calcula el acumulado actual esperado 

                                        ajaxCnk.consultarProyectoPorId(idProyecto, {
                                            callback: function(data) {
                                                if (data !== null) {
                                                    calculoFechaActual = Date.parse(fechaActual) - Date.parse(fechaInicialProyecto);
                                                    calculoFechaActual = Math.floor(calculoFechaActual / (1000 * 60 * 60 * 24));
                                                    acumuladoActualEsperado = (parseFloat(calculoFechaActual) / parseFloat(duracionDias)) * 100;
                                                    acumuladoActualEsperado = acumuladoActualEsperado.toFixed(2);
                                                    console.log("acumuladoActualEsperado ", acumuladoActualEsperado);

                                                    //otros calculos 

                                                    // se calcula actual diferencia
                                                    acumuladoActualDiferencia = parseFloat(acumuladoActualObservado) - parseFloat(acumuladoActualEsperado);

                                                    // calculos durante el periodo
                                                    durantePeriodoObservado = parseFloat(acumuladoActualObservado) - parseFloat(acumuladoAnteriorObservado);
                                                    durantePeriodoEsperado = parseFloat(acumuladoActualEsperado) - parseFloat(acumuladoAnteriorEsperado);
                                                    console.log("acumuladoActualEsperado ", acumuladoActualEsperado, " acumuladoAnteriorEsperado ", acumuladoAnteriorEsperado);
                                                    console.log("durantePeriodoEsperado ", durantePeriodoEsperado);
                                                    durantePeriodoDiferencia = parseFloat(durantePeriodoObservado) - parseFloat(durantePeriodoEsperado);


                                                    //calculo estado de la obra 
                                                    var fechaActual1 = new Date();
                                                    diasTranscurridos = Date.parse(fechaActual1) - Date.parse(fechaInicialProyecto);
                                                    diasTranscurridos = Math.floor(diasTranscurridos / (1000 * 60 * 60 * 24));
                                                    console.log("diasTranscurridos ", diasTranscurridos);
                                                    semanasTranscurridas = diasTranscurridos / 7;
                                                    console.log("semanasTranscurridas ", semanasTranscurridas);

                                                    estadoObraActual = ((parseFloat(acumuladoActualObservado)) - (parseFloat(semanasTranscurridas)));
                                                    estadoObraActual = estadoObraActual.toFixed(2);
                                                    estadoObraActualFechaTerminacion = hoy;
                                                    estadoObraDiferencia = ((parseFloat(estadoObraAnterior)) - (parseFloat(estadoObraActual)));
                                                    estadoObraDiferencia = estadoObraDiferencia.toFixed(2);

                                                    console.log("duracionObra", duracionObraContractualTotal);

                                                    //calculo duracion de la obra
                                                    duracionObraContractualTotal = semanasProyecto.toString();
                                                    duracionObraContractualTranscurrida = semanasTranscurridas.toFixed(2);
                                                    duracionObraContractualFaltante = parseFloat(duracionObraContractualTotal) - parseFloat(duracionObraContractualTranscurrida);
                                                    duracionObraContractualFaltante = duracionObraContractualFaltante.toFixed(2);
                                                    console.log("duracionObraContractualTotal ", duracionObraContractualTotal, " duracionObraContractualTranscurrida ", duracionObraContractualTranscurrida);
                                                    duracionObraContractualFaltantePorcentaje = -100 / (parseFloat(duracionObraContractualTotal) * parseFloat(duracionObraContractualTranscurrida)) + 100;
                                                    duracionObraContractualFaltantePorcentaje = duracionObraContractualFaltantePorcentaje.toFixed(2);
                                                    console.log("duracionObraContractualFaltantePorcentaje ", duracionObraContractualFaltantePorcentaje);

                                                    //registro de reporte semanal

                                                    console.log("fgh ", idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado,
                                                            acumuladoAnteriorEsperado, acumuladoAnteriorDiferencia, numeroInforme, acumuladoActualObservado,
                                                            acumuladoActualEsperado, acumuladoActualDiferencia, durantePeriodoObservado, durantePeriodoEsperado,
                                                            durantePeriodoDiferencia, estadoObraAnterior, estadoObraAnteriorFechaTerminacion, estadoObraActual,
                                                            estadoObraActualFechaTerminacion, estadoObraDiferencia, duracionObraContractualTotal, duracionObraContractualTranscurrida,
                                                            "###", duracionObraContractualFaltante, duracionObraContractualFaltantePorcentaje);

                                                    console.log("arrayImagenes ", arrayImagenes);

                                                    var files = document.getElementById("file").files;

                                                    var datosReporteSemanal = {
                                                        idProyecto: idProyecto,
                                                        acumuladoAnteriorObservado: acumuladoAnteriorObservado,
                                                        acumuladoAnteriorEsperado: acumuladoAnteriorEsperado,
                                                        acumuladoAnteriorDiferencia: acumuladoAnteriorDiferencia,
                                                        acumuladoActualObservado: acumuladoActualObservado,
                                                        acumuladoActualEsperado: acumuladoActualEsperado,
                                                        acumuladoActualDiferencia: acumuladoActualDiferencia,
                                                        durantePeriodoObservado: durantePeriodoObservado,
                                                        durantePeriodoEsperado: durantePeriodoEsperado,
                                                        durantePeriodoDiferencia: durantePeriodoDiferencia,
                                                        estObraSemanasAnterior: estadoObraAnterior,
                                                        estObraFechaTerminacionAnterior: estadoObraAnteriorFechaTerminacion,
                                                        estObraSemanaActual: estadoObraActual,
                                                        estObraFechaTerminacionActual: estadoObraActualFechaTerminacion,
                                                        estObraDiferenciaSemanas: estadoObraDiferencia,
                                                        duraObraContraActualTotal: duracionObraContractualTotal,
                                                        duraObraContraActualTranscurrido: duracionObraContractualTranscurrida,
                                                        duraObraContraActualFaltante: duracionObraContractualFaltante,
                                                        duraObraContraActualFaltantePorcent: duracionObraContractualFaltantePorcentaje,
                                                        fechaGeneraReporte: hoy,
                                                        numeroSemanaGenera: semanaActual,
                                                        numeroInforme: numeroInforme,
                                                        nombrePdf: "ReporteSemanal" + semanaActual + ".pdf"

                                                    };

                                                    console.log("datosRep ", datosReporteSemanal);

                                                    for (var i = 0; i < files.length; i++) {
                                                        tamanio = jQuery("#file")[0].files[i].size;
                                                        tamanioKb = parseFloat(tamanio) / 1024;
                                                        tamanioMb = (tamanioKb * 100) / 25000;
                                                        tamanioMb = tamanioMb.toFixed(2);
                                                    }

                                                    ajaxCnk.editarReporteSemanal(jQuery("#proyecto").val(), datosReporteSemanal, idSemanal, {
                                                        callback: function(data) {
                                                            if (data !== null) {

                                                                for (var i = 0; i < files.length; i++) {

                                                                    var img;
                                                                    img = new FormData();
                                                                    img.append('file', jQuery('#file')[0].files[i]);

                                                                    if (jQuery('#file')[0].files[i].type === "video/mpg" || jQuery('#file')[0].files[i].type === "viedo/mpeg" ||
                                                                            jQuery('#file')[0].files[i].type === "video/mp4") {

                                                                        jQuery.ajax({
                                                                            url: 'ServletSubirVideoReporteSemanal',
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

                                                                        verPdf(idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado,
                                                                                acumuladoAnteriorEsperado, acumuladoAnteriorDiferencia, numeroInforme, acumuladoActualObservado,
                                                                                acumuladoActualEsperado, acumuladoActualDiferencia, durantePeriodoObservado, durantePeriodoEsperado,
                                                                                durantePeriodoDiferencia, estadoObraAnterior, estadoObraAnteriorFechaTerminacion, estadoObraActual,
                                                                                estadoObraActualFechaTerminacion, estadoObraDiferencia, duracionObraContractualTotal, duracionObraContractualTranscurrida,
                                                                                duracionObraContractualFaltante, duracionObraContractualFaltantePorcentaje, semanaActual, arrayImagenes);

                                                                        console.log("numeroInforme ", numeroInforme);

                                                                        ajaxCnk.listarPdfSemanal(semanaActual, {
                                                                            callback: function(data) {
                                                                                console.log("dataPdf ", data);
                                                                                if (data !== null) {

                                                                                    tamanioPdf = parseFloat(data.tamanioPdf) / 1024;
                                                                                    console.log("tamanioPdf1 ", tamanioPdf);
                                                                                    tamanioPdf = (parseFloat(tamanioPdf) * 100) / 25000;
                                                                                    console.log("tamanioPdf2 ", tamanioPdf);
                                                                                    tamanioMb = parseFloat(tamanioMb) + parseFloat(tamanioPdf);
                                                                                    tamanioMb = tamanioMb.toFixed(2);
                                                                                    console.log("tamanioMb ", tamanioMb);

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
                //ajaxCnk.listarAvancesDiarios(idProyecto, {
                //callback: function(data) {
                //if (data !== null) {
                //var idAvance = data[data.length - 1].id;
                $.ajax({
                    url: 'ServletEnviaPdfRepSemanal',
                    data: {
                        semanaActual: semanaActual,
                        correo: correo
                    },
                    type: 'POST'
                });
                //notificacion("success", "Correo enviado satisfactoriamente", "alert");
                setTimeout('', '2000');

                ajaxCnk.ultimoIdDeReporteSemanal({
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

                //} else {
                //notificacion("warning", "no hay avances en este proyecto", "alert");
                //}

                //},
                //timeout: 20000
                //});
            } else {
                notificacion("warning", "no hay un correo registrado en este Proyecto", "alert");
            }
        },
        timeout: 20000
    });

}

function eliminarReporte() {
    ajaxCnk.ultimoIdDeReporteSemanal({
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

function verPdf(idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado, acumuladoAnteriorEsperado,
        acumuladoAnteriorDiferencia, numeroInforme, acumuladoActualObservado, acumuladoActualEsperado, acumuladoActualDiferencia,
        durantePeriodoObservado, durantePeriodoEsperado, durantePeriodoDiferencia, estadoObraAnterior, estadoObraAnteriorFechaTerminacion,
        estadoObraActual, estadoObraActualFechaTerminacion, estadoObraDiferencia, duracionObraContractualTotal, duracionObraContractualTranscurrida,
        duracionObraContractualFaltante, duracionObraContractualFaltantePorcentaje, semanaActual, arrayImagenes) {

    console.log("entraAqui funcion ver pdf");

    console.log("ver pdf", idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado, acumuladoAnteriorEsperado,
            acumuladoAnteriorDiferencia, numeroInforme, acumuladoActualObservado, acumuladoActualEsperado, acumuladoActualDiferencia, durantePeriodoObservado,
            durantePeriodoEsperado, durantePeriodoDiferencia, estadoObraAnterior, estadoObraAnteriorFechaTerminacion, estadoObraActual,
            estadoObraActualFechaTerminacion, estadoObraDiferencia, duracionObraContractualTotal, duracionObraContractualTranscurrida, "$$$",
            duracionObraContractualFaltante, duracionObraContractualFaltantePorcentaje, semanaActual, arrayImagenes);

    //setTimeout("alert('pasaron dos segundos desde el click');", 500);

    jQuery("#btnCallModalImprimirPlan").click();

    var object = "<iframe style=\"height: 470px;\" src='<%= request.getContextPath()%>/DescargarPdfSemanal?idProyecto=" + idProyecto +
            "&nombreProyecto=" + nombreProyecto + "&fechaReporteDesde=" + fechaReporteDesde + "&fechaReporteHasta=" + fechaReporteHasta +
            "&acumuladoAnteriorObservado=" + acumuladoAnteriorObservado + "&acumuladoAnteriorEsperado=" + acumuladoAnteriorEsperado
            + "&acumuladoAnteriorDiferencia=" + acumuladoAnteriorDiferencia + "&numeroInforme=" + numeroInforme + "&acumuladoActualObservado=" +
            acumuladoActualObservado + "&acumuladoActualEsperado=" + acumuladoActualEsperado + "&acumuladoActualDiferencia=" +
            acumuladoActualDiferencia + "&durantePeriodoObservado=" + durantePeriodoObservado + "&durantePeriodoEsperado=" + durantePeriodoEsperado +
            "&durantePeriodoDiferencia=" + durantePeriodoDiferencia + "&estadoObraAnterior=" + estadoObraAnterior + "&estadoObraAnteriorFechaTerminacion=" +
            estadoObraAnteriorFechaTerminacion + "&estadoObraActual=" + estadoObraActual + "&estadoObraActualFechaTerminacion=" +
            estadoObraActualFechaTerminacion + "&estadoObraDiferencia=" + estadoObraDiferencia + "&duracionObraContractualTotal=" +
            duracionObraContractualTotal + "&duracionObraContractualTranscurrida=" + duracionObraContractualTranscurrida +
            "&duracionObraContractualFaltante=" + duracionObraContractualFaltante + "&duracionObraContractualFaltantePorcentaje=" +
            duracionObraContractualFaltantePorcentaje + "&semanaActual=" + semanaActual + "&arrayImagenes=" + arrayImagenes + "' class=\"col-xs-12\"></iframe>";

    jQuery("#modalPlanDePagoImprimir").show();
    jQuery("#modalVisualizar").html(object);

}

function registrarReporteSemanal() {

    ajaxCnk.ultimoIdDeReporte({
        callback: function(data) {
            if (data !== null) {
                var ultimoId = parseInt(data);
                var codigo = ultimoId + 1;
                numeroInforme = codigo;
            }
        },
        timeout: 20000
    });

    ajaxCnk.ultimoReporteSemanalGenerado({
        callback: function(data) {
            if (data == null) {
                ajaxCnk.consultarDatosReporteSemanalAnteriorPorIdProyecto(jQuery("#proyecto").val(), {
                    callback: function(data) {

                        if (data !== null) {
                            nombreProyecto = data.nombreProyecto;
                            fechaReporteDesde = data.fechaGeneraReporte;
                            fechaReporteHasta = hoy;
                            acumuladoAnteriorObservado = data.acumuladoAnteriorObservado;
                            acumuladoAnteriorEsperado = data.acumuladoAnteriorEsperado;
                            acumuladoAnteriorDiferencia = data.acumuladoAnteriorDiferencia;
                            verPdf(idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado, acumuladoAnteriorEsperado, acumuladoAnteriorDiferencia, numeroInforme);
                        } else {
                            ajaxCnk.consultarProyectoPorId(idProyecto, {
                                callback: function(data) {
                                    console.log("##### ", data);
                                    console.log("$$$$ ", idProyecto);
                                    console.log("fechaFinalProy ", data.fechaFinal);
                                    if (data !== null) {
                                        nombreProyecto = data.nombreProyecto;
                                        fechaReporteDesde = data.fechaInicial;
                                        fechaReporteHasta = hoy;
                                        acumuladoAnteriorObservado = "0";
                                        var fechaFinalProyecto = data.fechaFinal;
                                        var fechaInicialProyecto = data.fechaInicial;
                                        var fiProy = new Date(fechaInicialProyecto);
                                        var ffProy = new Date(fechaFinalProyecto);
                                        var fechaActual = new Date();
                                        console.log("fechaActual ", fechaActual);
                                        console.log("ffproy ", ffProy);
                                        if (fechaActual >= ffProy) {
                                            console.log("dentra1");
                                            acumuladoAnteriorEsperado = "100";
                                            acumuladoAnteriorDiferencia = parseFloat(acumuladoAnteriorObservado) - parseFloat(acumuladoAnteriorEsperado);
                                        } else if (fechaActual < fiProy) {
                                            console.log("dentra2");
                                            acumuladoAnteriorEsperado = "0";
                                            acumuladoAnteriorDiferencia = parseFloat(acumuladoAnteriorObservado) - parseFloat(acumuladoAnteriorObservado);
                                        } else {
                                            console.log("dentra3");
                                            var fechaAct = Date.parse(fechaActual);
                                            var fiPro = Date.parse(fiProy);
                                            fechaAct = fechaAct - fiPro;
                                            var resultado = Math.floor(fechaAct / (1000 * 60 * 60 * 24));
                                            //calculo dias duracion de proyecto
                                            var duracionDias = ffProy - fiProy;
                                            duracionDias = Math.floor(duracionDias / (1000 * 60 * 60 * 24));
                                            console.log("dura ", duracionDias);
                                            var esperadoAcumulado = (resultado / duracionDias) * 100;
                                            console.log("esperadoAcumulado ", esperadoAcumulado);
                                            acumuladoAnteriorEsperado = esperadoAcumulado.toFixed(2);
                                            acumuladoAnteriorDiferencia = parseFloat(acumuladoAnteriorObservado) - parseFloat(acumuladoAnteriorEsperado);
                                        }

                                        //aqui se calcularan los datos de durante el periodo y acumulado actual
                                        var observado;
                                        var listado = [];
                                        var calculo = 0;
                                        var almacenado = 0;
                                        ajaxCnk.calcularAcumuladoActualObservado({
                                            callback: function(data) {
                                                //console.log("dentraaqui ", data);
                                                if (data !== null) {
                                                    listado = data;
                                                    for (i = 0; i < listado.length; i++) {
                                                        calculo = (parseFloat(listado[i].porcentajeActividad) * parseFloat(listado[i].porcentajeAvance) / 100);
                                                        almacenado = almacenado + calculo;
                                                    }
                                                    //console.log("almacenado ", almacenado);
                                                    acumuladoActualObservado = almacenado.toString();
                                                    //console.log("acumuladoActualObservado ", acumuladoActualObservado);
                                                    acumuladoActualEsperado = acumuladoAnteriorEsperado;
                                                    //console.log("acumuladoActualEsperado ", acumuladoActualEsperado);
                                                    acumuladoActualDiferencia = parseFloat(acumuladoActualObservado) - parseFloat(acumuladoActualEsperado);
                                                    //console.log("acumuladoActualDiferencia ", acumuladoActualDiferencia);

                                                    durantePeriodoObservado = parseFloat(acumuladoActualObservado) - parseFloat(acumuladoAnteriorObservado);
                                                    //console.log("durantePeriodoObservado ", acumuladoActualDiferencia);
                                                    durantePeriodoEsperado = parseFloat(acumuladoAnteriorEsperado);
                                                    //console.log("durantePeriodoEsperado ", durantePeriodoEsperado);
                                                    durantePeriodoDiferencia = parseFloat(durantePeriodoObservado) - parseFloat(durantePeriodoEsperado);
                                                    //console.log("durantePeriodoDiferencia ", durantePeriodoDiferencia);

                                                } else {
                                                    notificacion("danger", "prueba", "alert");
                                                }

                                                estadoObraAnterior = "0";
                                                estadoObraAnteriorFechaTerminacion = "NaN";
                                                for (i = 0; i < listado.length; i++) {
                                                    var fechaIniProy = data[0].fechaIniProyecto;
                                                    var fechaFinProy = data[0].fechaFinProyecto;
                                                }

                                                //console.log("fechaIniProy ", fechaIniProy);
                                                var fechaIP = new Date(fechaIniProy);
                                                var fechaFP = new Date(fechaFinProy);
                                                //console.log("fechaIP ", fechaIP);
                                                var actual = new Date(hoy);
                                                //console.log("actual ", actual);
                                                var dias = actual - fechaIP;
                                                //console.log("dias1 ", dias);
                                                dias = Math.floor(dias / (1000 * 60 * 60 * 24));
                                                //console.log("dias2 ", dias);
                                                var semanasTranscurridas = dias / 7;
                                                //console.log("semanas ", semanasTranscurridas);
                                                estadoObraActual = ((parseFloat(acumuladoActualObservado)) - (parseFloat(semanasTranscurridas)));
                                                estadoObraActualFechaTerminacion = hoy;
                                                //estadoObraDiferencia = ((parseFloat(estadoObraAnterior)) - (parseFloat(estadoObraActual)));
                                                estadoObraDiferencia = parseFloat(estadoObraActual);

                                                var diasProyecto = fechaFP - fechaIP;
                                                diasProyecto = Math.floor(diasProyecto / (1000 * 60 * 60 * 24));
                                                var semanasProyecto = diasProyecto / 7;
                                                duracionObraContractualTotal = semanasProyecto.toString();

                                                duracionObraContractualTranscurrida = semanasTranscurridas.toFixed(2);
                                                duracionObraContractualFaltante = parseFloat(duracionObraContractualTotal) - parseFloat(duracionObraContractualTranscurrida);
                                                duracionObraContractualFaltantePorcentaje = -100 / (parseFloat(duracionObraContractualTotal) * parseFloat(duracionObraContractualTranscurrida)) + 100;
                                                duracionObraContractualFaltante = duracionObraContractualFaltante.toFixed(2);
                                                duracionObraContractualFaltantePorcentaje = duracionObraContractualFaltantePorcentaje.toFixed(2);
                                                estadoObraDiferencia = estadoObraDiferencia.toFixed(2);
                                                estadoObraActual = estadoObraActual.toFixed(2);
                                                estadoObraAnterior = parseFloat(estadoObraAnterior);
                                                estadoObraAnterior = estadoObraAnterior.toFixed(2);

                                                ///// calculo de datos que necesitan prorroga ///////

                                                /////////////// esperar respuesta proyectos /////////////

                                                //////////////////////////////////////////////////////////

                                                console.log("fgh ", idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado,
                                                        acumuladoAnteriorEsperado, acumuladoAnteriorDiferencia, numeroInforme, acumuladoActualObservado,
                                                        acumuladoActualEsperado, acumuladoActualDiferencia, durantePeriodoObservado, durantePeriodoEsperado,
                                                        durantePeriodoDiferencia, estadoObraAnterior, estadoObraAnteriorFechaTerminacion, estadoObraActual,
                                                        estadoObraActualFechaTerminacion, estadoObraDiferencia, duracionObraContractualTotal, duracionObraContractualTranscurrida,
                                                        "###", duracionObraContractualFaltante, duracionObraContractualFaltantePorcentaje);

                                                verPdf(idProyecto, nombreProyecto, fechaReporteDesde, fechaReporteHasta, acumuladoAnteriorObservado,
                                                        acumuladoAnteriorEsperado, acumuladoAnteriorDiferencia, numeroInforme, acumuladoActualObservado,
                                                        acumuladoActualEsperado, acumuladoActualDiferencia, durantePeriodoObservado, durantePeriodoEsperado,
                                                        durantePeriodoDiferencia, estadoObraAnterior, estadoObraAnteriorFechaTerminacion, estadoObraActual,
                                                        estadoObraActualFechaTerminacion, estadoObraDiferencia, duracionObraContractualTotal, duracionObraContractualTranscurrida,
                                                        duracionObraContractualFaltante, duracionObraContractualFaltantePorcentaje);

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

            } else {
                notificacion("warning", "Ya se ha Generado el reporte de esta semana del proyecto seleccionado", "alert");
            }
        },
        timeout: 20000
    });
}

function cierraModal() {
    //cerrarReporte();
    //jQuery("#modalPlanDePagoImprimir").hide();
    jQuery("#btnCerrarEnviar").prop('disabled', false);
    notificacion("success", "ya se ha pre-vizualizado el reporte diario, se desea cerrarlo de click en el boton Cerrar Enviar", "alert");
}

function cerrarReporte() {
    jQuery("#verReporte").hide();
}

function notificacion(tipo, msj, id) {
    $(".alert").alert('close');
    $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
    setTimeout('$(".alert").alert("close");', '4000');
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
            registrarReporteSemanal();
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
