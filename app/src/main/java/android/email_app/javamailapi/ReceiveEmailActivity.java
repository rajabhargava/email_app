package android.email_app.javamailapi;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import net.simplifiedcoding.javamailexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raja on 11-Apr-18.
 */

public class ReceiveEmailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Email>> {

    EmailAdapter emailAdapter;
    final String user = Config.EMAIL;
    final String password = Config.PASSWORD;
    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox);

        boolean check_connection = isOnline();
        if(check_connection) {
            Log.i("info","InitLoader");
            getLoaderManager().initLoader(0, null, (LoaderManager.LoaderCallbacks<List<Email>>) this).forceLoad();
        }
        else {
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<List<Email>> onCreateLoader(int id, Bundle args) {
        Log.i("info","OnCreateLoader");
        return new EmailLoader(ReceiveEmailActivity.this, user, password);
    }

    @Override
    public void onLoadFinished(Loader<List<Email>> loader, List<Email> data) {
        //progressDialog.dismiss();
        Log.i("info","Finish Loader");

        final ArrayList<Email> emailList = (ArrayList<Email>) data;

        ListView emailListView = (ListView) findViewById(R.id.list_mail);

        emailAdapter = new EmailAdapter(this,emailList);

        emailListView.setAdapter(emailAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<Email>> loader) {
        Log.i("info","Reser Loader");
        loader.reset();
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
