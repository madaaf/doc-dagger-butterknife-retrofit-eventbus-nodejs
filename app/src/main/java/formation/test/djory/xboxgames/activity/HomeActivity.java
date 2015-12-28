package formation.test.djory.xboxgames.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx.OnPageChangeListener;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import formation.test.djory.xboxgames.BuildConfig;
import formation.test.djory.xboxgames.GameComponentApplication;
import formation.test.djory.xboxgames.R;
import formation.test.djory.xboxgames.manager.GameManager;
import formation.test.djory.xboxgames.model.Game;

public class HomeActivity extends AppCompatActivity implements OnPageChangeListener {

    private final static String EXTRA_GAME = "game";
    @Bind(R.id.quantity) TextView quantity;
    @Bind(R.id.fab_add_to_cart) FloatingActionButton fabAddToCart;
    @Bind(R.id.fab_remove_to_cart) FloatingActionButton fabRemoveCart;
    @Bind(R.id.fab_validate_cart) FloatingActionButton fabValidateCart;
    @Bind(R.id.description) TextView description;
    @Bind(R.id.progress_bar) ProgressBar progressBar;
    @Bind(R.id.slider) SliderLayout sliderLayout;
    @Bind(R.id.custom_indicator) PagerIndicator pagerIndicator;
    @Inject
    GameManager gameManager;
    @Inject EventBus bus;

    ArrayList<Game> shoppingCart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        GameComponentApplication.app().getComponent().inject(this);
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
        gameManager.getGame();
    }


    public void loadGames(List<Game> games) {
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);
        StringBuilder stringBuilder = new StringBuilder();
        for (Game game : games) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            // FIXME : The locale for the Currency should not be implemented like that.
            // This is just to have the current symbol.
            // We need to admit that the backend server send the currency and the price converte.
            stringBuilder.append(game.getTitle()).append(" - ").append(game.getPrice()).append(Currency.getInstance(Locale.getDefault()).getSymbol());
            textSliderView
                    .description(stringBuilder.toString())
                    .image(BuildConfig.rootUrl+game.getCover())
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putSerializable(EXTRA_GAME, game);

            sliderLayout.addSlider(textSliderView);

            stringBuilder.delete(0, stringBuilder.length());
        }
        sliderLayout.addOnPageChangeListener(this);
        fabAddToCart.setVisibility(View.VISIBLE);
        fabValidateCart.setVisibility(View.VISIBLE);
    }

    // VISIBILITY

    private void updateView() {
        Game game = getCurrentGame();
        if (game.getQuantity() > 0) {
            fabRemoveCart.setVisibility(View.VISIBLE);
            description.setText(getString(R.string.game_description, game.getTitle(), game.getQuantity()));
        } else {
            fabRemoveCart.setVisibility(View.GONE);
            description.setText(getString(R.string.game_none, game.getTitle()));
        }
        quantity.setText(Integer.toString(Stream.of(shoppingCart).map(b -> b.getQuantity()).reduce(0, (x, y) -> x + y)));
    }


    // LISTENERS

    @OnClick(R.id.fab_add_to_cart)
    public synchronized void addGame() {
        Game game = getCurrentGame();
        game.setQuantity(game.getQuantity() + 1);
        if(game.getQuantity() == 1){
            shoppingCart.add(game);
        }

        updateView();
    }

    @OnClick(R.id.fab_remove_to_cart)
    public synchronized void removeGame() {
        Game game = getCurrentGame();
        if (game.getQuantity() > 0) {
            game.setQuantity(game.getQuantity() - 1);
        }
        if (game.getQuantity() == 0) {
            shoppingCart.remove(game);
        }
        updateView();
    }

    @OnClick(R.id.fab_validate_cart)
    public void validateCart() {
        if (shoppingCart.isEmpty()) {
            Crouton.makeText(this, getString(R.string.error_cart_empty), Style.ALERT).show();
        } else {
            CartActivity.Starter(this, shoppingCart);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        updateView();
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    // EVENT
    @SuppressWarnings("unused")
    public void onEventMainThread(List<Game> games) {
        progressBar.setVisibility(View.GONE);
        loadGames(games);
    }

    // LISIBILITY

    private Game getCurrentGame() {
        return (Game) sliderLayout.getCurrentSlider().getBundle().getSerializable(EXTRA_GAME);
    }

}
