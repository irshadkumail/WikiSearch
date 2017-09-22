package example.irshad.com.myapp.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Irshad
 */

public class AppCustomRequest {

    public static void sendJSONRequest(Context context, int method, String url, JSONObject postParams, final CustomRequestInterface customRequestInterface){

        ConnectionDetector connectionDetector=new ConnectionDetector(context);

        if (connectionDetector.isConnectingToInternet()) {

            Log.d("IRSHAD_REQUEST",url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("IRSHAD_REQUEST",response+"");
                    customRequestInterface.onSuccessfulResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Log.e("IRSHAD_REQUEST","Timeout Error || NoConnectionError");
                        customRequestInterface.onErrorResponse(error,CustomRequestInterface.CONNECTION_TIMEOUT_ERROR);

                    } else if (error instanceof AuthFailureError) {
                        Log.e("IRSHAD_REQUEST","AuthFailureError");
                        customRequestInterface.onErrorResponse(error,CustomRequestInterface.SERVER_CONNECTION_ERROR);

                    } else if (error instanceof ServerError) {
                        Log.e("IRSHAD_REQUEST","ServerError");
                        customRequestInterface.onErrorResponse(error,CustomRequestInterface.SERVER_CONNECTION_ERROR);

                    } else if (error instanceof NetworkError) {
                        Log.e("IRSHAD_REQUEST","NetworkError");
                        customRequestInterface.onErrorResponse(error,CustomRequestInterface.SERVER_CONNECTION_ERROR);

                    } else if (error instanceof ParseError) {
                        Log.e("IRSHAD_REQUEST","ParseError");
                        customRequestInterface.onErrorResponse(error,CustomRequestInterface.SERVER_CONNECTION_ERROR);
                    }

                }
            });

            RequestSingleton.getInstance(context).add(jsonObjectRequest);
        }else
            customRequestInterface.onErrorResponse(new VolleyError(),CustomRequestInterface.NO_INTERNET_CONNECTION);

    }
}
