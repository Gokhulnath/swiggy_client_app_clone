package golhar.cocomo.zinger.enums;

public enum OrderStatus {
    PENDING, TXN_FAILURE, PLACED, CANCELLED_BY_USER, ACCEPTED, CANCELLED_BY_SELLER, READY, OUT_FOR_DELIVERY, COMPLETED, DELIVERED
}
// starting states -> failure,pending,placed
// terminal states -> cancelled by seller or user, delivered, completed

// pending -> failure ,placed
// placed  -> cancelled by user or seller , accepted
// cancelled by user or seller -> refund table entry must be added
// accepted -> ready, out_for_delivery , cancelled by seller -> refund table entry must be added
// ready -> secret key must be updated in table, completed
// out_for_delivery -> secret key must be updated in table, delivered