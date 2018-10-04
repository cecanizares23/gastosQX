/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.common.util;

/**
 *
 * @author carlos
 */
public class ValidaString {
 
    public ValidaString() {
    }

    public static boolean isEmptyString(String str) {
        if (str.length() == 0) {
            return true;
        }
        if (str.equals(Generales.EMPTYSTRING)) {
            return true;
        }
        return false;
    }

    public static boolean isNullString(String str) {
        if (str == null) {
            return true;
        }
        if (str.equals(Generales.NULLVALUE)) {
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmptyString(String str) {
        if (ValidaString.isNullString(str)) {
            return true;
        }
        if (ValidaString.isEmptyString(str)) {
            return true;
        }
        return false;
    }
    public static boolean isNumeric(String str){
	try {
		Integer.parseInt(str);
		return true;
	} catch (NumberFormatException e){
		return false;
	}
}
    
}
