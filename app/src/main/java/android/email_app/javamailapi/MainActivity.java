package android.email_app.javamailapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.simplifiedcoding.javamailexample.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring EditText
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;

    private TextView sender_name;
    private ListView listView;

    //Send button
    private Button buttonSend;
    private Button buttonInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);



        buttonSend = (Button) findViewById(R.id.buttonSend);

        //Adding click listener
        buttonSend.setOnClickListener(this);

        buttonInbox = (Button) findViewById(R.id.buttonInbox);

        buttonInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainActivity.this,ReceiveEmailActivity.class);
                startActivity(i);
            }
        });
    }


    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

//  private void receiveEmail() throws MessagingException {
//        ReceiveMail rm = new ReceiveMail(this,"bhargavar100@gmail.com","bhargavar100");
//        rm.execute();
//        setContentView(R.layout.inbox);
//        javax.mail.Message[] messages = rm.messages;
//        sender_name = (TextView) findViewById(R.id.sender_name);
//        listView = (ListView) findViewById(R.id.list_mail);
//        String[] from = new String[messages.length];
//        for(int i =0; i<messages.length; i++) {
//            javax.mail.Message msg = messages[i];
//            from[i] = String.valueOf(msg.getFrom());
//        }
//        ArrayAdapter<String> emails = new ArrayAdapter<String>(this,R.layout.inbox,R.id.sender_name,from);
//        listView.setAdapter(emails);



    @Override
    public void onClick(View v) {
        sendEmail();
    }
}
