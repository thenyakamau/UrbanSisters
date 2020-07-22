package ke.co.urbansisters.ui.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.urbansisters.R;

public class SellerDashBoardFragment extends Fragment {

    private FirebaseAuth mAuth;
    private SellersInterface sellersInterface;

    public SellerDashBoardFragment(SellersInterface sellersInterface, FirebaseAuth mAuth) {
        this.sellersInterface = sellersInterface;
        this.mAuth = mAuth;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_seller_dashboard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.floating_action_button_dashboard)
    void addProduct() {
        sellersInterface.goTOAddProduct();
    }

}
