package Entity;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * @auther Skay
 * @date 2020/10/20 16:59
 * @description
 */
public class Return_result {
    private HashMap<String,String> resp_json_map = new HashMap<>();
    private String resp_str;

    public Return_result(){
    }

    public void add_result_para(String key,String value){
        resp_json_map.put(key,value);
    }

    public String get_return_result(){
        resp_str = JSONObject.toJSONString(resp_json_map);
        return resp_str;
    }

    public HashMap get_map_result(){
        return resp_json_map;
    }
}
