package com.test.gpt_bakery.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.test.gpt_bakery.CustomerFoodPanel_BottomNavigation;
import com.test.gpt_bakery.R;
import com.test.gpt_bakery.customerFoodpanel.CustomHomeFragment;
import com.test.gpt_bakery.models.Cookie;

import java.util.ArrayList;
import java.util.Collection;

public class CookieRecViewAdapter extends RecyclerView.Adapter<CookieRecViewAdapter.ViewHolder> implements Filterable {

    ArrayList<Cookie> cookies = new ArrayList<>();
    ArrayList<Cookie> cookiesTemp = new ArrayList<>();
    Context mContext;
    private cookieClickListener cookieClickListener;
    public CookieRecViewAdapter(ArrayList<Cookie> cookieslist){
        this.cookies=cookieslist;
        this.cookiesTemp=new ArrayList<>(cookieslist);
    }
    public CookieRecViewAdapter(Context mContext, cookieClickListener cookieClickListener) {
        this.mContext = mContext;
        this.cookieClickListener = cookieClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cookie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(cookies.get(position).getName());
        holder.tvPrice.setText(String.valueOf(cookies.get(position).getPrice()));
        holder.tvDescription.setText(cookies.get(position).getDescription());
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(cookies.get(position).getImage(), 0, cookies.get(position).getImage().length));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cookieClickListener.onCookieClick(cookies.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cookies.size();
    }

    public void setCookies(ArrayList<Cookie> cookies) {
        this.cookies = cookies;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch= constraint.toString();
                if (strSearch.isEmpty()){
                    cookies=cookiesTemp;
                }
                else{
                    ArrayList<Cookie> list = new ArrayList<>();
                    for (Cookie c:cookiesTemp){
                        if (c.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(c);
                        }
                    }
                    cookies=list;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=cookies;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                cookies.clear();
                cookies.addAll((Collection<? extends Cookie>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView tvName, tvPrice, tvDescription;
        private ConstraintLayout parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            parent = itemView.findViewById(R.id.parent);
        }
    }

    public interface cookieClickListener{
        public void onCookieClick(Cookie cookie);
    }
}
