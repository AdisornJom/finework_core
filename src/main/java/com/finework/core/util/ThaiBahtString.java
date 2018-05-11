/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finework.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author Lenovo
 */

public final class ThaiBahtString {
    
  private static final String[] SCALE_TH = { "ล้าน", "สิบ", "ร้อย", "พัน", "หมื่น", "แสน", "" };
  private static final String[] DIGIT_TH = { "ศูนย์", "หนึ่ง", "สอง", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด", "เก้า" };
  private static final String[] SYMBOLS_TH = { "ลบ", "บาท", "ถ้วน", "สตางค์", "ยี่", "เอ็ด", ",", " ", "฿" };
  private String valueText;

  public String getText(double amount)
  {
    BigDecimal value = new BigDecimal(amount);
    this.valueText = getThaiBaht(value);
    return this.valueText;
  }

  public String getText(float amount) {
    BigDecimal value = new BigDecimal(amount);
    this.valueText = getThaiBaht(value);
    return this.valueText;
  }

  public String getText(int amount) {
    BigDecimal value = new BigDecimal(amount);
    this.valueText = getThaiBaht(value);
    return this.valueText;
  }

  public String getText(long amount) {
    BigDecimal value = new BigDecimal(amount);
    this.valueText = getThaiBaht(value);
    return this.valueText;
  }

  public String getText(String amount)
  {
    for (String element : SYMBOLS_TH) {
      amount = amount.replace(element, "");
    }

    BigDecimal value = new BigDecimal(amount.trim());
    this.valueText = getThaiBaht(value);
    return this.valueText;
  }

  public String getText(Number amount) {
    BigDecimal value = new BigDecimal(String.valueOf(amount));
    this.valueText = getThaiBaht(value);
    return this.valueText;
  }

  private static String getThaiBaht(BigDecimal amount) {
    StringBuilder builder = new StringBuilder();
    BigDecimal absolute = amount.abs();
    int precision = absolute.precision();
    int scale = absolute.scale();
    int rounded_precision = precision - scale + 2;
    MathContext mc = new MathContext(rounded_precision, RoundingMode.HALF_UP);
    BigDecimal rounded = absolute.round(mc);
    BigDecimal[] compound = rounded.divideAndRemainder(BigDecimal.ONE);
    boolean negative_amount = -1 == amount.compareTo(BigDecimal.ZERO);

    compound[0] = compound[0].setScale(0);
    compound[1] = compound[1].movePointRight(2);

    if (negative_amount) {
      builder.append(SYMBOLS_TH[0].toString());
    }

    builder.append(getNumberText(compound[0].toBigIntegerExact()));
    builder.append(SYMBOLS_TH[1].toString());

    if (compound[1].compareTo(BigDecimal.ZERO) == 0) {
      builder.append(SYMBOLS_TH[2].toString());
    } else {
      builder.append(getNumberText(compound[1].toBigIntegerExact()));
      builder.append(SYMBOLS_TH[3].toString());
    }
    return builder.toString();
  }

  private static String getNumberText(BigInteger number) {
    StringBuffer buffer = new StringBuffer();
    char[] digits = number.toString().toCharArray();

    for (int index = digits.length; index > 0; index--) {
      int digit = Integer.parseInt(String.valueOf(digits[(digits.length - index)]));
      String digit_text = DIGIT_TH[digit];
      int scale_idx = 1 < index ? (index - 1) % 6 : 6;

      if ((1 == scale_idx) && (2 == digit)) {
        digit_text = SYMBOLS_TH[4].toString();
      }

      if (1 == digit) {
        switch (scale_idx) {
        case 0:
        case 6:
          buffer.append(index < digits.length ? SYMBOLS_TH[5].toString() : digit_text);
          break;
        case 1:
          break;
        case 2:
        case 3:
        case 4:
        case 5:
        default:
          buffer.append(digit_text);

          break; }  } else { if (digit == 0) {
          if (scale_idx != 0) continue;
          buffer.append(SCALE_TH[scale_idx]);

          continue;
        }
        buffer.append(digit_text);
      }
      buffer.append(SCALE_TH[scale_idx]);
    }
    return buffer.toString();
  }

  public static void main(String[] args)
  {
    System.out.println("Negative value of -1257.5463 : " + new ThaiBahtString().getText(-1257.5463D));
    System.out.println("Positive value of 1234.5463 : " + new ThaiBahtString().getText(1234.5463D));
    System.out.println("Negative string value of -1,234.5463 : " + new ThaiBahtString().getText("-1,234.5463"));
    System.out.println("Positive string value of 1,234.5463 : " + new ThaiBahtString().getText("1,234.5463"));
  }

}