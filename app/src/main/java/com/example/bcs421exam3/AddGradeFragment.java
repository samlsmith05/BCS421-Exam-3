package com.example.bcs421exam3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddGradeFragment extends Fragment {

    private EditText mNameEditText;
    private EditText mGradeEditText;
    private Button mAddGradeButton;
    private FirebaseFirestore db;
    private String docID;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_add_grade, container, false);

        mNameEditText = v.findViewById(R.id.AGFEditTextName);
        mGradeEditText = v.findViewById(R.id.AGFEditTextGrade);
        mAddGradeButton = v.findViewById(R.id.AGFButtonAddGrade);
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();

        mAddGradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("Hwk4Students").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                String name = mNameEditText.getText().toString();
                                String sGrade = mGradeEditText.getText().toString();
                                int nGrade = Integer.parseInt(sGrade);

                                DocumentSnapshot doc = task.getResult().getDocuments().get(0);
                                docID = doc.getId();

                                Map<String, Object> GradeMap = new HashMap<>();
                                GradeMap.put("name", name);
                                GradeMap.put("grade", nGrade);

                                String path = "Hwk4Students/" + docID + "/grades";
                                db.collection(path).document().set(GradeMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Grade added", Toast.LENGTH_SHORT).show();

                                        }
                                        else {
                                            Toast.makeText(getActivity(), "Add FAILED!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                getActivity().finish();
                            }
                        }
                        else {
                            Log.d("MYDEBUG", "Can't get document");
                        }
                    }
                });

            }
        });

        return v;
    }
}
