import junit.framework.TestCase;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import formation.test.djory.xboxgames.GameUtil;
import formation.test.djory.xboxgames.model.Game;
import formation.test.djory.xboxgames.model.Offer;

/**
 * Created by excilys on 15/11/15.
 */
public class GamePromotionTest extends TestCase {
    @Test
    public void testSimplePromotionMinus() throws Exception {
        //GIVEN
        List<Game> games = Arrays.asList(Game.getBuilder()
                .quantity(2)
                .price(25d).build());
        List<Offer> offers = Arrays.asList(Offer.getBuilder().type(Offer.Type.MINUS).value(10).build());
        //WHEN
        double total = GameUtil.computeBestPrice(games, offers);
        //THEN
        assertEquals(40d, total);
    }

    @Test
    public void testSimplePromotionPercentage() throws Exception {
        //GIVEN
        List<Game> games = Arrays.asList(Game.getBuilder()
                .quantity(2)
                .price(25d).build());
        List<Offer> offers = Arrays.asList(Offer.getBuilder().type(Offer.Type.PERCENTAGE).value(10).build());
        //WHEN
        double total = GameUtil.computeBestPrice(games, offers);
        //THEN
        assertEquals(45d, total);
    }

    @Test
    public void testSimplePromotionSlice() throws Exception {
        //GIVEN
        List<Game> games_49 = Arrays.asList(Game.getBuilder().quantity(1).price(49d).build());
        List<Game> games_50 = Arrays.asList(Game.getBuilder().quantity(2).price(25d).build());
        List<Game> games_76 = Arrays.asList(Game.getBuilder().quantity(1).price(76d).build());

        List<Offer> offers = Arrays.asList(Offer.getBuilder().type(Offer.Type.SLICE).value(10).sliceValue(25).build());
        //WHEN
        double total_49 = GameUtil.computeBestPrice(games_49, offers);
        double total_50 = GameUtil.computeBestPrice(games_50, offers);
        double total_76 = GameUtil.computeBestPrice(games_76, offers);
        //THEN
        assertEquals(39d, total_49);
        assertEquals(30d, total_50);
        assertEquals(46d, total_76);
    }

    @Test
    public void testComplexePromotionWithMinusChoosen() throws Exception {
        //GIVEN
        List<Game> games_200 = Arrays.asList(
                Game.getBuilder().quantity(2).price(50d).build(),
                Game.getBuilder().quantity(2).price(30d).build(),
                Game.getBuilder().quantity(1).price(40d).build());

        List<Offer> offers = Arrays.asList(
                Offer.getBuilder().type(Offer.Type.MINUS).value(60).build(), // -60
                Offer.getBuilder().type(Offer.Type.PERCENTAGE).value(10).build(), // -10
                Offer.getBuilder().type(Offer.Type.SLICE).value(5).sliceValue(25).build()); // -40
        //WHEN
        double total = GameUtil.computeBestPrice(games_200, offers);
        //THEN
        assertEquals(140d, total);
    }

    @Test
    public void testComplexePromotionWithPercentageChoosen() throws Exception {
        //GIVEN
        List<Game> games_200 = Arrays.asList(
                Game.getBuilder().quantity(2).price(50d).build(),
                Game.getBuilder().quantity(2).price(30d).build(),
                Game.getBuilder().quantity(1).price(40d).build());

        List<Offer> offers = Arrays.asList(
                Offer.getBuilder().type(Offer.Type.MINUS).value(30).build(), // -60
                Offer.getBuilder().type(Offer.Type.PERCENTAGE).value(40).build(), // -80
                Offer.getBuilder().type(Offer.Type.SLICE).value(9).sliceValue(25).build()); // -72
        //WHEN
        double total = GameUtil.computeBestPrice(games_200, offers);
        //THEN
        assertEquals(120d, total);
    }

    @Test
    public void testComplexePromotionWithSliceChoosen() throws Exception {
        //GIVEN
        List<Game> games_200 = Arrays.asList(
                Game.getBuilder().quantity(2).price(50d).build(),
                Game.getBuilder().quantity(2).price(30d).build(),
                Game.getBuilder().quantity(1).price(40d).build());

        List<Offer> offers = Arrays.asList(
                Offer.getBuilder().type(Offer.Type.MINUS).value(30).build(), // -60
                Offer.getBuilder().type(Offer.Type.PERCENTAGE).value(10).build(), // -10
                Offer.getBuilder().type(Offer.Type.SLICE).value(9).sliceValue(25).build()); // -72
        //WHEN
        double total = GameUtil.computeBestPrice(games_200, offers);
        //THEN
        assertEquals(128d, total);
    }
}