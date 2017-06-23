package fr.fbouton.sudoku.game;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.mariuszgromada.math.janetsudoku.SudokuGenerator;
import org.mariuszgromada.math.janetsudoku.SudokuSolver;
import org.mariuszgromada.math.janetsudoku.SudokuStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorService extends Service{

    public static final String GENERATE_PUZZLE = "fr.fbouton.sudoku.game";

    private SudokuGenerator generator = new SudokuGenerator(SudokuGenerator.PARAM_GEN_RND_BOARD);
    private SudokuSolver solver = new SudokuSolver();

    public GeneratorService(){}

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void generate(){
        int[][] solved = null;
        int[][] generate = generator.generate();
        Log.w("TEST",generator.getMessages());
        solver.loadBoard(generate);
        if(solver.solve() == 3){
            solved = solver.getSolvedBoard();
        }
        Intent sendPuzzle = new Intent(GENERATE_PUZZLE, PuzzleReceiver.class);
        sendPuzzle.putExtra("generated", generate);
        sendPuzzle.putExtra("solved", solved);
        sendBroadcast(sendPuzzle);
    }
}
