package com.salah.amr.workplace.EmployeeList;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.salah.amr.workplace.Base.BaseListener;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.R;
import com.salah.amr.workplace.Utils.IAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 11/24/2017.
 */

public class EmployeeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IAdapter {

    private static final String TAG = "EmployeeListAdapter";
    Context context;

    public interface OnItemClickListener extends BaseListener {
        void onItemClick(int position);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;


    private List<Employee> employeeList;
    private OnItemClickListener onItemClickListener;
    private OnLoadMoreListener onLoadMoreListener;

    @Inject
    public EmployeeListAdapter(BaseListener onItemClickListener) {
        this.employeeList = new ArrayList<>();
        this.onItemClickListener = (OnItemClickListener) onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_ITEM;

        } else if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position > employeeList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.item_employee_list, parent, false);
            return new EmployeeHolder(v);

        } else if (viewType == TYPE_FOOTER) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.item_footer, parent, false);
            return new FooterHolder(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmployeeHolder) {
             Employee employee = employeeList.get(position);
            ((EmployeeHolder) holder).bindEmployee(employee);
            ((EmployeeHolder) holder).bind(onItemClickListener);
        } else {
            Log.d(TAG, "onBindViewHolder: footer");
        }

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    class EmployeeHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;

        EmployeeHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_employee_name);
            image = itemView.findViewById(R.id.item_image);
        }

        void bind(OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(getLayoutPosition()));
        }

        void bindEmployee(Employee employee) {
            name.setText(employee.getName());

            Glide.with(context).load(employee.getImageURL()).asBitmap().placeholder(R.drawable.avatar).into(image);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    static class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
        private int offset;

        public ItemOffsetDecoration(int offset) {
            this.offset = offset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {

            // Add padding only to the zeroth item
            if (parent.getChildAdapterPosition(view) == 0) {

                outRect.right = offset;
                outRect.left = offset;
                outRect.top = offset;
                outRect.bottom = offset;
            }
        }
    }


    @Override
    public void setEmployees(List<Employee> employeeList) {
        Log.d(TAG, "setEmployees: ");
        this.employeeList = employeeList;
    }

}
