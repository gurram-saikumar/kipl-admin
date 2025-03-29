package com.kipl.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class KiplUtils
{
    private KiplUtils()
    {

    }

   // private static final Log LOG = LogFactory.getLog(KiplUtils.class);

    public static String roundToNearestInteger(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        // Set scale to 0 decimal places using HALF_UP rounding
        return bd.setScale(0, RoundingMode.HALF_UP).toPlainString();
    }
    public static String roundAndFormat(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        // Round to the nearest integer
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        // Format with two decimal places
        return bd.setScale(2, RoundingMode.UNNECESSARY).toPlainString();
    }
    public static double getDoubleRoundToNearestInteger(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        // Set scale to 0 decimal places using HALF_UP rounding
        bd = bd.setScale(0, RoundingMode.HALF_UP); // Round to the nearest integer
        return bd.doubleValue(); // Return as double
    }
    public static String roundToOneDecimalPlace(double value) {
        // Convert the value to a BigDecimal to avoid floating-point inaccuracies
        BigDecimal bd = new BigDecimal(Double.toString(value));
        // Set scale to 1 decimal place using HALF_UP rounding
        return bd.setScale(1, RoundingMode.HALF_UP).toPlainString();
    } 
    public static String roundCustom(double value) {
    	BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP); // Keep two decimal places for checking

        BigDecimal floorValue = bd.setScale(1, RoundingMode.FLOOR); // Get value rounded to 1 decimal place
        bd.subtract(floorValue);

        // Extract first and second decimal digits
        int firstDecimal = bd.movePointRight(1).remainder(BigDecimal.TEN).intValue();
        int secondDecimal = bd.movePointRight(2).remainder(BigDecimal.TEN).intValue();

        if (firstDecimal == 9) {
            // If first decimal is 9, round up to next whole number
            return bd.setScale(0, RoundingMode.UP).toPlainString();
        } else if (firstDecimal > 5) {
            // If first decimal is more than 5, round up to next whole number
            return bd.setScale(0, RoundingMode.UP).toPlainString();
        } else {
            // If first decimal is ≤ 5, check second decimal
            if (secondDecimal > 5) {
                return floorValue.add(new BigDecimal("0.1")).toPlainString(); // Increase first decimal by 1
            } else {
                return floorValue.toPlainString(); // Keep first decimal as is
            }
        }
    }
    
}
