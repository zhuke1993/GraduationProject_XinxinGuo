package com.gxx.nqh.util;

/**
 * 根据分数得到相应的等级
 * 信用分数 0-50:A, 50-100:A+, 100+:S, -10-0:B, -50--10:C, -100--50:D, -100:E
 * Created by ZHUKE on 2016/5/20.
 */
public class CreditScoreUtil {

    public static String getLevel(int score) {
        if (score >= 0 && score < 50) {
            return "A";
        } else if (score >= 50 && score < 100) {
            return "A+";
        } else if (score >= 100) {
            return "S";
        } else if (score >= -10 && score < 0) {
            return "B";
        } else if (score >= -50 && score < -10) {
            return "C";
        } else if (score >= -100 && score < -50) {
            return "D";
        } else if (score < -100) {
            return "E";
        }
        return null;
    }


}
