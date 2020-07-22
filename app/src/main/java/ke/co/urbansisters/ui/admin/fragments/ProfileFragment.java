package ke.co.urbansisters.ui.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.urbansisters.R;
import ke.co.urbansisters.models.User;

public class ProfileFragment extends Fragment {

    @BindView(R.id._profile_name)
    EditText profile_name;
    @BindView(R.id._profile_email)
    EditText profile_email;
    @BindView(R.id._profile_phone)
    EditText profile_phone;
    @BindView(R.id._profile_type)
    EditText profile_type;

    private User user;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private SellersInterface sellersInterface;

    public ProfileFragment(SellersInterface sellersInterface, FirebaseAuth mAuth) {
        this.sellersInterface = sellersInterface;
        this.mAuth = mAuth;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Creating user");
        progressDialog.setCancelable(false);
        FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(mAuth.getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                String name = (String) snapshot.child("name").getValue();
                String email1 = (String) snapshot.child("email").getValue();
                String phone = (String) snapshot.child("phone").getValue();
                String type = (String) snapshot.child("type").getValue();
                user = new User(name, email1, phone, type);
                profile_name.setText(name);
                profile_email.setText(email1);
                profile_phone.setText(phone);
                profile_type.setText(type);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @OnClick(R.id.profile_sign_out)
    void signOut() {
        sellersInterface.logOut();
    }
}
