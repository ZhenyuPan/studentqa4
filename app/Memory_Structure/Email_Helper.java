package Memory_Structure;
import java.util.Properties;

import javax.mail.Message;

import javax.mail.PasswordAuthentication;

import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;


public class Email_Helper

{

        String Sender_Gmail_Name;

        String Sender_Gmail_Password;

        Session session;

        public Email_Helper(String Name , String Pass)

        {

                Sender_Gmail_Name = Name;

                Sender_Gmail_Password = Pass;

                Properties props = new Properties();

                props.put("mail.smtp.host", "smtp.gmail.com");

                props.put("mail.smtp.socketFactory.port", "465");

                props.put("mail.smtp.socketFactory.class",

                                "javax.net.ssl.SSLSocketFactory");

                props.put("mail.smtp.auth", "true");

                props.put("mail.smtp.port", "465");


                session= Session.getInstance(props,

                  new javax.mail.Authenticator() {

                        protected PasswordAuthentication getPasswordAuthentication() {

                                return new PasswordAuthentication(Sender_Gmail_Name, Sender_Gmail_Password);

                        }

                  });

        }

        public boolean Sending_Message(String Destination, String title , String Contant )

        {

                try

                {

                        Message message = new MimeMessage(session);

                        message.setFrom(new InternetAddress(Sender_Gmail_Name));

                        message.setRecipients(Message.RecipientType.TO,

                                        InternetAddress.parse(Destination));

                        message.setSubject(title);

                        message.setText(Contant);

                        Transport.send(message);

                        System.out.println("The Message have been delive");

                        return true;

                }catch(Exception e)

                {

                        e.printStackTrace();

                        return false;

                }

        }

}

