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
import edu.sharif.courseware.model.Course;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {

    private ArrayList<Course> mCourses;
    private OnCourseListener mOnCourseListener;

    public CourseRecyclerAdapter(ArrayList<Course> Courses, OnCourseListener onCourseListener) {
        this.mCourses = Courses;
        this.mOnCourseListener = onCourseListener;
    }

    public void changeDataSet(ArrayList<Course> mCourses) {
        this.mCourses = mCourses;
    }

    @NonNull
    @Override
    public CourseRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_course_list,parent,false);
        ViewHolder holder = new ViewHolder(view, mOnCourseListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecyclerAdapter.ViewHolder holder, int position) {
        holder.courseNameList.setText(mCourses.get(position).getName());
        holder.courseIdList.setText(Integer.toString(mCourses.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView courseNameList;
        TextView courseIdList;
        ConstraintLayout parentLayout;
        OnCourseListener onCourseListener;

        public ViewHolder(@NonNull View itemView, OnCourseListener onCourseListener) {
            super(itemView);
            courseNameList = itemView.findViewById(R.id.courseNameList);
            courseIdList = itemView.findViewById(R.id.courseIdList);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            this.onCourseListener = onCourseListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCourseListener.onCourseClick(getAdapterPosition());
        }
    }

    public interface OnCourseListener{
        void onCourseClick(int position);
    }
}
