import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 *   Class Stock :
 *
 *   Variables:
 *
 *              String name:
 *
 *              Double price:
 *
 *
 *
 *   Constructors:
 *
 *
 *   Methods:
 *
 *
 */

public class Stock implements Comparable<Stock> {

    private String companyName;
    private int volume;
    private int holding;
    private double price;
    private double avgPrice;

    Stock(String n, int vol, double pri) throws IOException {
        this.companyName = n;
        this.volume = vol;
        this.price = pri;
        this.holding = 0;
        this.avgPrice = 0;
    }

    public void add_holding ( int hol){
        this.holding += hol;
    }

    public void changeAvgPrice( double p ){ avgPrice = p; }

    public double getPrice () {
        return price;
    }

    public void setPrice( double p ) { this.price = p; }

    public int getHolding () {
        return holding;
    }

    public double getAvgPrice () {
        return avgPrice;
    }

    public double getVolume () {
        return volume;
    }

    public String toString () {
        return "Symbol: " + this.companyName + "\nPrice: " + this.price + "\nVolume: " + this.volume + "\nYour Holding: " + this.holding + "\nYour Profit:   " + (double) this.holding*(this.price-this.avgPrice);
    }

    @Override
    public int compareTo (Stock o){
        if (Objects.equals(this.companyName, o.companyName)) {
            return 0;
        }
        return 1;
    }
}
