package com.vitor.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    
    // Merge properties null
    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyKeys(source));
    }

    // Get all properies null
    public static String[] getNullPropertyKeys(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source); // interface to acess property in java object

        PropertyDescriptor[] pds = src.getPropertyDescriptors(); // get the property

        Set<String> emptyNames = new HashSet<>();

        // Verify if properies is null 
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()]; // Created a Array with same size that emptyNames
        return emptyNames.toArray(result); // Convert emptyNames collection with propries empty to String Array
    }
}
