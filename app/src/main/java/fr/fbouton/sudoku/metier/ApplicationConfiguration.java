package fr.fbouton.sudoku.metier;


import android.app.Application;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import fr.fbouton.sudoku.models.Sudoku;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;

public class ApplicationConfiguration extends Application {

    private List<Sudoku> listeGen = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("TEST", "lancement application");
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("SudokuBdd")
                .schemaVersion(1)
                .build();
        // to remove realm completely :
        //Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
