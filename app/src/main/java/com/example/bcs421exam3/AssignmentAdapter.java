package com.example.bcs421exam3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.MyViewHolder> {
    interface AssignmentAdapterListener {
        void onItemClick(int position);
    }

    private List<Assignment> assignmentList;
    private AssignmentAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;

        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.ARVITextViewName);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                } });

        }
    }

    public AssignmentAdapter(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_recyclerview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Assignment a = assignmentList.get(position);
        String name = a.getName();
        holder.textViewName.setText(name);
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public void setData(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(AssignmentAdapterListener listener) {
        this.listener = listener;
    }

}
