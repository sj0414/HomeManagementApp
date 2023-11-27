package com.example.home_management_app.utilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.home_management_app.R;
import com.example.home_management_app.utilities.role.AddScheduleDialogFragment;
import com.example.home_management_app.utilities.role.DeleteScheduleDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ScheduleActionBottomSheetDialogFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_role_management_btm_floating, container, false);

        Button addScheduleButton = view.findViewById(R.id.addScheduleButton);
        addScheduleButton.setOnClickListener(v -> {
            // 여기에서 AddScheduleDialogFragment를 표시합니다.
            AddScheduleDialogFragment addScheduleDialog = new AddScheduleDialogFragment();
            addScheduleDialog.show(getParentFragmentManager(), "addSchedule");
        });

        Button deleteScheduleButton = view.findViewById(R.id.deleteScheduleButton);
        deleteScheduleButton.setOnClickListener(v -> {
            //String scheduleId = ; // 삭제할 일정의 ID를 얻는 코드
            // DeleteScheduleDialogFragment의 인스턴스를 생성하고 인자를 전달합니다.
            //DeleteScheduleDialogFragment deleteDialogFragment = DeleteScheduleDialogFragment.newInstance(scheduleId);
            // 프래그먼트 매니저를 사용하여 대화상자를 표시합니다.
            //deleteDialogFragment.show(getParentFragmentManager(), "deleteSchedule");
        });

        return view;
    }
}

