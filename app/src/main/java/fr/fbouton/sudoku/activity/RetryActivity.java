package fr.fbouton.sudoku.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import fr.fbouton.sudoku.R;
import fr.fbouton.sudoku.activity.adapter.BoardAdapter;
import fr.fbouton.sudoku.models.UserInput;
import io.realm.Realm;
import io.realm.RealmResults;


public class RetryActivity extends Activity {

    public final static String TAG_LOAD_GAME = "loadGame";

    private RecyclerView mRecyclerView;
    private BoardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retry_game);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerRetry);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_list));
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        // specify an adapter (see also next example)

        mAdapter = new BoardAdapter(new BoardAdapter.OnItemClickListener() {
            @Override public void onItemClick(UserInput item) {
                Intent load = new Intent(RetryActivity.this, GameActivity.class);
                load.putExtra(TAG_LOAD_GAME, item.getId());
                load.setAction(TAG_LOAD_GAME);
                startActivity(load);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        Realm r = Realm.getDefaultInstance();
        RealmResults<UserInput> listStart = r.where(UserInput.class).findAll();
        mAdapter.setData(listStart);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent toMenu = new Intent(this, MenuActivity.class);
        startActivity(toMenu);
    }
}
