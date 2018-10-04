/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.common.util;

import java.util.StringTokenizer;

/**
 *
 * @author carlos
 */
public class StringUtil {
    
     /**
     * ************************************************************************
     * Return true if the specified String is null or empty; false otherwise.
     *
     * @param strIn String to compare.
     * @return true if equal or both null or empty; false otherwise.
     *************************************************************************
     */
    public static boolean isNullOrEmpty(String strIn) {
        return (strIn == null || strIn.equals(""));
    }

    /**
     * ************************************************************************
     * Return true if the specified Strings are equal or if both of them are
     * null or empty; false otherwise.
     *
     * @param str1 String to compare.
     * @param str2 String to compare.
     * @return true if equal or both null or empty; false otherwise.
     *************************************************************************
     */
    public static boolean equalsOrBothNullOrEmpty(String str1, String str2) {
        if (isNullOrEmpty(str1) != isNullOrEmpty(str2)) {
            // One is null or empty and the other is not.
            return false;
        }

        // Both are null or empty, or both are non-null and non-empty.
        return (isNullOrEmpty(str1) || str1.equals(str2));
    }

    /**
     * ************************************************************************
     * Return true if the specified Strings are equalsIgnoreCase() or if both of
     * them are null or empty; false otherwise.
     *
     * @param str1 String to compare.
     * @param str2 String to compare.
     * @return true if equal or both null or empty; false otherwise.
     *************************************************************************
     */
    public static boolean equalsIgnoreCaseOrBothNullOrEmpty(String str1, String str2) {
        if (isNullOrEmpty(str1) != isNullOrEmpty(str2)) {
            // One is null or empty and the other is not.
            return false;
        }

        // Both are null or empty, or both are non-null and non-empty.
        return (isNullOrEmpty(str1) || str1.equalsIgnoreCase(str2));
    }

    /**
     * ************************************************************************
     * Return true if the specified Strings are equal or both null; false
     * otherwise.
     *
     * @param str1 String to compare.
     * @param str2 String to compare.
     * @return true if equal or both null; false otherwise.
     *************************************************************************
     */
    public static boolean equalsIgnoreCaseOrBothNull(String str1, String str2) {
        if ((str1 == null) != (str2 == null)) {
            // One is null and the other is not.
            return false;
        }
        // Both are null, or both are non-null.
        return (str1 == null || str1.equalsIgnoreCase(str2));
    }

    /**
     * ************************************************************************
     * Returns null if the specified string, when trimmed, is null or empty.
     * Otherwise, returns the specified string.
     *
     * @param strIn The input string.
     * @return Null or the value of strIn.
     *************************************************************************
     */
    public static String mapEmptyToNull(String strIn) {
        if (strIn == null || strIn.trim().equals("")) {
            return null;
        }
        return strIn;
    }

    /**
     * ************************************************************************
     * Return a String that is a copy of strIn with all occurrences of strFrom
     * replaced with strTo. If strFrom is the empty string, return strIn. Note:
     * The advantage of this over the native Java String.replaceAll() is that it
     * does not try to interpret strFrom as a regular expression.
     *
     * @param strIn String to start with.
     * @param strFrom String to change from.
     * @param strTo String to change to.
     * @return Copy of strIn with updates.
     *************************************************************************
     */
    public static String replaceAll(String strIn, String strFrom, String strTo) {
        //-- Note:  Beware an empty strFrom.  Without this test, the loop
        //--        below would be infinite.
        int intFromLength = strFrom.length();
        if (intFromLength <= 0) {
            return strIn;
        }

        StringBuffer sbRC = new StringBuffer();
        int intIndex = 0;
        int intLength = strIn.length();
        while (intIndex < intLength) {
            //-- Find the next occurrence of strFrom in strIn.
            int intMatchPos = strIn.indexOf(strFrom, intIndex);
            if (intMatchPos > -1) {
                //-- Copy the chars we just scanned past.
                sbRC.append(strIn.substring(intIndex, intMatchPos));

                //-- Copy strTo instead of the matched strFrom.
                sbRC.append(strTo);

                //-- Advance past the matched strFrom.
                intIndex = intMatchPos + intFromLength;
            } else {
                //-- No more matches.  Copy the remaining chars, and
                //-- advance to the end of the string.
                sbRC.append(strIn.substring(intIndex, intLength));
                intIndex = intLength;
            }
        }

        return sbRC.toString();
    }

