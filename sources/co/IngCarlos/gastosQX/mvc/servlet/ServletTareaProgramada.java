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
import java.io.IOException;
import java.util.Properties;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
@Lock(LockType.READ)
@WebServlet(name = "ServletTareaProgramada", urlPatterns = {"/ServletTareaProgramada"})
public class ServletTareaProgramada extends HttpServlet {

    @Schedules({
    //    @Schedule(dayOfWeek = "Sun, Mon, Tue, Wed, Thu, Fri, Sat", hour = "15", minute = "30", second = "0")
    })
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void processRequest() throws ServletException, IOException, Exception {

        try {
            System.out.println("entra al servlet");

            boolean enviar = false;
            String[] emailTo = {"hebertmedelo29@gmail.com", "olga.blanco@mobiltech.com", "carlos.canizares@mbcolombia.com"};
            enviar = sendFromGMail(Constantes.CORREO, Constantes.CLAVE_CORREO, emailTo, "reporte.pdf", "tarea programada");
            
            

            System.out.println("enviar :::: " + enviar);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Finaliza ServletTareaProgramada");
        }
    }

  public boolean sendFromGMail(String from, String pass, String[] to, String subject, String body) {
       Properties props = System.getProperties();
       String host = "smtp.gmail.com";
       props.put("mail.smtp.starttls.enable", "true");
       props.put("mail.smtp.host", host);
       props.put("mail.smtp.user", from);
       props.put("mail.smtp.password", pass);
       props.put("mail.smtp.port", "587");
       props.put("mail.smtp.auth", "true");

       Session session = Session.getDefaultInstance(props);
       MimeMessage message = new MimeMessage(session);
       Boolean exito = false;
       try {
           message.setFrom(new InternetAddress(from));
           InternetAddress[] toAddress = new InternetAddress[to.length];

           // To get the array of addresses
           for (int i = 0; i < to.length; i++) {
               toAddress[i] = new InternetAddress(to[i]);
           }

           for (int i = 0; i < toAddress.length; i++) {
               message.addRecipient(Message.RecipientType.TO, toAddress[i]);
           }

           message.setSubject(subject);
           message.setText(body);
           Transport transport = session.getTransport("smtp");
           transport.connect(host, from, pass);
           transport.sendMessage(message, message.getAllRecipients());
           System.out.print("Successfully Sent" + "\n");

           transport.close();
           exito = true;
       } catch (AddressException ae) {
           ae.printStackTrace();
           return exito;
       } catch (MessagingException me) {
           me.printStackTrace();
           return exito;
       }
       return exito;

   }

    //editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
            processRequest();
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
            processRequest();
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
