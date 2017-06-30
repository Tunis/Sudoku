package fr.fbouton.sudoku.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Random;

import fr.fbouton.sudoku.R;
import fr.fbouton.sudoku.classes.Chrono;
import fr.fbouton.sudoku.layout.utils.ToggleGroup;
import fr.fbouton.sudoku.metier.ConfirmDialog;
import fr.fbouton.sudoku.models.Sudoku;
import fr.fbouton.sudoku.models.UserInput;
import io.realm.Realm;
import io.realm.RealmResults;

import static fr.fbouton.sudoku.activity.RetryActivity.TAG_LOAD_GAME;

/**
 * game activity, will show the board and react to user input.
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener, ToggleGroup.OnCheckedChangeListener, ConfirmDialog.NoticeDialogListener{

    private Sudoku sudoku;
    private UserInput user;
    private int valueChoosed = -1;
    private TableLayout board;

    private Realm r;
    private Chrono chrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        board = (TableLayout) findViewById(R.id.gameBoard);
        chrono = (Chrono) findViewById(R.id.timer_game);

        ToggleGroup toggleGroup = (ToggleGroup) findViewById(R.id.toggleGroup);
        toggleGroup.setOnCheckedChangeListener(this);

        r = Realm.getDefaultInstance();
        Intent intent = getIntent();
        boolean isResume = false;
        if(intent.getAction() != null) {
            if (intent.getAction().equals(TAG_LOAD_GAME)) {
                loadGame();
                isResume = true;
            } else {
                startGame();
            }
        } else {
            startGame();
        }

        showBoard(isResume);
    }

    // load a random sudoku
    private void startGame() {
        RealmResults<Sudoku> result = r.where(Sudoku.class)
                    .notEqualTo("done", true)
                    .notEqualTo("doing", true)
                .findAll();
        Random ra = new Random();
        int random = ra.nextInt(result.size());
        sudoku = result.get(random);
        r.beginTransaction();
        sudoku.setDoing(true);
        r.commitTransaction();

        r.beginTransaction();
        user = new UserInput(sudoku);
        user.setLastPlayed(Calendar.getInstance());
        r.insertOrUpdate(user);
        r.commitTransaction();
    }
    // load the selected sudoku
    private void loadGame() {
        String id = getIntent().getStringExtra(TAG_LOAD_GAME);
        user = r.where(UserInput.class).equalTo("id", id).findFirst();
        sudoku = r.where(Sudoku.class).equalTo("idBoard", user.getBoard().getIdBoard()).findFirst();
    }

    /**
     * show the initial board
     * @param isResume to know if we need to load from the Sudoku or UserInput object.
     */
    public void showBoard(boolean isResume) {
        for (int i = 0; i < 81; i++) {

            TableRow row = (TableRow) board.getChildAt(i/9);

            TextView btn = (TextView) row.getChildAt(i % 9);

            btn.setOnClickListener(this);
            char value;
            if(isResume){
                value = user.getUserBoard().charAt(i);
            }else {
                value = sudoku.getOriginalBoard().charAt(i);
            }

            switch (value){
                case '1': btn.setId(i); btn.setText(getString(R.string.btn_1)); btn.setEnabled(false); break;
                case '2': btn.setId(i); btn.setText(getString(R.string.btn_2)); btn.setEnabled(false); break;
                case '3': btn.setId(i); btn.setText(getString(R.string.btn_3)); btn.setEnabled(false); break;
                case '4': btn.setId(i); btn.setText(getString(R.string.btn_4)); btn.setEnabled(false); break;
                case '5': btn.setId(i); btn.setText(getString(R.string.btn_5)); btn.setEnabled(false); break;
                case '6': btn.setId(i); btn.setText(getString(R.string.btn_6)); btn.setEnabled(false); break;
                case '7': btn.setId(i); btn.setText(getString(R.string.btn_7)); btn.setEnabled(false); break;
                case '8': btn.setId(i); btn.setText(getString(R.string.btn_8)); btn.setEnabled(false); break;
                case '9': btn.setId(i); btn.setText(getString(R.string.btn_9)); btn.setEnabled(false); break;
                default: btn.setId(i); btn.setText(""); btn.setEnabled(true);
            }
            if(isResume){
                checkCase();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(!chrono.isRunning()){
            if(user.getTimer() != 0)
                chrono.setBase(SystemClock.elapsedRealtime() - user.getTimer());
            chrono.start();
        }
        int value = -1;
        TextView text = (TextView) v;
        int id = text.getId();

        CharSequence t = text.getText();
        if(valueChoosed == -1) {
            try {
                value = Integer.valueOf(t.toString());
            } catch (Exception ignored) {
                value = 0;
            }
        }

        if(value == 9){
            value = 0;
        }else if(value == -1){
            if(t.equals(""+valueChoosed)){
                value = 0;
            }else value = valueChoosed;
        }else value++;

        text.setTextColor(getResources().getColorStateList(R.color.grid_text));
        r.beginTransaction();
        StringBuilder newBoard = new StringBuilder(user.getUserBoard());
            switch (value){
                case 1: text.setText(getString(R.string.btn_1)); newBoard.setCharAt(id,'1'); break;
                case 2: text.setText(getString(R.string.btn_2)); newBoard.setCharAt(id,'2'); break;
                case 3: text.setText(getString(R.string.btn_3)); newBoard.setCharAt(id,'3'); break;
                case 4: text.setText(getString(R.string.btn_4)); newBoard.setCharAt(id,'4'); break;
                case 5: text.setText(getString(R.string.btn_5)); newBoard.setCharAt(id,'5'); break;
                case 6: text.setText(getString(R.string.btn_6)); newBoard.setCharAt(id,'6'); break;
                case 7: text.setText(getString(R.string.btn_7)); newBoard.setCharAt(id,'7'); break;
                case 8: text.setText(getString(R.string.btn_8)); newBoard.setCharAt(id,'8'); break;
                case 9: text.setText(getString(R.string.btn_9)); newBoard.setCharAt(id,'9'); break;
                default: text.setText(""); newBoard.setCharAt(id,'0');
            }
            user.setUserBoard(newBoard.toString());
            user.setLastPlayed(Calendar.getInstance());
        r.insertOrUpdate(user);
        r.commitTransaction();
    }

    @Override
    public void onCheckedChanged(ToggleGroup group, int checkedId) {
        if(checkedId != -1) {
            ToggleButton toggleButton = (ToggleButton) findViewById(checkedId);
            CharSequence text = toggleButton.getText();
            try {
                valueChoosed = Integer.valueOf(text.toString());
            }catch (Exception ignored){
                valueChoosed = -1;
            }
        }else{
            valueChoosed = -1;
        }
    }

    /**
     * check if board is solve or not
     * if solve save sudoku with update data and launch stats activity
     * if not call checkCase()
     */
    public void solve(View view) {

        saveChrono();
        chrono.stop();

        r.beginTransaction();
            user.addTry();
            user.setLastPlayed(Calendar.getInstance());
        r.commitTransaction();
        boolean b = user.getUserBoard().equals(sudoku.getSolvedBoard());
        if(!b) checkCase();
        else {
            r.beginTransaction();
                sudoku.setDone(true);
                sudoku.setDoing(false);
                sudoku.setNumberEssais(user.getNumberEssais());
                sudoku.setTime(user.getTimer());
                r.insertOrUpdate(sudoku);
                r.insertOrUpdate(user);
            r.commitTransaction();
            Intent toStats = new Intent(this, StatsSudokuSingle.class);
            toStats.putExtra("user", user.getUserBoard());
            startActivity(toStats);
        }
    }

    /**
     * check each case for good value and lock them
     */
    private void checkCase() {
        String userBoard = user.getUserBoard();
        String solvedBoard = sudoku.getSolvedBoard();
        for (int i = 0; i < 81; i++) {
            TableRow row = (TableRow) board.getChildAt(i/9);
            TextView text = (TextView) row.getChildAt(i % 9);
            if(sudoku.getOriginalBoard().charAt(i) != userBoard.charAt(i)) {
                if (userBoard.charAt(i) == solvedBoard.charAt(i)) {
                    text.setTextColor(getResources().getColorStateList(R.color.grid_text_good));
                    text.setEnabled(false);
                } else {
                    text.setTextColor(getResources().getColorStateList(R.color.grid_text_bad));
                    text.setEnabled(true);
                }
            }
        }
    }

    /**
     * juste save the timer in userInput
     */
    private void saveChrono(){
        r.beginTransaction();
            user.setTimer(SystemClock.elapsedRealtime() - chrono.getBase());
        r.commitTransaction();
    }

    @Override
    protected void onPause() {
        super.onPause();
        r.beginTransaction();
            if(chrono.isRunning()){
                user.setTimer(SystemClock.elapsedRealtime() - chrono.getBase());
                chrono.stop();
            }
            r.insertOrUpdate(user);
        r.commitTransaction();
        if(!r.isClosed())
            r.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        r = Realm.getDefaultInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        r.beginTransaction();
            if(chrono.isRunning()){
                user.setTimer(SystemClock.elapsedRealtime() - chrono.getBase());
                chrono.stop();
            }
            r.insertOrUpdate(user);
        r.commitTransaction();
        Intent toMenu = new Intent(this, MenuActivity.class);
        startActivity(toMenu);
    }



    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        ConfirmDialog dialog = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putInt("TEXT", R.string.dialogReset);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    /**
     * called by button reset
     * @param view
     */
    public void reset(View view) {
        showNoticeDialog();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        r.beginTransaction();
            user.reset();
        r.commitTransaction();
        showBoard(false);
        checkCase();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!r.isClosed())
            r.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        r = Realm.getDefaultInstance();
    }
}
