package fr.fbouton.sudoku.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.fbouton.sudoku.R;
import fr.fbouton.sudoku.models.Sudoku;
import fr.fbouton.sudoku.models.UserInput;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {

    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StatsFragment.
     */
    public static StatsFragment newInstance() {
        return new StatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        TextView nbEssai = (TextView) view.findViewById(R.id.nbEssaiGlobalField);
        TextView nbEssaiMoy = (TextView) view.findViewById(R.id.nbEssaiMoyField);

        TextView nbSudoku = (TextView) view.findViewById(R.id.numberOfSudokuField);

        TextView nbStartSudoku = (TextView) view.findViewById(R.id.numberOfStartingSudokuField);

        TextView nbFinishSudoku = (TextView) view.findViewById(R.id.numberOfFinishedSudokuField);

        TextView timeClear = (TextView) view.findViewById(R.id.timeToClearGlobalField);
        TextView timeClearMoy = (TextView) view.findViewById(R.id.timeToClearMoyField);

        Realm r = Realm.getDefaultInstance();
        long nbsSudoku = r.where(Sudoku.class).count();
        RealmResults<Sudoku> listdone = r.where(Sudoku.class).equalTo("done", true).findAll();
        long doing = r.where(Sudoku.class).equalTo("doing", true).count();
        long done = listdone.size();
        RealmResults<UserInput> listStats = r.where(UserInput.class).findAll();


        long numberEssais = 0;
        long essayMoy = 0;
        long time = 0;
        long timeMoy = 0;

        long nbEssaiDone = listdone.size() > 0 ? listdone.sum("numberEssais").longValue() : 0;
        long nbEssaiDoing = listStats.size() > 0 ? listStats.sum("numberEssais").longValue() : 0;
        long timeDone = listdone.size() > 0 ? listdone.sum("time").longValue() : 0;
        long timeDoing = listStats.size() > 0 ? listStats.sum("timer").longValue() : 0;
        if(doing > 0 || done > 0) {
            numberEssais = nbEssaiDoing + nbEssaiDone;
            essayMoy = numberEssais / (doing + done);

            time = timeDoing + timeDone;
            timeMoy = time / (done + doing);
        }

        nbSudoku.setText(getString(R.string.numberOfSudoku, nbsSudoku));
        nbStartSudoku.setText(getString(R.string.numberOfStartingSudoku, doing));
        nbFinishSudoku.setText(getString(R.string.numberOfFinishedSudoku, done));

        nbEssai.setText(getString(R.string.nbEssaiGlobal, numberEssais));
        nbEssaiMoy.setText(getString(R.string.nbEssaiMoy, essayMoy));

        timeClear.setText(getString(R.string.timeToClearGlobal, getYears(time), getDays(time, true), getHeures(time), getMinutes(time), getSecondes(time)));
        timeClearMoy.setText(getString(R.string.timeToClearMoy, getDays(timeMoy, false), getHeures(timeMoy), getMinutes(timeMoy), getSecondes(timeMoy)));

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     *
     *  simple method to calculate the number of year in the timer.
     *
     * @param timer the timer that you want the number of year from
     * @return the number of year in the timer
     */
    public int getYears(long timer){
        return (int)(timer / ((((1000L*60L)*60L)*24L)*365L));
    }
    /**
     *
     *  simple method to calculate the number of days in the timer.
     *
     * @param timer the timer that you want the number of days from
     * @param withYears boolean to say if you have the years calculate too or not
     * @return the number of days in the timer
     */
    public int getDays(long timer, boolean withYears){
        if(withYears)
            return (int)((timer / (1000*60*60*24)) % 365);
        return (int)((timer / (1000*60*60*24)));
    }
    /**
     *
     *  simple method to calculate the number of hours in the timer.
     *
     * @param timer the timer that you want the number of hours from
     * @return the number of hours in the timer
     */
    public int getHeures(long timer) {
        return (int) ((timer / (1000*60*60)) % 24);
    }
    /**
     *
     *  simple method to calculate the number of minutes in the timer.
     *
     * @param timer the timer that you want the number of minutes from
     * @return the number of minutes in the timer
     */
    public int getMinutes(long timer) {
        return (int) ((timer / (1000*60)) % 60);
    }
    /**
     *
     *  simple method to calculate the number of secondes in the timer.
     *
     * @param timer the timer that you want the number of secondes from
     * @return the number of secondes in the timer
     */
    public int getSecondes(long timer) {
        return (int) (timer / 1000) % 60 ;
    }
}
