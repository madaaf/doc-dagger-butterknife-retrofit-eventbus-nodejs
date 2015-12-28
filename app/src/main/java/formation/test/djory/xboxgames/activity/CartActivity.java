package formation.test.djory.xboxgames.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import formation.test.djory.xboxgames.GameAdapter;
import formation.test.djory.xboxgames.GameComponentApplication;
import formation.test.djory.xboxgames.GameUtil;
import formation.test.djory.xboxgames.R;
import formation.test.djory.xboxgames.manager.GameManager;
import formation.test.djory.xboxgames.model.Game;
import formation.test.djory.xboxgames.model.Offers;

public class CartActivity extends AppCompatActivity {
    private static final String EXTRA_SHOPPING_CART = "shopping_cart";

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.total)
    TextView total;
    @Inject
    GameManager gameManager;
    @Inject
    EventBus bus;

    private ArrayList<Game> shoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        GameComponentApplication.app().getComponent().inject(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        Bundle extra = getIntent().getExtras();
        shoppingCart = (ArrayList<Game>) extra.getSerializable(EXTRA_SHOPPING_CART);
        GameAdapter gameAdapter = new GameAdapter(new ArrayList<>(shoppingCart));

        recyclerView.setAdapter(gameAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameManager.getOffers(shoppingCart);
    }


    public void loadOffers(Offers offers) {
        double totalPrice = GameUtil.computeBestPrice(shoppingCart, offers.getOffers());
        double totalWithoutReduction = GameUtil.sum(shoppingCart);
        double reduction = totalWithoutReduction - totalPrice;
        total.setText(getString(R.string.total_price, totalWithoutReduction, reduction, totalPrice));
    }


    // EVENT
    @SuppressWarnings("unused")
    public void onEventMainThread(Offers offers) {
        progressBar.setVisibility(View.GONE);
        loadOffers(offers);
    }


    // STARTER

    public static void Starter(Context context, ArrayList<Game> shoppingCart) {
        Intent intent = new Intent(context, CartActivity.class);
        intent.putExtra(EXTRA_SHOPPING_CART, shoppingCart);
        context.startActivity(intent);
    }

}
