package com.salah.amr.workplace.Utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by user on 12/1/2017.
 */

public  class Converters {
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}
