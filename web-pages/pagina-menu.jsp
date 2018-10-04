<%-- 
    Document   : pagina-menu
    Created on : 26/10/2017, 10:56:34 AM
    Author     : carlos
--%>

<%@page import="co.IngCarlos.gastosQX.mvc.dto.*"%>
<%@ page import="java.util.*,java.io.*"%>
<%@page session='true'%>

<%
    DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) session.getAttribute("datosUsuario");
    ArrayList<MenuDTO> menu = datosUsuario.getMenu();

%>


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
            <span class="fa <%=menu.get(i).getIconoMenu()%> first_level_icon"></span>
            <span class="menu-title"><%=menu.get(i).getTituloMenu()%></span>
        </a>
        <% for (int j = 0; j < menu.get(i).getFuncionalidad().size(); j++) {%>
        <ul>
            <li><a href="javascript:cargarPagina('<%=menu.get(i).getFuncionalidad().get(j).getPagina()%>');"><%=menu.get(i).getFuncionalidad().get(j).getTitulo()%></a></li>
        </ul>
        <% }%>
    </li>
    <% }%>
</ul>


<!-- Yukon Admin functions -->
<script src="assets/js/yukon_all.js"></script>

<!-- page specific plugins -->

