package fr.fbouton.sudoku.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import fr.fbouton.sudoku.R;
import fr.fbouton.sudoku.models.UserInput;
import io.realm.Realm;


public class StatsSudokuSingle extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_sudoku);

        TextView nbEssai = (TextView) findViewById(R.id.nbEssaiField);
        TextView timeClear = (TextView) findViewById(R.id.timeToClearField);

        String pk = (String) getIntent().getSerializableExtra("user");
        Realm r = Realm.getDefaultInstance();
        UserInput user = r.where(UserInput.class).equalTo("userBoard", pk).findFirst();

        nbEssai.setText(getString(R.string.varInt, user.getNumberEssais()));
        timeClear.setText(getString(R.string.varTimer, user.getHeures(), user.getMinutes(), user.getSecondes()));
        r.beginTransaction();
            user.deleteFromRealm();
        r.commitTransaction();
    }

    public void onClick(View v){
        Intent backIntent = new Intent(this,MenuActivity.class);
        startActivity(backIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent toMenu = new Intent(this, MenuActivity.class);
        startActivity(toMenu);
    }
}
