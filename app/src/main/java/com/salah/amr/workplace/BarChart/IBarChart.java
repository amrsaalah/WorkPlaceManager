package com.salah.amr.workplace.BarChart;

import java.util.List;

/**
 * Created by user on 11/29/2017.
 */

public interface IBarChart {

    interface view{
        void addBarChartEntries(List<Float> floatList , EmployeeXAxisFormatter formatter);
        void hideBeginningView();
    }

    interface presenter {
        void loadBarChartLateData();
        void loadBarChartAbsentData();
    }
}
