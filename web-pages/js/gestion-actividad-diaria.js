/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


jQuery(document).ready(function() {

    jQuery("#consultarActividad").show();
    jQuery("#actividadesDetallado").hide();
    jQuery("#divTituloListadoActividadesPadre").show();
    jQuery("#divFiltroActividadesPadre").show();
    $("#divTituloListadoHijas").hide();
    jQuery("#divTituloListadoPadres").hide();
    jQuery("#divTablas").hide();

    console.log("idUusssss ", idUsuarioSesion);

    ajaxCnk.listarProyectoPorIdUsuario(idUsuarioSesion, {
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
});

var listadoActividades = [];
var mapaListadoActividades = [
    function(data) {
        return '<div  id="' + data.id + '"><td><button class="btn-link status-active" title="Active" onclick="javascript:consultarActividadHija(' + data.id + ');"><i class="el-icon-plus"></i></Button></td></div>';
    },
    function(data) {
        return data.contador;
    },
    function(data) {
        return data.id;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        if (data.estadoActividad == 1) {
            var Abierta = 'Abierta';
            return Abierta;
        } else if (data.estadoActividad == 0) {
            var Terminada = 'Terminada';
            return Terminada;
        }
    },
    function(data) {
        return '<td>' + data.porcentajeActividad + '%</td>';
    },
    function(data) {
        return data.fechaInicio;
    },
    function(data) {
        return data.fechaFin;
    },
    //cantidad de dias que faltan o han pasado realizando la actividad
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><div class=""><label type="text" class="label-control"></label></div></td>';
        } else {
            if (data.diasFaltaTermine > 0) {
                console.log("tdGreen");
                return '<td><div class="tdGreen"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine == 0) {
                console.log("tdYellow");
                return '<td><div class="tdYellow"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine < 0) {
                console.log("tdRed");
                return '<td><div class="tdRed"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            }
        }
    },
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico()" id="checkCritico" disabled></td>';
        } else {
            if (data.critico == 1) {
                console.log("critico ", data.critico);
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" checked></td>';
                //jQuery("#logoCheck").prop("checked", true);
            } else {
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" ></td>';
                //jQuery("#logoCheck").prop("checked", false);
            }
        }

    },
    //porcentaje esperado actividad
    function(data) {
        return '<td>' + data.porcentajeEsperado + '%</td>';
    },
    //porcentaje observado actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<td>' + data.porcentajeAvance + '%</td>';
        } else {
            return '<td>0%</td>';
        }

    },
    //el % que va ejecutado de la actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "' + data.porcentajeAvance + '" aria - valuemin = "0" aria - valuemax = "100" style = "width: ' + data.porcentajeAvance + '%;">' + data.porcentajeAvance + '%</div></div>';
        } else {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "0" aria - valuemin = "0" aria - valuemax = "100" style = "width: 0%;">0%</div></div>';
        }

    },
    function(data) {
        return '<td><button id="btnVerRequisitos" class="btn-info status-active" data-toggle="modal" data-target="#modalDefault" onclick="listarRequisitos(' + data.id + ')">ver</button></td>';
    },
    function(data) {
        return data.duracionDias;
    },
    function(data) {
        return data.duracionSemanas;
    }

];

function listarActividadesPadresPorIdProyectoYFecha() {

    if ($("#proyecto").val() != "" && $("#fechaIni").val() == "" || $("#fechaFin").val() == "") {

        ajaxCnk.listarActividadesPadresPorIdProyecto($("#proyecto").val(), {
            callback: function(data) {                
                if (data !== null) {                    
                    dwr.util.removeAllRows("listadoActividadesPadre");
                    listadoActividades = data;

                    dwr.util.addRows("listadoActividadesPadre", listadoActividades, mapaListadoActividades, {
                        escapeHtml: false
                    });
                    jQuery("#consultarActividad").hide();
                    jQuery("#nombreProyecto").text(data[0].nombreProyecto);
                    $("#divTituloListadoPadres").show();
                    $("#divTablas").show();
                    $("#actividadesDetallado").hide();
                    $("#divTituloListadoHijas").hide();

                    $(".tdGreen").css({"background": "#5BFF33"});
                    $(".tdYellow").css({"background": "#F9FF33"});
                    $(".tdRed").css({"background": "#FF3333"});

                    ajaxCnk.sumatoriaAvanceProyecto(jQuery("#proyecto").val(), {
                        callback: function(data) {
                            if (data != null) {
                                console.log("%%%%% ", data);
                                var sumaAvanceProy = parseFloat(data.sumaAvanceProyecto);
                                sumaAvanceProy = sumaAvanceProy.toFixed(2);
                                console.log("sumaAvanceProy11111111111 ", sumaAvanceProy);
                                if(sumaAvanceProy == "NaN"){
                                    sumaAvanceProy = 0;
                                }
                                jQuery("#barraProgreso").css("width", sumaAvanceProy + "%");
                                jQuery("#valorProgreso").text(sumaAvanceProy + "%");
                            }
                        },
                        timeout: 20000
                    });

                } else {
                    notificacion("warning", "No se encontraron resultados para este Filtro, Ingrese nuevos datos.", "alert");
                    $("#proyecto").val("");
                    $("#fechaIni").val("");
                    $("#fechaFin").val("");
                    jQuery("#actividadesDetallado").hide();
                }
            },
            timeout: 20000
        });

    } else if ($("#proyecto").val() != "" && $("#fechaIni").val() != "" && $("#fechaFin").val() != "") {

        ajaxCnk.listarActividadesPadresPorIdProyectoYFecha($("#proyecto").val(), $("#fechaIni").val(), $("#fechaFin").val(), {
            callback: function(data) {
                console.log("dataPadres ", data);
                if (data !== null) {
                    console.log("$$$$", data);
                    dwr.util.removeAllRows("listadoActividadesPadre");
                    listadoActividades = data;

                    dwr.util.addRows("listadoActividadesPadre", listadoActividades, mapaListadoActividades, {
                        escapeHtml: false
                    });
                    jQuery("#consultarActividad").hide();
                    jQuery("#nombreProyecto").text(data[0].nombreProyecto);
                    $("#divTituloListadoPadres").show();
                    $("#divTablas").show();
                    $("#actividadesDetallado").hide();
                    $("#divTituloListadoHijas").hide();

                    $(".tdGreen").css({"background": "#5BFF33"});
                    $(".tdYellow").css({"background": "#F9FF33"});
                    $(".tdRed").css({"background": "#FF3333"});

                    ajaxCnk.sumatoriaAvanceProyecto(jQuery("#proyecto").val(), {
                        callback: function(data) {
                            if (data != null) {
                                console.log(data);
                                var sumaAvanceProy = parseFloat(data.sumaAvanceProyecto);
                                sumaAvanceProy = sumaAvanceProy.toFixed(2);
                                jQuery("#barraProgreso").css("width", sumaAvanceProy + "%");
                                jQuery("#valorProgreso").text(sumaAvanceProy + "%");
                            }
                        },
                        timeout: 20000
                    });

                } else {
                    notificacion("warning", "No se encontraron resultados para este Filtro, Ingrese nuevos datos.", "alert");
                    $("#proyecto").val("");
                    $("#fechaIni").val("");
                    $("#fechaFin").val("");
                    jQuery("#actividadesDetallado").hide();
                }
            },
            timeout: 20000
        });

    }

}

