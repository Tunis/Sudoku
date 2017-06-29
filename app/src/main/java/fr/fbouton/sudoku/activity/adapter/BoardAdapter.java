package fr.fbouton.sudoku.activity.adapter;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import fr.fbouton.sudoku.R;
import fr.fbouton.sudoku.activity.RetryActivity;
import fr.fbouton.sudoku.models.Sudoku;
import fr.fbouton.sudoku.models.UserInput;
import io.realm.Realm;
import io.realm.RealmResults;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    RealmResults<UserInput> data;
    public interface OnItemClickListener {
        void onItemClick(UserInput item);
    }
    private final OnItemClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TableLayout layout;

        public ViewHolder(TableLayout v) {
            super(v);
            layout = v;
        }

        public void bind(final UserInput item, final OnItemClickListener listener) {
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

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public BoardAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(RealmResults<UserInput> listStart) {
        data = listStart;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        UserInput userInput = data.get(position);
        Realm r = Realm.getDefaultInstance();
        r.beginTransaction();
        if(userInput.getUserBoard() != null) {
            char[] chars = userInput.getUserBoard().toCharArray();
            for (int i = 0; i < 81; i++) {
                TableRow row = (TableRow) holder.layout.getChildAt(i / 9);
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
        holder.bind(data.get(position), listener);
    }

}