    /**
     * ************************************************************************
     * Return a String that is a copy of strIn with the first occurrences, if
     * any, of strFrom replaced with strTo. If strFrom is the empty string,
     * return strIn. Note: The advantage of this over the native Java
     * String.replaceFirst() is that it does not try to interpret strFrom as a
     * regular expression.
     *
     * @param strIn String to start with.
     * @param strFrom String to change from.
     * @param strTo String to change to.
     * @return Copy of strIn with updates.
     *************************************************************************
     */
    public static String replaceFirst(String strIn, String strFrom, String strTo) {
        int intFromLength = strFrom.length();
        if (intFromLength <= 0) {
            return strIn;
        }

        StringBuffer sbRC = new StringBuffer();

        //-- Find the next occurrence of strFrom in strIn.
        int intMatchPos = strIn.indexOf(strFrom);
        if (intMatchPos > -1) {
            //-- Copy the chars we just scanned past.
            sbRC.append(strIn.substring(0, intMatchPos));

            //-- Copy strTo instead of the matched strFrom.
            sbRC.append(strTo);

            //-- Advance past the matched strFrom.
            sbRC.append(strIn.substring(intMatchPos + intFromLength));
            return sbRC.toString();
        } else {
            return strIn;
        }
    }

    /**
     * ************************************************************************
     * Return a string that is a copy of strString with strQuote inserted before
     * and after each substring that is delimited by chDelim.
     *
     * @param strString String to search for delimited substrings.
     * @param chDelim Delimiter character.
     * @param strQuote String to insert before and after each substring.
     * @return String with quotes inserted.
     *************************************************************************
     */
    public static String quoteDelimitedSubstrings(String strString, char chDelim, String strQuote) {
        String strDelim = String.valueOf(chDelim);
        boolean blnFirstTime = true;
        String strRC = "";
        StringTokenizer st = new StringTokenizer(strString, strDelim);
        while (st.hasMoreTokens()) {
            strRC += (blnFirstTime ? "" : strDelim)
                    + strQuote + st.nextToken() + strQuote;
            blnFirstTime = false;
        }
        return strRC;
    }

    /**
     * ************************************************************************
     * Return a string that is a copy of strString with strInsert inserted after
     * each occurrence of chDelim.
     *
     * @param strString String to search for delimited substrings.
     * @param chDelim Delimiter character.
     * @param strInsert String to insert after each delimiter.
     * @return String with insertions.
     *************************************************************************
     */
    public static String insertAfterDelimiters(String strString, char chDelim, String strInsert) {
        String strDelim = String.valueOf(chDelim);
        return replaceAll(strString,
                strDelim,
                strDelim + strInsert);
    }

    /**
     * ************************************************************************
     * Return a string containing intLength occurrences of the character chChar.
     * If intLength is zero or negative, return the empty string ("");
     *
     * @param chChar Character to put in the string.
     * @param intLength Desired length of the result string.
     * @return Generated string.
     *************************************************************************
     */
    public static String makeStringOfChars(char chChar, int intLength) {
        if (intLength <= 0) {
            return "";
        }
        char ach[] = new char[intLength];
        for (int i = 0; i < intLength; i++) {
            ach[i] = chChar;
        }
        return new String(ach);
    }

    /**
     * ************************************************************************
     * Left pad a string. Return a string that is a copy of strString preceded
     * by enough copies of chPad to make the length of the resulting string
     * equal intLength. If strString is already too long, return it unchanged.
     *
     * @param strString String to pad.
     * @param chPad Character to pad with.
     * @param intLength Desired length of the result string.
     * @return Padded string.
     *************************************************************************
     */
    public static String lpad(String strString, char chPad, int intLength) {
        return StringUtil.makeStringOfChars(chPad, intLength - strString.length())
                + strString;
    }

    /**
     * ************************************************************************
     * Right pad a string. Return a string that is a copy of strString followed
     * by enough copies of chPad to make the length of the resulting string
     * equal intLength. If strString is already too long, return it unchanged.
     *
     * @param strString String to pad.
     * @param chPad Character to pad with.
     * @param intLength Desired length of the result string.
     * @return Padded string.
     *************************************************************************
     */
    public static String rpad(String strString, char chPad, int intLength) {
        return strString
                + StringUtil.makeStringOfChars(chPad, intLength - strString.length());
    }

