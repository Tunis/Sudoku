package fr.fbouton.sudoku.services;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import fr.fbouton.sudoku.activity.GameActivity;

import static fr.fbouton.sudoku.services.GeneratorService.PUZZLE_GENERATED;
import static fr.fbouton.sudoku.services.GeneratorService.PUZZLE_SOLVED;

public class PuzzleReceiver extends BroadcastReceiver {


    public static final String ACTION_RESP = "get_puzzle";
    private final GameActivity gameActivity;

    public PuzzleReceiver(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("TEST", "puzzle generer.");
        int[][] base = (int[][]) intent.getSerializableExtra(PUZZLE_GENERATED);
        int[][] solved = (int[][]) intent.getSerializableExtra(PUZZLE_SOLVED);
        //gameActivity.setBoards(base, solved);
    }
}
