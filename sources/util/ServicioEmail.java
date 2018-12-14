/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

    
/**
 * Clase que permite enviar correo electrónico a través de los servidores de gmail
 * @author Marco Adarme
 * @version 2.0
 */
public class ServicioEmail {
    
    //Dirección del servidor smtp, puede cambiarlo a su servidor de su cuenta particular, en el caso de gmail es smtp.gmail.com
    private final String direccionServidorEmail="smtp.gmail.com";
    //Numéro del puerto del servidor smtp, en el caso de gmail es el 587
    private final String puertoServidor="587";
    private Properties props = new Properties();
    //Dirección del email del usario que envía el mensaje
    private String emailUsuarioEmisor;
    //Contraseña del usuario que envía el correo electrónico
    private String claveEmailUsuarioEmisor;

    /**
     * 
     * Crea un objeto para enviar correo electrónico
     * a través de los servidores de gmail
     * @param emailUsuarioEmisor dirección email del usuario que envía el mensaje
     * @param claveEmailUsuarioEmisor contraseña del usuario que envía el mensaje
     */
    
    public ServicioEmail(String emailUsuarioEmisor, String claveEmailUsuarioEmisor) {
        
        System.out.println("--- " + emailUsuarioEmisor + " --- " + claveEmailUsuarioEmisor);
        this.emailUsuarioEmisor = emailUsuarioEmisor;
        this.claveEmailUsuarioEmisor = claveEmailUsuarioEmisor;
        inicializarPropiedades();
                
    }
    
    
    /**
     * Inicia las propiedades del servicio de correo
     */    
    private void inicializarPropiedades() {
        
        props.setProperty("mail.smtp.host", this.direccionServidorEmail);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", this.puertoServidor);
        props.setProperty("mail.smtp.user", this.emailUsuarioEmisor);
        props.setProperty("mail.smtp.auth", "true");
    
    }
    
    /**
     * Método que permite enviar un correo electrónico en texto plano
     * @param receptor dirección email del usuario a quien se le envía el mensaje
     * @param asunto asunto del correo electrónico
     * @param cuerpoMensaje  cuerpo del mensaje del correo electrónico
     */    
    public void enviarEmail(String receptor, String asunto, String cuerpoMensaje) {
        
        try {
            
            Session session = Session.getDefaultInstance(props);
            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            System.out.println("email1");
            message.setFrom(new InternetAddress(this.emailUsuarioEmisor));
            System.out.println("email2");
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(receptor));
            System.out.println("email3");
            message.setSubject(asunto);
            System.out.println("email4");
            message.setText(cuerpoMensaje);
            System.out.println("email5");
            Transport t = session.getTransport("smtp");
            System.out.println("email6");
            t.connect(this.emailUsuarioEmisor, this.claveEmailUsuarioEmisor);
            System.out.println("email7");
            t.sendMessage(message, message.getAllRecipients());
            System.out.println("email8");
            t.close();
            System.out.println("email9");
        } catch (Exception e) {
            System.err.println("Error en enviar EMAIL -------------- "+e.getMessage());
            
        }
    }
    
    /**
     * Método get que retona la clave del email
     * @return un tipo String que contiene la clave del email
     */
    public String getClaveEmailUsuarioEmisor() {
        
        return claveEmailUsuarioEmisor;
        
    }
    
    /**
     * Método set que modifica la clave del email
     * @param claveEmailUsuarioEmisor es de tipo String y contiene la nueva clave
     */
    public void setClaveEmailUsuarioEmisor(String claveEmailUsuarioEmisor) {
        
        this.claveEmailUsuarioEmisor = claveEmailUsuarioEmisor;
        
    }
    
    /**
     * Método get que retorna el email
     * @return un tipo String que ocntiene el email
     */
    public String getEmailUsuarioEmisor() {
        
        return emailUsuarioEmisor;
        
    }
    
    /**
     * Método set que modifica el email
     * @param emailUsuarioEmisor es de tipo String y contiene el nuevo email
     */
    public void setEmailUsuarioEmisor(String emailUsuarioEmisor) {
        
        this.emailUsuarioEmisor = emailUsuarioEmisor;
        
    }
    
}//Fin de la Clase 

