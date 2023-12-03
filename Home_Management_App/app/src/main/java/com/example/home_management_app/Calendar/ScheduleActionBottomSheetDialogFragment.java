package com.example.home_management_app.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.home_management_app.R;
import com.example.home_management_app.model.OnEventChangeListener;
import com.example.home_management_app.Calendar.role.AddScheduleDialogFragment;
import com.example.home_management_app.Calendar.role.DeleteScheduleFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ScheduleActionBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private OnEventChangeListener eventChangeListener;

    public void setEventChangeListener(OnEventChangeListener eventChangeListener) {
        this.eventChangeListener = eventChangeListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_role_management_btm_floating, container, false);

        Button addScheduleButton = view.findViewById(R.id.addScheduleButton);
        Button deleteScheduleButton = view.findViewById(R.id.deleteScheduleButton);

        addScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddScheduleDialogFragment addScheduleDialog = new AddScheduleDialogFragment();
                addScheduleDialog.setEventChangeListener(eventChangeListener);
                addScheduleDialog.show(getParentFragmentManager(), "addSchedule");
            }
        });

        deleteScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteScheduleFragment deleteScheduleDialog = new DeleteScheduleFragment();
                deleteScheduleDialog.setEventChangeListener(eventChangeListener);
                deleteScheduleDialog.show(getParentFragmentManager(), "delSchedule");
            }
        });

        return view;
    }
}



