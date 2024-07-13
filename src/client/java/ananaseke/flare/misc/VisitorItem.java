package ananaseke.flare.misc;

public class VisitorItem {
    String itemName;
    int itemAmount;
    String visitorName;

    public VisitorItem(String itemName, int itemAmount, String visitorName) {
        this.itemName = itemName;
        this.itemAmount = itemAmount;
        this.visitorName = visitorName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public String getVisitorName() {
        return visitorName;
    }
}
