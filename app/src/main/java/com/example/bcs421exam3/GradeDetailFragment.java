package com.example.bcs421exam3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class GradeDetailFragment extends Fragment {

    private EditText mNameEditText;
    private EditText mGradeEditText;

    void ShowGrade(Assignment assignment){
        String name = assignment.getName();
        int nGrade = assignment.getGrade();
        String sGrade = Integer.toString(nGrade);
        mNameEditText.setText(name);
        mGradeEditText.setText(sGrade);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_grade_detail, container, false);

        mNameEditText = v.findViewById(R.id.GDEditTextName);
        mGradeEditText = v.findViewById(R.id.GDEditTextGrade);

        return v;
    }
}
