package golhar.cocomo.zinger.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ConfigurationModel implements Parcelable {
    private ShopModel shopModel;
    private Double deliveryPrice;
    private Integer isDeliveryAvailable;
    private Integer isOrderTaken;

    public ConfigurationModel() {
        shopModel = new ShopModel();
    }

    protected ConfigurationModel(Parcel in) {
        shopModel = in.readParcelable(ShopModel.class.getClassLoader());
        if (in.readByte() == 0) {
            deliveryPrice = null;
        } else {
            deliveryPrice = in.readDouble();
        }
        if (in.readByte() == 0) {
            isDeliveryAvailable = null;
        } else {
            isDeliveryAvailable = in.readInt();
        }
        if (in.readByte() == 0) {
            isOrderTaken = null;
        } else {
            isOrderTaken = in.readInt();
        }
    }

    public static final Creator<ConfigurationModel> CREATOR = new Creator<ConfigurationModel>() {
        @Override
        public ConfigurationModel createFromParcel(Parcel in) {
            return new ConfigurationModel(in);
        }

        @Override
        public ConfigurationModel[] newArray(int size) {
            return new ConfigurationModel[size];
        }
    };

    public ShopModel getShopModel() {
        return shopModel;
    }

    public void setShopModel(ShopModel shopModel) {
        this.shopModel = shopModel;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Integer getIsDeliveryAvailable() {
        return isDeliveryAvailable;
    }

    public void setIsDeliveryAvailable(Integer isDeliveryAvailable) {
        this.isDeliveryAvailable = isDeliveryAvailable;
    }

    public Integer getIsOrderTaken() {
        return isOrderTaken;
    }

    public void setIsOrderTaken(Integer isOrderTaken) {
        this.isOrderTaken = isOrderTaken;
    }

    @Override
    public String toString() {
        return "ConfigurationModel{" +
                "shopModel=" + shopModel +
                ", deliveryPrice=" + deliveryPrice +
                ", isDeliveryAvailable=" + isDeliveryAvailable +
                ", isOrderTaken=" + isOrderTaken +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(shopModel, flags);
        if (deliveryPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(deliveryPrice);
        }
        if (isDeliveryAvailable == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isDeliveryAvailable);
        }
        if (isOrderTaken == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isOrderTaken);
        }
    }
}
