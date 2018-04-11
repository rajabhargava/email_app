package android.email_app.javamailapi;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raja on 11-Apr-18.
 */

public class EmailLoader extends AsyncTaskLoader<List<Email>> {
    private Context c;
    final String user;
    final String password;

    public EmailLoader(Context context, final String user, final String password) {
        super(context);
        c = context;
        this.user = user;
        this.password = password;
    }

    @Override
    protected void onStartLoading() {
        Log.i("info","Start Loading");
        forceLoad();
    }

    @Override
    public List<Email> loadInBackground() {
        Log.i("info","Background load");
        ArrayList<Email> emailArrayList = ReceiveEmail.fetchEmail(user,password);
        return emailArrayList;
    }
}
