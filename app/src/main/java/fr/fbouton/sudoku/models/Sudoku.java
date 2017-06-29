package fr.fbouton.sudoku.models;

import java.io.Serializable;
import java.util.UUID;

import io.realm.RealmObject;

@SuppressWarnings("unused")
/**
 * Models for the sudoku,
 * save the initial board, save board, the status of the sudoku
 * the time and number of try for finished sudoku
 */
public class Sudoku extends RealmObject implements Serializable {


    private String idBoard = UUID.randomUUID().toString();
    private String originalBoard;
    private String solvedBoard;
    private boolean done;
    private int numberEssais;
    private long time;
    private boolean doing;

    public Sudoku() {}

    public String getIdBoard() {
        return idBoard;
    }

    public void setIdBoard(String idBoard) {
        this.idBoard = idBoard;
    }

    public String getOriginalBoard() {
        return originalBoard;
    }

    public void setOriginalBoard(String originalBoard) {
        this.originalBoard = originalBoard;
    }

    public String getSolvedBoard() {
        return solvedBoard;
    }

    public void setSolvedBoard(String solvedBoard) {
        this.solvedBoard = solvedBoard;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isDoing() {
        return doing;
    }

    public void setDoing(boolean doing) {
        this.doing = doing;
    }
}
