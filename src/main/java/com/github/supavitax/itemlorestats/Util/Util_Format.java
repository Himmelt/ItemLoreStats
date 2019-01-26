package com.github.supavitax.itemlorestats.Util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Util_Format {

   public double format(double value) {
      Locale forceLocale = new Locale("en", "UK");
      String decimalPattern = "0.0";
      DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(forceLocale);
      decimalFormat.applyPattern(decimalPattern);
      String format = decimalFormat.format(value);
      return Double.parseDouble(format);
   }

   public String formatString(double value) {
      Locale forceLocale = new Locale("en", "UK");
      String decimalPattern = "0.0";
      DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(forceLocale);
      decimalFormat.applyPattern(decimalPattern);
      String format = decimalFormat.format(value);
      return format;
   }
}
