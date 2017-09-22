package example.irshad.com.myapp.detail;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.irshad.com.myapp.R;

public class WebViewActivity extends AppCompatActivity {

    @Bind(R.id.web_view)
    WebView webView;

    private ProgressDialog progressDialog;

    public static final String PAGE_ID="page_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        String pageID=getIntent().getStringExtra(PAGE_ID);



        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://en.m.wikipedia.org/?curid="+pageID);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }
        });
    }
}
