package ke.co.urbansisters.ui.auth.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.urbansisters.R;
import ke.co.urbansisters.models.User;

public class RegisterView extends Fragment {
    private static final String TAG = "registerActivity";

    @BindView(R.id.register_name_input)
    EditText registerName;
    @BindView(R.id.register_phone_input)
    EditText registerPhone;
    @BindView(R.id.register_email_input)
    EditText registerEmail;
    @BindView(R.id.register_password_input)
    EditText registerPassword;
    @BindView(R.id.register_c_password_input)
    EditText confirmPassword;
    @BindView(R.id.register_check)
    AppCompatCheckBox registerCheck;

    private AuthInterface authInterface;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    public RegisterView(AuthInterface authInterface) {
        this.authInterface = authInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_register, container, false);
        ButterKnife.bind(this, view);
        mAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Creating user");
        progressDialog.setCancelable(false);
    }

    @OnClick(R.id.auth_go_to_login)
    void goToLogin() {
        authInterface.goToLogin();
    }

    @OnClick(R.id.register_btn)
    void register() {
        String name = registerName.getText().toString();
        String phone = registerPhone.getText().toString();
        String email = registerEmail.getText().toString().trim().toLowerCase();
        String password = registerPassword.getText().toString().trim().toLowerCase();
        String type = "";
        if (registerCheck.isChecked()) type = "seller";
        else type = "buyer";
        final String userType = type;
        if (!validate()) {

            return;
        }
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                       User saveUser = new User(name, email, phone, userType);
                        FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(mAuth.getUid())).setValue(saveUser).addOnCompleteListener(task1 -> {
                            if(task1.isSuccessful()) {
                                progressDialog.dismiss();
                                if(saveUser.getType().equals("buyer")) {
                                    authInterface.goToBuyerDashBoard();
                                }else {
                                    authInterface.goToSellerDashBoard();
                                }
                            }else {
                                progressDialog.dismiss();
                                Log.d(TAG, "register: registration failure");
                            }
                        });
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());

//                            updateUI(null);
                    }

                    // ...
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private boolean validate() {
        boolean valid = true;

        String name = registerName.getText().toString();
        String phone = registerPhone.getText().toString();
        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();
        String cPassword = confirmPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerEmail.setError("enter a valid email address");
            valid = false;
        } else {
            registerEmail.setError(null);
        }

        if (name.isEmpty()) {
            registerName.setError("Enter a valid name");
            valid = false;
        } else {
            registerName.setError(null);
        }

        if (phone.isEmpty() || !android.util.Patterns.PHONE.matcher(phone).matches()) {
            registerPhone.setError("Enter a valid phone number");
            valid = false;
        } else {
            registerPhone.setError(null);
        }

        if (password.length() < 6 || password.length() > 20) {
            registerPassword.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        } else {
            registerPassword.setError(null);
        }

        if (!password.equals(cPassword)) {
            registerPassword.setError("Password do no match");
            confirmPassword.setError("Password do no match");
            valid = false;
        } else {
            registerPassword.setError(null);
            confirmPassword.setError(null);
        }

        return valid;

    }

}

