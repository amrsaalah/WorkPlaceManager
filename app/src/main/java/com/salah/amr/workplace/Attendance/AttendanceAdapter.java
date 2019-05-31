package com.salah.amr.workplace.Attendance;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.salah.amr.workplace.Base.BaseListener;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.R;
import com.salah.amr.workplace.Utils.IAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 11/28/2017.
 */

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceHolder> implements IAdapter {

    private static final String TAG = "AttendanceAdapter";

    public interface OnCheckBoxListener extends BaseListener{
        void onCheckBoxClick(int position, int id);
    }

    private OnCheckBoxListener listener;
    private List<Employee> employeeList;

    @Inject
    public AttendanceAdapter(BaseListener listener) {
        this.employeeList = new ArrayList<>();
        this.listener = (OnCheckBoxListener) listener;

    }

    @Override
    public AttendanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_attendance_list, parent, false);
        return new AttendanceHolder(v);
    }

    @Override
    public void onBindViewHolder(AttendanceHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.bindAttendance(employee);
        holder.bind(listener);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    public class AttendanceHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private CheckBox onTime, late, absent;

        public AttendanceHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_employee_name);
            onTime = itemView.findViewById(R.id.item_checkbox_ontime);
            late = itemView.findViewById(R.id.item_checkbox_late);
            absent = itemView.findViewById(R.id.item_checkbox_absent);

        }

        public void bindAttendance(Employee employee) {
            name.setText(employee.getName());
            onTime.setChecked(employee.isOnTimeCheck());
            late.setChecked(employee.isLateCheck());
            absent.setChecked(employee.isAbsentCheck());
        }

        public void bind(OnCheckBoxListener listener) {
            onTime.setOnClickListener(view -> {
                if (!onTime.isChecked() && !late.isChecked() && !absent.isChecked()) {
                        onTime.setChecked(true);
                }else{
                    late.setChecked(false);
                    absent.setChecked(false);
                    listener.onCheckBoxClick(getLayoutPosition(), 0);
                }
            });

            late.setOnClickListener(view -> {
                if (!onTime.isChecked() && !late.isChecked() && !absent.isChecked()) {
                    late.setChecked(true);
                }
                else{
                    absent.setChecked(false);
                    onTime.setChecked(false);
                    listener.onCheckBoxClick(getLayoutPosition(), 1);
                }
            });

            absent.setOnClickListener(view -> {
                if (!onTime.isChecked() && !late.isChecked() && !absent.isChecked()) {
                    absent.setChecked(true);
                }else{
                    late.setChecked(false);
                    onTime.setChecked(false);
                    listener.onCheckBoxClick(getLayoutPosition(), 2);
                }
            });

        }
    }

    @Override
    public void setEmployees(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

}
