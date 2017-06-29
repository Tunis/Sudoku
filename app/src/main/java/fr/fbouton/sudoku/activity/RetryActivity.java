package fr.fbouton.sudoku.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import fr.fbouton.sudoku.R;
import fr.fbouton.sudoku.activity.adapter.BoardAdapter;
import fr.fbouton.sudoku.models.UserInput;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * activity showing the list of starting sudoku
 */
public class RetryActivity extends Activity {

    public final static String TAG_LOAD_GAME = "loadGame";
    private BoardAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retry_game);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerRetry);
        Realm r = Realm.getDefaultInstance();

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_list));
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        // specify an adapter (see also next example)
        // add listener to load gameActivity with the good sudoku
        mAdapter = new BoardAdapter(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserInput item) {
                Intent load = new Intent(RetryActivity.this, GameActivity.class);
                load.putExtra(TAG_LOAD_GAME, item.getId());
                load.setAction(TAG_LOAD_GAME);
                startActivity(load);
            }
        }, r);
        mRecyclerView.setAdapter(mAdapter);

        // get data from bdd and sorted them by date
        RealmResults<UserInput> listStart = r.where(UserInput.class).findAll().sort("lastPlayed");
        mAdapter.setData(listStart);
        initSwipe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent toMenu = new Intent(this, MenuActivity.class);
        startActivity(toMenu);
    }

    /**
     * method that init the support of swipe for the recycler view
     */
    private void initSwipe(){
        // ItemtouchHelper.LEFT to only permit left swip
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // action to do when swipe
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    mAdapter.removeItem(position);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}
