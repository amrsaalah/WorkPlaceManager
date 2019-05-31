package com.salah.amr.workplace.EmployeeList;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.salah.amr.workplace.Base.BaseFragment;
import com.salah.amr.workplace.ChatRoom.Activity.ChatRoomActivity;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.Login.LoginActivity;
import com.salah.amr.workplace.Model.UserDatabase;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;
import com.salah.amr.workplace.Settings.SettingsActivity;

import javax.inject.Inject;

/**
 * Created by user on 11/23/2017.
 */

public class EmployeeListFragment extends BaseFragment implements IEmployeeList.view, EmployeeListAdapter.OnItemClickListener {


    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: position " + position);
        Log.d(TAG, "onItemClick: presenter" + presenter);
        presenter.onItemClicked(position);
    }


    public interface Callback {
        void navigateToEmployeeActivity(int position);
    }

    private static final String TAG = "EmployeeListFragment";

    private BroadcastReceiver receiver;
    private Callback callback;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    TextView beginningView;


    @Inject
    EmployeeListPresenter presenter;

    @Inject
    UserDatabase userDatabase;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getStringExtra(SyncService.COPA_MESSAGE);
                Log.d(TAG, "onReceive: msg received msg is " + s);
                if (s.equals("finish")) {
                    Intent i = new Intent(getActivity(), EmployeeListActivity.class);
                    startActivity(i);
                }
            }
        };
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_employee_list, container, false);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        Log.d(TAG, "onCreateView: userdatabase " + userDatabase);

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        Log.d(TAG, "onCreateView: fragment starting ");


        initWidgets(v);

        presenter.setupManagerOrEmployeeDialog();
        presenter.setupManagerOrEmployeeView();
        presenter.loadEmployees();


        floatingActionButton.setOnClickListener(view -> {
            presenter.onFabClicked();
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_employee_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        switch (item.getItemId()) {
            case R.id.item_menu_sync:
                presenter.onSyncButtonClicked();
                return true;
            case R.id.item_menu_settings:
                presenter.onSettingsButtonClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initWidgets(View v) {
        Log.d(TAG, "initWidgets: ");
        recyclerView = v.findViewById(R.id.recycler_view);
        floatingActionButton = v.findViewById(R.id.fab);
        beginningView = v.findViewById(R.id.beginning_view);
    }


    @Override
    public void startEmployeeActivity() {
        Log.d(TAG, "startEmployeeActivity: ");
        callback.navigateToEmployeeActivity(-2);
    }

    @Override
    public void showEmployeeList(EmployeeListAdapter adapter) {
        Log.d(TAG, "showEmployeeList: adapter" + adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new EmployeeListAdapter.ItemOffsetDecoration(2));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastvisibleitemposition = linearLayoutManager.findLastVisibleItemPosition();

                if (lastvisibleitemposition == adapter.getItemCount() - 1) {

                   /* if (!loading && !isLastPage) {

                        loading = true;
                        fetchData((++pageCount));
                        // Increment the pagecount everytime we scroll to fetch data from the next page
                        // make loading = false once the data is loaded
                        // call mAdapter.notifyDataSetChanged() to refresh the Adapter and Layout

                    }*/
                }
            }


        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startEmployeeActivityWithExtra(int position) {
        Log.d(TAG, "startEmployeeActivityWithExtra: ");
        callback.navigateToEmployeeActivity(position);
    }

    @Override
    public void navigateToSettingsActivity() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToChatRoomActivity() {
        Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
        startActivity(intent);
    }

    @Override
    public void showManagerOrEmployeeDialog() {
        ManagerOrEmployeeDialog managerOrEmployeeDialog = new ManagerOrEmployeeDialog();
        managerOrEmployeeDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        managerOrEmployeeDialog.show(getFragmentManager(), TAG);
    }

    @Override
    public void beginSyncService() {
        Intent intent = new Intent(getActivity(), SyncService.class);
        getActivity().startService(intent);
    }

    @Override
    public void showSyncErrorMessage() {
        Toast.makeText(getActivity(), "You have to Register Chat Room from the last screen in the app", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSyncAlertDialog() {
        Log.d(TAG, "showSyncAlertDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setMessage("All Employee images , email and phone will be overwritten by the data entered by the employees. Do you want to continue? ")
                .setTitle("Sync Alert")
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    beginSyncService();
                })
                .setNegativeButton("cancel", null);
        builder.create().show();
    }

    @Override
    public void hideBeginningView() {
        beginningView.setVisibility(View.GONE);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((receiver),
                new IntentFilter(SyncService.COPA_RESULT)
        );
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onStop();
    }
}