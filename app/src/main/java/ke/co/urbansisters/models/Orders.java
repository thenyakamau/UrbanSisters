package ke.co.urbansisters.models;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orders implements Serializable, Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("seller_id")
    @Expose
    private String sellerId;
    @SerializedName("buyer_id")
    @Expose
    private String buyerId;
    public final static Parcelable.Creator<Orders> CREATOR = new Creator<Orders>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Orders createFromParcel(Parcel in) {
            return new Orders(in);
        }

        public Orders[] newArray(int size) {
            return (new Orders[size]);
        }

    }
            ;
    private final static long serialVersionUID = 189446828007666563L;

    protected Orders(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.sellerId = ((String) in.readValue((String.class.getClassLoader())));
        this.buyerId = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Orders() {
    }

    /**
     *
     * @param image
     * @param sellerId
     * @param name
     * @param category
     * @param buyerId
     * @param status
     */
    public Orders(String name, String image, String category, String status, String sellerId, String buyerId) {
        super();
        this.name = name;
        this.image = image;
        this.category = category;
        this.status = status;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(image);
        dest.writeValue(category);
        dest.writeValue(status);
        dest.writeValue(sellerId);
        dest.writeValue(buyerId);
    }

    public int describeContents() {
        return 0;
    }

}