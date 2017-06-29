package fr.fbouton.sudoku.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@SuppressWarnings("unused")
/**
 * model for the save of in doing sudoku
 */
public class UserInput extends RealmObject implements Serializable{
    private Sudoku board;
    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String userBoard;
    private int numberEssais = 1;
    private long timer = 0;
    private long lastPlayed;


    public UserInput() {}

    public UserInput(Sudoku board) {
        this.board = board;
        setUserBoard(board.getOriginalBoard());
    }

    public Sudoku getBoard() {
        return board;
    }

    public void setBoard(Sudoku board) {
        this.board = board;
    }

    public String getUserBoard() {
        return userBoard;
    }

    public void setUserBoard(String userBoard) {
        this.userBoard = userBoard;
    }

    public int getNumberEssais() {
        return numberEssais;
    }

    public void setNumberEssais(int numberEssais) {
        this.numberEssais = numberEssais;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public void reset(){
        setUserBoard(board.getOriginalBoard());
    }

    public void addTry() {
        numberEssais++;
    }

    public int getHeures() {
        return (int) ((timer / (1000*60*60)) % 24);
    }

    public int getMinutes() {
        return (int) ((timer / (1000*60)) % 60);
    }

    public int getSecondes() {
        return (int) (timer / 1000) % 60 ;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Calendar getLastPlayed() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(lastPlayed);
        return date;
    }

    public void setLastPlayed(Calendar lastPlayed) {
        this.lastPlayed = lastPlayed.getTimeInMillis();
    }
}
