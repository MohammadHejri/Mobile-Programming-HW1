package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.courseware.R;
import model.Course;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {

    private ArrayList<Course> mCourses = new ArrayList<>();
    private OnCourseListener mOnCourseListener;
    private Context mContext;

    public CourseRecyclerAdapter(Context context,ArrayList<Course> Courses, OnCourseListener onCourseListener) {
        this.mCourses = Courses;
        this.mOnCourseListener = onCourseListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CourseRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_course_list,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView courseNameList;
        TextView courseIdList;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameList = itemView.findViewById(R.id.courseNameList);
            courseIdList = itemView.findViewById(R.id.courseIdList);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

    public interface OnCourseListener{
        void onCourseClick(int position);
    }
}
