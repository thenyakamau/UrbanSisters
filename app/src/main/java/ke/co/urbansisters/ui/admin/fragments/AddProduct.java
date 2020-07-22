package ke.co.urbansisters.ui.admin.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.urbansisters.R;
import ke.co.urbansisters.models.Product;

import static android.app.Activity.RESULT_OK;

public class AddProduct extends Fragment {

    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    private static final String TAG = "AddProductFragment";

    @BindView(R.id._add_product_image)
    ImageView addProductImage;
    @BindView(R.id._add_product_name)
    EditText addProductName;
    @BindView(R.id._add_product_category)
    EditText addProductCategory;
    @BindView(R.id._add_product_brand)
    EditText addProductBrand;
    @BindView(R.id._add_product_description)
    EditText addProductDescription;
    @BindView(R.id._add_product_quantity)
    EditText addProductQuantity;
    @BindView(R.id._add_product_amount)
    EditText addProductAmount;

    private FirebaseAuth mAuth;
    private SellersInterface sellersInterface;
    private ProgressDialog progressDialog;
    private Uri filePath;

    public AddProduct(FirebaseAuth mAuth, SellersInterface sellersInterface) {
        this.mAuth = mAuth;
        this.sellersInterface = sellersInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_product, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Adding product");
        progressDialog.setCancelable(false);
    }

    @OnClick(R.id._add_product_image)
    void fetchImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(getActivity().getContentResolver(), filePath);
                addProductImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id._add_product_btn)
    void addProduct() {
        if (!validate()) {
            return;
        }

        progressDialog.show();

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://urban-sisters-92a96.appspot.com");
        StorageReference ref
                = mStorageRef
                .child(
                        "images/upload/"
                                + UUID.randomUUID().toString());
        String productName = addProductName.getText().toString();
        String productCategory = addProductCategory.getText().toString();
        String productBrand = addProductBrand.getText().toString();
        String productDescription = addProductDescription.getText().toString();
        String productQuantity = addProductQuantity.getText().toString();
        String productAmount = addProductAmount.getText().toString();
        ref.putFile(filePath)
                .addOnSuccessListener(taskSnapshot -> {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Product product = new Product(productName, productCategory, productBrand, productAmount, productQuantity, productDescription, mAuth.getUid(), uri.toString());
                            FirebaseDatabase.getInstance().getReference("Products").setValue(product).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Product has been added", Toast.LENGTH_SHORT).show();
                                    sellersInterface.goToDashBoard();
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                })
                .addOnFailureListener(exception -> {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Could not upload image", Toast.LENGTH_SHORT).show();
                });
//        FirebaseDatabase.getInstance().getReference("Products").child(Objects.requireNonNull(mAuth.getUid())).setValue()
    }

    Boolean validate() {
        boolean valid = true;
        String productName = addProductName.getText().toString();
        String productAmount = addProductAmount.getText().toString();
        String productCategory = addProductCategory.getText().toString();
        String productBrand = addProductBrand.getText().toString();
        String productDescription = addProductDescription.getText().toString();
        String productQuantity = addProductQuantity.getText().toString();

        if (productName.isEmpty()) {
            addProductName.setError("Input Name");
            valid = false;
        } else {
            addProductName.setError(null);
        }

        if (filePath == null) {
            Toast.makeText(getContext(), "Please input an image", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (productAmount.isEmpty()) {
            addProductAmount.setError("Input amount");
            valid = false;
        } else {
            addProductAmount.setError(null);
        }

        if (productCategory.isEmpty()) {
            addProductCategory.setError("Input Category");
            valid = false;
        } else {
            addProductCategory.setError(null);
        }

        if (productQuantity.isEmpty()) {
            addProductQuantity.setError("Input product quantity");
        } else {
            addProductQuantity.setError(null);
        }

        if (productBrand.isEmpty()) {
            addProductBrand.setError("Input brand");
            valid = false;
        } else {
            addProductBrand.setError(null);
        }

        if (productDescription.length() <= 99) {
            addProductDescription.setError("Input Category");
            valid = false;
        } else {
            addProductDescription.setError(null);
        }

        return valid;
    }

}
