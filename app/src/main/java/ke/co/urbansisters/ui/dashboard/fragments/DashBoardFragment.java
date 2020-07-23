package ke.co.urbansisters.ui.dashboard.fragments;

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
import androidx.recyclerview.widget.GridLayoutManager;
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
import ke.co.urbansisters.ui.dashboard.fragments.adapters.ProductAdapter;

public class DashBoardFragment extends Fragment {

    private static final String TAG = "UserDashBoardFragment";

    @BindView(R.id.clip_in_recyclerview)
    RecyclerView clipRecyclerView;
    @BindView(R.id.tape_in_recyclerview)
    RecyclerView tapeRecyclerView;
    @BindView(R.id.sew_in_recyclerview)
    RecyclerView sewRecyclerVIew;
    @BindView(R.id.fusion_in_recyclerview)
    RecyclerView fusionRecyclerView;
    @BindView(R.id.micro_in_recyclerview)
    RecyclerView microRecyclerView;
    @BindView(R.id.wig_in_recyclerview)
    RecyclerView wigRecyclerView;

    private UserInterface userInterface;
    private FirebaseAuth mAuth;

    private List<Product> clipProducts = new ArrayList<>();
    private List<Product> tapeProducts = new ArrayList<>();
    private List<Product> sewProducts = new ArrayList<>();
    private List<Product> fusionProducts = new ArrayList<>();
    private List<Product> microlinkProducts = new ArrayList<>();
    private List<Product> wigProducts = new ArrayList<>();
    private ProgressDialog progressDialog;

    public DashBoardFragment(UserInterface userInterface, FirebaseAuth mAuth) {
        this.userInterface = userInterface;
        this.mAuth = mAuth;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_user_dash_board, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clipRecyclerView.setHasFixedSize(true);
        clipRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        tapeRecyclerView.setHasFixedSize(true);
        tapeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        sewRecyclerVIew.setHasFixedSize(true);
        sewRecyclerVIew.setLayoutManager(new GridLayoutManager(getContext(), 2));

        fusionRecyclerView.setHasFixedSize(true);
        fusionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        microRecyclerView.setHasFixedSize(true);
        microRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        wigRecyclerView.setHasFixedSize(true);
        wigRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Fetching products...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        clipProducts.clear();
        tapeProducts.clear();
        sewProducts.clear();
        fusionProducts.clear();
        microlinkProducts.clear();
        wigProducts.clear();
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


                        Product product = new Product(name, category, brand,  amount, quantity, description,uuid, image );
                    switch (category) {
                        case "Clip-in Hair":
                            clipProducts.add(product);
                            break;
                        case "Tape-In Hair":
                            tapeProducts.add(product);
                            break;
                        case "Sew-In Hair":
                            sewProducts.add(product);
                            break;
                        case "Fusion & Pre-Bonded Hair":
                            fusionProducts.add(product);
                            break;
                        case "Microlink Hair":
                            microlinkProducts.add(product);
                            break;

                        case "Wigs & Hair Pieces":
                            wigProducts.add(product);
                            break;
                        default:
                            break;
                    }

                }
                addClipProducts(clipProducts);
                addTapeProducts(tapeProducts);
                addSewProducts(sewProducts);
                addFusionProducts(fusionProducts);
                addMicrolinkProducts(microlinkProducts);
                addWigProduct(wigProducts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addWigProduct(List<Product> wigProducts) {
        ProductAdapter productAdapter = new ProductAdapter(wigProducts, getContext(), userInterface);
        wigRecyclerView.setAdapter(productAdapter);
    }

    private void addMicrolinkProducts(List<Product> microlinkProducts) {
        ProductAdapter productAdapter = new ProductAdapter(microlinkProducts, getContext(), userInterface);
        microRecyclerView.setAdapter(productAdapter);
    }

    private void addFusionProducts(List<Product> fusionProducts) {
        ProductAdapter productAdapter = new ProductAdapter(fusionProducts, getContext(), userInterface);
        fusionRecyclerView.setAdapter(productAdapter);
    }

    private void addSewProducts(List<Product> sewProducts) {
        ProductAdapter productAdapter = new ProductAdapter(sewProducts, getContext(), userInterface);
        sewRecyclerVIew.setAdapter(productAdapter);
    }

    private void addTapeProducts(List<Product> tapeProducts) {
        ProductAdapter productAdapter = new ProductAdapter(tapeProducts, getContext(), userInterface);
        tapeRecyclerView.setAdapter(productAdapter);
    }

    private void addClipProducts(List<Product> clipProducts) {
        ProductAdapter productAdapter = new ProductAdapter(clipProducts, getContext(), userInterface);
        clipRecyclerView.setAdapter(productAdapter);
    }

}
