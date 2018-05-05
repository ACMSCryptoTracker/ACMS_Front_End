package android.cryptocurrencytracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Pager_Adapter extends FragmentStatePagerAdapter{

    int noOfTabs;
    public Pager_Adapter(FragmentManager fm , int NUM) {
        super(fm);
        this.noOfTabs = NUM;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Coin_Alert coin_alert=new Coin_Alert();
                return  coin_alert;
            case 1:
                High_Low_Alert high_low_alert = new High_Low_Alert();
                return  high_low_alert;
            case 2:
                Percentage_Alert percentage_alert= new Percentage_Alert();
                return percentage_alert;
            case 3:
                Volume_Alert volume_alert=new Volume_Alert();
                return volume_alert;
            case 4:
                Market_Cap_Alert market_cap_alert=new Market_Cap_Alert();
                return market_cap_alert;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
