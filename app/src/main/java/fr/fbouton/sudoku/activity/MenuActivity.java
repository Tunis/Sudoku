package fr.fbouton.sudoku.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import fr.fbouton.sudoku.R;
import fr.fbouton.sudoku.layout.MenuFragment;
import fr.fbouton.sudoku.layout.StatsFragment;
import fr.fbouton.sudoku.models.UserInput;
import io.realm.Realm;

public class MenuActivity extends AppCompatActivity {

    private final String TAG_MENU = "MENU";
    private final String TAG_STATS = "STATS";
    private BottomNavigationView navigation;

    private FragmentManager fManager;
    private MenuFragment menuFragment;
    private StatsFragment statsFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content, menuFragment, TAG_MENU);
                    transaction.commit();
                    return true;
                case R.id.navigation_Stats:
                    transaction.replace(R.id.content, statsFragment, TAG_STATS);
                    transaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w("TEST", "chargement menu activity");
        setContentView(R.layout.activity_menu);

        fManager = getFragmentManager();
        menuFragment = MenuFragment.newInstance();

        statsFragment = StatsFragment.newInstance();

        FragmentTransaction transaction = fManager.beginTransaction();
        transaction.replace(R.id.content, menuFragment);
        transaction.commit();


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void startGame(View view) {
        Intent newGame = new Intent(this, GameActivity.class);
        startActivity(newGame);
    }

    public void resumeGame(View view) {
        Intent retry = new Intent(this, RetryActivity.class);
        startActivity(retry);
    }

    @Override
    public void onBackPressed() {
        StatsFragment stats = (StatsFragment)getFragmentManager().findFragmentByTag(TAG_STATS);
        if (stats != null && stats.isVisible()) {
            View view = navigation.findViewById(R.id.navigation_home);
            view.performClick();
        }else{
            this.finishAffinity();
        }
    }
}
