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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.List;
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
//    @BindView(R.id._add_product_category)
//    EditText addProductCategory;
    @BindView(R.id._add_product_category_spinner)
    Spinner categorySpinner;
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
    private String productCategory;

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

        List<String> categories = new ArrayList<>();
        categories.add("Clip-in Hair");
        categories.add("Tape-In Hair");
        categories.add("Sew-In Hair");
        categories.add("Fusion & Pre-Bonded Hair");
        categories.add("Microlink Hair");
        categories.add("Wigs & Hair Pieces");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        String productBrand = addProductBrand.getText().toString();
        String productDescription = addProductDescription.getText().toString();
        String productQuantity = addProductQuantity.getText().toString();
        String productAmount = addProductAmount.getText().toString();
        ref.putFile(filePath)
                .addOnSuccessListener(taskSnapshot -> {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                        Product product = new Product(productName, productCategory, productBrand, productAmount, productQuantity, productDescription, mAuth.getUid(), uri.toString());
                        FirebaseDatabase.getInstance().getReference("Products").child(UUID.randomUUID().toString()).setValue(product).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Product has been added", Toast.LENGTH_SHORT).show();
                                sellersInterface.goToDashBoard();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                })
                .addOnFailureListener(exception -> {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Could not upload image", Toast.LENGTH_SHORT).show();
                });
    }

    Boolean validate() {
        boolean valid = true;
        String productName = addProductName.getText().toString();
        String productAmount = addProductAmount.getText().toString();
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
            Toast.makeText(getContext(), "Please pick a Category", Toast.LENGTH_SHORT).show();
            valid = false;
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
