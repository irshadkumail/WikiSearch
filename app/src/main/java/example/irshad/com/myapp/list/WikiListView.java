package example.irshad.com.myapp.list;

import android.content.Context;

/**
 * Created by Irshad on 21/9/17.
 */

public interface WikiListView {

    void doSearchSuccess();

    void doSearchFailure(String message);

    Context getContext();

}
