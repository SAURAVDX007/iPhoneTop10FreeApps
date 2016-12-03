package com.example.saurav.iphonetop10freeapps;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button btnParse;
    private ListView listApps;
    private String mFileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnParse = (Button) findViewById(R.id.btnParse);
        listApps = (ListView) findViewById(R.id.xmlListView);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO : write parse code here.
                ParseApplications parser = new ParseApplications(mFileContents);
                parser.process();
                ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(MainActivity.this,R.layout.list_view,
                        parser.getApplications());
                listApps.setAdapter(arrayAdapter);
            }
        });
        DownloadData data = new DownloadData();
        data.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//     Why we are using Asynchronous processing here. Remember in old days, a computer used to
//     perform a single operation, and other operation will have to wait until the current operation
//     got finished. That is not the case today, neither with computers nor with phones or tablets.
//     Imagine that was the case and you are using an app(may be trying to download a certain data)
//     and you get a call, you wont be able to pick it up, because the phone wont let you. To overcome
//     it, we make our task asynchronous. It lets you do things in background without disturbing any other
//     activity. You can perform other operations in that same app and this downloading process will
//     continue in background. Once the download is finished, it will give a confirmation.
//
//            AsyncTask<Params, Progress, Result>
//
//            Params, the type of the parameters sent to the task upon execution.
//            Progress, the type of the progress units published during the background computation.
//            Result, the type of the result of the background computation.

    private class DownloadData extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... params) {
            mFileContents = downloadXMLFiles(params[0]);
            if (mFileContents == null){
                Log.d("DownloadData","Error Message");
            }
            return mFileContents;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DownloadData","Result is "+result);

        }

        private String downloadXMLFiles(String urlPath) {
            StringBuilder builder  = new StringBuilder();
            try{
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d("DownloadData","The response code is "+response);
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());

                int charRead;
                char[] inputBuffer = new char[500];
                while (true){
                    charRead = reader.read(inputBuffer);
                    if (charRead<=0){
                        break;
                    }
                    builder.append(String.copyValueOf(inputBuffer));
                }
                return builder.toString();
            }catch (IOException e){
                Log.d("DownloadData", "IOException reading data "+e.getMessage());
            }catch (SecurityException e){
                Log.d("DownloadData", "Security Exception. Needs permission?"+e.getMessage());
            }
            return null;
        }

    }
}
