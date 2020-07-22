package ke.co.urbansisters.ui.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.urbansisters.R;

public class AddProduct extends Fragment {

    private FirebaseAuth mAuth;
    private SellersInterface sellersInterface;

    public AddProduct(FirebaseAuth mAuth, SellersInterface sellersInterface) {
        this.mAuth = mAuth;
        this.sellersInterface = sellersInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_product, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id._add_product_btn) void addProduct() {
//        FirebaseDatabase.getInstance().getReference("Products").child(Objects.requireNonNull(mAuth.getUid())).setValue()
    }

}
