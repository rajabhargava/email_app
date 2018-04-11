package android.email_app.javamailapi;

import java.util.ArrayList;

/**
 * Created by Raja on 11-Apr-18.
 */

public class Email extends ArrayList {

    private String sender_name;
    private String sub;
    private String date;

    public Email(String sender_name, String sub, String date) {
        this.sender_name = sender_name;
        this.sub = sub;
        this.date = date;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getSub() {
        return sub;
    }

    public String getDate() {
        return date;
    }
}
