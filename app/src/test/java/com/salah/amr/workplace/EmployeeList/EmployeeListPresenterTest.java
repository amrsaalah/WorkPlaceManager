package com.salah.amr.workplace.EmployeeList;

import com.salah.amr.workplace.Base.BaseFragment;
import com.salah.amr.workplace.Base.BaseView;
import com.salah.amr.workplace.Employee.IEmployee;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.Model.EmployeeDatabase;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by user on 1/30/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeListPresenterTest {

    @Mock
    IEmployeeList.view view;

    @Mock
    EmployeeListAdapter adapter;

    @Mock
    EmployeeSharedPreferences preferences;

    @Mock
    EmployeeDatabase employeeDatabase;

    @Inject
    EmployeeListPresenter presenter;

    @Before
    public void setUp(){
        presenter = new EmployeeListPresenter(view, adapter , preferences , employeeDatabase);
        Mockito.when(employeeDatabase.getEmployees()).thenReturn(Arrays.asList(new Employee("amr salah" , "email" , "phone" , 50.5),
                new Employee("mohamed salah" , "email" , "phone" , 50.5)));
        //Mockito.when(employeeDatabase.getEmployees()).thenReturn(Collections.emptyList());
    }

  @Test
    public void verifyWhenAddEmployeeBtnIsClicked(){
        presenter.onFabClicked();
        verify(view).startEmployeeActivity();
  }

  @Test
    public void verifyViewSetup(){
      presenter.loadEmployees();
      verify(view).showEmployeeList(adapter);
  }

    @Test
    public void verifyViewSetup2(){
        presenter.loadEmployees();
        verify(view).hideBeginningView();
    }
}