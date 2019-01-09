<%-- 
    Document   : login
    Created on : 17/10/2017, 09:53:55 AM
    Author     : carlos
--%>
<%@ page import="javax.servlet.*"%>
<%@ page import="co.IngCarlos.gastosQX.common.util.*"%>
<%@ page import="java.util.*,java.io.*"  contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>

<%
    session.invalidate();
    String mensajeError = request.getParameter("error");
    boolean mostrarError = false;
    if (!ValidaString.isNullOrEmptyString(mensajeError)) {
        mostrarError = true;
    }
%>

<!doctype html>
<html lang="en">

    <!--Encabezado-->
    <head>
        <script Language="JavaScript">
            if (history.forward(1)) {
                history.replace(history.forward(1));
            }
        </script>

        <meta charset="UTF-8">
        <title>Gastos CSJ</title>
        <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">

        <!-- bootstrap framework -->
        <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <!-- google webfonts -->
        <link href='http://fonts.googleapis.com/css?family=Open+Sans&amp;subset=latin,latin-ext' rel='stylesheet' type='text/css'>
        <!-- elegant icons -->
        <link href="assets/icons/elegant/style.css" rel="stylesheet" media="screen">
        <!-- main stylesheet -->
        <link href="assets/css/main.min.css" rel="stylesheet" media="screen">

        <!-- jQuery -->
        <script src="assets/js/jquery.min.js"></script>
    </head>
    <!--Fin Encabezado-->

    <body class="login_page">

        <div class="login_header">
            <img src="assets/img/logo.png" alt="site_logo">
        </div>
        <div class="login_register_form">
            <div class="form_wrapper animated-short" id="login_form">
                <h3 class="sepH_c text-center"><span>Iniciar Sesión</span></h3>
                <form action="<%= request.getContextPath()%>/j_security_check" id="login-form" method="post" autocomplete="off">
                    <%if (mostrarError) {%>
                    <p>
                    <div class="alert alert-danger">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <strong><%= mensajeError%></strong>
                    </div>
                    </p>
                    <% }%>
                    <div class="input-group input-group-lg sepH_a login-mail">
                        <span class="input-group-addon"><span class="icon_profile"></span></span>
                        <input type="text" class="form-control" placeholder="Username" id="username" name="j_username" required>
                    </div>
                    <div class="input-group input-group-lg login-mail">
                        <span class="input-group-addon"><span class="icon_key_alt"></span></span>
                        <input type="password" class="form-control" placeholder="Password" id="password" name="j_password" required>
                    </div>
                    <div class="sepH_c text-right">

                    </div>
                    <div class="form-group sepH_c">
                        <input class="btn btn-lg btn-primary btn-block" type="submit" value="login" name="login-submit" id="login-submit" tabindex="4">

                        <a href="recuperar-contrasenia.jsp" class="btn btn-lg btn-primary btn-block">Recuperar Contraseña</a>
                    </div>

                </form>
            </div>            
        </div>
                    
        

        <script>
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

        </script>

    </body>

</html>
