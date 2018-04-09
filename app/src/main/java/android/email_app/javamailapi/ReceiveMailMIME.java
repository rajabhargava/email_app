package android.email_app.javamailapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.mail.pop3.POP3Store;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * Created by @singhrohan62 on 09-Apr-18.
 */

public class ReceiveMailMIME extends AsyncTask<Void, Void, Void> {

    private final String password;
    private final String user;
    private Context context;
    private Session session;
    private ProgressDialog progressDialog;

    private TextView sender_name;
    private ListView listView;
    Message[] messages;


    public ReceiveMailMIME(Context context, String user, String password) {
        this.context = context;
        this.user = user;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,"Receiving email", "Please wait", false, false);
    }

    @Override
    protected void onPostExecute(Void v) {
        super.onPostExecute(v);
        progressDialog.dismiss();
        Toast.makeText(context, "Emails Received", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {

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
                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
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
                Message message = messages[i];  
                System.out.println("---------------------------------");  
                System.out.println("Email Number " + (i + 1));  
                System.out.println("Subject: " + message.getSubject());  
                System.out.println("From: " + message.getFrom()[0]);  
                System.out.println("Text: " + message.getContent().toString());  
             System.out.println("Sent Date: " +message.getSentDate()); 
                 
              Multipart multipart = (Multipart) message.getContent();  
               
                for (int j = 0; j < multipart.getCount(); j++) {  
                     BodyPart bodyPart = multipart.getBodyPart(j);  
                     InputStream stream = bodyPart.getInputStream();  
                     BufferedReader br = new BufferedReader(new InputStreamReader(stream));  
                  
                    while (br.ready()) {  
                         System.out.println(br.readLine());  
                    }  
                    System.out.println();  
                }  
             }

             //Close the folder and store objects
            emailFolder.close(false);
            emailStore.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}