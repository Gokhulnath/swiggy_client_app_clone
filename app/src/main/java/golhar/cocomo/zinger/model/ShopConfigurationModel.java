package golhar.cocomo.zinger.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopConfigurationModel implements Parcelable {
    private ShopModel shopModel;
    private RatingModel ratingModel;
    private ConfigurationModel configurationModel;

    protected ShopConfigurationModel(Parcel in) {
        shopModel = in.readParcelable(ShopModel.class.getClassLoader());
        ratingModel = in.readParcelable(RatingModel.class.getClassLoader());
        configurationModel = in.readParcelable(ConfigurationModel.class.getClassLoader());
    }

    public static final Creator<ShopConfigurationModel> CREATOR = new Creator<ShopConfigurationModel>() {
        @Override
        public ShopConfigurationModel createFromParcel(Parcel in) {
            return new ShopConfigurationModel(in);
        }

        @Override
        public ShopConfigurationModel[] newArray(int size) {
            return new ShopConfigurationModel[size];
        }
    };

    public ShopModel getShopModel() {
        return shopModel;
    }

    public void setShopModel(ShopModel shopModel) {
        this.shopModel = shopModel;
    }

    public RatingModel getRatingModel() {
        return ratingModel;
    }

    public void setRatingModel(RatingModel ratingModel) {
        this.ratingModel = ratingModel;
    }

    public ConfigurationModel getConfigurationModel() {
        return configurationModel;
    }

    public void setConfigurationModel(ConfigurationModel configurationModel) {
        this.configurationModel = configurationModel;
    }

    @Override
    public String toString() {
        return "ShopConfigurationModel{" +
                "shopModel=" + shopModel +
                ", ratingModel=" + ratingModel +
                ", configurationModel=" + configurationModel +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(shopModel, flags);
        dest.writeParcelable(ratingModel, flags);
        dest.writeParcelable(configurationModel, flags);
    }
}
