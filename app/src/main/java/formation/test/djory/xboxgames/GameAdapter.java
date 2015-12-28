package formation.test.djory.xboxgames;


import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import formation.test.djory.xboxgames.model.Game;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private ArrayList<Game> gameSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.quantity)
        TextView quantity;
        @Bind(R.id.price)
        TextView price;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public void add(int position, Game item) {
        gameSet.add(position, item);
        notifyItemInserted(position);
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public GameAdapter(ArrayList<Game> gameSet) {
        this.gameSet = gameSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_game, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        v.setTag(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Game game = gameSet.get(position);
        Context context = holder.quantity.getContext();
        holder.title.setText(game.getTitle());
        holder.quantity.setText(context.getString(R.string.quantity, game.getQuantity()));
        holder.price.setText(game.getPrice() * game.getQuantity() + Currency.getInstance(Locale.getDefault()).getSymbol());
        Picasso.with(context).load(BuildConfig.rootUrl + game.getCover()).into(holder.image);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return gameSet.size();
    }


}