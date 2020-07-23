package ke.co.urbansisters.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.urbansisters.R;
import ke.co.urbansisters.models.Product;
import ke.co.urbansisters.models.User;
import ke.co.urbansisters.ui.auth.AuthActivity;
import ke.co.urbansisters.ui.dashboard.fragments.CartFragment;
import ke.co.urbansisters.ui.dashboard.fragments.DashBoardFragment;
import ke.co.urbansisters.ui.dashboard.fragments.TransactionsFragments;
import ke.co.urbansisters.ui.dashboard.fragments.UserInterface;
import ke.co.urbansisters.ui.dashboard.fragments.UserProfile;

public class DashBoardActivity extends AppCompatActivity implements UserInterface, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id._user_bottom_navigation)
    BottomNavigationView bottomNavigationView;

    private static final String TAG = "dashBoardActivity";

    private FirebaseAuth mAuth;
    private DashBoardFragment dashBoardFragment;
    private UserProfile userProfile;
    private TransactionsFragments transactionsFragments;
    private List<Product> cartProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();

        dashBoardFragment = new DashBoardFragment(this, mAuth);
        userProfile = new UserProfile(this, mAuth);
        transactionsFragments = new TransactionsFragments(this, mAuth);

        setFragment(dashBoardFragment);

    }

    void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.user_frame_layout, fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, AuthActivity.class));
        }

    }

    @Override
    public void logOut() {
        mAuth.signOut();
        startActivity(new Intent(this, AuthActivity.class));
        this.finish();
    }

    @Override
    public void goToDescription(int position) {

    }

    @Override
    public void addToCart(Product product) {
        cartProducts.add(product);
    }

    private void goToCart() {
        CartFragment cartFragment = new CartFragment(this, mAuth, cartProducts);
        setFragment(cartFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()) {
           case R.id._user_dashboard:
               setFragment(dashBoardFragment);
               return true;
           case R.id._user_profile:
               setFragment(userProfile);
               return true;
           case R.id._user_cart:
               goToCart();
               return true;
           case R.id._user_transactions:
               setFragment(transactionsFragments);
               return true;
           default:
               return true;
       }
    }
}
