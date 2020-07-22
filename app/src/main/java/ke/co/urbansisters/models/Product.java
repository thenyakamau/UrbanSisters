package ke.co.urbansisters.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable, Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("image")
    @Expose
    private String image;
    public final static Parcelable.Creator<Product> CREATOR = new Creator<Product>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return (new Product[size]);
        }

    };
    private final static long serialVersionUID = -1854389096767449576L;

    protected Product(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
        this.brand = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.quantity = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.uuid = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Product() {
    }

    /**
     * @param amount
     * @param quantity
     * @param name
     * @param description
     * @param category
     * @param brand
     * @param uuid
     */
    public Product(String name, String category, String brand, String amount, String quantity, String description, String uuid, String image) {
        super();
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.amount = amount;
        this.quantity = quantity;
        this.description = description;
        this.uuid = uuid;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(category);
        dest.writeValue(brand);
        dest.writeValue(amount);
        dest.writeValue(quantity);
        dest.writeValue(description);
        dest.writeValue(uuid);
        dest.writeValue(image);
    }

    public int describeContents() {
        return 0;
    }
}