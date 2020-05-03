package com.example.bcs421exam3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GradeActivity extends AppCompatActivity implements GradeListFragment.AssignmentSelectionCallback{

    private GradeDetailFragment detailFragment;
    private GradeListFragment listFragment;
    public void showAssignment(Assignment a){
            detailFragment.ShowGrade(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        detailFragment = new GradeDetailFragment();
        listFragment = new GradeListFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.fragment_grade_detail_container, detailFragment);
        ft.add(R.id.fragment_grade_list_container, listFragment);

        ft.commit();

    }
}
