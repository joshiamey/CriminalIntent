package com.example.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date inDate){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE,inDate);

        DatePickerFragment df = new DatePickerFragment();
        df.setArguments(args);
        return df;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date,null);
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date,null);

        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);

        mDatePicker.init(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,null)
                .create();


    }
}
