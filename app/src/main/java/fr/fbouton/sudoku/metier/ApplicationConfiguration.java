package fr.fbouton.sudoku.metier;


import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ApplicationConfiguration extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("SudokuBdd")
                .schemaVersion(1)
                .build();
        // to remove realm completely :
        //Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
    }
}
