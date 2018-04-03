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
 * Created by Raja on 02-Apr-18.
 */

public class ReceiveMail extends AsyncTask<Void, Void, Void> {

    private final String password;
    private final String user;
    private Context context;
    private Session session;
    private ProgressDialog progressDialog;

    private TextView sender_name;
    private ListView listView;
    Message[] messages;


    public ReceiveMail(Context context, String user, String password) {
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
        //properties.put("mail.pop3.socketFactory.port","995");
        //properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //properties.put("mail.pop3.auth", "true");
        //properties.put("mail.pop3.port", "995");

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

            Folder emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            messages = emailFolder.getMessages();
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
