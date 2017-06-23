package fr.fbouton.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import org.mariuszgromada.math.janetsudoku.SudokuStore;

import fr.fbouton.sudoku.game.GeneratorService;
import fr.fbouton.sudoku.layout.utils.ToggleGroup;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, ToggleGroup.OnCheckedChangeListener {

    private int[][] userBoard;
    private int[][] originalBoard;
    private int[][] solvedBoard;
    private android.support.v7.widget.GridLayout board;
    private ToggleGroup toggleGroup;
    private int valueChoosed = -1;
    private GeneratorService generatorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        generatorService = new GeneratorService();


        board = (android.support.v7.widget.GridLayout) findViewById(R.id.gameBoard);
        toggleGroup = (ToggleGroup) findViewById(R.id.toggleGroup);
        toggleGroup.setOnCheckedChangeListener(this);

        showBoard();
    }

    private void showBoard() {

        for (int i = 0; i < 81; i++) {

            Button btn = (Button) board.getChildAt(i);
            btn.setOnClickListener(this);
            int value = originalBoard[i/9][i%9];

            switch (value){
                case 1: btn.setText(getString(R.string.btn_1)); btn.setEnabled(false); break;
                case 2: btn.setText(getString(R.string.btn_2)); btn.setEnabled(false); break;
                case 3: btn.setText(getString(R.string.btn_3)); btn.setEnabled(false); break;
                case 4: btn.setText(getString(R.string.btn_4)); btn.setEnabled(false); break;
                case 5: btn.setText(getString(R.string.btn_5)); btn.setEnabled(false); break;
                case 6: btn.setText(getString(R.string.btn_6)); btn.setEnabled(false); break;
                case 7: btn.setText(getString(R.string.btn_7)); btn.setEnabled(false); break;
                case 8: btn.setText(getString(R.string.btn_8)); btn.setEnabled(false); break;
                case 9: btn.setText(getString(R.string.btn_9)); btn.setEnabled(false); break;
                default: btn.setText("");
            }
        }
    }

    @Override
    public void onClick(View v) {
        int value = -1;
        Button btn = (Button) v;
        if(valueChoosed == -1) {
            CharSequence t = btn.getText();
            try {
                value = Integer.valueOf(t.toString());
            } catch (Exception ignored) {
                value = 0;
            }
        }

        if(value == 9){
            value = 0;
        }else if(value == -1){
            value = valueChoosed;
        }else value++;
        switch (value){
            case 1: btn.setText(getString(R.string.btn_1)); break;
            case 2: btn.setText(getString(R.string.btn_2)); break;
            case 3: btn.setText(getString(R.string.btn_3)); break;
            case 4: btn.setText(getString(R.string.btn_4)); break;
            case 5: btn.setText(getString(R.string.btn_5)); break;
            case 6: btn.setText(getString(R.string.btn_6)); break;
            case 7: btn.setText(getString(R.string.btn_7)); break;
            case 8: btn.setText(getString(R.string.btn_8)); break;
            case 9: btn.setText(getString(R.string.btn_9)); break;
            default: btn.setText("");
        }
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

    public void reset(View view) {
        showBoard();
    }

    public void solve(View view) {
        SudokuStore.boardsAreEqual(userBoard, solvedBoard);
    }
}
