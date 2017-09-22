package example.irshad.com.myapp.list;

import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import example.irshad.com.myapp.cache.CacheDBHeper;
import example.irshad.com.myapp.utils.AppCustomRequest;
import example.irshad.com.myapp.utils.CustomRequestInterface;
import example.irshad.com.myapp.utils.JSONUtils;

/**
 * Created by Irshad on 21/9/17.
 */

public class WikiListPresenterImpl implements WikiListPresenter {

    private WikiListView wikiListView;

    private ArrayList<WikiListItem> wikiListItems;

    private CacheDBHeper cacheDBHeper;

    private String query;

    public WikiListPresenterImpl(WikiListView wikiListView, ArrayList<WikiListItem> wikiListItems) {
        this.wikiListView = wikiListView;
        this.wikiListItems = wikiListItems;
        this.cacheDBHeper = new CacheDBHeper(wikiListView.getContext());

    }

    @Override
    public void doSearch(String query) {

        this.query=query;
        CacheChecker cacheChecker=new CacheChecker();
        cacheChecker.execute();


    }

    private void parseResponse(JSONObject jsonObject) throws JSONException {
        wikiListItems.clear();
        JSONObject query = JSONUtils.getJSONObjectfromResponse(jsonObject, "query");
        JSONArray pages = JSONUtils.getJSONArrayfromResponse(query, "pages");

        if (pages.length() == 0) {
            wikiListView.doSearchFailure("No Search Results");
            return;
        }

        for (int i = 0; i < pages.length(); i++) {
            JSONObject currentObject = pages.getJSONObject(i);

            WikiListItem wikiListItem = new WikiListItem();

            wikiListItem.setName(JSONUtils.getJsonStringfromObject(currentObject, "title"));
            wikiListItem.setPageId(JSONUtils.getJsonStringfromObject(currentObject, "pageid"));

            JSONObject imageObject = JSONUtils.getJSONObjectfromResponse(currentObject, "thumbnail");
            wikiListItem.setImage(JSONUtils.getJsonStringfromObject(imageObject, "source"));

            JSONObject termsObject = JSONUtils.getJSONObjectfromResponse(currentObject, "terms");
            JSONArray descriptionArray = JSONUtils.getJSONArrayfromResponse(termsObject, "description");

            if (descriptionArray.length() > 0)
                wikiListItem.setSubHeading(descriptionArray.getString(0));

            wikiListItems.add(wikiListItem);

        }
        wikiListView.doSearchSuccess();


    }

    private String getSearchURL(String QUERY) {

        String URL = "https://en.wikipedia.org//w/api.php?action=query&format=json&prop=pageimages|pageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description&gpssearch=" + QUERY + "&gpslimit=10";

        return URL;
    }


    private class CacheChecker extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            return cacheDBHeper.getSearchResponse(query);

        }

        public void onPostExecute(String result) {
            try {
                if (result.equalsIgnoreCase("no_result"))
                    startSearch();
                else{
                    Toast.makeText(wikiListView.getContext(),"From Cache",Toast.LENGTH_SHORT).show();;
                    parseResponse(new JSONObject(result));
                }
                } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }






    private void startSearch(){

        String url = getSearchURL(query.replaceAll(" ", "+"));

        AppCustomRequest.sendJSONRequest(wikiListView.getContext(), Request.Method.GET, url, null, new CustomRequestInterface() {
            @Override
            public void onSuccessfulResponse(JSONObject response) {

                try {
                    cacheDBHeper.insertIntoDB(query,response+"");
                    parseResponse(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                    wikiListView.doSearchFailure("Error in server response");

                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError, int errorCode) {
                switch (errorCode) {
                    case CustomRequestInterface.NO_INTERNET_CONNECTION:
                        wikiListView.doSearchFailure("No Internet Connection ");
                        break;
                    default:
                        wikiListView.doSearchFailure("Unable to connect to Server. Please try again");
                        break;


                }
            }
        });
    }


}