var listadoActividadesHijasXIdPadre = [];
var mapaListadoActividadesHijas = [
    function(data) {
        return '<div class="tdDiv" id="'+ data.id +'"><td><button class="btn-link status-active" title="Active" onclick="javascript:consultarActividadHijaUno(' + data.id + ');"><i class="el-icon-plus"></i></Button></td></div>';
    },
    function(data) {
        return data.contador;
    },
    function(data) {
        return data.id;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return '<td><button class="btn-primary status-active" title="Active" data-toggle="modal" onclick="consultarAvancePorIdActividad(' + data.id + ')" data-target="#modalLarge"><i class="el-icon-lines"></i></Button></td>';
    },
    function(data) {
        if (data.estadoActividad == 1) {
            var Abierta = 'Abierta';
            return Abierta;
        } else if (data.estadoActividad == 0) {
            var Terminada = 'Terminada';
            return Terminada;
        }
    },
    function(data) {
        return '<td>' + data.porcentajeActividad + '%</td>';
    },
    function(data) {
        return data.fechaInicio;
    },
    function(data) {
        return data.fechaFin;
    },
    //cantidad de dias que faltan o han pasado realizando la actividad
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><div class=""><label type="text" class="label-control"></label></div></td>';
        } else {
            if (data.diasFaltaTermine > 0) {
                console.log("tdGreen");
                return '<td><div class="tdGreen"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine == 0) {
                console.log("tdYellow");
                return '<td><div class="tdYellow"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine < 0) {
                console.log("tdRed");
                return '<td><div class="tdRed"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            }
        }
    },
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico()" id="checkCritico" disabled></td>';
        } else {
            if (data.critico == 1) {
                console.log("critico ", data.critico);
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" checked></td>';
                //jQuery("#logoCheck").prop("checked", true);
            } else {
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" ></td>';
                //jQuery("#logoCheck").prop("checked", false);
            }
        }

    },
    //porcentaje esperado actividad
    function(data) {
        return '<td>' + data.porcentajeEsperado + '%</td>';
    },
    //porcentaje observado actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<td>' + data.porcentajeAvance + '%</td>';
        } else {
            return '<td>0%</td>';
        }

    },
    //el % que va ejecutado de la actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "' + data.porcentajeAvance + '" aria - valuemin = "0" aria - valuemax = "100" style = "width: ' + data.porcentajeAvance + '%;">' + data.porcentajeAvance + '%</div></div>';
        } else {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "0" aria - valuemin = "0" aria - valuemax = "100" style = "width: 0%;">0%</div></div>';
        }

    },
    function(data) {
        return '<td><button id="btnVerRequisitos" class="btn-info status-active" data-toggle="modal" data-target="#modalDefault" onclick="listarRequisitos(' + data.id + ')">ver</button></td>';
    },
    function(data) {
        return data.duracionDias;
    },
    function(data) {
        return data.duracionSemanas;
    }

];

