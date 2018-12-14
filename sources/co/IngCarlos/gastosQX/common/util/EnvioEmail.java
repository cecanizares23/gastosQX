/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.common.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Carlos
 */
public class EnvioEmail {

    public static boolean sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        //String host = "smtp.live.com";
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        //props.put("mail.smtp.port", "25");//465
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
            System.out.println("------------------- " + host + " " + from + " " + pass);
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

}