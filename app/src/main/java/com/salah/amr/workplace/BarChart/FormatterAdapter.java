package com.salah.amr.workplace.BarChart;

import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.Utils.IAdapter;

/**
 * Created by user on 11/29/2017.
 */

public interface FormatterAdapter extends IAdapter{
    void addEmployees(Employee employee);
}
