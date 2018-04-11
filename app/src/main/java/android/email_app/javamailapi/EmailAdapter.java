package android.email_app.javamailapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.simplifiedcoding.javamailexample.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Raja on 11-Apr-18.
 */

public class EmailAdapter extends ArrayAdapter {

    public EmailAdapter(@NonNull Context context, ArrayList<Email> eq) {
        super(context, 0, eq);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list = convertView;
        if (list == null) {
            list = LayoutInflater.from(getContext()).inflate(R.layout.sender_name, parent, false);
        }

        final Email current = (Email) getItem(position);

        TextView sender_name = (TextView) list.findViewById(R.id.sender_name);
        if(current.getSender_name().length()>10)
            sender_name.setText(current.getSender_name().substring(0,10));
        else
            sender_name.setText(current.getSender_name());
        TextView sub = (TextView) list.findViewById(R.id.sub);
        if(current.getSub().length()>10)
             sub.setText(current.getSub().substring(0,10));
        else
            sub.setText(current.getSub());

        Date dateObject = new Date(current.getDate());
        String formattedDate = DateFormat.getDateInstance().format(dateObject);
        TextView date = (TextView) list.findViewById(R.id.date);
        date.setText(formattedDate);
        return list;
    }
}
