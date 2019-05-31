package com.salah.amr.workplace.Employee;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.salah.amr.workplace.AddImage.AddImageActivity;
import com.salah.amr.workplace.Base.BaseFragment;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.EmployeeList.EmployeeListActivity;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by user on 11/23/2017.
 */

public class EmployeeFragment extends BaseFragment implements IEmployee.view {

    private static final String TAG = "EmployeeFragment";
    private static final String ARG_POSITION = "arg_position";

    CircleImageView profileImage;
    TextView changePhotoBtn, textTimesLate, textTimesAbsent, textCurrentSalary, textLastSalary, textActualLastSalary, textActualCurrentSalary , textActualTimesLate , textActualTimesAbsent;
    EditText editEmployeeName, editEmployeeEmail, editEmployeePhone, editEmployeeSalary;
    Button phoneButton, emailButton;
    ImageView deleteButton, saveButton, backButton;

    String employeeName, employeeEmail, employeePhone, employeeSalary;
    int position;

    @Inject
    EmployeePresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        Log.d(TAG, "onCreate: " + position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        Log.d(TAG, "onCreateView: fragment starting ");
        v = inflater.inflate(R.layout.fragment_employee, container, false);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        hideViewsWhenKeyboardShows(v);

        initWidgets(v);

        presenter.checkMonthlyOrYearly();
        presenter.loadEmployee(position);
        saveButton.setOnClickListener(view -> {
            employeeName = editEmployeeName.getText().toString();
            employeeEmail = editEmployeeEmail.getText().toString();
            employeePhone = editEmployeePhone.getText().toString();
            employeeSalary = editEmployeeSalary.getText().toString();
            presenter.onSaveChangesClicked(employeeName, employeeEmail, employeePhone, employeeSalary, position);
        });

        backButton.setOnClickListener(view -> {
            presenter.onBackButtonClicked();
        });

        deleteButton.setOnClickListener(view -> {
            presenter.onDeleteButtonClicked(position);
        });

        phoneButton.setOnClickListener(view -> {
            presenter.onPhoneButtonClicked(position);
        });

        emailButton.setOnClickListener(view -> {
            presenter.onEmailButtonClicked(position);
        });

        changePhotoBtn.setOnClickListener(view -> {
            presenter.onChangePhotoClicked(position);
        });

        return v;
    }

    private void hideViewsWhenKeyboardShows(View v) {
        final View rootView = v.findViewById(R.id.root_view);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
            if (heightDiff > dpToPx(getActivity(), 200)) { // if more than 200 dp, it's probably a keyboard...
                // ... do something here
                textTimesAbsent.setVisibility(View.GONE);
                textActualCurrentSalary.setVisibility(View.GONE);
                textActualLastSalary.setVisibility(View.GONE);
                textCurrentSalary.setVisibility(View.GONE);
                textTimesLate.setVisibility(View.GONE);
                textLastSalary.setVisibility(View.GONE);
                textActualTimesAbsent.setVisibility(View.GONE);
                textActualTimesLate.setVisibility(View.GONE);

            }
            else{
                textTimesAbsent.setVisibility(View.VISIBLE);
                textActualCurrentSalary.setVisibility(View.VISIBLE);
                textActualLastSalary.setVisibility(View.VISIBLE);
                textCurrentSalary.setVisibility(View.VISIBLE);
                textTimesLate.setVisibility(View.VISIBLE);
                textLastSalary.setVisibility(View.VISIBLE);
                textActualTimesAbsent.setVisibility(View.VISIBLE);
                textActualTimesLate.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initWidgets(View v) {
        Log.d(TAG, "initWidgets: ");
        profileImage = v.findViewById(R.id.image_employee);
        changePhotoBtn = v.findViewById(R.id.text_change_photo);
        textTimesLate = v.findViewById(R.id.number_times_late);
        textTimesAbsent = v.findViewById(R.id.number_times_absent);
        textCurrentSalary = v.findViewById(R.id.number_current_salary);
        textLastSalary = v.findViewById(R.id.number_last_salary);
        editEmployeeName = v.findViewById(R.id.edit_employee_name);
        editEmployeeEmail = v.findViewById(R.id.edit_employee_email);
        editEmployeePhone = v.findViewById(R.id.edit_employee_phone);
        editEmployeeSalary = v.findViewById(R.id.edit_employee_salary);
        phoneButton = v.findViewById(R.id.btn_phone);
        emailButton = v.findViewById(R.id.btn_email);
        deleteButton = v.findViewById(R.id.btn_employee_delete);
        saveButton = v.findViewById(R.id.btn_save_changes);
        backButton = v.findViewById(R.id.back_navigation);
        textActualLastSalary = v.findViewById(R.id.text_last_salary);
        textActualCurrentSalary = v.findViewById(R.id.text_current_salary);
        textActualTimesAbsent  = v.findViewById(R.id.text_times_late);
        textActualTimesLate = v.findViewById(R.id.text_times_absent);
    }

    @Override
    public void showMissingDataError() {
        Log.d(TAG, "showMissingDataError: ");
        Toast.makeText(getActivity(), "You have to enter name and salary", Toast.LENGTH_LONG).show();
    }

    @Override
    public void returnToParentActivity() {
        getActivity().onBackPressed();
    }

    @Override
    public void fillEmployeeData(String name, String email, String phone, String imageURL, String salary, String timesLate, String timesAbsent, String currentSalary, String lastSalary) {
        Log.d(TAG, "fillEmployeeData: ");
        editEmployeeName.setText(name);
        editEmployeeEmail.setText(email);
        editEmployeePhone.setText(phone);

        Glide.with(getActivity()).load(imageURL).asBitmap().fitCenter().placeholder(R.drawable.avatar).into(profileImage);

        editEmployeeSalary.setText(salary);
        textTimesAbsent.setText(timesAbsent);
        textTimesLate.setText(timesLate);
        textCurrentSalary.setText(currentSalary);
        textLastSalary.setText(lastSalary);
    }


    @Override
    public void navigateToEmployeeListActivity() {
        Log.d(TAG, "navigateToEmployeeListActivity: ");
        Intent intent = new Intent(getActivity(), EmployeeListActivity.class);
        startActivity(intent);
    }

    @Override
    public void startPhoneIntent(String phone) {
        Log.d(TAG, "startPhoneIntent: ");
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    @Override
    public void startEmailIntent(String email) {
        Log.d(TAG, "startEmailIntent: ");
        String[] addresses = new String[1];
        addresses[0] = email;
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses); // String[] addresses
        startActivity(Intent.createChooser(emailIntent, "Send email..."));

    }

    @Override
    public void navigateToAddImageActivity(int id) {
        Intent intent = AddImageActivity.newIntent(getActivity(), id);
        startActivity(intent);
    }


    @Override
    public void setMonthlyOrYearly(int flag) {
        if (flag == 0) {
            textActualCurrentSalary.setText("Salary (This Month)");
            textActualLastSalary.setText("Salary (Last Month)");
        } else {
            textActualCurrentSalary.setText("Salary (This Year)");
            textActualLastSalary.setText("Salary (Last Year)");
        }
    }

    @Override
    public void showChangePhotoError() {
        Toast.makeText(getActivity() , "You have to save the employee before you can change the photo" , Toast.LENGTH_LONG).show();
    }

    public static EmployeeFragment newInstance(int position) {
        Log.d(TAG, "newInstance: ");
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        EmployeeFragment employeeFragment = new EmployeeFragment();
        employeeFragment.setArguments(args);
        return employeeFragment;
    }


    public static float dpToPx(Context context, float valueInDp) {
        if(context != null){
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
        }
       return 0;
    }
}
