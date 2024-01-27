package com.pangdang.pxcheck.util;

public class DecimalFormat {

    public static String decimalFormat (int amount) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("###,###");
        String df_money = df.format(amount);

        return df_money;
    }
}
