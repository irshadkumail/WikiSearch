package example.irshad.com.myapp.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import example.irshad.com.myapp.R;
import example.irshad.com.myapp.utils.ItemClickListener;

/**
 * Created by Irshad on 21/9/17.
 */

public class WikiListAdapter extends RecyclerView.Adapter<WikiListAdapter.WikiListViewHolder> {

    private List<WikiListItem> wikiListItems;

    private Context context;

    private ItemClickListener itemClickListener;

    public WikiListAdapter(Context context, List<WikiListItem> wikiListItems)
    {
        this.context=context;
        this.wikiListItems=wikiListItems;
        this.itemClickListener= (ItemClickListener) context;

    }


    @Override
    public WikiListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=layoutInflater.inflate(R.layout.wiki_list_item,parent,false);
        WikiListViewHolder wikiListViewHolder=new WikiListViewHolder(itemView);

        return wikiListViewHolder;
    }

    @Override
    public void onBindViewHolder(WikiListViewHolder holder, final int position) {

        holder.heading.setText(wikiListItems.get(position).getName());
        holder.subHeadng.setText(wikiListItems.get(position).getSubHeading());

        if (!wikiListItems.get(position).getImage().isEmpty())
            Picasso.with(context).load(wikiListItems.get(position).getImage()).error(R.drawable.default_user).into(holder.imageView);
        else
            Picasso.with(context).load(R.drawable.default_user).into(holder.imageView);


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClicked(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return wikiListItems.size();
    }

    public class WikiListViewHolder extends RecyclerView.ViewHolder {

        TextView heading,subHeadng;
        ImageView imageView;
        View container;


        public WikiListViewHolder(View itemView) {
            super(itemView);

            container=itemView;
            heading= (TextView) itemView.findViewById(R.id.heading);
            subHeadng= (TextView) itemView.findViewById(R.id.sub_heading);
            imageView= (ImageView) itemView.findViewById(R.id.image_view);

        }
    }


}
