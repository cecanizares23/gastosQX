<%-- 
    Document   : panel-superior
    Created on : 26/10/2017, 10:51:38 AM
    Author     : carlos
--%>

<%@page import="co.IngCarlos.gastosQX.mvc.dto.*"%>
<%@ page import="java.util.*,java.io.*"%>
<%@page session='true'%>

<%
    DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");
%>

<header id="main_header">
    <div class="container-fluid">
        <div class="brand_section center-block">
            <a href="index.jsp"><img src="assets/img/logo2.png" alt="site_logo" width="63" height="26"></a>
        </div>

        <div class="header_user_actions dropdown">
            <div data-toggle="dropdown" class="dropdown-toggle user_dropdown">
                <div class="user_avatar">
                    <label style="color: white;"><strong><h4><%= datosUsuario.getUsuario()%></h4></strong></label><!--<img src="assets/img/avatars/user.png" alt="" title="Carrol Clark (carrol@example.com)" width="38" height="38">-->
                </div>
                <span class="caret" id="nombreUser"><%//= datosUsuario.getNombre()%></span>
            </div>
            <ul class="dropdown-menu dropdown-menu-right">
                <li><a onclick="javascript:cargarPagina('perfil.jsp');">Editar Perfil</a></li>
                <!--<li><a href="gestion-actividad-diaria.html">Rol Interventor</a></li>-->
                <li><a href="login.jsp">Salir</a></li>
            </ul>
        </div>
        <!--<div class="search_section hidden-sm hidden-xs">
            <input type="text" class="form-control input-sm">
            <button class="btn btn-link btn-sm" type="button"><span class="icon_search"></span></button>
        </div>-->
    </div>
</header>

