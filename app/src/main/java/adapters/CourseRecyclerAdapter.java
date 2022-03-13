package adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import model.Course;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {

    private ArrayList<Course> mCourses = new ArrayList<>();
    private OnCourseListener mOnCourseListener;

    public CourseRecyclerAdapter(ArrayList<Course> mCourses, OnCourseListener onCourseListener) {
        this.mCourses = mCourses;
        this.mOnCourseListener = onCourseListener;
    }

    @NonNull
    @Override
    public CourseRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnCourseListener{
        void onCourseClick(int position);
    }
}
