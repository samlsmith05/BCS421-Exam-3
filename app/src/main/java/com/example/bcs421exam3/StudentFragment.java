package com.example.bcs421exam3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentFragment extends Fragment {

    private EditText mEmailEditText;
    private EditText mFirstEditText;
    private EditText mLastEditText;
    private FirebaseFirestore db;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_student, container, false);

        mEmailEditText = v.findViewById(R.id.SFEditTextEmail);
        mFirstEditText = v.findViewById(R.id.SFEditTextFirst);
        mLastEditText = v.findViewById(R.id.SFEditTextLast);
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        db.collection("Hwk4Students").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        String email = document.getString("email");
                        mEmailEditText.setText(email);

                        String first = document.getString("first");
                        mFirstEditText.setText(first);

                        String last = document.getString("last");
                        mLastEditText.setText(last);
                    }
                }
                else {
                    Log.d("MYDEBUG", "Can't get document");
                }
            }
        });

        return v;
    }

}
