package formation.test.djory.xboxgames.model;

import java.io.Serializable;

/**
 * Created by excilys on 12/11/15.
 */
public class Game implements Serializable {
    private String code;
    private String title;
    private double price;
    private String cover;
    private int quantity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Game{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", cover='" + cover + '\'' +
                '}';
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Game game = new Game();

        public Builder code(String code) {
            this.game.code = code;
            return this;
        }

        public Builder title(String title) {
            this.game.title = title;
            return this;
        }

        public Builder price(double price) {
            this.game.price = price;
            return this;
        }

        public Builder cover(String cover) {
            this.game.cover = cover;
            return this;
        }

        public Builder quantity(int quantity) {
            this.game.quantity = quantity;
            return this;
        }

        public Game build() {
            return game;
        }

    }

}
