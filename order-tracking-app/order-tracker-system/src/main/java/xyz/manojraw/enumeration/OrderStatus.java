package xyz.manojraw.enumeration;

public enum OrderStatus {
    RECEIVED("Received"),
    PACKED("Packed"),
    SHIPPED("Shipped"),
    OUT_FOR_DELIVERY("Out For Delivery"),
    DELIVERED("Delivered");


    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