var listadoActividadesHijasXIdPadreUno = [];
var mapaListadoActividadesHijasUno = [
    function(data) {
        return '<div class="tdDivUno" id="'+ data.id +'"><td><button class="btn-link status-active" title="Active" onclick="javascript:consultarActividadHijaDos(' + data.id + ');"><i class="el-icon-plus"></i></Button></td></div>';
    },
    function(data) {
        return data.contador;
    },
    function(data) {
        return data.id;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return '<td><button class="btn-primary status-active" title="Active" data-toggle="modal" onclick="consultarAvancePorIdActividad(' + data.id + ')" data-target="#modalLarge"><i class="el-icon-lines"></i></Button></td>';
    },
    function(data) {
        if (data.estadoActividad == 1) {
            var Abierta = 'Abierta';
            return Abierta;
        } else if (data.estadoActividad == 0) {
            var Terminada = 'Terminada';
            return Terminada;
        }
    },
    function(data) {
        return '<td>' + data.porcentajeActividad + '%</td>';
    },
    function(data) {
        return data.fechaInicio;
    },
    function(data) {
        return data.fechaFin;
    },
    //cantidad de dias que faltan o han pasado realizando la actividad
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><div class=""><label type="text" class="label-control"></label></div></td>';
        } else {
            if (data.diasFaltaTermine > 0) {
                console.log("tdGreen");
                return '<td><div class="tdGreen"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine == 0) {
                console.log("tdYellow");
                return '<td><div class="tdYellow"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine < 0) {
                console.log("tdRed");
                return '<td><div class="tdRed"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            }
        }
    },
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico()" id="checkCritico" disabled></td>';
        } else {
            if (data.critico == 1) {
                console.log("critico ", data.critico);
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" checked></td>';
                //jQuery("#logoCheck").prop("checked", true);
            } else {
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" ></td>';
                //jQuery("#logoCheck").prop("checked", false);
            }
        }

    },
    //porcentaje esperado actividad
    function(data) {
        return '<td>' + data.porcentajeEsperado + '%</td>';
    },
    //porcentaje observado actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<td>' + data.porcentajeAvance + '%</td>';
        } else {
            return '<td>0%</td>';
        }

    },
    //el % que va ejecutado de la actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "' + data.porcentajeAvance + '" aria - valuemin = "0" aria - valuemax = "100" style = "width: ' + data.porcentajeAvance + '%;">' + data.porcentajeAvance + '%</div></div>';
        } else {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "0" aria - valuemin = "0" aria - valuemax = "100" style = "width: 0%;">0%</div></div>';
        }

    },
    function(data) {
        return '<td><button id="btnVerRequisitos" class="btn-info status-active" data-toggle="modal" data-target="#modalDefault" onclick="listarRequisitos(' + data.id + ')">ver</button></td>';
    },
    function(data) {
        return data.duracionDias;
    },
    function(data) {
        return data.duracionSemanas;
    }

];

var listadoActividadesHijasXIdPadreDos = [];
var mapaListadoActividadesHijasDos = [
    function(data) {
        return '<div class="tdDivDos" id="'+ data.id +'"><td><button class="btn-link status-active" title="Active" onclick="javascript:consultarActividadHijaTres(' + data.id + ');"><i class="el-icon-plus"></i></Button></td></div>';
    },
    function(data) {
        return data.contador;
    },
    function(data) {
        return data.id;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return '<td><button class="btn-primary status-active" title="Active" data-toggle="modal" onclick="consultarAvancePorIdActividad(' + data.id + ')" data-target="#modalLarge"><i class="el-icon-lines"></i></Button></td>';
    },
    function(data) {
        if (data.estadoActividad == 1) {
            var Abierta = 'Abierta';
            return Abierta;
        } else if (data.estadoActividad == 0) {
            var Terminada = 'Terminada';
            return Terminada;
        }
    },
    function(data) {
        return '<td>' + data.porcentajeActividad + '%</td>';
    },
    function(data) {
        return data.fechaInicio;
    },
    function(data) {
        return data.fechaFin;
    },
    //cantidad de dias que faltan o han pasado realizando la actividad
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><div class=""><label type="text" class="label-control"></label></div></td>';
        } else {
            if (data.diasFaltaTermine > 0) {
                console.log("tdGreen");
                return '<td><div class="tdGreen"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine == 0) {
                console.log("tdYellow");
                return '<td><div class="tdYellow"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine < 0) {
                console.log("tdRed");
                return '<td><div class="tdRed"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            }
        }
    },
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico()" id="checkCritico" disabled></td>';
        } else {
            if (data.critico == 1) {
                console.log("critico ", data.critico);
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" checked></td>';
                //jQuery("#logoCheck").prop("checked", true);
            } else {
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" ></td>';
                //jQuery("#logoCheck").prop("checked", false);
            }
        }

    },
    //porcentaje esperado actividad
    function(data) {
        return '<td>' + data.porcentajeEsperado + '%</td>';
    },
    //porcentaje observado actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<td>' + data.porcentajeAvance + '%</td>';
        } else {
            return '<td>0%</td>';
        }

    },
    //el % que va ejecutado de la actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "' + data.porcentajeAvance + '" aria - valuemin = "0" aria - valuemax = "100" style = "width: ' + data.porcentajeAvance + '%;">' + data.porcentajeAvance + '%</div></div>';
        } else {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "0" aria - valuemin = "0" aria - valuemax = "100" style = "width: 0%;">0%</div></div>';
        }

    },
    function(data) {
        return '<td><button id="btnVerRequisitos" class="btn-info status-active" data-toggle="modal" data-target="#modalDefault" onclick="listarRequisitos(' + data.id + ')">ver</button></td>';
    },
    function(data) {
        return data.duracionDias;
    },
    function(data) {
        return data.duracionSemanas;
    }

];

