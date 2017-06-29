package fr.fbouton.sudoku.services;


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.mariuszgromada.math.janetsudoku.SudokuGenerator;
import org.mariuszgromada.math.janetsudoku.SudokuSolver;


public class GeneratorService extends IntentService{

    public static final String GENERATE_PUZZLE = "fr.fbouton.sudoku.game";
    public static final String PUZZLE_GENERATED = "generated";
    public static final String PUZZLE_SOLVED = "solved";

    private SudokuGenerator generator;
    private SudokuSolver solver = new SudokuSolver();

    public GeneratorService(){super("service");}

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public GeneratorService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        generate();
    }

    private void generate(){
        Log.w("TEST", "lancement generation");
        int[][] solved = null;
        int[][] generate;
        do {
            generator = new SudokuGenerator(SudokuGenerator.PARAM_GEN_RND_BOARD);
            generate = generator.generate();
            solver.loadBoard(generate);
            solver.solve();
            if (solver.checkIfUniqueSolution() == 1) {
                solved = solver.getSolvedBoard();
            }
        }while (solved == null);
        Log.w("TEST", "fin generation");
        Intent sendPuzzle = new Intent(GENERATE_PUZZLE);
        sendPuzzle.setAction(PuzzleReceiver.ACTION_RESP);
        sendPuzzle.addCategory(Intent.CATEGORY_DEFAULT);
        sendPuzzle.putExtra(PUZZLE_GENERATED, generate);
        sendPuzzle.putExtra(PUZZLE_SOLVED, solved);
        sendBroadcast(sendPuzzle);
    }
}
