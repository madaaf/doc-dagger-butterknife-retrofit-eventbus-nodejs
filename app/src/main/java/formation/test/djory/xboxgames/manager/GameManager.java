package formation.test.djory.xboxgames.manager;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.event.EventBus;
import formation.test.djory.xboxgames.model.Game;
import formation.test.djory.xboxgames.model.Offers;
import formation.test.djory.xboxgames.service.GameService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * Created by excilys on 12/11/15.
 */
@Singleton
public class GameManager {
    GameService gameService;
    EventBus bus;

    @Inject
    public GameManager(GameService gameService, EventBus bus) {
        this.gameService = gameService;
        this.bus = bus;
    }

    public void getGame() {
        gameService.getGames(new Callback<List<Game>>() {
            @Override
            public void success(List<Game> games, Response response) {
                Timber.d("getting games : %s ", games);
                bus.post(games);
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e(error, "Impossible to get games");
            }
        });
    }

    public void getOffers(List<Game> games){
        // Offers are define by the code.
        StringBuilder codes = new StringBuilder();
        Stream.of(games).forEach(game -> codes.append(game.getCode()).append(","));
        gameService.getOffers(codes.toString(), new Callback<Offers>() {
                    @Override
                    public void success(Offers offers, Response response) {
                        Timber.e("getting offers : %s ", offers);
                        bus.post(offers);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Timber.e(error, "Impossible to get offers");
                    }
                }
        );
    }
}
