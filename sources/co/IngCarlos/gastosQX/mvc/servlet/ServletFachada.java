/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.mvc.servlet;

import co.IngCarlos.gastosQX.common.util.LoggerMessage;
import co.IngCarlos.gastosQX.mvc.fachada.FachadaSeguridad;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlos
 */
@WebServlet(name = "ServletFachada", urlPatterns = {"/servletFachada"}, loadOnStartup = 1)
public class ServletFachada extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

        LoggerMessage logMsg = LoggerMessage.getInstancia();

        try {

            logMsg = LoggerMessage.getInstancia();

            ServletContext contexto = this.getServletContext();

            FachadaSeguridad fachadaSeguridad = new FachadaSeguridad();
            contexto.setAttribute("fachadaSeguridad", fachadaSeguridad);

            logMsg.loggerMessageDebug("FachadaSeguridad cargada en el contexto de la aplicacion");

        } catch (Exception e) {
            logMsg.loggerMessageException(e);
        }

    }

    @Override
    public void destroy() {
    }

}