var listadoActividadesHijasXIdPadreTres = [];
var mapaListadoActividadesHijasTres = [
    function(data) {
        return '<div class="tdDivTres" id="'+ data.id +'"><td><button class="btn-link status-active" title="Active" onclick="javascript:consultarActividadHijaCuatro(' + data.id + ');"><i class="el-icon-plus"></i></Button></td></div>';
    },
    function(data) {
        return data.contador;
    },
    function(data) {
        return data.id;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return '<td><button class="btn-primary status-active" title="Active" data-toggle="modal" onclick="consultarAvancePorIdActividad(' + data.id + ')" data-target="#modalLarge"><i class="el-icon-lines"></i></Button></td>';
    },
    function(data) {
        if (data.estadoActividad == 1) {
            var Abierta = 'Abierta';
            return Abierta;
        } else if (data.estadoActividad == 0) {
            var Terminada = 'Terminada';
            return Terminada;
        }
    },
    function(data) {
        return '<td>' + data.porcentajeActividad + '%</td>';
    },
    function(data) {
        return data.fechaInicio;
    },
    function(data) {
        return data.fechaFin;
    },
    //cantidad de dias que faltan o han pasado realizando la actividad
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><div class=""><label type="text" class="label-control"></label></div></td>';
        } else {
            if (data.diasFaltaTermine > 0) {
                console.log("tdGreen");
                return '<td><div class="tdGreen"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine == 0) {
                console.log("tdYellow");
                return '<td><div class="tdYellow"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine < 0) {
                console.log("tdRed");
                return '<td><div class="tdRed"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            }
        }
    },
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico()" id="checkCritico" disabled></td>';
        } else {
            if (data.critico == 1) {
                console.log("critico ", data.critico);
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" checked></td>';
                //jQuery("#logoCheck").prop("checked", true);
            } else {
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" ></td>';
                //jQuery("#logoCheck").prop("checked", false);
            }
        }

    },
    //porcentaje esperado actividad
    function(data) {
        return '<td>' + data.porcentajeEsperado + '%</td>';
    },
    //porcentaje observado actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<td>' + data.porcentajeAvance + '%</td>';
        } else {
            return '<td>0%</td>';
        }

    },
    //el % que va ejecutado de la actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "' + data.porcentajeAvance + '" aria - valuemin = "0" aria - valuemax = "100" style = "width: ' + data.porcentajeAvance + '%;">' + data.porcentajeAvance + '%</div></div>';
        } else {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "0" aria - valuemin = "0" aria - valuemax = "100" style = "width: 0%;">0%</div></div>';
        }

    },
    function(data) {
        return '<td><button id="btnVerRequisitos" class="btn-info status-active" data-toggle="modal" data-target="#modalDefault" onclick="listarRequisitos(' + data.id + ')">ver</button></td>';
    },
    function(data) {
        return data.duracionDias;
    },
    function(data) {
        return data.duracionSemanas;
    }

];

var listadoActividadesHijasXIdPadreCuatro = [];
var mapaListadoActividadesHijasCuatro = [
    function(data) {
        return '<div class="tdDivCuatro" id="'+ data.id +'"><td><button class="btn-link status-active" title="Active" onclick="javascript:consultarActividadHijaCinco(' + data.id + ');"><i class="el-icon-plus"></i></Button></td></div>';
    },
    function(data) {
        return data.contador;
    },
    function(data) {
        return data.id;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return '<td><button class="btn-primary status-active" title="Active" data-toggle="modal" onclick="consultarAvancePorIdActividad(' + data.id + ')" data-target="#modalLarge"><i class="el-icon-lines"></i></Button></td>';
    },
    function(data) {
        if (data.estadoActividad == 1) {
            var Abierta = 'Abierta';
            return Abierta;
        } else if (data.estadoActividad == 0) {
            var Terminada = 'Terminada';
            return Terminada;
        }
    },
    function(data) {
        return '<td>' + data.porcentajeActividad + '%</td>';
    },
    function(data) {
        return data.fechaInicio;
    },
    function(data) {
        return data.fechaFin;
    },
    //cantidad de dias que faltan o han pasado realizando la actividad
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><div class=""><label type="text" class="label-control"></label></div></td>';
        } else {
            if (data.diasFaltaTermine > 0) {
                console.log("tdGreen");
                return '<td><div class="tdGreen"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine == 0) {
                console.log("tdYellow");
                return '<td><div class="tdYellow"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine < 0) {
                console.log("tdRed");
                return '<td><div class="tdRed"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            }
        }
    },
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico()" id="checkCritico" disabled></td>';
        } else {
            if (data.critico == 1) {
                console.log("critico ", data.critico);
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" checked></td>';
                //jQuery("#logoCheck").prop("checked", true);
            } else {
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" ></td>';
                //jQuery("#logoCheck").prop("checked", false);
            }
        }

    },
    //porcentaje esperado actividad
    function(data) {
        return '<td>' + data.porcentajeEsperado + '%</td>';
    },
    //porcentaje observado actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<td>' + data.porcentajeAvance + '%</td>';
        } else {
            return '<td>0%</td>';
        }

    },
    //el % que va ejecutado de la actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "' + data.porcentajeAvance + '" aria - valuemin = "0" aria - valuemax = "100" style = "width: ' + data.porcentajeAvance + '%;">' + data.porcentajeAvance + '%</div></div>';
        } else {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "0" aria - valuemin = "0" aria - valuemax = "100" style = "width: 0%;">0%</div></div>';
        }

    },
    function(data) {
        return '<td><button id="btnVerRequisitos" class="btn-info status-active" data-toggle="modal" data-target="#modalDefault" onclick="listarRequisitos(' + data.id + ')">ver</button></td>';
    },
    function(data) {
        return data.duracionDias;
    },
    function(data) {
        return data.duracionSemanas;
    }

];

