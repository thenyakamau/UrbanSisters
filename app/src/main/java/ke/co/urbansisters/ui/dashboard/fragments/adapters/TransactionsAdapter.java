package ke.co.urbansisters.ui.dashboard.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ke.co.urbansisters.R;
import ke.co.urbansisters.models.Orders;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.RecyclerViewHolder> {

    private List<Orders> orders = new ArrayList<>();
    private Context context;

    public TransactionsAdapter(List<Orders> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_transactions, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        Orders order = orders.get(position);
        holder.productName.setText(order.getName());
        holder.productCategory.setText(order.getCategory());
        holder.productStatus.setText(order.getStatus());
        Glide.with(context).load(order.getImage()).into(holder.productImage);

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName, productCategory, productStatus;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id._item_transaction_image);
            productName = itemView.findViewById(R.id._item_transaction_name);
            productCategory = itemView.findViewById(R.id._item_transaction_category);
            productStatus = itemView.findViewById(R.id._item_transaction_status);
        }
    }

}
