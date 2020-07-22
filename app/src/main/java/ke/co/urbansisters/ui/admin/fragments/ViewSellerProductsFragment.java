package ke.co.urbansisters.ui.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.urbansisters.R;
import ke.co.urbansisters.models.Product;
import ke.co.urbansisters.ui.admin.fragments.adapters.RecyclerAdapter;

public class ViewSellerProductsFragment extends Fragment {

    private static final String TAG = "SellerFragment";

    @BindView(R.id._view_products_recycler_view)
    RecyclerView productsRecyclerView;

    private SellersInterface sellersInterface;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private List<Product> products = new ArrayList<>();

    public ViewSellerProductsFragment(SellersInterface sellersInterface, FirebaseAuth mAuth) {
        this.sellersInterface = sellersInterface;
        this.mAuth = mAuth;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_view_products, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productsRecyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Fetching products...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        products.clear();
        FirebaseDatabase.getInstance().getReference("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Log.d(TAG, "onDataChange: "+snapshot1);
                    String name = Objects.requireNonNull(snapshot1.child("name").getValue()).toString();
                    String category = Objects.requireNonNull(snapshot1.child("category").getValue()).toString();
                    String brand = Objects.requireNonNull(snapshot1.child("brand").getValue()).toString();
                    String image = Objects.requireNonNull(snapshot1.child("image").getValue()).toString();
                    String description = Objects.requireNonNull(snapshot1.child("description").getValue()).toString();
                    String quantity = Objects.requireNonNull(snapshot1.child("quantity").getValue()).toString();
                    String amount = Objects.requireNonNull(snapshot1.child("amount").getValue()).toString();
                    String uuid = Objects.requireNonNull(snapshot1.child("uuid").getValue()).toString();

                    if(uuid.equals(mAuth.getUid())) {
                        Product product = new Product(name, category, brand,  amount, quantity, description,uuid, image );
                        products.add(product);
                    }

                }
                Log.d(TAG, "onDataChange: "+products);
                addProducts(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addProducts(List<Product> products) {
        RecyclerAdapter adapter = new RecyclerAdapter(products, getContext());
        productsRecyclerView.setAdapter(adapter);
    }
}
