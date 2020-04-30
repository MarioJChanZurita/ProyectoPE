package com.example.medicinereminder.RemindersDate;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFragmentCollectionAdapter extends FragmentStatePagerAdapter {
    public DateFragmentCollectionAdapter(Resources resources, @NonNull FragmentManager fm) {
        super(fm,FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DAY_OF_YEAR,position-5000);
        String date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        return DateFragment.create(date);
    }

    @Override
    public int getCount() {
        return 10000;
    }
}
