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
import io.realm.RealmConfiguration;

/**
 * simple activity to load data in bdd when starting the app for the first time. will go to menu automatically.
 */
public class Loading extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new doThing().execute();
    }

    private class doThing extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {

            if(getIntent().getAction() != null && getIntent().getAction().equals("deleteBdd")){

                RealmConfiguration config = new RealmConfiguration.Builder()
                        .name("SudokuBdd")
                        .schemaVersion(1)
                        .build();
                int globalInstanceCount;
                do {
                    globalInstanceCount = Realm.getGlobalInstanceCount(config);
                    Log.e("TEST", "nb instance : " + globalInstanceCount);
                }while (globalInstanceCount != 0);
                Realm.deleteRealm(config);
                Realm.init(Loading.this);
                Realm.setDefaultConfiguration(config);
            }
//            r.beginTransaction();
//            r.deleteAll();
//            r.commitTransaction();
            Realm r = Realm.getDefaultInstance();

            long count = r.where(Sudoku.class).count();

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
            r.close();
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
