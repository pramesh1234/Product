package com.codestrela.product.util;

/**
 * Created by Gourav on 30-04-2017.
 */

public class ValidationUtil {

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
