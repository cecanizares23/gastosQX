<%-- 
    Document   : recuperar-contrasenia
    Created on : 17/10/2017, 03:59:49 PM
    Author     : carlos
--%>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Recuperar Contrasenia</title>
        <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <!-- favicon 
        <link rel="shortcut icon" type="image/x-icon" href="favicon.ico">-->

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

        <!-- page specific stylesheets -->

        <!-- select2 -->
        <link href="assets/lib/select2/select2.css" rel="stylesheet" media="all">

        <!-- google webfonts -->
        <link href='http://fonts.googleapis.com/css?family=Open+Sans&amp;subset=latin,latin-ext' rel='stylesheet' type='text/css'>

        <!-- main stylesheet -->
        <link href="assets/css/main.min.css" rel="stylesheet" media="all" id="mainCss">

        <!-- print stylesheet -->
        <link href="assets/css/print.css" rel="stylesheet" media="print">

        <!-- moment.js (date library) -->
        <script src="assets/js/moment-with-langs.min.js"></script>
    </head>

    <body class="login_page">

        <div class="login_header">
            <img src="assets/img/logo.png" alt="site_logo">
        </div>
        <div class="login_register_form"><br>

            <div role="alert" class="alert alert-info" id="alertCorrecto">
                <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <strong>Recuperacion Exitosa!</strong><br> Su nueva clave a sido enviada a su correo de contacto.
            </div>
            
            <div role="alert" class="alert alert-danger" id="alertError">
                <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <strong>Error!</strong><br> Este documento no existe o el campo se esta vacio, digite un documento valido.
            </div>

            <div class="form_wrapper animated-short" id="login_form"><br><br><br>
                <h3 class="sepH_c"><span>Recuperar Contraseña</span></h3>
                <form action="return:false">
                    <div class="input-group input-group-lg sepH_a">
                        <span class="input-group-addon"><span class="icon_document"></span></span>
                        <input type="text" class="form-control" placeholder="Documento" required id="documento">
                    </div>
                    <div class="form-group sepH_c">                        
                        <a class="btn btn-lg btn-primary btn-block" onclick="javascript:llamarServlet();" id="dtnContinuar">Continuar</a>
                        <a href="login.jsp" class="btn btn-lg btn-primary btn-block">Volver</a>
                    </div>

                </form>
            </div>
        </div>

    </body>    

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

    <!-- page specific plugins -->

    <!-- select2 -->
    <script src="assets/lib/select2/select2.min.js"></script>
    <!-- validation (parsley.js) -->
    <script src="assets/js/parsley.config.js"></script>
    <script src="assets/lib/parsley/dist/parsley.min.js"></script>
    <!-- wysiwg editor -->
    <script src="assets/lib/ckeditor/ckeditor.js"></script>
    <script src="assets/lib/ckeditor/adapters/jquery.js"></script>

</script>
<script>

                            jQuery(document).ready(function() {
                                jQuery("#alertCorrecto").hide();
                                jQuery("#alertError").hide();
                            });                           

                            $(function() {
                                $('.form-switch').on('click', function(e) {
                                    e.preventDefault();

                                    var $switchTo = $(this).data('switchForm'),
                                            $thisForm = $(this).closest('.form_wrapper');

                                    $('.form_wrapper').removeClass('fadeInUpBig');
                                    $thisForm.addClass('fadeOutDownBig');

                                    setTimeout(function() {
                                        $thisForm.removeClass('fadeOutDownBig').hide();
                                        $('#' + $switchTo).show().addClass('fadeInUpBig');
                                    }, 300);

                                });
                            });

                            $(function() {
                                // wysiwg editor
                                yukon_wysiwg.p_forms_validation();
                                // multiselect
                                yukon_select2.p_forms_validation();
                                // validation
                                yukon_parsley_validation.p_forms_validation();
                            });

                            function llamarServlet() {
                                $('#dtnContinuar').attr("disabled", true);
                                $.ajax({
                                    url: 'ServletRecuperarClave',
                                    data: {
                                        documento: $("#documento").val()
                                    },
                                    success: function(data) {
                                        if (data != "incorrecto" && data != "") {
                                            $("#alertCorrecto").show();
                                            $('#correo').text(data);
                                        }
                                        if (data === "incorrecto") {
                                            $("#alertError").show();
                                            $('#msgError').text("Ocurrio un problema en la recuperación del la contreseña");
                                        }
                                        if (data === "") {
                                            $("#alertError").show();
                                            $('#msgError').text("No existe un usuario con documento: " + $("#documento").val());
                                        }
                                        $('#dtnContinuar').attr("disabled", false);
                                    }
                                    
                                });
                                document.location.href = "login.jsp";
                            }

</script>
</body>
</html>