package Util.safe;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther Skay
 * @date 2020/10/20 15:57
 * @description
 */
public class XSS_Safe {
    public static HashMap<String, Object> check(HashMap<String, Object> req_map) {
        HashMap<String, Object> newhaspmap = new HashMap<>();
        String key;
        String value;
        for (Map.Entry<String, Object> entry : req_map.entrySet()) {
            key = entry.getKey();
            value = (String) entry.getValue();
            newhaspmap.put(cleanXSS(key.toLowerCase()),cleanXSS(value.toLowerCase()));

        }
        return newhaspmap;
    }

    private static String cleanXSS(String value) {
        //todo 全部实体编码


        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");

        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");

        value = value.replaceAll("'", "& #39;");

        value = value.replaceAll("eval\\((.*)\\)", "");

        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

        value = value.replaceAll("script", "");

        return value;

    }
}
