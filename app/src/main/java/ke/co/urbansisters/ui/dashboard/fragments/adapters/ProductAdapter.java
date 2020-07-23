package ke.co.urbansisters.ui.dashboard.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ke.co.urbansisters.R;
import ke.co.urbansisters.models.Product;
import ke.co.urbansisters.ui.dashboard.fragments.UserInterface;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.RecyclerViewHolder> {

    private List<Product> products = new ArrayList<>();
    private Context context;
    private UserInterface userInterface;

    public ProductAdapter(List<Product> products, Context context, UserInterface userInterface) {
        this.products = products;
        this.context = context;
        this.userInterface = userInterface;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_product, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

            Product product = products.get(position);
            holder.productName.setText(product.getName());
            holder.productPrice.setText(product.getAmount());
            Glide.with(context).load(product.getImage()).into(holder.productImage);

            holder.productCard.setOnClickListener(v -> {
                userInterface.addToCart(product);
                Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show();
            });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, productCart;
        CardView productCard;
        TextView productName, productPrice;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            productCard = itemView.findViewById(R.id._user_product_card);
            productImage = itemView.findViewById(R.id._user_product_image);
            productCart = itemView.findViewById(R.id._user_product_cart);
            productName = itemView.findViewById(R.id._user_product_name);
            productPrice = itemView.findViewById(R.id._user_product_amount);
        }
    }

}
