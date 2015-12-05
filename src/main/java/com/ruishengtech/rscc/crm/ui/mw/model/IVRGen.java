package com.ruishengtech.rscc.crm.ui.mw.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ruishengtech.framework.core.ApplicationHelper;
import com.ruishengtech.framework.core.SpringPropertiesUtil;
import com.ruishengtech.framework.core.util.UnicodeConverter;

/**
 * Created by yaoliceng on 2015/6/3.
 */
public class IVRGen {

    /**
     *
     * @param jsonArray
     * @param mwivr
     * @return
     *
     */
    public static String gen(MWIVR mwivr, JSONObject jsonObject) {
    	
    	System.out.println(jsonObject);
    	
        StringBuilder ivr = new StringBuilder();
        Map<String,JSONObject> map = new HashMap<>();

        for (Object o : jsonObject.keySet()) {
            map.put((String) o,jsonObject.getJSONObject(String.valueOf(o)));
        }

        List<String> contents  = new ArrayList<>();
        genMap(map,"1",contents,mwivr);

        for (String content : contents) {
            ivr.append(content);
        }

        File file = new File("test.xml");
        try {
            FileUtils.writeStringToFile(file,ivr.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ivr.toString();
    }

    private static void genMap(Map<String, JSONObject> map, String i, List<String> contents, MWIVR mwivr) {

        JSONObject node = map.get(i);
        contents.add(genMenu(node, map, mwivr.getName(),mwivr.getName(),contents));
    }

    /**
     * <menu name="demo_ivr"
     * greet-long="phrase:demo_ivr_main_menu"
     * greet-short="phrase:demo_ivr_main_menu_short"
     * invalid-sound="ivr/ivr-that_was_an_invalid_entry.wav"
     * exit-sound="voicemail/vm-goodbye.wav"
     * confirm-macro=""
     * confirm-key=""
     * tts-engine="flite"
     * tts-voice="rms"
     * confirm-attempts="3"
     * timeout="10000"
     * inter-digit-timeout="2000"
     * max-failures="3"
     * max-timeouts="3"
     * digit-len="4">
     *
     *
     * * {"1":{"child":["11"],"voices":"/upload/1430104924086.mp3","playtimes":"1"},
     * "11":{"type":"v","ivrname":"11","ivrkey":"3","voices":"/upload/1430104924086.mp3"}}
     *
     *
     */
    private static String genMenu(JSONObject jsonObject, Map<String, JSONObject> map, String root_name, String name, List<String> contents) {

        StringBuilder menu = new StringBuilder();

        menu.append("<include>\n");

        menu.append("<menu name=\"" + name + "\"   greet-long=\"" + jsonObject.getString("voices") + "\" " +
                " max-timeouts=\""+jsonObject.getString("playtimes")+"\" invalid-sound=\"ivr/ivr-that_was_an_invalid_entry.wav\" >\n");

        JSONArray keys = jsonObject.getJSONArray("child");
        for(int i=0; i<keys.length();i++){
            menu.append(getAction(String.valueOf(keys.getInt(i)),map.get(String.valueOf(keys.getInt(i))),map,root_name,contents));
        }
        menu.append("</menu>\n");
        menu.append("</include>\n");
        return menu.toString();
    }


    /**
     * {"ivrname":"1 语音","voices":"/upload/1432088164940.mp3","pid":1,"type":"v","ivrkey":"1"}
     */
    private static String getAction(String key, JSONObject jsonObject, Map<String, JSONObject> map, String root_name, List<String> contents) {

        SpringPropertiesUtil springPropertiesUtil = (SpringPropertiesUtil) ApplicationHelper.getApplicationContext().getBean("propertyConfigurer");
        String ip = springPropertiesUtil.getProperty("fs.ip");
        String port = springPropertiesUtil.getProperty("fs.port");

        StringBuilder ret = new StringBuilder();

        String action ="set action=ivrlog";
        ret.append("<entry action=\"menu-exec-app\" digits=\""+jsonObject.getString("ivrkey")+"\" param=\""+action+"\"/>\n");

        action ="set node_name="+ UnicodeConverter.toEncodedUnicode(jsonObject.getString("ivrname"),true);
        ret.append("<entry action=\"menu-exec-app\" digits=\""+jsonObject.getString("ivrkey")+"\" param=\""+action+"\"/>\n");

        action ="set node_type="+jsonObject.getString("type");
        ret.append("<entry action=\"menu-exec-app\" digits=\""+jsonObject.getString("ivrkey")+"\" param=\""+action+"\"/>\n");

        action ="set node_key="+ jsonObject.getString("ivrkey");
        ret.append("<entry action=\"menu-exec-app\" digits=\""+jsonObject.getString("ivrkey")+"\" param=\""+action+"\"/>\n");

        action ="socket '"+ip+":"+port +" async full'";
        ret.append("<entry action=\"menu-exec-app\" digits=\""+jsonObject.getString("ivrkey")+"\" param=\""+action+"\"/>\n");

        if("e".equals(jsonObject.getString("type"))){

            action="transfer "+jsonObject.getString("extension")+" XML default";
            ret.append("<entry action=\"menu-exec-app\" digits=\""+jsonObject.getString("ivrkey")+"\" param=\""+action+"\"/>\n");
            return ret.toString();

        }else if("m".equals(jsonObject.getString("type"))){

            action="ivr "+root_name+"_"+key;
            ret.append("<entry action=\"menu-exec-app\" digits=\""+jsonObject.getString("ivrkey")+"\" param=\""+action+"\"/>\n");

           contents.add(genMenu(jsonObject, map, root_name, root_name + "_" + key, contents));
            return ret.toString();

        }else if("v".equals(jsonObject.getString("type"))){
            ret.append("<entry action=\"menu-play-sound\" digits=\""+jsonObject.getString("ivrkey")+"\" param=\""+jsonObject.getString("voices")+"\" />\n");
            return ret.toString();

        }else if("a".equals(jsonObject.getString("type"))){

            ret.append("<entry action=\"menu-back\" digits=\""+jsonObject.getString("ivrkey")+"\"/>\n");
            return ret.toString();

        }else if("p".equals(jsonObject.getString("type"))){

            action="ivr "+ root_name;
            ret.append("<entry action=\"menu-exec-app\" digits=\""+jsonObject.getString("ivrkey")+"\" param=\""+action+"\"/>\n");
            return ret.toString();

        }
        return "";
    }

}
