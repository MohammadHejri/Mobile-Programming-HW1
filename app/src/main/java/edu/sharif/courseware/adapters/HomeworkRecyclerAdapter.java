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
import edu.sharif.courseware.model.Homework;

public class HomeworkRecyclerAdapter extends RecyclerView.Adapter<HomeworkRecyclerAdapter.ViewHolder> {

    private ArrayList<Homework> mHomeworks;
    private OnHomeworkListener mOnHomeworkListener;

    public HomeworkRecyclerAdapter(ArrayList<Homework> Homeworks, OnHomeworkListener onHomeworkListener) {
        this.mHomeworks = Homeworks;
        this.mOnHomeworkListener = onHomeworkListener;
    }

    public void changeDataSet(ArrayList<Homework> mHomeworks) {
        this.mHomeworks = mHomeworks;
    }

    @NonNull
    @Override
    public HomeworkRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_course_list,parent,false);
        ViewHolder holder = new ViewHolder(view, mOnHomeworkListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkRecyclerAdapter.ViewHolder holder, int position) {
        holder.homeworkNameList.setText(mHomeworks.get(position).getName());
        holder.homeworkIdList.setText("");
    }

    @Override
    public int getItemCount() {
        return mHomeworks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView homeworkNameList;
        TextView homeworkIdList;
        ConstraintLayout parentLayout;
        OnHomeworkListener onHomeworkListener;

        public ViewHolder(@NonNull View itemView, OnHomeworkListener onHomeworkListener) {
            super(itemView);
            homeworkNameList = itemView.findViewById(R.id.courseNameList);
            homeworkIdList = itemView.findViewById(R.id.courseIdList);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            this.onHomeworkListener = onHomeworkListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onHomeworkListener.onHomeworkClick(getAdapterPosition());
        }
    }

    public interface OnHomeworkListener{
        void onHomeworkClick(int position);
    }
}
