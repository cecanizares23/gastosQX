/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.mvc.servlet;

import br.eti.mertz.wkhtmltopdf.wrapper.Pdf;
import br.eti.mertz.wkhtmltopdf.wrapper.page.PageType;
import br.eti.mertz.wkhtmltopdf.wrapper.params.Param;
import co.IngCarlos.gastosQX.common.util.Generales;
import co.IngCarlos.gastosQX.common.util.LoggerMessage;
import co.IngCarlos.gastosQX.mvc.dto.DatosUsuarioDTO;
import co.IngCarlos.gastosQX.mvc.fachada.FachadaAppGastos;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author eynson
 */
@WebServlet(name = "ServletDescargarPdf", urlPatterns = {"/DescargarPdf"})
public class ServletDescargarPdf extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        response.setContentType("application/pdf");
        LoggerMessage logMsg = LoggerMessage.getInstancia();

        HttpSession servletSesion;
        servletSesion = request.getSession(false);
        DatosUsuarioDTO datosUsuario = (DatosUsuarioDTO) servletSesion.getAttribute("datosUsuario");

        try {

            Pdf pdf = new Pdf();

            String idProyecto = request.getParameter("idProyecto");
            String estadoTiempo = request.getParameter("estadoTiempo");
            //estadoTiempo = estadoTiempo.replace("_", " ");
            String nombreProyecto = request.getParameter("nombreProyecto");
            //nombreProyecto = nombreProyecto.replace("_", " ");
            String nombreUsuario = request.getParameter("nombreUsuario");
            //nombreUsuario = nombreUsuario.replace("_", " ");
            String fecha = request.getParameter("fecha");
            String idAvance = request.getParameter("idAvance");
            String[] arrayImagenes = request.getParameterValues("arrayImagenes");
            String imagenesLi = arrayImagenes[0];
            System.out.print("variablesServlet idProyecto " + idProyecto + " estadoTiempo " + estadoTiempo + " nombreProyecto " + nombreProyecto + " nombreUsuario " + nombreUsuario
                    + " fecha " + fecha + " idAvance " + idAvance + " imagenesLi " + imagenesLi);

            //arrayImagenes[0].split(",");                                                           
            //for (int i = 0; i < arrayImagenes.length; i++) {                
            //System.out.println(">>"+ arrayImagenes[i] );                                
            //}                        
            String url = request.getContextPath();
            String urlGeneraPdf = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()
                    + "/pdf-reporte-diario.jsp?idProyecto=" + request.getParameter("idProyecto") + "&estadoTiempo=" + request.getParameter("estadoTiempo")
                    + "&nombreProyecto=" + request.getParameter("nombreProyecto") + "&nombreUsuario=" + request.getParameter("nombreUsuario") + "&fecha="
                    + request.getParameter("fecha") + "&idAvance=" + request.getParameter("idAvance") + "&imagenesLi=" + imagenesLi;
            pdf.addPage(urlGeneraPdf, PageType.url);
            pdf.addParam(new Param("-s", "Letter"));
            pdf.addParam(new Param("-T", "20mm"));
            InputStream is = null;
            String pathToWeb = Generales.EMPTYSTRING;

            if (System.getProperty("os.name").toLowerCase().startsWith("window")) {
                pathToWeb = (String) new InitialContext().lookup("java:comp/env/ruta_archivo_pdf_windows");
                pdf.saveAs(pathToWeb + request.getParameter("idAvance") + "ReporteDiario.pdf");
            } else if (System.getProperty("os.name").toLowerCase().startsWith("linux")) {
                pathToWeb = (String) new InitialContext().lookup("java:comp/env/ruta_archivo_pdf_linux");
                pdf.saveAs(pathToWeb + request.getParameter("idAvance") + "ReporteDiario.pdf");
            }
            is = new BufferedInputStream(new FileInputStream((pathToWeb + request.getParameter("idAvance") + "ReporteDiario.pdf")));

            IOUtils.copy(is, response.getOutputStream());

        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
        } finally {
        }
        logMsg.loggerMessageDebug("ServletDescargarPdf Cargado");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletDescargarPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletDescargarPdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
