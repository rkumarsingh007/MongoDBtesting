package com.example.raj.mongodbtesting;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ConnectToMongo().execute("adminOfAll","admin","Raj@1234");
    }

    private class ConnectToMongo extends AsyncTask< String, Void , Boolean> {
        protected Boolean doInBackground(String... urls) {
            try{
                Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
                mongoLogger.setLevel(Level.SEVERE);
                MongoCredential cred = MongoCredential.createCredential(urls[0],urls[1],urls[2].toCharArray());
                MongoClient mongo = new MongoClient(new ServerAddress("192.168.116.200",27017), Arrays.asList(cred));
                MongoDatabase db = mongo.getDatabase(urls[1]);
                db.createCollection("temp");
                MongoCollection temp = db.getCollection("temp");
                temp.drop();
                return true;
            }
            catch (Exception ex) {
                return false;
            }

        }



        protected void onPostExecute(Boolean bool) {
            if(bool.booleanValue() == true)
            {
                Toast.makeText(MainActivity.this,"connected to the database !! ",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(MainActivity.this,"Unable to connect to the database :( ",Toast.LENGTH_LONG).show();
            }
        }
    }
}
