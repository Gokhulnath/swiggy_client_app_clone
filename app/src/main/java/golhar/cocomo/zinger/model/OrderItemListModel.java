package golhar.cocomo.zinger.model;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
public class OrderItemListModel implements Parcelable {

    OrderModel orderModel;
    List<OrderItemModel> orderItemsList;

    public OrderItemListModel() {
        orderModel = new OrderModel();
        orderItemsList = new ArrayList<>();
    }

    protected OrderItemListModel(Parcel in) {
        orderModel = in.readParcelable(OrderModel.class.getClassLoader());
        orderItemsList = in.createTypedArrayList(OrderItemModel.CREATOR);
    }

    public static final Creator<OrderItemListModel> CREATOR = new Creator<OrderItemListModel>() {
        @Override
        public OrderItemListModel createFromParcel(Parcel in) {
            return new OrderItemListModel(in);
        }

        @Override
        public OrderItemListModel[] newArray(int size) {
            return new OrderItemListModel[size];
        }
    };

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public List<OrderItemModel> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItemModel> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    @Override
    public String toString() {
        return "OrderItemListModel{" +
                "orderModel=" + orderModel +
                ", orderItemsList=" + orderItemsList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(orderModel, flags);
        dest.writeTypedList(orderItemsList);
    }
}
