package ke.co.urbansisters.ui.auth.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.urbansisters.R;
import ke.co.urbansisters.models.User;

public class LoginView extends Fragment {

    private  static final String TAG = "loginActivity";

    @BindView(R.id.login_email)
    EditText login_email;
    @BindView(R.id.login_password)
    EditText loginPassword;

    private AuthInterface authInterface;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    public LoginView(AuthInterface authInterface) {
        this.authInterface = authInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_login, container, false);
        ButterKnife.bind(this, view);
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Loading user");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null) {
            progressDialog.show();
            FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressDialog.dismiss();
                    String type = (String) snapshot.child("type").getValue();

                    if(type != null&&type.equals("buyer")){
                        authInterface.goToBuyerDashBoard();
                    }else{
                        authInterface.goToSellerDashBoard();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "SomeThing went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        updateUI(currentUser);

    }

    @OnClick(R.id.auth_go_to_register)
    void goToRegister(){
        authInterface.goToRegister();
    }

    @OnClick(R.id.login_btn)
    void login() {
        if(!validate()){

            return;
        }
        String email = login_email.getText().toString().trim().toLowerCase();
        String password = loginPassword.getText().toString().trim().toLowerCase();
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(mAuth.getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                progressDialog.dismiss();
                                String name = (String) snapshot.child("name").getValue();
                                String email1 = (String) snapshot.child("email").getValue();
                                String phone = (String) snapshot.child("phone").getValue();
                                String type = (String) snapshot.child("type").getValue();
                                User savedUser = new User(name, email1, phone, type);

                                if(type != null&&type.equals("buyer")){
                                    authInterface.goToBuyerDashBoard();
                                }else{
                                    authInterface.goToSellerDashBoard();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "SomeThing went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });

//                            updateUI(user);
                    } else {
                        progressDialog.dismiss();
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }

                    // ...
                });
    }
    private boolean validate() {
        boolean valid = true;

        String email = login_email.getText().toString();
        String password = loginPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            login_email.setError("enter a valid email address");
            valid = false;
        } else {
            login_email.setError(null);
        }

        if ( password.length() < 6 || password.length() > 20) {
            loginPassword.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        } else {
            loginPassword.setError(null);
        }

        return valid;

    }
}
