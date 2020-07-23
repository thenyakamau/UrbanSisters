package ke.co.urbansisters.ui.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.urbansisters.R;
import ke.co.urbansisters.ui.dashboard.fragments.UserInterface;

public class OrdersFragment extends Fragment {

    private SellersInterface sellersInterface;
    private FirebaseAuth mAuth;

    public OrdersFragment(SellersInterface sellersInterface, FirebaseAuth mAuth) {
        this.sellersInterface = sellersInterface;
        this.mAuth = mAuth;
    }


}
