package com.salah.amr.workplace.Utils;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by user on 11/28/2017.
 */

public class LocaleCurrencyHelper {
    public static Currency getLocalCurrency() {
        try {
            return Currency.getInstance(Locale.getDefault());
        } catch (NullPointerException | IllegalArgumentException ex) {
            return Currency.getInstance(new Locale("ar", "EG"));
        }
    }
}
