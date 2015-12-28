package formation.test.djory.xboxgames;

import com.annimon.stream.Stream;

import java.util.List;

import formation.test.djory.xboxgames.model.Game;
import formation.test.djory.xboxgames.model.Offer;
import timber.log.Timber;

/**
 * Created by excilys on 15/11/15.
 */
public final class GameUtil {

    private GameUtil() {
        super();
    }

    public static double computeBestPrice(List<Game> games, List<Offer> offers) {
        double sum = sum(games);
        double minimum = sum;
        for (Offer offer : offers) {
            double temporary = sum;
            switch (offer.getType()) {
                case PERCENTAGE:
                    temporary = sum * (100 - offer.getValue()) / 100;
                    break;
                case MINUS:
                    temporary -= offer.getValue();
                    break;
                case SLICE:
                    temporary -= ((int) temporary / offer.getSliceValue()) * offer.getValue();
                    break;
                default:
                    Timber.e("The Offer.Type is unknown");
            }
            if (minimum > temporary) {
                minimum = temporary;
            }
        }
        return minimum;
    }

    public static double sum(List<Game> games) {
        return Stream.of(games).map(game -> game.getPrice() * game.getQuantity()).reduce(0d, (x, y) -> x + y);
    }

}
