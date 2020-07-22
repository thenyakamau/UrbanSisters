package ke.co.urbansisters.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.urbansisters.R;
import ke.co.urbansisters.models.User;
import ke.co.urbansisters.ui.admin.fragments.ProfileFragment;
import ke.co.urbansisters.ui.admin.fragments.SellerDashBoardFragment;
import ke.co.urbansisters.ui.admin.fragments.SellersInterface;
import ke.co.urbansisters.ui.auth.AuthActivity;

public class SellerDashBoardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , SellersInterface {

    @BindView(R.id._seller_bottom_navigation)
    BottomNavigationView sellerNavigation;

    private static final String TAG = "SellerDashBoardActivity";
    private FirebaseAuth mAuth;
    private ProfileFragment profileFragment;
    private SellerDashBoardFragment dashBoardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dash_board);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
         sellerNavigation.setOnNavigationItemSelectedListener(this);

        profileFragment = new ProfileFragment(this, mAuth);
        dashBoardFragment = new SellerDashBoardFragment(this, mAuth);
        setFragment(dashBoardFragment);

    }

    void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.seller_frame_layout, fragment);
        transaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id._seller_dashboard:
                setFragment(dashBoardFragment);
                return true;
            case R.id._sellers_profile:
                setFragment(profileFragment);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void goToDashBoard() {

    }

    @Override
    public void goTOAddProduct() {

    }

    @Override
    public void goToViewProducts() {

    }

    @Override
    public void goToProfile() {

    }

    @Override
    public void logOut() {
        mAuth.signOut();
        startActivity(new Intent(this, AuthActivity.class));
    }
}