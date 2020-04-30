package com.example.medicinereminder.RemindersDate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medicinereminder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateFragment extends Fragment {

    public DateFragment() {
        // Required empty public constructor
    }

    public static DateFragment create(String dateNumber) {

        DateFragment fragment = new DateFragment();
        Bundle args = new Bundle();
        args.putString("date", dateNumber);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_date, container, false);

        String date = getArguments().getString("date");
        ((TextView) rootView.findViewById(R.id.txt_display)).setText(date);

        return rootView;

    }
}
