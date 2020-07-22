package ke.co.urbansisters.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

import butterknife.ButterKnife;
import ke.co.urbansisters.R;
import ke.co.urbansisters.models.User;
import ke.co.urbansisters.ui.auth.fragments.AuthInterface;
import ke.co.urbansisters.ui.auth.fragments.LoginView;
import ke.co.urbansisters.ui.auth.fragments.RegisterView;
import ke.co.urbansisters.ui.dashboard.DashBoardActivity;

public class AuthActivity extends AppCompatActivity implements AuthInterface {

    private LoginView loginView;
    private RegisterView registerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        loginView = new LoginView(this);
        registerView = new RegisterView(this);

        setFragment(loginView);
    }

    void  setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.auth_frame_layout, fragment);
        transaction.commit();
    }

    @Override
    public void goToLogin() {
        setFragment(loginView);
    }

    @Override
    public void goToRegister() {
        setFragment(registerView);
    }

    @Override
    public void goToBuyerDashBoard(User user) {
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.putExtra("user", (Serializable) user);
        startActivity(intent);
    }

    @Override
    public void goToSellerDashBoard(User user) {

    }


}