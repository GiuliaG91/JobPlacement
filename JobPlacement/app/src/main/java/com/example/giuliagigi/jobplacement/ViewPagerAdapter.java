package com.example.giuliagigi.jobplacement;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by pietro on 25/04/2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    TabHomeStudentFragment parent;
    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb,TabHomeStudentFragment fragment) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        parent=fragment;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Home_tab tab1 = Home_tab.newInstance();
            return tab1;
        }
        else if(position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            //Fav_tab tab2 = Fav_tab.newInstance();
            OfferSearchFragment tab2= OfferSearchFragment.newInstance();
            return tab2;
        }
        else if(position == 2)
        {
           // Fav_tab tab3 = Fav_tab.newInstance();
            StudentCompanySearchFragment tab3=StudentCompanySearchFragment.newInstance();
            return tab3;
        }
        else if(position == 3)
        {
            Applies_Tab tab3=Applies_Tab.newInstance();
            return tab3;
        }

        else return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }




}