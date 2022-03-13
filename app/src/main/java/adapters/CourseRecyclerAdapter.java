package adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
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
        ViewHolder holder = new ViewHolder(view, mOnCourseListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRecyclerAdapter.ViewHolder holder, int position) {
        holder.courseNameList.setText("Text");
        holder.courseIdList.setText("Test");
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
