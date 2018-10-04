<%-- 
    Document   : index_ctr
    Created on : 28/09/2017, 10:11:48 AM
    Author     : carlos
--%>

<%@ page import="javax.servlet.*"%>
<%@ page import="java.util.*,java.io.*"%>
<%@ page import="co.IngCarlos.gastosQX.mvc.dto.*"%>
<%@ page import="co.IngCarlos.gastosQX.common.util.*"%>
<jsp:useBean type="co.IngCarlos.gastosQX.mvc.fachada.FachadaSeguridad" scope="application" id="fachadaSeguridad"/>

<%
    DatosUsuarioDTO datosUsuario = null;
    try {

        if (!ValidaString.isNullOrEmptyString(request.getRemoteUser())) {

            datosUsuario = fachadaSeguridad.consultarDatosUsuarioLogueado(request.getRemoteUser());
            
             //DatosUsuarioDTO datosUsuario1 = (DatosUsuarioDTO) session.getAttribute("datosUsuario");
             //System.out.println("datosUsuario CTR " + datosUsuario1.toStringJson());

            if (datosUsuario != null && datosUsuario.getEstado().equals("1")) {

                session.setAttribute("datosUsuario", datosUsuario);
//                response.sendRedirect(request.getContextPath() + "/index.jsp");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {

                request.getRequestDispatcher("/login.jsp?error=Ingreso no autorizado").forward(request, response);
            }
        } else {

            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    } catch (Exception e) {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
%>
