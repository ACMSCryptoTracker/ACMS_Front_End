package android.cryptocurrencytracker;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Alerts extends AppCompatActivity{

    private Toolbar myToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        //set toolbar as the appbar of the activity
        myToolbar = (Toolbar) findViewById(R.id.Alerts_Toolbar);
        setSupportActionBar(myToolbar);

        // Setting ViewPager
        viewPager = (ViewPager) findViewById(R.id.Alerts_Viewpager);
        setupViewPager(viewPager);

        // Set Tabs using view pager
        tabLayout = (TabLayout) findViewById(R.id.Alerts_Tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(new Coin_Alert(), "COIN ALERT");
        adapter.addFragment(new High_Low_Alert(), "HIGH/LOW ALERT");
        adapter.addFragment(new Percentage_Alert(),"PERCENTAGE ALERT");
        adapter.addFragment(new Volume_Alert(),"VOLUME ALERT");
        adapter.addFragment(new Market_Cap_Alert(),"MARKET CAP ALERT");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter  {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
