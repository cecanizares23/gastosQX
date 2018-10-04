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
import co.IngCarlos.gastosQX.common.util.Constantes;
import co.IngCarlos.gastosQX.common.util.Generales;
import co.IngCarlos.gastosQX.common.util.LoggerMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Administrador
 */
@WebServlet(name = "ServletFactura", urlPatterns = {"/ServletFactura"})
public class ServletFactura extends HttpServlet {

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

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        LoggerMessage logMsg = LoggerMessage.getInstancia();

        try {

            String idFactura = Generales.EMPTYSTRING;
            String emailTo = Generales.EMPTYSTRING;
            idFactura = request.getParameter("facturaId");
            emailTo = request.getParameter("emailTo");
            String nombreFactura = Generales.EMPTYSTRING;            

            System.out.println("datos en servlet factura " + idFactura + " " + emailTo);

            nombreFactura = "factura_venta_" + idFactura + ".pdf";

            System.out.println("nombreFactura ::: " + nombreFactura);

            Pdf pdf = new Pdf();
            String url = request.getContextPath();
            String urlGeneraPdf = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/factura.jsp?idFactura=" + idFactura;
            pdf.addPage(urlGeneraPdf, PageType.url);
            pdf.addParam(new Param("-s", "Letter"));
            String pathToWeb = Generales.EMPTYSTRING;

            if (System.getProperty("os.name").toLowerCase().startsWith("window")) {
                pathToWeb = (String) new InitialContext().lookup("java:comp/env/ruta_archivo_pdf_windows");
                pdf.saveAs(pathToWeb + nombreFactura);
            } else if (System.getProperty("os.name").toLowerCase().startsWith("linux")) {
                pathToWeb = (String) new InitialContext().lookup("java:comp/env/ruta_archivo_pdf_linux");
                pdf.saveAs(pathToWeb + nombreFactura);
            }

            boolean enviar = sendFromGMail(nombreFactura, Constantes.CORREO, Constantes.CLAVE_CORREO, emailTo, "Tu factura Medipin", "Gracias por confiar en Medipin, En el archivo adjunto podrás descargar la factura de tu compra. Si tienes alguna duda, nuestros médicos están disponibles las 24 horas para atenderte.", pathToWeb);

            if (enviar == true) {
                System.out.println("Se envio la factura con exito");
                response.getWriter().print(enviar);
            } else {
                System.out.println("No se pudo enviar la factura");
                response.getWriter().write("No se pudo enviar la factura");
            }

            //System.out.println("ServletFactura Cargado, factura enviada");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Finaliza ServletFactura");
        }

    }

    public boolean sendFromGMail(String nombreFactura, String from, String pass, String to, String subject, String body, String pathToWeb) throws NamingException, FileNotFoundException {
        Boolean exito = false;

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", from);
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props, null);

            BodyPart texto = new MimeBodyPart();
            texto.setText(body);

            BodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(pathToWeb + nombreFactura)));
            adjunto.setFileName(nombreFactura);

            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(multiParte);

            Transport t = session.getTransport("smtp");
            t.connect(from, pass);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            exito = true;
        } catch (Exception e) {
            e.printStackTrace();
            return exito;
        }
        return exito;
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
            //Logger.getLogger(ServletPdfActa.class.getName()).log(Level.SEVERE, null, ex);
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
            //Logger.getLogger(ServletPdfActa.class.getName()).log(Level.SEVERE, null, ex);
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
