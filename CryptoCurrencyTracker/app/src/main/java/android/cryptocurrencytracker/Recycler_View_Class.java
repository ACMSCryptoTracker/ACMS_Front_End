package android.cryptocurrencytracker;

public class Recycler_View_Class {

    private int image;
    private int id;
    private String cryto_name;
    private String crypto_symbol;
    private double price;
    private double change;

    public Recycler_View_Class() {
    }

    public Recycler_View_Class(int id,int image, String cryto_name, String crypto_symbol, double price, double change) {
        this.id=id;
        this.image = image;
        this.cryto_name = cryto_name;
        this.crypto_symbol = crypto_symbol;
        this.price = price;
        this.change = change;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getCryto_name() {
        return cryto_name;
    }

    public String getCrypto_symbol() {
        return crypto_symbol;
    }

    public double getPrice() {
        return price;
    }

    public double getChange() {
        return change;
    }
}
