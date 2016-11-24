package com.mitdy.core.persistence;

public class JpaCriteriaUtils {

    public static boolean appendWhere(StringBuffer querySql, boolean where, int conditionCount) {
        if (!where) {
            querySql.append("where ");
            where = true;
        }
        if (conditionCount > 0) {
            querySql.append("and ");
        }
        return where;
    }

}
