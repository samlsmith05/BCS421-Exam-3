package com.example.bcs421exam3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GradeListFragment extends Fragment {

    private AssignmentSelectionCallback callback;
    private List<Assignment> assignmentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AssignmentAdapter mAdapter;
    private FirebaseFirestore db;
    private String docID;

    interface AssignmentSelectionCallback{
        void showAssignment(Assignment a);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_grade_list, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView = (RecyclerView) v.findViewById(R.id.RVRecyclerViewAssignments);
        mAdapter = new AssignmentAdapter(assignmentList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();

        db.collection("Hwk4Students").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final QueryDocumentSnapshot document : task.getResult()){

                        DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                        docID = doc.getId();
                        String path = "Hwk4Students/" + docID + "/grades";

                        db.collection(path).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()) {
                                    for (final QueryDocumentSnapshot document : task.getResult()) {
                                        String name = document.getString("name");
                                        int grade = document.getLong("grade").intValue();
                                        assignmentList.add(new Assignment(name, grade));
                                    }
                                }
                                else{
                                    Log.d("MYDEBUG", "Can't get document");
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                else {
                    Log.d("MYDEBUG", "Can't get document");
                }
            }
        });

        mAdapter.setOnItemClickListener(new AssignmentAdapter.AssignmentAdapterListener() {
            @Override
            public void onItemClick(int position) {
                Assignment selectedAssignment = assignmentList.get(position);

//                String name = selectedAssignment.getName();
//                int grade = selectedAssignment.getGrade();

                callback.showAssignment(selectedAssignment);

            }
        });


        return v;
    }

    public GradeListFragment(){
        //Required empty constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        callback = (AssignmentSelectionCallback) context;
    }


}