var listadoActividadesHijasXIdPadreCinco = [];
var mapaListadoActividadesHijasCinco = [
    function(data) {
        return '<div class="tdDivCinco" id="'+ data.id +'"><td><button class="btn-link status-active" title="Active" onclick="javascript:consultarActividadHijaSeis(' + data.id + ');"><i class="el-icon-plus"></i></Button></td></div>';
    },
    function(data) {
        return data.contador;
    },
    function(data) {
        return data.id;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return '<td><button class="btn-primary status-active" title="Active" data-toggle="modal" onclick="consultarAvancePorIdActividad(' + data.id + ')" data-target="#modalLarge"><i class="el-icon-lines"></i></Button></td>';
    },
    function(data) {
        if (data.estadoActividad == 1) {
            var Abierta = 'Abierta';
            return Abierta;
        } else if (data.estadoActividad == 0) {
            var Terminada = 'Terminada';
            return Terminada;
        }
    },
    function(data) {
        return '<td>' + data.porcentajeActividad + '%</td>';
    },
    function(data) {
        return data.fechaInicio;
    },
    function(data) {
        return data.fechaFin;
    },
    //cantidad de dias que faltan o han pasado realizando la actividad
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><div class=""><label type="text" class="label-control"></label></div></td>';
        } else {
            if (data.diasFaltaTermine > 0) {
                console.log("tdGreen");
                return '<td><div class="tdGreen"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine == 0) {
                console.log("tdYellow");
                return '<td><div class="tdYellow"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine < 0) {
                console.log("tdRed");
                return '<td><div class="tdRed"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            }
        }
    },
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico()" id="checkCritico" disabled></td>';
        } else {
            if (data.critico == 1) {
                console.log("critico ", data.critico);
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" checked></td>';
                //jQuery("#logoCheck").prop("checked", true);
            } else {
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" ></td>';
                //jQuery("#logoCheck").prop("checked", false);
            }
        }

    },
    //porcentaje esperado actividad
    function(data) {
        return '<td>' + data.porcentajeEsperado + '%</td>';
    },
    //porcentaje observado actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<td>' + data.porcentajeAvance + '%</td>';
        } else {
            return '<td>0%</td>';
        }

    },
    //el % que va ejecutado de la actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "' + data.porcentajeAvance + '" aria - valuemin = "0" aria - valuemax = "100" style = "width: ' + data.porcentajeAvance + '%;">' + data.porcentajeAvance + '%</div></div>';
        } else {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "0" aria - valuemin = "0" aria - valuemax = "100" style = "width: 0%;">0%</div></div>';
        }

    },
    function(data) {
        return '<td><button id="btnVerRequisitos" class="btn-info status-active" data-toggle="modal" data-target="#modalDefault" onclick="listarRequisitos(' + data.id + ')">ver</button></td>';
    },
    function(data) {
        return data.duracionDias;
    },
    function(data) {
        return data.duracionSemanas;
    }

];

var listadoActividadesHijasXIdPadreSeis = [];
var mapaListadoActividadesHijasSeis = [
    function(data) {
        return '<div class="tdDivSeis" id="'+ data.id +'"><td><button class="btn-link status-active" title="Active" onclick="javascript:consultarActividadHijaSeis(' + data.id + ');"><i class="el-icon-plus"></i></Button></td></div>';
    },
    function(data) {
        return data.contador;
    },
    function(data) {
        return data.id;
    },
    function(data) {
        return data.nombreActividad;
    },
    function(data) {
        return '<td><button class="btn-primary status-active" title="Active" data-toggle="modal" onclick="consultarAvancePorIdActividad(' + data.id + ')" data-target="#modalLarge"><i class="el-icon-lines"></i></Button></td>';
    },
    function(data) {
        if (data.estadoActividad == 1) {
            var Abierta = 'Abierta';
            return Abierta;
        } else if (data.estadoActividad == 0) {
            var Terminada = 'Terminada';
            return Terminada;
        }
    },
    function(data) {
        return '<td>' + data.porcentajeActividad + '%</td>';
    },
    function(data) {
        return data.fechaInicio;
    },
    function(data) {
        return data.fechaFin;
    },
    //cantidad de dias que faltan o han pasado realizando la actividad
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><div class=""><label type="text" class="label-control"></label></div></td>';
        } else {
            if (data.diasFaltaTermine > 0) {
                console.log("tdGreen");
                return '<td><div class="tdGreen"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine == 0) {
                console.log("tdYellow");
                return '<td><div class="tdYellow"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            } else if (data.diasFaltaTermine < 0) {
                console.log("tdRed");
                return '<td><div class="tdRed"><label type="text" class="label-control">' + data.diasFaltaTermine + '</label></div></td>';
            }
        }
    },
    function(data) {
        if (data.estadoActividad == 0) {
            return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico()" id="checkCritico" disabled></td>';
        } else {
            if (data.critico == 1) {
                console.log("critico ", data.critico);
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" checked></td>';
                //jQuery("#logoCheck").prop("checked", true);
            } else {
                return '<td><input type="checkbox" class="icheck" onclick="actualizarCampoCritico(' + data.id + ')" id="checkCritico' + data.id + '" ></td>';
                //jQuery("#logoCheck").prop("checked", false);
            }
        }

    },
    //porcentaje esperado actividad
    function(data) {
        return '<td>' + data.porcentajeEsperado + '%</td>';
    },
    //porcentaje observado actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<td>' + data.porcentajeAvance + '%</td>';
        } else {
            return '<td>0%</td>';
        }

    },
    //el % que va ejecutado de la actividad
    function(data) {
        if (data.porcentajeAvance !== null) {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "' + data.porcentajeAvance + '" aria - valuemin = "0" aria - valuemax = "100" style = "width: ' + data.porcentajeAvance + '%;">' + data.porcentajeAvance + '%</div></div>';
        } else {
            return '<div class="progress"><div class = "progress-bar" role = "progressbar" aria - valuenow = "0" aria - valuemin = "0" aria - valuemax = "100" style = "width: 0%;">0%</div></div>';
        }

    },
    function(data) {
        return '<td><button id="btnVerRequisitos" class="btn-info status-active" data-toggle="modal" data-target="#modalDefault" onclick="listarRequisitos(' + data.id + ')">ver</button></td>';
    },
    function(data) {
        return data.duracionDias;
    },
    function(data) {
        return data.duracionSemanas;
    }

];

