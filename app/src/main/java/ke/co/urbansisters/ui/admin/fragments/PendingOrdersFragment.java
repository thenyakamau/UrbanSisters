package ke.co.urbansisters.ui.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ke.co.urbansisters.R;
import ke.co.urbansisters.models.Orders;
import ke.co.urbansisters.ui.dashboard.fragments.adapters.TransactionsAdapter;

public class PendingOrdersFragment extends Fragment  {
    @BindView(R.id._status_recycler_view)
    RecyclerView statusRecyclerView;

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private List<Orders> orders = new ArrayList<>();
    private TransactionsAdapter transactionsAdapter;

    public PendingOrdersFragment(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_orders_status, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        statusRecyclerView.setHasFixedSize(true);
        statusRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Fetching products...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        orders.clear();

        FirebaseDatabase.getInstance().getReference("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {


                    String name = Objects.requireNonNull(snapshot1.child("name").getValue()).toString();
                    String category = Objects.requireNonNull(snapshot1.child("category").getValue()).toString();
                    String image = Objects.requireNonNull(snapshot1.child("image").getValue()).toString();
                    String status = Objects.requireNonNull(snapshot1.child("status").getValue()).toString();
                    String sellerId = Objects.requireNonNull(snapshot1.child("sellerId").getValue()).toString();
                    String buyerId = Objects.requireNonNull(snapshot1.child("buyerId").getValue()).toString();

                    if (sellerId.equals(mAuth.getUid())&&status.equals("pending")) {
                        Orders order = new Orders(name, image, category, status, sellerId, buyerId);
                        orders.add(order);
                    }

                }

                addOrders(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addOrders(List<Orders> orders) {
        transactionsAdapter = new TransactionsAdapter(orders, getContext());
        statusRecyclerView.setAdapter(transactionsAdapter);
    }
}
