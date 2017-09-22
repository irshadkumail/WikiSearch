package example.irshad.com.myapp.list;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.irshad.com.myapp.R;
import example.irshad.com.myapp.detail.WebViewActivity;
import example.irshad.com.myapp.utils.ItemClickListener;

import static example.irshad.com.myapp.detail.WebViewActivity.PAGE_ID;

public class WikiListActivity extends AppCompatActivity implements WikiListView,SearchView.OnQueryTextListener,ItemClickListener {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.search_view)
    SearchView searchView;

    private WikiListPresenter wikiListPresenter;

    private ArrayList<WikiListItem> wikiListItems;

    private WikiListAdapter wikiListAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_list);
        ButterKnife.bind(this);
        initComponents();
    }

    private void initComponents()
    {
        progressDialog=new ProgressDialog(this);
        wikiListItems=new ArrayList<>();
        wikiListPresenter=new WikiListPresenterImpl(this,wikiListItems);
        searchView.setOnQueryTextListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        wikiListAdapter=new WikiListAdapter(this,wikiListItems);
        recyclerView.setAdapter(wikiListAdapter);


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        progressDialog.setMessage("Searching...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        wikiListPresenter.doSearch(query);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void doSearchSuccess() {
        progressDialog.dismiss();
        wikiListAdapter.notifyDataSetChanged();

    }

    @Override
    public void doSearchFailure(String message) {
        progressDialog.dismiss();
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void onItemClicked(int postion) {

        Intent intent=new Intent(this, WebViewActivity.class);
        intent.putExtra(PAGE_ID,wikiListItems.get(postion).getPageId());
        startActivity(intent);

    }
}
