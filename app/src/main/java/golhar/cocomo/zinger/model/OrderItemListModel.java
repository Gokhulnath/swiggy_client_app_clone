package golhar.cocomo.zinger.model;

import java.util.List;

public class OrderItemListModel {

    OrderModel orderModel;
    List<OrderItemModel> orderItemsList;

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
}
