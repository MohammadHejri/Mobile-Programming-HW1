package edu.sharif.courseware.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import edu.sharif.courseware.model.Submission;

public class SubmissionRecyclerAdapter extends RecyclerView.Adapter<SubmissionRecyclerAdapter.ViewHolder> {

    private ArrayList<Submission> mSubmissions;
    private OnSubmissionListener mOnSubmissionListener;

    public SubmissionRecyclerAdapter(ArrayList<Submission> Submissions, OnSubmissionListener onSubmissionListener) {
        this.mSubmissions = Submissions;
        this.mOnSubmissionListener = onSubmissionListener;
    }

    public void changeDataSet(ArrayList<Submission> mSubmissions) {
        this.mSubmissions = mSubmissions;
    }

    @NonNull
    @Override
    public SubmissionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_course_list,parent,false);
        ViewHolder holder = new ViewHolder(view, mOnSubmissionListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubmissionRecyclerAdapter.ViewHolder holder, int position) {
        holder.submissionNameList.setText(mSubmissions.get(position).getStudent().getUsername());
        Float mark = mSubmissions.get(position).getMark();
        holder.submissionIdList.setText(mark == -1 ? "Not marked yet" : String.valueOf(mark));
    }

    @Override
    public int getItemCount() {
        return mSubmissions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView submissionNameList;
        TextView submissionIdList;
        ConstraintLayout parentLayout;
        OnSubmissionListener onSubmissionListener;

        public ViewHolder(@NonNull View itemView, OnSubmissionListener onSubmissionListener) {
            super(itemView);
            submissionNameList = itemView.findViewById(R.id.courseNameList);
            submissionIdList = itemView.findViewById(R.id.courseIdList);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            this.onSubmissionListener = onSubmissionListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onSubmissionListener.onSubmissionClick(getAdapterPosition());
        }
    }

    public interface OnSubmissionListener{
        void onSubmissionClick(int position);
    }
}