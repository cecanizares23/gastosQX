<%-- 
    Document   : index
    Created on : 25/10/2017, 05:37:45 PM
    Author     : carlos
--%>

<%@page import="co.IngCarlos.gastosQX.mvc.dto.*"%>
<%@ page import="java.util.*,java.io.*"%>
<%@page session='true'%>

<%
    DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");
    ArrayList<MenuDTO> menu = datosUsuario.getMenu();
%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="UTF-8">
        <title>Gastos CSJ</title>
        <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <!-- favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">

        <!-- bootstrap framework -->
        <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="all">

        <!-- icon sets -->
        <!-- elegant icons -->
        <link href="assets/icons/elegant/style.css" rel="stylesheet" media="all">
        <!-- elusive icons -->
        <link href="assets/icons/elusive/css/elusive-webfont.css" rel="stylesheet" media="all">
        <!-- flags -->
        <link rel="stylesheet" href="assets/icons/flags/flags.css" media="all">
        <!-- scrollbar -->
        <link rel="stylesheet" href="assets/lib/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.css">

        <!-- select2 -->
        <link href="assets/lib/select2/select2.css" rel="stylesheet" media="all">
        <!-- datepicker -->
        <link href="assets/lib/bootstrap-datepicker/css/datepicker3.css" rel="stylesheet" media="all">
        <!-- date range picker -->
        <link href="assets/lib/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" media="all">
        <!-- rangeSlider -->
        <link href="assets/lib/ion.rangeSlider/css/ion.rangeSlider.css" rel="stylesheet" media="all">
        <link href="assets/lib/ion.rangeSlider/css/ion.rangeSlider.skinFlat.css" rel="stylesheet" media="all">
        <!-- uplaoder -->
        <link href="assets/lib/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css" rel="stylesheet" media="all">
        <!-- icheck -->
        <link href="assets/lib/iCheck/skins/minimal/blue.css" rel="stylesheet" media="all">
        <!-- selectize.js -->
        <link href="assets/lib/selectize-js/css/selectize.css" rel="stylesheet" media="all">

        <!-- google webfonts -->
        <link href='http://fonts.googleapis.com/css?family=Open+Sans&amp;subset=latin,latin-ext' rel='stylesheet' type='text/css'>         

        <!-- main stylesheet -->
        <link href="assets/css/main.min.css" rel="stylesheet" media="all" id="mainCss">

        <!-- print stylesheet -->
        <link href="assets/css/print.css" rel="stylesheet" media="print">

        <!-- moment.js (date library) -->
        <script src="assets/js/moment-with-langs.min.js"></script>

        <!-- jBox -->
        <link href="assets/lib/jBox-0.3.0/Source/jBox.css" rel="stylesheet" media="all">
        <link href="assets/lib/jBox-0.3.0/Source/themes/NoticeBorder.css" rel="stylesheet" media="all">

        <!-- select2 -->
        <link href="assets/lib/select2/select2.css" rel="stylesheet" media="all">

    </head>

    <body class="side_menu_active side_menu_expanded">

        <!--<div id="spinner" class="spinner">
            <div class="Spinner">
                <div class="mascara"></div>
                <div  class="imagenSpinner">

                </div>
            </div>
        </div>-->

        <!--Inicio Encabezado de las paginas -->
        <div id="panelPrincipal1">



        </div>
        <!--Fin Header-->

        <!--Inicio cuerpo-->         
        <div id="main_wrapper"> 

            <div class="container-fluid" id="contenido1">

                <input type="text" id="idProyecto" hidden>                
            </div>
        </div>

        <!--Inicio main menu -->
        <nav id="main_menu">
            <div class="menu_wrapper" id="menu1">

                <ul>
                    <li class="first_level">
                        <a onclick="javascript:cargarPagina('pagina-inicial.jsp');">
                            <span class="icon_house_alt first_level_icon"></span>
                            <span class="menu-title">Inicio</span>
                        </a>
                    </li>



                    <% for (int i = 0; i < menu.size(); i++) {%>
                    <li class="first_level">
                        <a href="javascript:void(0)">
                            <span class="<%=menu.get(i).getIconoMenu()%> first_level_icon"></span>
                            <span class="menu-title"><%=menu.get(i).getTituloMenu()%></span>
                        </a>

                        <ul>
                            <li class="submenu-title"><%=menu.get(i).getTituloMenu()%></li>
                                <% for (int j = 0; j < menu.get(i).getFuncionalidad().size(); j++) {%>

                            <li><a href="javascript:cargarPagina('<%=menu.get(i).getFuncionalidad().get(j).getPagina()%>');"><%=menu.get(i).getFuncionalidad().get(j).getTitulo()%></a></li>
                                <% }%>
                        </ul>

                    </li>
                    <% }%>
                </ul>


            </div>


            <div class="menu_toggle">
                <span class="icon_menu_toggle">
                    <i class="arrow_carrot-2left toggle_left"></i>
                    <i class="arrow_carrot-2right toggle_right" style="display:none"></i>
                </span>
            </div>

        </nav>
        <!--Fin main menu-->



        <!-- jQuery -->
        <script src="assets/js/jquery.min.js"></script>
        <!-- jQuery Cookie -->
        <script src="assets/js/jqueryCookie.min.js"></script>
        <!-- Bootstrap Framework -->
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <!-- retina images -->
        <script src="assets/js/retina.min.js"></script>
        <!-- switchery -->
        <script src="assets/lib/switchery/dist/switchery.min.js"></script>
        <!-- typeahead -->
        <script src="assets/lib/typeahead/typeahead.bundle.min.js"></script>
        <!-- fastclick -->
        <script src="assets/js/fastclick.min.js"></script>
        <!-- match height -->
        <script src="assets/lib/jquery-match-height/jquery.matchHeight-min.js"></script>
        <!-- scrollbar -->
        <script src="assets/lib/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>

        <!-- Yukon Admin functions -->
        <script src="assets/js/yukon_all.js"></script>

        <!-- script validaciones -->
        <script src="assets/js/jquery.validate.js"></script>

        <!--<script src="datatables/js/jquery-1.12.3.js"></script>-->
        <script src="datatables/js/jquery.dataTables.min.js"></script>
        <script src="datatables/js/dataTables.bootstrap.min.js"></script>
        <!--<link href="datatables/css/bootstrap.min.css" rel="stylesheet">-->
        <link href="datatables/css/dataTables.bootstrap.min.css" rel="stylesheet">
        <link href="datatables/css/responsive.bootstrap.min.css" rel="stylesheet">
        <!-- Formateo de montos de dinero en los input -->

        <!-- page specific plugins -->        

        <!-- c3 charts -->
        <script src="assets/lib/d3/d3.min.js"></script>
        <script src="assets/lib/c3/c3.min.js"></script>
        <!-- vector maps -->
        <script src="assets/lib/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
        <script src="assets/lib/jvectormap/maps/jquery-jvectormap-world-mill-en.js"></script>
        <!-- countUp animation -->
        <script src="assets/js/countUp.min.js"></script>
        <!-- easePie chart -->
        <script src="assets/lib/easy-pie-chart/dist/jquery.easypiechart.min.js"></script>

        <!-- select2 -->
        <script src="assets/lib/select2/select2.min.js"></script>
        <!-- datepicker -->
        <script src="assets/lib/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
        <!-- date range picker -->
        <script src="assets/lib/bootstrap-daterangepicker/daterangepicker.js"></script>
        <!-- rangeSlider -->
        <script src="assets/lib/ion.rangeSlider/js/ion-rangeSlider/ion.rangeSlider.min.js"></script>
        <!-- autosize -->
        <script src="assets/lib/autosize/jquery.autosize.min.js"></script>
        <!-- inputmask -->
        <script src="assets/lib/jquery.inputmask/jquery.inputmask.bundle.min.js"></script>
        <!-- maxlength for textareas -->
        <script src="assets/lib/stopVerbosity/stopVerbosity.min.js"></script>
        <!-- uplaoder -->
        <script src="assets/lib/plupload/js/plupload.full.min.js"></script>
        <script src="assets/lib/plupload/js/jquery.plupload.queue/jquery.plupload.queue.min.js"></script>
        <!-- wysiwg editor -->
        <script src="assets/lib/ckeditor/ckeditor.js"></script>
        <script src="assets/lib/ckeditor/adapters/jquery.js"></script>
        <!-- 2col multiselect -->
        <script src="assets/lib/lou-multi-select/js/jquery.multi-select.js"></script>
        <!-- quicksearch -->
        <script src="assets/lib/quicksearch/jquery.quicksearch.min.js"></script>
        <!-- clock picker -->
        <script src="assets/lib/clock-picker/bootstrap-clockpicker.min.js"></script>
        <!-- chained selects -->
        <script src="assets/lib/jquery_chained/jquery.chained.min.js"></script>
        <!-- show/hide passwords -->
        <script src="assets/lib/hideShowPassword/hideShowPassword.min.js"></script>
        <!-- password strength metter -->
        <script src="assets/lib/jquery.pwstrength.bootstrap/pwstrength-bootstrap-1.2.2.min.js"></script>
        <!-- icheck -->
        <script src="assets/lib/iCheck/icheck.min.js"></script>
        <!-- selectize.js -->
        <script src="assets/lib/selectize-js/js/standalone/selectize.min.js"></script>

        <script src="assets/lib/footable/footable.min.js"></script>
        <script src="assets/lib/footable/footable.paginate.min.js"></script>
        <script src="assets/lib/footable/footable.filter.min.js"></script>

        <!-- validation (parsley.js) -->
        <script src="assets/js/parsley.config.js"></script>
        <script src="assets/lib/parsley/dist/parsley.min.js"></script>
        <!-- wysiwg editor -->
        <script src="assets/lib/ckeditor/ckeditor.js"></script>
        <script src="assets/lib/ckeditor/adapters/jquery.js"></script>

        <!--ajaxDWR-->
        <script type='text/javascript' src='/gastosQX/dwr/interface/ajaxGastos.js'></script>        
        <script type='text/javascript' src='/gastosQX/dwr/interface/ajaxSeguridad.js'></script>
        <script type='text/javascript' src='/gastosQX/dwr/engine.js'></script>

        <script type='text/javascript' src='/gastosQX/dwr/util.js'></script>

        <script src="assets/lib/jBox-0.3.0/Source/jBox.min.js"></script>

        <script type="text/javascript" src="assets/js/jquery.idle.js"></script>

        <script src="assets/js/moment-with-langs.min.js"></script>

        <!-- select2 -->
        <script src="assets/lib/select2/select2.min.js"></script>



        <script>

                            jQuery(document).ready(function() {
                                jQuery("#btnInactivo1").hide();
                                jQuery("#btnActivo1").show();
                                jQuery("#btnActivo2").hide();
                                jQuery("#btnInactivo2").show();
                                jQuery("#spinner").hide();
                                //$('#menu1').load("pagina-menu.jsp");
                                $('#panelPrincipal1').load("panel-superior.jsp");
                                $('#contenido1').load("pagina-inicial.jsp");
                                //cargarSpinner();
                            });

                            function notificacion(tipo, msj, id) {
                                $(".alert").alert('close');
                                $("#" + id).append('<div class="alert alert-' + tipo + '" role="alert">\n\
                                           <button type="button" class="close" data-dismiss="alert" aria-label="Close">\n\
                                               <span aria-hidden="true">&times;</span>\n\
                                           </button>' + msj + '\n\
                                       </div>');
                                setTimeout('$(".alert").alert("close");', '3000');
                            }

                            function cargarPagina(pagina) {
                                $('#contenido1').load(pagina);

                            }
//                            function cargarSpinner() {
//                                $('#spinner').load("assets/js/logo.html");
//                            }


                            function cargarPaginaIdProyecto(pagina, idProyecto) {
                                $("#idProyecto").val(idProyecto);
                                $('#contenido1').load(pagina);
                            }

                            dwr.engine.setTextHtmlHandler(function() {

                                notificacion("danger", "Su sesión ha expirado por favor ingrese de nuevo", "alert");
                                setTimeout("location.reload()", "2000");
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

                            $(function() {
                                // footable
                                yukon_footable.p_plugins_tables_footable();
                            });



        </script>

    </body>

</html>