    /**
     * ************************************************************************
     * Left trim a string. Return a string that is a copy of strString with all
     * consecutive leading occurrences of the specified substring removed.
     *
     * @param strString String to trim.
     * @param strMatch Substring to remove.
     * @return Padded string.
     *************************************************************************
     */
    public static String ltrim(String strString, String strMatch) {
        int intStringLen = strString.length();
        int intMatchLen = strMatch.length();
        if (intStringLen == 0 || intMatchLen == 0 || intMatchLen > intStringLen) {
            return strString;
        }
        int intTrimAt = 0;
        int intBegin = 0;
        int intEnd = intMatchLen;
        while (intEnd <= intStringLen
                && strString.substring(intBegin, intEnd).equals(strMatch)) {
            intTrimAt = intEnd;
            intBegin += intMatchLen;
            intEnd += intMatchLen;
        }
        if (intTrimAt > 0) {
            strString = strString.substring(intTrimAt);
        }
        return strString;
    }

    /**
     * ************************************************************************
     * Right trim a string. Return a string that is a copy of strString with all
     * consecutive trailing occurrences of the specified substring removed.
     *
     * @param strString String to trim.
     * @param strMatch Substring to remove.
     * @return Padded string.
     *************************************************************************
     */
    public static String rtrim(String strString, String strMatch) {
        int intStringLen = strString.length();
        int intMatchLen = strMatch.length();
        if (intStringLen == 0 || intMatchLen == 0 || intMatchLen > intStringLen) {
            return strString;
        }
        int intTrimAt = intStringLen;
        int intBegin = intStringLen - intMatchLen;
        int intEnd = intStringLen;
        while (intBegin >= 0
                && strString.substring(intBegin, intEnd).equals(strMatch)) {
            intTrimAt = intBegin;
            intBegin -= intMatchLen;
            intEnd -= intMatchLen;
        }
        if (intTrimAt < intStringLen) {
            strString = strString.substring(0, intTrimAt);
        }
        return strString;
    }

    /**
     * ************************************************************************
     * Returns a string containing the specified number of chars copied from the
     * beginning of a string, or less if the specified string is too short.
     *
     * @param strString String to get substring from.
     * @param intLength Max length of the substring to return. Negative values
     * rounded up to zero.
     * @return Substring.
     *************************************************************************
     */
    public static String left(String strString, int intLength) {
        int intEndPos = Math.min(strString.length(),
                Math.max(0, intLength));
        return strString.substring(0, intEndPos);
    }

    /**
     * ************************************************************************
     * Returns a string containing the specified number of chars copied from the
     * end of a string, or less if the specified string is too short.
     *
     * @param strString String to get substring from.
     * @param intLength Max length of the substring to return. Negative values
     * rounded up to zero.
     * @return Substring.
     *************************************************************************
     */
    public static String right(String strString, int intLength) {
        int intAdjustedLength = Math.min(strString.length(),
                Math.max(0, intLength));
        int intStartPos = strString.length() - intAdjustedLength;
        return strString.substring(intStartPos);
    }

    /**
     * ************************************************************************
     * Returns the specified string with commas inserted every 3 chars counting
     * from the right -- the traditional formatting of a large number, broken
     * into ones, thousands, millions, etc.
     *
     * @param strString String to insert commas into.
     * @return String with commas.
     *************************************************************************
     */
    public static String insertCommas(String strString) {
        StringBuffer sb = new StringBuffer(strString);
        for (int i = sb.length() - 3; i > 0; i -= 3) {
            sb.insert(i, ',');
        }
        return sb.toString();
    }

    /**
     * ************************************************************************
     * Return true if strString contains any of the chars in strChars; false
     * otherwise.
     *
     * @param strString String to search.
     * @param strChars String of chars to search for.
     * @return true if strIn contains any of the chars in strChars; false
     * otherwise.
     *************************************************************************
     */
    public static boolean containsAnyChar(String strString, String strChars) {
        // Loop through the specified chars, searching strString for each char.
        // Stop at the first match.
        if (strString == null || strChars == null) {
            return false;
        }
        for (int i = 0; i < strChars.length(); i++) {
            if (strString.indexOf(strChars.charAt(i)) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * ************************************************************************
     * Return true if strString contains any char other than those in strChars;
     * false otherwise.
     *
     * @param strString String to search.
     * @param strChars String of chars to search for.
     * @return true if strString contains any char other than those in strChars;
     * false otherwise.
     *************************************************************************
     */
    public static boolean containsAnyOtherChar(String strString, String strChars) {
        // Loop through strString, searching strChars for each char.
        // Stop at the first non-match.
        if (strString == null || strChars == null) {
            return false;
        }
        for (int i = 0; i < strString.length(); i++) {
            if (strChars.indexOf(strString.charAt(i)) < 0) {
                return true;
            }
        }
        return false;
    }

    public static String capitalize(String s) {
        if (s.length() == 0) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
    
}
