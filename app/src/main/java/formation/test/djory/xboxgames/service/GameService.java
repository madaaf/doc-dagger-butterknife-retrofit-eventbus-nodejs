package formation.test.djory.xboxgames.service;

import java.util.List;

import formation.test.djory.xboxgames.model.Game;
import formation.test.djory.xboxgames.model.Offers;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;


public interface GameService {
    @GET("/games")
    void getGames(Callback<List<Game>> games);

    @GET("/games/{codes}/offers")
    void getOffers(@Path("codes") String codes, Callback<Offers> offers);

}
