package fr.fbouton.sudoku.layout;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
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

    private OnFragmentInteractionListener mListener;
    private TextView timeClearMoy;
    private TextView nbEssai;
    private TextView nbEssaiMoy;
    private TextView nbSudoku;
    private TextView nbStartSudoku;
    private TextView nbFinishSudoku;
    private TextView timeClear;

    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StatsFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        nbEssai = (TextView) view.findViewById(R.id.nbEssaiGlobalField);
        nbEssaiMoy = (TextView) view.findViewById(R.id.nbEssaiMoyField);

        nbSudoku = (TextView) view.findViewById(R.id.numberOfSudokuField);

        nbStartSudoku = (TextView) view.findViewById(R.id.numberOfStartingSudokuField);

        nbFinishSudoku = (TextView) view.findViewById(R.id.numberOfFinishedSudokuField);

        timeClear = (TextView) view.findViewById(R.id.timeToClearGlobalField);
        timeClearMoy = (TextView) view.findViewById(R.id.timeToClearMoyField);

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public int getYears(long timer){
        return (int)(timer / ((((1000L*60L)*60L)*24L)*365L));
    }

    public int getDays(long timer, boolean withYears){
        if(withYears)
            return (int)((timer / (1000*60*60*24)) % 365);
        return (int)((timer / (1000*60*60*24)));
    }

    public int getHeures(long timer) {
        return (int) ((timer / (1000*60*60)) % 24);
    }

    public int getMinutes(long timer) {
        return (int) ((timer / (1000*60)) % 60);
    }

    public int getSecondes(long timer) {
        return (int) (timer / 1000) % 60 ;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
