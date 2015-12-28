package formation.test.djory.xboxgames.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by excilys on 12/11/15.
 */
public class Offer {

    @SerializedName("type")
    private Type type;
    private int value;
    private int sliceValue;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSliceValue() {
        return sliceValue;
    }

    public void setSliceValue(int sliceValue) {
        this.sliceValue = sliceValue;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "type=" + type +
                ", value=" + value +
                ", sliceValue=" + sliceValue +
                '}';
    }

    public enum Type {
        @SerializedName("percentage")PERCENTAGE,
        @SerializedName("minus")MINUS,
        @SerializedName("slice")SLICE
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Offer offer = new Offer();

        public Builder type(Type type) {
            this.offer.type = type;
            return this;
        }

        public Builder value(int value) {
            this.offer.value = value;
            return this;
        }

        public Builder sliceValue(int sliceValue) {
            this.offer.sliceValue = sliceValue;
            return this;
        }

        public Offer build() {
            return offer;
        }
    }
}
