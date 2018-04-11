package android.email_app.javamailapi;

import com.sun.mail.pop3.POP3Store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * Created by Raja on 11-Apr-18.
 */

public final class ReceiveEmail {

    private ReceiveEmail() {
    }

    public static ArrayList<Email> fetchEmail (final String user, final String password) {
        Session session;
        Message[] messages;
        ArrayList<Email> emailArrayList = new ArrayList<Email>();

        Properties properties = new Properties();
        properties.put("mail.pop3.host","pop.gmail.com");
        properties.put("mail.pop3.socketFactory.port","995");
        properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.pop3.auth", "true");
        properties.put("mail.pop3.port", "995");

        session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        try {
            POP3Store emailStore = (POP3Store)session.getStore("pop3");
            emailStore.connect(user, password);

            //Creating POP3 store object and connect with the POP server

            Folder emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            //Retrieving messages from the folder in an array and print it

            messages = emailFolder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Email email;
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
                System.out.println("Sent Date: " +message.getSentDate());

                email = new Email(String.valueOf(message.getFrom()),String.valueOf(message.getSubject()),String.valueOf(message.getSentDate()));
                emailArrayList.add(email);


//                Multipart multipart = (Multipart) message.getContent();
//
//                for (int j = 0; j < multipart.getCount(); j++) {
//                    BodyPart bodyPart = multipart.getBodyPart(j);
//                    InputStream stream = bodyPart.getInputStream();
//                    BufferedReader br = new BufferedReader(new InputStreamReader(stream));
//
//                    while (br.ready()) {
//                        System.out.println(br.readLine());
//                    }
//                    System.out.println();
//                }
            }

            //Close the folder and store objects
            emailFolder.close(false);
            emailStore.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emailArrayList;
    }
}
