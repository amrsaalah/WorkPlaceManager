package com.salah.amr.workplace.BarChart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.salah.amr.workplace.Base.BaseFragment;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 11/29/2017.
 */

public class TimesLateBarChartFragment extends BaseFragment implements IBarChart.view {

    BarChart barChart;

    @Inject
    BarChartPresenter presenter;

    TextView beginningView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_times_late_chart , container  , false);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        barChart = v.findViewById(R.id.times_late_barchart);
        beginningView = v.findViewById(R.id.beginning_view);

        presenter.loadBarChartLateData();
        return v;
    }

    @Override
    public void addBarChartEntries(List<Float> floatList , EmployeeXAxisFormatter formatter) {
        barChart.setDrawGridBackground(false);
        barChart.getDescription().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setValueFormatter(formatter);

        List<BarEntry> entries = new ArrayList<>();
        for(int i =0 ;i<floatList.size() ; i++){
            entries.add(new BarEntry(i, floatList.get(i)));
        }

        BarDataSet set = new BarDataSet(entries, "EmployeeTimesLate");
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh
    }

    @Override
    public void hideBeginningView() {
        beginningView.setVisibility(View.GONE);
    }
}
