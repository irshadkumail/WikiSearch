package example.irshad.com.myapp.utils;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Irshad
 */

public interface CustomRequestInterface {

    public static final int NO_INTERNET_CONNECTION=101;

    public static final int SERVER_RESPONSE_ERROR=102;

    public static final int SERVER_CONNECTION_ERROR=103;

    public static final int CONNECTION_TIMEOUT_ERROR=104;

    public static final int NO_LOGIN_ERROR=105;


    public void onSuccessfulResponse(JSONObject response);

    public void onErrorResponse(VolleyError volleyError, int errorCode);


}