var idPadre;
function consultarActividadHija(id) {
    idPadre = id;
    console.log("idPadre ", idPadre);

    ajaxCnk.consultarActividadesHijasPorIdPadre(idPadre, {
        callback: function(data) {
            console.log("datahija ", data);
            if (data !== null) {
                if (data.length == 0) {
                    notificacion("warning", "Esta actividad no tiene hijos", "alert1");
                    return;
                }
                dwr.util.removeAllRows("listadoActividadesHijas");
                listadoActividadesHijasXIdPadre = data;

                dwr.util.addRows("listadoActividadesHijas", listadoActividadesHijasXIdPadre, mapaListadoActividadesHijas, {
                    escapeHtml: false
                });
                $("#actividadesDetallado").show();
                $("#divTituloListadoHijas").show();
                $("#divHijaUno").hide();
                $("#divHijaDos").hide();
                $("#divHijaTres").hide();
                $("#divHijaCuatro").hide();
                $("#divHijaCinco").hide();
                $("#divHijaSeis").hide();

                $(".tdGreen").css({"background": "#5BFF33"});
                $(".tdYellow").css({"background": "#F9FF33"});
                $(".tdRed").css({"background": "#FF3333"});
                for (var i = 0; i < listadoActividades.length; i++) {
                    console.log('------------------', listadoActividades[i].id);
                    if (listadoActividades[i].id == id) {
                        jQuery("#" + listadoActividades[i].id).addClass("tdDiv");
                    } else {
                        jQuery("#" + listadoActividades[i].id).removeClass("tdDiv");
                    }
                }
                ajaxCnk.consultarActividadPorId(idPadre, {
                    callback: function(data) {
                        console.log("###$$$$ ", data.nombreActividad);
                        if (data !== null) {
                            jQuery("#nombreActividadPadre").text(data.nombreActividad);
                        }
                    },
                    timeout: 20000
                });

            } else {
                notificacion("warning", "Esta actividad no tiene hijos", "alert1");
            }
        },
        timeout: 20000
    });
}

var idPadreUno;
function consultarActividadHijaUno(id) {
    idPadreUno = id;
    console.log("idPadreUno ", idPadreUno);

    ajaxCnk.consultarActividadesHijasPorIdPadre(idPadreUno, {
        callback: function(data) {
            console.log("datahija ", data);
            if (data !== null) {
                if (data.length == 0) {
                    notificacion("warning", "Esta actividad no tiene hijos", "alert1");
                    return;
                }
                dwr.util.removeAllRows("listadoActividadesHijasUno");
                listadoActividadesHijasXIdPadreUno = data;

                dwr.util.addRows("listadoActividadesHijasUno", listadoActividadesHijasXIdPadreUno, mapaListadoActividadesHijasUno, {
                    escapeHtml: false
                });
                $("#actividadesDetallado").show();
                $("#divTituloListadoHijas").show();
                $("#divHijaUno").show();
                $("#divHijaDos").hide();
                $("#divHijaTres").hide();
                $("#divHijaCuatro").hide();
                $("#divHijaCinco").hide();
                $("#divHijaSeis").hide();

                $(".tdGreen").css({"background": "#5BFF33"});
                $(".tdYellow").css({"background": "#F9FF33"});
                $(".tdRed").css({"background": "#FF3333"});
                for (var i = 0; i < listadoActividadesHijasXIdPadre.length; i++) {
                    console.log('------------------', listadoActividadesHijasXIdPadre[i].id);
                    if (listadoActividadesHijasXIdPadre[i].id == id) {
                        jQuery("#" + listadoActividadesHijasXIdPadre[i].id).addClass("tdDivUno");
                    } else {
                        jQuery("#" + listadoActividadesHijasXIdPadre[i].id).removeClass("tdDivUno");
                    }
                }
                ajaxCnk.consultarActividadPorId(idPadreUno, {
                    callback: function(data) {
                        console.log("###$$$$ ", data.nombreActividad);
                        if (data !== null) {
                            jQuery("#nombreActividadPadreUno").text(data.nombreActividad);
                        }
                    },
                    timeout: 20000
                });

            } else {
                notificacion("warning", "Esta actividad no tiene hijos", "alert1");
            }
        },
        timeout: 20000
    });
}

var idPadreDos;
function consultarActividadHijaDos(id) {
    idPadreDos = id;
    console.log("idPadreDos ", idPadreDos);

    ajaxCnk.consultarActividadesHijasPorIdPadre(idPadreDos, {
        callback: function(data) {
            console.log("datahija ", data);
            if (data !== null) {
                if (data.length == 0) {
                    notificacion("warning", "Esta actividad no tiene hijos", "alert1");
                    return;
                }
                dwr.util.removeAllRows("listadoActividadesHijasDos");
                listadoActividadesHijasXIdPadreDos = data;

                dwr.util.addRows("listadoActividadesHijasDos", listadoActividadesHijasXIdPadreDos, mapaListadoActividadesHijasDos, {
                    escapeHtml: false
                });
                $("#actividadesDetallado").show();
                $("#divTituloListadoHijas").show();
                $("#divHijaUno").show();
                $("#divHijaDos").show();
                $("#divHijaTres").hide();
                $("#divHijaCuatro").hide();
                $("#divHijaCinco").hide();
                $("#divHijaSeis").hide();

                $(".tdGreen").css({"background": "#5BFF33"});
                $(".tdYellow").css({"background": "#F9FF33"});
                $(".tdRed").css({"background": "#FF3333"});
                for (var i = 0; i < listadoActividadesHijasXIdPadreUno.length; i++) {
                    console.log('------------------', listadoActividadesHijasXIdPadreUno[i].id);
                    if (listadoActividadesHijasXIdPadreUno[i].id == id) {
                        jQuery("#" + listadoActividadesHijasXIdPadreUno[i].id).addClass("tdDivDos");
                    } else {
                        jQuery("#" + listadoActividadesHijasXIdPadreUno[i].id).removeClass("tdDivDos");
                    }
                }
                ajaxCnk.consultarActividadPorId(idPadreDos, {
                    callback: function(data) {
                        console.log("###$$$$ ", data.nombreActividad);
                        if (data !== null) {
                            jQuery("#nombreActividadPadreDos").text(data.nombreActividad);
                        }
                    },
                    timeout: 20000
                });

            } else {
                notificacion("warning", "Esta actividad no tiene hijos", "alert1");
            }
        },
        timeout: 20000
    });
}

