package ke.co.urbansisters.ui.admin.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import ke.co.urbansisters.R;
import ke.co.urbansisters.models.Product;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyRecyclerViewHolder> {

    List<Product> products;
    private Context context;

    public RecyclerAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products,parent, false);

        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position) {

        for (int i=0; i<products.size(); i++) {
           Product product = products.get(i);
           holder.productName.setText(product.getName());
            holder.productCategory.setText(product.getCategory());
            holder.productQuantity.setText(product.getQuantity());
            holder.productPrice.setText(product.getAmount());
            Glide.with(context).load(product.getImage()).into(holder.productImage);
        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, productCategory, productPrice, productQuantity;

        public MyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage= itemView.findViewById(R.id._item_product_image);
            productName = itemView.findViewById(R.id._item_product_name);
            productCategory = itemView.findViewById(R.id._item_product_category);
            productPrice = itemView.findViewById(R.id._item_product_price);
            productQuantity = itemView.findViewById(R.id._item_product_quantity);
        }

    }

}
