package fr.fbouton.sudoku.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import fr.fbouton.sudoku.R;
import fr.fbouton.sudoku.models.Sudoku;
import io.realm.Realm;


public class Loading extends Activity {

    private Realm r;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("TEST", "chargement loading activity");
        setContentView(R.layout.activity_loading);
        AsyncTask<String, String, String> execute = new doThing().execute();
    }

    private class doThing extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            r = Realm.getDefaultInstance();

//            r.beginTransaction();
//            r.deleteAll();
//            r.commitTransaction();

            long count = r.where(Sudoku.class).count();
            Log.w("TEST", "il y a " + count + " items en bdd");

            if(count == 0){
                InputStream in = Loading.this.getResources().openRawResource(R.raw.sudoku_list);
                r.beginTransaction();
                try {
                    r.createAllFromJson(Sudoku.class, in);
                    r.commitTransaction();
                } catch (IOException e) {
                    r.cancelTransaction();
                    Snackbar.make(findViewById(R.id.loadingText), "Erreur lors du chargement.", Snackbar.LENGTH_LONG).show();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent activityToLaunch;
            activityToLaunch = new Intent(Loading.this, MenuActivity.class);
            startActivity(activityToLaunch);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
