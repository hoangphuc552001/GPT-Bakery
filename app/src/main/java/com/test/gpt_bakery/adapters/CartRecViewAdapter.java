package com.test.gpt_bakery.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.gpt_bakery.R;
import com.test.gpt_bakery.models.Cookie;

import java.util.ArrayList;

public class CartRecViewAdapter extends RecyclerView.Adapter<CartRecViewAdapter.ViewHolder> {
    ArrayList<Cookie> cookies = new ArrayList<>();
    Context mContext;
    private changeQuantityListener listener;

    public CartRecViewAdapter(Context mContext, changeQuantityListener changeQuantityListener) {
        this.mContext = mContext;
        this.listener = changeQuantityListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(cookies.get(position).getName());
        holder.tvPrice.setText(String.valueOf(cookies.get(position).getTotalPrice()));
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(cookies.get(position).getImage(), 0, cookies.get(position).getImage().length));
        holder.tvQuantity.setText(String.valueOf(cookies.get(position).getQuantity()));
        holder.btnIncrease.setOnClickListener(view -> listener.onChangeQuantityClick(cookies.get(position), "+", position));
        holder.btnDecrease.setOnClickListener(view -> listener.onChangeQuantityClick(cookies.get(position), "-", position));
    }

    public void setCart(ArrayList<Cookie> cookies) {
        this.cookies = cookies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cookies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView image;
        private final TextView tvName;
        private final TextView tvPrice;
        private final TextView tvQuantity;
        private final Button btnIncrease;
        private final Button btnDecrease;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity= itemView.findViewById(R.id.tvQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }

    public interface changeQuantityListener{
        void onChangeQuantityClick(Cookie cookie, String action, int position);
    }
}
