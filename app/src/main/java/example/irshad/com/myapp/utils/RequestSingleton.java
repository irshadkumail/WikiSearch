package example.irshad.com.myapp.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Irshad on 21/9/17.
 */

public class RequestSingleton {


    private static RequestQueue requestQueue;


    public static RequestQueue getInstance(Context context)
    {
        if (requestQueue==null)
            requestQueue= Volley.newRequestQueue(context);


        return requestQueue;
    }



}
