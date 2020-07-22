package ke.co.urbansisters.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ke.co.urbansisters.R;
import ke.co.urbansisters.models.User;
import ke.co.urbansisters.ui.auth.AuthActivity;
import ke.co.urbansisters.ui.dashboard.fragments.UserInterface;

public class DashBoardActivity extends AppCompatActivity implements UserInterface {

    private static final String TAG = "dashBoardActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        mAuth = FirebaseAuth.getInstance();

    }

    void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.seller_frame_layout, fragment);
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
    }

    @Override
    public void goToDescription(int position) {

    }
}
