package golhar.cocomo.zinger.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import golhar.cocomo.zinger.enums.OrderStatus;

public class OrderModel implements Parcelable {
    private String id;
    private UserModel userModel;
    private TransactionModel transactionModel;
    private ShopModel shopModel;
    private Date date;
    private OrderStatus orderStatus;
    private Date lastStatusUpdatedTime;
    private Double price;
    private Double deliveryPrice;
    private String deliveryLocation;
    private String cookingInfo;
    private Double rating;
    private String secretKey;

    public OrderModel() {
        userModel = new UserModel();
        transactionModel = new TransactionModel();
        shopModel = new ShopModel();
    }

    protected OrderModel(Parcel in) {
        id = in.readString();
        userModel = in.readParcelable(UserModel.class.getClassLoader());
        transactionModel = in.readParcelable(TransactionModel.class.getClassLoader());
        shopModel = in.readParcelable(ShopModel.class.getClassLoader());
        date = (java.util.Date) in.readSerializable();
        orderStatus=orderStatus.valueOf(in.readString());
        lastStatusUpdatedTime=(java.util.Date) in.readSerializable();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        if (in.readByte() == 0) {
            deliveryPrice = null;
        } else {
            deliveryPrice = in.readDouble();
        }
        deliveryLocation = in.readString();
        cookingInfo = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        secretKey = in.readString();
    }

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public TransactionModel getTransactionModel() {
        return transactionModel;
    }

    public void setTransactionModel(TransactionModel transactionModel) {
        this.transactionModel = transactionModel;
    }

    public ShopModel getShopModel() {
        return shopModel;
    }

    public void setShopModel(ShopModel shopModel) {
        this.shopModel = shopModel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getLastStatusUpdatedTime() {
        return lastStatusUpdatedTime;
    }

    public void setLastStatusUpdatedTime(Date lastStatusUpdatedTime) {
        this.lastStatusUpdatedTime = lastStatusUpdatedTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getCookingInfo() {
        return cookingInfo;
    }

    public void setCookingInfo(String cookingInfo) {
        this.cookingInfo = cookingInfo;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", userModel=" + userModel +
                ", transactionModel=" + transactionModel +
                ", shopModel=" + shopModel +
                ", date=" + date +
                ", orderStatus=" + orderStatus +
                ", lastStatusUpdatedTime=" + lastStatusUpdatedTime +
                ", price=" + price +
                ", deliveryPrice=" + deliveryPrice +
                ", deliveryLocation='" + deliveryLocation + '\'' +
                ", cookingInfo='" + cookingInfo + '\'' +
                ", rating=" + rating +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(userModel, flags);
        dest.writeParcelable(transactionModel, flags);
        dest.writeParcelable(shopModel, flags);
        dest.writeSerializable(date);
        dest.writeString(orderStatus.name());
        dest.writeSerializable(lastStatusUpdatedTime);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        if (deliveryPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(deliveryPrice);
        }
        dest.writeString(deliveryLocation);
        dest.writeString(cookingInfo);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        dest.writeString(secretKey);
    }
}
