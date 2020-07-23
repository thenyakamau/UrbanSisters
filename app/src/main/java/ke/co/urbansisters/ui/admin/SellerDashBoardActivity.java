package ke.co.urbansisters.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

import ke.co.urbansisters.R;
import ke.co.urbansisters.ui.admin.fragments.AddProduct;
import ke.co.urbansisters.ui.admin.fragments.OrdersFragment;
import ke.co.urbansisters.ui.admin.fragments.PendingOrdersFragment;
import ke.co.urbansisters.ui.admin.fragments.ProfileFragment;
import ke.co.urbansisters.ui.admin.fragments.SellerDashBoardFragment;
import ke.co.urbansisters.ui.admin.fragments.SellersInterface;
import ke.co.urbansisters.ui.admin.fragments.ViewSellerProductsFragment;
import ke.co.urbansisters.ui.auth.AuthActivity;

public class SellerDashBoardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener , SellersInterface {

    @BindView(R.id._seller_bottom_navigation)
    BottomNavigationView sellerNavigation;

    private static final String TAG = "SellerDashBoardActivity";
    private FirebaseAuth mAuth;
    private ProfileFragment profileFragment;
    private SellerDashBoardFragment dashBoardFragment;
    private AddProduct addProduct;
    private ViewSellerProductsFragment productsFragment;
    private PendingOrdersFragment ordersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dash_board);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
         sellerNavigation.setOnNavigationItemSelectedListener(this);

        profileFragment = new ProfileFragment(this, mAuth);
        dashBoardFragment = new SellerDashBoardFragment(this, mAuth);
        addProduct = new AddProduct(mAuth, this);
        productsFragment = new ViewSellerProductsFragment(this, mAuth);
        ordersFragment = new PendingOrdersFragment( mAuth);
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
            case R.id._seller_products:
                setFragment(productsFragment);
                return true;
            case R.id._sellers_profile:
                setFragment(profileFragment);
                return true;
            case R.id._seller_orders:
                setFragment(ordersFragment);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void goToDashBoard() {
        setFragment(dashBoardFragment);
    }

    @Override
    public void goTOAddProduct() {
        setFragment(addProduct);
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
        this.finish();
    }
}