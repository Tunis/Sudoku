package fr.fbouton.sudoku.models;

import java.io.Serializable;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class UserInput extends RealmObject implements Serializable{
    private Sudoku board;
    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private String userBoard;
    @Index
    private boolean done = false;
    private int numberEssais = 1;
    private long timer = 0;


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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
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
}
