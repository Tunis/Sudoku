package fr.fbouton.sudoku.activity.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import fr.fbouton.sudoku.R;
import fr.fbouton.sudoku.models.UserInput;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * simple adapter for the recyclerView listing the already started sudoku.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private RealmResults<UserInput> data;
    public interface OnItemClickListener {
        void onItemClick(UserInput item);
    }
    private final OnItemClickListener listener;
    public interface OnDeleteItemListener {
        void onDeleteItem(UserInput userInput);
    }
    OnDeleteItemListener mListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TableLayout layout;

        ViewHolder(TableLayout v) {
            super(v);
            layout = v;
        }

        void bind(final UserInput item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        TableLayout v = (TableLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_grid_retry_list, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    public BoardAdapter(OnItemClickListener listener, OnDeleteItemListener deleteItemListener) {
        this.listener = listener;
        this.mListener = deleteItemListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(RealmResults<UserInput> listStart) {
        data = listStart;
    }

    /**
     * method where we load the data and show it.
     * @param holder the cell view.
     * @param position the position of the data in the list.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        UserInput userInput = data.get(position);
        Realm r = Realm.getDefaultInstance();
        r.beginTransaction();
        if(userInput.getUserBoard() != null) {
            TextView date = (TextView) holder.layout.getChildAt(0);
            Calendar lastPlayed = userInput.getLastPlayed();
            CharSequence dateValue = String.format(Locale.getDefault(), "%1$tA %1$td %1$tb %1$tY", lastPlayed);
            date.setText(dateValue);
            char[] chars = userInput.getUserBoard().toCharArray();
            for (int i = 0; i < 81; i++) {
                TableRow row = (TableRow) holder.layout.getChildAt((i / 9)+1);
                TextView text = (TextView) row.getChildAt(i % 9);
                char value = chars[i];
                switch (value) {
                    case '1':
                        text.setText(holder.layout.getContext().getString(R.string.btn_1));
                        break;
                    case '2':
                        text.setText(holder.layout.getContext().getString(R.string.btn_2));
                        break;
                    case '3':
                        text.setText(holder.layout.getContext().getString(R.string.btn_3));
                        break;
                    case '4':
                        text.setText(holder.layout.getContext().getString(R.string.btn_4));
                        break;
                    case '5':
                        text.setText(holder.layout.getContext().getString(R.string.btn_5));
                        break;
                    case '6':
                        text.setText(holder.layout.getContext().getString(R.string.btn_6));
                        break;
                    case '7':
                        text.setText(holder.layout.getContext().getString(R.string.btn_7));
                        break;
                    case '8':
                        text.setText(holder.layout.getContext().getString(R.string.btn_8));
                        break;
                    case '9':
                        text.setText(holder.layout.getContext().getString(R.string.btn_9));
                        break;
                    default:
                        text.setText("");
                }
            }
        }
        r.commitTransaction();
        r.close();
        holder.bind(data.get(position), listener);
    }

    /**
     * remove the item in the list at the position.
     * @param position the position of the data in the list.
     */
    public void removeItem(int position) {
        final UserInput userInput = data.get(position);
        mListener.onDeleteItem(userInput);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }

}