var idPadreTres;
function consultarActividadHijaTres(id) {
    idPadreTres = id;
    console.log("idPadreTres ", idPadreTres);

    ajaxCnk.consultarActividadesHijasPorIdPadre(idPadreTres, {
        callback: function(data) {
            console.log("datahija ", data);
            if (data !== null) {
                if (data.length == 0) {
                    notificacion("warning", "Esta actividad no tiene hijos", "alert1");
                    return;
                }
                dwr.util.removeAllRows("listadoActividadesHijasTres");
                listadoActividadesHijasXIdPadreTres = data;

                dwr.util.addRows("listadoActividadesHijasTres", listadoActividadesHijasXIdPadreTres, mapaListadoActividadesHijasTres, {
                    escapeHtml: false
                });
                $("#actividadesDetallado").show();
                $("#divTituloListadoHijas").show();
                $("#divHijaUno").show();
                $("#divHijaDos").show();
                $("#divHijaTres").show();
                $("#divHijaCuatro").hide();
                $("#divHijaCinco").hide();
                $("#divHijaSeis").hide();

                $(".tdGreen").css({"background": "#5BFF33"});
                $(".tdYellow").css({"background": "#F9FF33"});
                $(".tdRed").css({"background": "#FF3333"});
                for (var i = 0; i < listadoActividadesHijasXIdPadreDos.length; i++) {
                    console.log('------------------', listadoActividadesHijasXIdPadreDos[i].id);
                    if (listadoActividadesHijasXIdPadreDos[i].id == id) {
                        jQuery("#" + listadoActividadesHijasXIdPadreDos[i].id).addClass("tdDivTres");
                    } else {
                        jQuery("#" + listadoActividadesHijasXIdPadreDos[i].id).removeClass("tdDivTres");
                    }
                }
                ajaxCnk.consultarActividadPorId(idPadreTres, {
                    callback: function(data) {
                        console.log("###$$$$ ", data.nombreActividad);
                        if (data !== null) {
                            jQuery("#nombreActividadPadreTres").text(data.nombreActividad);
                        }
                    },
                    timeout: 20000
                });

            } else {
                notificacion("warning", "Esta actividad no tiene hijos", "alert1");
            }
        },
        timeout: 20000
    });
}

var idPadreCuatro;
function consultarActividadHijaCuatro(id) {
    idPadreCuatro = id;
    console.log("idPadreCuatro ", idPadreCuatro);

    ajaxCnk.consultarActividadesHijasPorIdPadre(idPadreCuatro, {
        callback: function(data) {
            console.log("datahija ", data);
            if (data !== null) {
                if (data.length == 0) {
                    notificacion("warning", "Esta actividad no tiene hijos", "alert1");
                    return;
                }
                dwr.util.removeAllRows("listadoActividadesHijasCuatro");
                listadoActividadesHijasXIdPadreCuatro = data;

                dwr.util.addRows("listadoActividadesHijasCuatro", listadoActividadesHijasXIdPadreCuatro, mapaListadoActividadesHijasCuatro, {
                    escapeHtml: false
                });
                $("#actividadesDetallado").show();
                $("#divTituloListadoHijas").show();
                $("#divHijaUno").show();
                $("#divHijaDos").show();
                $("#divHijaTres").show();
                $("#divHijaCuatro").show();
                $("#divHijaCinco").hide();
                $("#divHijaSeis").hide();

                $(".tdGreen").css({"background": "#5BFF33"});
                $(".tdYellow").css({"background": "#F9FF33"});
                $(".tdRed").css({"background": "#FF3333"});
                for (var i = 0; i < listadoActividadesHijasXIdPadreTres.length; i++) {
                    console.log('------------------', listadoActividadesHijasXIdPadreTres[i].id);
                    if (listadoActividadesHijasXIdPadreTres[i].id == id) {
                        jQuery("#" + listadoActividadesHijasXIdPadreTres[i].id).addClass("tdDivCuatro");
                    } else {
                        jQuery("#" + listadoActividadesHijasXIdPadreTres[i].id).removeClass("tdDivCuatro");
                    }
                }
                ajaxCnk.consultarActividadPorId(idPadreCuatro, {
                    callback: function(data) {
                        console.log("###$$$$ ", data.nombreActividad);
                        if (data !== null) {
                            jQuery("#nombreActividadPadreCuatro").text(data.nombreActividad);
                        }
                    },
                    timeout: 20000
                });

            } else {
                notificacion("warning", "Esta actividad no tiene hijos", "alert1");
            }
        },
        timeout: 20000
    });
}

