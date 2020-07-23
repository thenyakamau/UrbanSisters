package ke.co.urbansisters.ui.dashboard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.urbansisters.R;
import ke.co.urbansisters.models.Product;
import ke.co.urbansisters.ui.admin.fragments.adapters.RecyclerAdapter;

public class CartFragment extends Fragment {

    @BindView(R.id._cart_recycler_view)
    RecyclerView cartRecyclerView;

    private UserInterface userInterface;
    private FirebaseAuth mAuth;
    private List<Product> cartProduct = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;

    public CartFragment(UserInterface userInterface, FirebaseAuth mAuth, List<Product> cartProduct) {
        this.userInterface = userInterface;
        this.mAuth = mAuth;
        this.cartProduct = cartProduct;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_user_cart, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerAdapter = new RecyclerAdapter(cartProduct, getContext());
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setAdapter(recyclerAdapter);
    }

    @OnClick(R.id._cart_check_out_btn)
    void checkOut(){

    }

}
