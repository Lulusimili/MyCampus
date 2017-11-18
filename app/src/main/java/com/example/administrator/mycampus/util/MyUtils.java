package com.example.administrator.mycampus.util;

import android.text.TextUtils;
import android.widget.Toast;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Administrator on 2017/11/18 0018.
 */

public class MyUtils {
   static Toast  mToast;
    public static void showToast(String text){
            if (!TextUtils.isEmpty(text)) {
                if (mToast == null) {
                    mToast = Toast.makeText(getApplicationContext(), text,
                            Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(text);
                }
                mToast.show();
            }
        }
    public static String[] myExpressionString = {
            "aaa", "aab", "aac", "aad", "aae",
            "aaf", "aag", "aah", "aai", "aaj",
            "aak", "aal", "aam", "aan", "aao",
            "aap", "aaq", "aar", "aas", "aat",
            "aau", "aav", "aaw", "aax", "aay",
            "aaz", "aba", "abc", "abd", "abe",
            "abf", "abg", "abg", "abh", "abi",
            "abj", "abk", "abl", "abm", "abn",
            "abo", "abq", "abr", "abs", "abt",
            "abu", "abv", "abw", "abx",
            "aby", "abz", "baa", "bab", "bac",
            "bad", "bae", "baf", "bag", "bah",
            "bai", "baj", "bak", "bal",
            "bam", "ban", "bao", "bap", "baq",
            "bar", "bas", "bat", "bau", "bav",
            "baw", "bax", "bay", "baz", "bba",
            "bbc", "bbd", "bbe", "bbf", "bbg",
            "bbh", "bbi", "bbj", "bbk", "bbl",
            "bbm", "bbn", "bbo", "bbp", "bbq",
            "bbr", "bbs", "bbt", "bbu", "bbv",
            "bbw", "bbx", "bby", "bbz", "caa", "cab", "cac"};
    public static String filterHtml(String str) {
        str = str.replaceAll("<(?!br|img)[^>]+>", "").trim();
        return str;
    }


}
