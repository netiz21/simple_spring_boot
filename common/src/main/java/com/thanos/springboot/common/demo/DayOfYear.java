package com.thanos.springboot.common.demo;

/**
 * @author solarknight created on 17/3/29 上午10:00
 * @version 1.0
 */
public class DayOfYear {

  public static final int[] MONTH_DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

  public static int dayOfYear(int y, int m, int d) {
    // check year
    if (y < 0) {
      return -1;
    }
    // check month
    if (m < 1 || m > 12) {
      return -1;
    }
    // check day
    int maxMonthDays = getMonthDays(y, m);
    if (d < 1 || d > maxMonthDays) {
      return -1;
    }

    return getAllPreMonthDays(y, m) + d;
  }

  private static int getMonthDays(int y, int m) {
    return MONTH_DAYS[m] + (m == 2 && isLeapYear(y) ? 1 : 0);
  }

  private static boolean isLeapYear(int y) {
    // optimized version for return (y % 4 == 0) && (y % 100 != 0 || y % 400 == 0);
    return (y & 3) == 0 && ((y % 25) != 0 || (y & 15) == 0);
  }

  private static int getAllPreMonthDays(int y, int m) {
    int sum = 0;
    for (int i = 1; i < m; i++) {
      sum += getMonthDays(y, i);
    }
    return sum;
  }

  public static void main(String[] args) {
    System.out.println(dayOfYear(2017, 1, 1));
    System.out.println(dayOfYear(2017, 3, 2));
    System.out.println(dayOfYear(2017, 2, 28));
    System.out.println(dayOfYear(2017, 6, 30));
  }
}
