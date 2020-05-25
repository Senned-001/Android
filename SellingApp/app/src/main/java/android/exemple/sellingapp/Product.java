package android.exemple.sellingapp;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String info;
    private String[] params;
    private double[] paramPrices;
    private double bonus1Price;
    private double bonus2Price;
    private String bonus1Name;
    private String bonus2Name;
    private int imageID;

    public Product(String resources, int imageID) {
        //parsing string resources
        String[] data = resources.split(":");
        this.name = data[0];
        this.info = data[1];
        this.params = new String[Integer.parseInt(data[2])];
        this.paramPrices = new double[Integer.parseInt(data[2])];
        for (int i = 0; i < params.length; i++) {
            this.params[i] = data[3 + i];
            this.paramPrices[i] = Double.parseDouble(data[3 + params.length + i]);
        }
        this.bonus1Name = data[data.length - 4];
        this.bonus1Price = Double.parseDouble(data[data.length - 3]);
        this.bonus2Name = data[data.length - 2];
        this.bonus2Price =  Double.parseDouble(data[data.length - 1]);
        this.imageID = imageID;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String[] getParams() {
        return params;
    }

    public double[] getParamPrices() {
        return paramPrices;
    }

    public double getBonus1Price() {
        return bonus1Price;
    }

    public double getBonus2Price() {
        return bonus2Price;
    }

    public String getBonus1Name() {
        return bonus1Name;
    }

    public String getBonus2Name() {
        return bonus2Name;
    }

    public int getImageID() {
        return imageID;
    }
}
