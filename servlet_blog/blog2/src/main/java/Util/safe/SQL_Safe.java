package Util.safe;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther Skay
 * @date 2020/10/20 15:57
 * @description
 */
public class SQL_Safe {
    public static boolean check(HashMap<String, Object> req_map) {
        String key;
        String value;
        for (Map.Entry<String, Object> entry : req_map.entrySet()) {
            key = entry.getKey();
            value = (String) entry.getValue();
            sql_inj(value.toLowerCase());
            sql_inj(key.toLowerCase());

        }
        return true;
    }

    public static boolean sql_inj(String str){
        //todo 预编译
        String inj_str = "'|and|exec|insert|select|delete|update| count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
        String[] inj_stra=inj_str.split("\\|");
        for (int i=0 ; i < inj_stra.length ; i++ ){
            if (str.indexOf(inj_stra[i]) >= 0){
                return false;
            }
        }
        return true;

    }
}
