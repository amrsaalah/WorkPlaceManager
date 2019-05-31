package com.salah.amr.workplace.Tasks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.salah.amr.workplace.Base.BaseListener;
import com.salah.amr.workplace.Model.Task;
import com.salah.amr.workplace.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Created by user on 12/1/2017.
 */


public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksHolder> implements ITaskAdapter {
    private static final String TAG = "TasksAdapter";
    List<Task> tasks;
    OnItemClickListener onItemClickListener;
    Context context;

    interface OnItemClickListener extends BaseListener {
        void onItemClick(int position);

        void onDateClick(int position);

        void onTimeClick(int position);

        void onSelectEmployeeClick(int position);

        void onEditDescriptionClick(int position, String text);
    }

    @Inject
    public TasksAdapter(BaseListener onItemClickListener) {
        this.onItemClickListener = (OnItemClickListener) onItemClickListener;
        this.tasks = new ArrayList<>();
    }

    @Override
    public TasksHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasks_list, parent, false);
        return new TasksHolder(v);
    }

    @Override
    public void onBindViewHolder(TasksHolder holder, int position) {
        holder.bindTask(tasks.get(position), onItemClickListener);
        holder.bind(onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public class TasksHolder extends RecyclerView.ViewHolder {

        Button dateButton, timeButton;
        CheckBox submitted;
        TextView assignedEmployeeButton;
        EditText editTaskDescription;
        ScrollView scrollView;

        public TasksHolder(View itemView) {
            super(itemView);
            dateButton = itemView.findViewById(R.id.task_date);
            timeButton = itemView.findViewById(R.id.task_time);
            submitted = itemView.findViewById(R.id.task_checkbox);
            assignedEmployeeButton = itemView.findViewById(R.id.choose_employee_btn);
            editTaskDescription = itemView.findViewById(R.id.task_description);
            scrollView = itemView.findViewById(R.id.scrollView);

        }

        public void bindTask(Task task, OnItemClickListener listener) {

            dateButton.setOnClickListener(view -> {
                Log.d(TAG, "bindTask: date button clicked");
                listener.onDateClick(getLayoutPosition());
            });

            timeButton.setOnClickListener(view -> {
                Log.d(TAG, "bindTask: time button clicked ");
                listener.onTimeClick(getLayoutPosition());
            });

            assignedEmployeeButton.setOnClickListener(view -> {
                Log.d(TAG, "bindTask: ");
                listener.onSelectEmployeeClick(getLayoutPosition());
            });

            editTaskDescription.setMaxLines(100);
            editTaskDescription.setVerticalScrollBarEnabled(true);
            editTaskDescription.setMovementMethod(new ScrollingMovementMethod());
            View.OnTouchListener touchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    boolean isLarger;

                    isLarger = ((EditText) v).getLineCount()
                            * ((EditText) v).getLineHeight() > v.getHeight();
                    if (event.getAction() == MotionEvent.ACTION_MOVE
                            && isLarger) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        v.getParent().requestDisallowInterceptTouchEvent(false);

                    }
                    return false;
                }
            };
            editTaskDescription.setOnTouchListener(touchListener);

            RxTextView.textChanges(editTaskDescription)
                    .debounce(1, TimeUnit.SECONDS)
                    .subscribe(textChanged -> {
                        Log.d(TAG, "bindTask: detection ended");
                        listener.onEditDescriptionClick(getLayoutPosition(), editTaskDescription.getText().toString());
                    });

            if (task.getAssignedEmployee() != null)
                assignedEmployeeButton.setText("Employee: " + task.getAssignedEmployee());
            if (task.getDescription() != null) editTaskDescription.setText(task.getDescription());

            String formattedDate = null, formattedTime = null;
            SimpleDateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat dfTime = new SimpleDateFormat("HH:mm");

            if (task.getDate() != null) {
                formattedDate = dfDate.format(task.getDate());
                formattedTime = dfTime.format(task.getDate());
            }

            if (formattedDate != null) {
                dateButton.setText(formattedDate);
            } else {
                dateButton.setText("Date");
            }
            if (formattedTime != null) {
                timeButton.setText(formattedTime);
            } else {
                timeButton.setText("Time");
            }


        }

        public void bind(OnItemClickListener listener) {
            itemView.setOnClickListener(view -> {
                Log.d(TAG, "bind: item click ");
                Log.d(TAG, "bind: adapter position" + getLayoutPosition());
                listener.onItemClick(getLayoutPosition());
            });
        }
    }


    @Override
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
