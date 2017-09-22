package example.irshad.com.myapp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Irshad
 */

public class JSONUtils {


    public static String getJsonStringfromObject(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject.has(key) && !jsonObject.isNull(key)) {
            return jsonObject.getString(key);
        } else
            return "";
    }

    public static int getJsonIntfromObject(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject.has(key)) {
            return jsonObject.getInt(key);
        } else
            return -1;
    }

    public static JSONObject getJSONObjectfromResponse(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject.has(key)) {
            return jsonObject.getJSONObject(key);
        } else
            return new JSONObject();
    }

    public static JSONArray getJSONArrayfromResponse(JSONObject jsonObject, String key) throws JSONException {
        if (jsonObject.has(key) && jsonObject.getJSONArray(key) != null)
            return jsonObject.getJSONArray(key);
        else
            return new JSONArray();


    }

}
