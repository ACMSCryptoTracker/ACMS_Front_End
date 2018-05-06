package android.cryptocurrencytracker;

public class Recycler_View_Class {

    private int image;
    private int id;
    private String cryto_name;
    private String crypto_symbol;
    private double price;
    private double change1d;

    public Recycler_View_Class() {
    }

    public Recycler_View_Class(int id,int image, String cryto_name, String crypto_symbol, double price, double change1d) {
        this.id=id;
        this.image = image;
        this.cryto_name = cryto_name;
        this.crypto_symbol = crypto_symbol;
        this.price = price;
        this.change1d = change1d;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id;
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

    public double getChange1d() {
        return change1d;
    }

}
