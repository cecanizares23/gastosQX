/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.mvc.servlet;


import co.IngCarlos.gastosQX.common.util.Constantes;
import co.IngCarlos.gastosQX.common.util.Generales;
import co.IngCarlos.gastosQX.mvc.dto.UsuarioDTO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author carlos
 */
@WebServlet(name = "SubirImagenPerfil", urlPatterns = {"/SubirImagenPerfil"})
public class ServletSubirImagenperfil extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*
             * TODO output your page here. You may use following sample code.
             */
        }
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
        processRequest(request, response);

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
        // Linea comentariada para que la respuesta llegue como data al ajax success
        //processRequest(request, response);
        HttpSession servletSesion;
        // PrintWriter out = response.getWriter();
        System.out.println("Servlet imgperfil >>>> Entra al servlet para cargar archivo imagen perfil de usuario");
        servletSesion = request.getSession(false);
        response.setContentType("text/plain");

        try {

            PrintWriter out = response.getWriter();

            System.out.println("Servlet imgperfil >>>> Entro al servlet");
            servletSesion = request.getSession(false);
            response.setContentType("text/plain");

            if (ServletFileUpload.isMultipartContent(request)) {

                FileItemFactory itemfactory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(itemfactory);
                List<FileItem> items = upload.parseRequest(request);
                String dirImg = "";
                ServletContext srvcon = getServletContext();
                String rutafisica = srvcon.getRealPath("");

                String pathToWeb = Generales.EMPTYSTRING;

                if (System.getProperty("os.name").toLowerCase().startsWith("window")) {
                    System.out.println("Servlet imgperfil >>>> Sistema operativo es Windows");
                    pathToWeb = (String) new InitialContext().lookup("java:comp/env/ruta_imagen_perfil_windows");
                    System.out.println("Servlet imgperfil >>>> La ruta de la imagen es: " + pathToWeb);
                } else if (System.getProperty("os.name").toLowerCase().startsWith("linux")) {
                    System.out.println("Servlet imgperfil >>>> Sistema operativo es Linux");
                    pathToWeb = (String) new InitialContext().lookup("java:comp/env/ruta_imagen_perfil_linux");
                    System.out.println("Servlet imgperfil >>>> La ruta de la imagen es: " + pathToWeb);
                }

                UsuarioDTO datos = new UsuarioDTO();
                // datos = (ClienteDTO) servletSesion.getAttribute("datosCliente");
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>antes del for");
                for (FileItem item : items) {
                    if (item.getName() != null) {

                        if ("image/jpeg".equals(item.getContentType()) || "image/png".equals(item.getContentType())) {

                            System.out.println("Servlet imgperfil >>>> idCliente en sesion es actualización   " + request.getParameter("idUsuario"));
                            datos.setIdUsuario(request.getParameter("idCliente"));

                            if (datos.getIdUsuario() != null && !datos.getIdUsuario().equals("")) {
                            } else {
                                datos.setIdUsuario(servletSesion.getAttribute("idCliente").toString());
                                System.out.println("Servlet imgperfil >>>>idCiente en sesion es registro nuevo" + datos.getIdUsuario());
                            }

                            File uploadDir = new File(pathToWeb);
                            File file = new File(uploadDir, Constantes.NOMBRE_IMAGEN_PERIL_USUARIO + datos.getIdUsuario() + ".png");
                            System.out.println("nombre del archivo>>>>> " + file.getName());

                            item.write(file);
                            dirImg = file.getName();
                           // datos.setLogo(dirImg);
                            out.println("Imagen subida con exito");
                            servletSesion.setAttribute("datosCliente", datos);

                        } else {
                            out.println("Tipo de imagen no admitido");
                            Exception exception = new Exception("tipo no valido");
                            System.out.println(" Servlet imgperfil >>>> " + exception);

                        }
                    }

                }
            } else {
            }

            //  out.println("");
            out.flush();

        } catch (Exception ex) {
            Logger.getLogger(ServletSubirImagenperfil.class.getName()).log(Level.SEVERE, null, ex);
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
