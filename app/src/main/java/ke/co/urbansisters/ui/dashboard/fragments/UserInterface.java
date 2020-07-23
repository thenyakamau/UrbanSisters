package ke.co.urbansisters.ui.dashboard.fragments;

import ke.co.urbansisters.models.Product;

public interface UserInterface {
    void logOut();
    void goToDescription(int position);
    void addToCart(Product product);
}
