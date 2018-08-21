package com.zaiym;

import com.zaiym.reflec.AmsReceiveBean;

import java.lang.reflect.Field;

/**
 * Hello world!
 */
public class App {

    private static String result = "<result column=\"%s\" property=\"%s\"  jdbcType=\"%s\" />";

    private static String isNotNull = "<isNotNull prepend=\" or \" property=\"%s\">%s $%s$ #%s#</isNotNull>";

    private static String update = "<isNotNull prepend=\",\" property=\"%s\">%s = #%s#</isNotNull>";

    public static void main(String[] args) {
        StringBuilder select_sb = new StringBuilder();
        StringBuilder result_sb = new StringBuilder();
        StringBuilder result_null = new StringBuilder();
        StringBuilder values_sb = new StringBuilder();
        StringBuilder update_sb = new StringBuilder();
        Class clazz = AmsReceiveBean.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            select_sb.append(name.toUpperCase()).append(",");
            result_sb.append(String.format(result, name.toUpperCase(), name, getType(field.getGenericType().toString()))).append("\n");
            result_null.append(String.format(isNotNull, name+"_cause", name.toUpperCase(), name+"_cause", name)).append("\n");
            values_sb.append("#").append(name).append("#,");
            update_sb.append(String.format(update, name, name.toUpperCase(), name)).append("\n");
        }
        System.out.println(select_sb.toString());
        System.out.println(result_sb.toString());
        System.out.println(result_null.toString());
        System.out.println(values_sb.toString());
        System.out.println(update_sb.toString());
    }

    public static String getType(String type){
        type = type.toUpperCase();
        String t = "VARCHAR";
        if (type.contains("STRING")) {
            t = "VARCHAR";
        } else if (type.contains("LONG")
                || type.contains("INT")
                || type.contains("DOUBLE")
                || type.contains("SHORT")){
            t = "DECIMAL";
        } else if (type.contains("DATE")){
            t = "DATETIME";
        }
        return t;
    }
}
