package golhar.cocomo.zinger.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RatingModel implements Parcelable {
    private ShopModel shopModel;
    private Double rating;
    private Integer userCount;

    public RatingModel() {
        shopModel = new ShopModel();
    }

    protected RatingModel(Parcel in) {
        shopModel = in.readParcelable(ShopModel.class.getClassLoader());
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        if (in.readByte() == 0) {
            userCount = null;
        } else {
            userCount = in.readInt();
        }
    }

    public static final Creator<RatingModel> CREATOR = new Creator<RatingModel>() {
        @Override
        public RatingModel createFromParcel(Parcel in) {
            return new RatingModel(in);
        }

        @Override
        public RatingModel[] newArray(int size) {
            return new RatingModel[size];
        }
    };

    public ShopModel getShopModel() {
        return shopModel;
    }

    public void setShopModel(ShopModel shopModel) {
        this.shopModel = shopModel;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return "RatingModel{" +
                "shopModel=" + shopModel +
                ", rating=" + rating +
                ", userCount=" + userCount +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(shopModel, flags);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        if (userCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userCount);
        }
    }
}
