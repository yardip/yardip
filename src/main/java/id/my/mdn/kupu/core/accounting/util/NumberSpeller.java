package id.my.mdn.kupu.core.accounting.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aku
 */
public class NumberSpeller {

    private static final String[] STR_NUM = {"NOL", "SATU ", "DUA ", "TIGA ", "EMPAT ", "LIMA ", "ENAM ", "TUJUH ", "DELAPAN ", "SEMBILAN "};
    private static final String[] STR_TRIPLE = {"RATUS ", "PULUH "};
    private static final String[] STR_TRIPLET = {"", "", "RIBU ", "JUTA ", "MILYAR "};
    private static final String[] STR_SPECIAL = {"SE", "BELAS "};

    public static String[] parseMoney(BigDecimal amount) {
        BigDecimal shifted = amount.movePointRight(2);
        int precision = shifted.toBigInteger().toString().length();
        String strNumber = shifted.round(new MathContext(precision)).toPlainString();

        String integerPart = strNumber.substring(0, strNumber.length() - 2);
        String fractionalPart = strNumber.substring(integerPart.length());

        try {
            return new String[]{parse(integerPart), parse(fractionalPart)};
        } catch (IOException ex) {
            Logger.getLogger(NumberSpeller.class.getName()).log(Level.SEVERE, null, ex);
            return new String[]{"", ""};
        }
    }

    public static String parse(String strNumeric) throws IOException {
        int len = strNumeric.length(); // Length of numeric string

        int head = len % 3; // The left most triplet
        if (head == 2) {
            strNumeric = "0" + strNumeric; // Prefix by a zero if the left most triplet contains only 2 chars
        } else if (head == 1) {
            strNumeric = "00" + strNumeric;
        }

        int tripletCount = strNumeric.length() / 3; // Set number of triplets

        int pointer = 0; // Pointer for the whole string

        ByteArrayOutputStream result = new ByteArrayOutputStream(); // Container for the result

        for (int i = tripletCount; i >= 1; i--) { // Looping across chars triplet by triplet
            int locPointer = 0; // Pointer for chars in the triplet, reset to the left most char in the triplet
            boolean flagBelasan = false; // Flag of tenth position
            int empty = 0;
            for (int j = 0; j < 3; j++) { // Looping across triplet
                if (pointer == strNumeric.length() - 1) { // Break if reached the end of string 
                    break;
                }
                String str = Character.toString(strNumeric.charAt(j + pointer)); // Char at index j inside a triplet
                switch (j) {
                    case 0 -> {
                        // First char of triplet
                        switch (str) {
                            case "0" -> // Add nothing
                                empty++;
                            case "1" -> {
                                result.write(STR_SPECIAL[0].getBytes()); // Add something like SE-bla..bla..bla
                                result.write(STR_TRIPLE[j].getBytes());
                            }
                            default -> {
                                // Add the spelling of the number like DUA, TIGA, etc
                                result.write(STR_NUM[Integer.parseInt(str)].getBytes());
                                result.write(STR_TRIPLE[j].getBytes());
                            }
                        }
                    }
                    case 1 -> {
                        // Second char of triplet
                        switch (str) {
                            case "0" ->
                                empty++;
                            case "1" ->
                                flagBelasan = true; // indicate special number spelling like SEBELAS not SATU BELAS or SERATUS not SATU RATUS
                            default -> {
                                result.write(STR_NUM[Integer.parseInt(str)].getBytes());
                                result.write(STR_TRIPLE[j].getBytes());
                            }
                        }
                    }
                    case 2 -> {
                        // Third char of triplet
                        if ((str.equals("0") && flagBelasan) || (str.equals("1") && flagBelasan) || (str.equals("1") && i == 2 && empty == 2)) {
                            result.write(STR_SPECIAL[0].getBytes()); // If flagBelasan is true, add something like SEBELAS
                        } else if (str.equals("0")) {
                            empty++;
                        } else {
                            result.write(STR_NUM[Integer.parseInt(str)].getBytes());
                        }

                        if (str.equals("1") && flagBelasan) {
                            result.write(STR_SPECIAL[1].getBytes());
                            flagBelasan = false;
                        } else if (str.equals("0") && flagBelasan) {
                            result.write(STR_TRIPLE[1].getBytes());
                            flagBelasan = false;
                        } else if (flagBelasan) {
                            result.write(STR_SPECIAL[1].getBytes());
                            flagBelasan = false;
                        }
                    }
                    default -> {
                    }
                }

                locPointer++; // Advances local pointer to the net char in the triplet
            }
            pointer = pointer + locPointer; // Advances pointer of whole chars by offsetting by local pointer

            if (i == tripletCount) { // After the last triplet
                result.write(STR_TRIPLET[i].getBytes()); // Add something like RIBU, JUTA, MILYAR
            } else if (i != tripletCount && empty != 3) { // After each non empty triplet
                result.write(STR_TRIPLET[i].getBytes()); // Add something like RIBU, JUTA, MILYAR
            }
            empty = 0;
        }

        return new String(result.toByteArray()).trim().toUpperCase();
    }
}
