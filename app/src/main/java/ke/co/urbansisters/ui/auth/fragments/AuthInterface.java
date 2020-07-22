package ke.co.urbansisters.ui.auth.fragments;

import ke.co.urbansisters.models.User;

public interface AuthInterface {
    void goToLogin();
    void goToRegister();
    void goToBuyerDashBoard(User user);
    void goToSellerDashBoard(User user);
}
