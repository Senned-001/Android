package android.exemple.sellingapp;

public class Order {
    private int imageID;
    private String info;
    private double totalCost;

    public Order(int imageID, String info, double totalCost) {
        this.imageID = imageID;

        this.info = info;
        this.totalCost = totalCost;
    }

    public int getImageID() {
        return imageID;
    }


    public String getInfo() {
        return info;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return info;
    }
}
