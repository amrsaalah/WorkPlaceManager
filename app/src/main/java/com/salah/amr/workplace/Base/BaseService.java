package com.salah.amr.workplace.Base;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by user on 1/28/2018.
 */

public abstract class BaseService extends IntentService {

    public BaseService(String name) {
        super(name);
    }
}
