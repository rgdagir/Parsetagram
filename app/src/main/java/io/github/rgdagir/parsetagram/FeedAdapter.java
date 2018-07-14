package io.github.rgdagir.parsetagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    // Post array in the constructor
    // for each row, inflate the layout and cache references into viewholder

    private List<Post> mPostList;
    private Context mContext;
    public FeedAdapter(List<Post> posts) {
        mPostList = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflate = LayoutInflater.from(mContext);
        Log.d("RecyclerViewAdapterTag", "ON CREATE VIEW HOLDER ");

        View feedView = inflate.inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(feedView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPostList.get(position);

        try {
            Glide.with(mContext)
                    .load(post.getImage().getFile())
                    .into(holder.ivCoverImage);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvCaption.setText(post.getCaption());
        Log.d("RecyclerViewAdapterTag", "On BIND VIEW HOLDER");
        holder.tvUserName.setText(post.getUser().getUsername());
        holder.tvUsernameBottom.setText(post.getUser().getUsername());
        holder.tvCaption.setText(post.getCaption());
        holder.createdAt.setText(post.getCreatedAt().toString());
        Log.d("RecyclerViewAdapterTag", "herou");
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivCoverImage;
        public TextView tvUserName;
        public TextView tvUsernameBottom;
        public TextView tvCaption;
        public TextView createdAt;

        public ViewHolder(View itemView) {
            super(itemView);

            ivCoverImage = itemView.findViewById(R.id.mainImagePost);
            tvUsernameBottom = itemView.findViewById(R.id.usernameBottomBar);
            tvUserName = itemView.findViewById(R.id.usernameBar);
            tvCaption = itemView.findViewById(R.id.caption);
            createdAt = itemView.findViewById(R.id.createdAt);

        }
    }

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        mPostList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPostList.addAll(list);
        notifyDataSetChanged();
    }
}