var idPadreCinco;
function consultarActividadHijaCinco(id) {
    idPadreCinco = id;
    console.log("idPadreCinco ", idPadreCinco);

    ajaxCnk.consultarActividadesHijasPorIdPadre(idPadreCinco, {
        callback: function(data) {
            console.log("datahija ", data);
            if (data !== null) {
                if (data.length == 0) {
                    notificacion("warning", "Esta actividad no tiene hijos", "alert1");
                    return;
                }
                dwr.util.removeAllRows("listadoActividadesHijasCinco");
                listadoActividadesHijasXIdPadreCinco = data;
                dwr.util.addRows("listadoActividadesHijasCinco", listadoActividadesHijasXIdPadreCinco, mapaListadoActividadesHijasCinco, {
                    escapeHtml: false
                });
                $("#actividadesDetallado").show();
                $("#divTituloListadoHijas").show();
                $("#divHijaUno").show();
                $("#divHijaDos").show();
                $("#divHijaTres").show();
                $("#divHijaCuatro").show();
                $("#divHijaCinco").show();
                $("#divHijaSeis").hide();

                $(".tdGreen").css({"background": "#5BFF33"});
                $(".tdYellow").css({"background": "#F9FF33"});
                $(".tdRed").css({"background": "#FF3333"});
                for (var i = 0; i < listadoActividadesHijasXIdPadreCuatro.length; i++) {
                    console.log('------------------', listadoActividadesHijasXIdPadreCuatro[i].id);
                    if (listadoActividadesHijasXIdPadreCuatro[i].id == id) {
                        jQuery("#" + listadoActividadesHijasXIdPadreCuatro[i].id).addClass("tdDivCinco");
                    } else {
                        jQuery("#" + listadoActividadesHijasXIdPadreCuatro[i].id).removeClass("tdDivCinco");
                    }
                }
                ajaxCnk.consultarActividadPorId(idPadreCinco, {
                    callback: function(data) {
                        console.log("###$$$$ ", data.nombreActividad);
                        if (data !== null) {
                            jQuery("#nombreActividadPadreCinco").text(data.nombreActividad);
                        }
                    },
                    timeout: 20000
                });

            } else {
                notificacion("warning", "Esta actividad no tiene hijos", "alert1");
            }
        },
        timeout: 20000
    });
}

var idPadreSeis;
function consultarActividadHijaSeis(id) {
    idPadreSeis = id;
    console.log("idPadreSeis ", idPadreSeis);

    ajaxCnk.consultarActividadesHijasPorIdPadre(idPadreSeis, {
        callback: function(data) {
            console.log("datahija ", data);
            if (data !== null) {
                if (data.length == 0) {
                    notificacion("warning", "Esta actividad no tiene hijos", "alert1");
                    return;
                }
                dwr.util.removeAllRows("listadoActividadesHijasSeis");
                listadoActividadesHijasXIdPadreSeis = data;
                dwr.util.addRows("listadoActividadesHijasSeis", listadoActividadesHijasXIdPadreSeis, mapaListadoActividadesHijasSeis, {
                    escapeHtml: false
                });
                $("#actividadesDetallado").show();
                $("#divTituloListadoHijas").show();
                $("#divHijaUno").show();
                $("#divHijaDos").show();
                $("#divHijaTres").show();
                $("#divHijaCuatro").show();
                $("#divHijaCinco").show();
                $("#divHijaSeis").show();

                $(".tdGreen").css({"background": "#5BFF33"});
                $(".tdYellow").css({"background": "#F9FF33"});
                $(".tdRed").css({"background": "#FF3333"});
                for (var i = 0; i < listadoActividadesHijasXIdPadreCinco.length; i++) {
                    console.log('------------------', listadoActividadesHijasXIdPadreCinco[i].id);
                    if (listadoActividadesHijasXIdPadreCinco[i].id == id) {
                        jQuery("#" + listadoActividadesHijasXIdPadreCinco[i].id).addClass("tdDivSeis");
                    } else {
                        jQuery("#" + listadoActividadesHijasXIdPadreCinco[i].id).removeClass("tdDivSeis");
                    }
                }
                ajaxCnk.consultarActividadPorId(idPadreSeis, {
                    callback: function(data) {
                        console.log("###$$$$ ", data.nombreActividad);
                        if (data !== null) {
                            jQuery("#nombreActividadPadreSeis").text(data.nombreActividad);
                        }
                    },
                    timeout: 20000
                });

            } else {
                notificacion("warning", "Esta actividad no tiene hijos", "alert1");
            }
        },
        timeout: 20000
    });
}

function actualizarCampoCritico(id) {
    var idActividad = id;
    var campoCritico;
    if ($('#checkCritico' + idActividad + '').prop('checked')) {
        console.log("####");
        campoCritico = 1;
    } else {
        console.log("$$$$$$");
        campoCritico = 0;
    }
    ajaxCnk.actualizarCampoCritico(campoCritico, idActividad, {
        callback: function(data) {
            if (data != null) {
                if ($('#checkCritico' + idActividad + '').prop('checked')) {
                    notificacion("info", "Esta Actividad ahora es CRITICA", "alert");
                } else {
                    notificacion("info", "Esta Actividad ha dejado de ser CRITICA", "alert");
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
        return data.fechaDigitacion;
    },
    function(data) {
        return '<td>' + data.valorAvance + '%</td>';
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

function listarRequisitos(id) {
    var idActividad = id;
    ajaxCnk.listarRequisitosPorIdActividad(idActividad, {
        callback: function(data) {
            console.log('muestra data', data);
            if (data !== null) {
                jQuery("#tablaRequisitos").show();
                jQuery("#mensaje").hide();
                console.log("$$$$", data);
                dwr.util.removeAllRows("listadoRequisitosPorActividad");
                listadoRequisitos = data;
                dwr.util.addRows("listadoRequisitosPorActividad", listadoRequisitos, mapaListadoRequisitos, {
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

function volverPadre() {
    $("#divTituloListadoPadres").show();
    $("#divTablas").show();
    $("#divTituloListadoActividadesPadre").show();
    $("#divFiltroActividadesPadre").show();
    $("#actividadesDetallado").hide();
    $("#divTituloListadoHijas").hide();
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

            listarActividadesPadresPorIdProyectoYFecha();
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
