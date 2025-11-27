
package tests;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutFlowTest extends BaseTest {

    @Parameters({"searchTerm", "email", "name", "address"})
    @Test(enabled = false , description = "Search product -> Add to cart -> Checkout as guest")
    public void e2eCheckoutFlow(String searchTerm, String email, String name, String address) {
        HomePage home = new HomePage();
        home.search(searchTerm);

        SearchResultsPage results = new SearchResultsPage();
        results.openFirstProduct();

        ProductPage product = new ProductPage();
        product.addToCart();
        product.goToCart();

        CartPage cart = new CartPage();
        cart.proceedToCheckout();

        CheckoutPage checkout = new CheckoutPage();
        checkout.fillGuestCheckout(email, name, address);
        checkout.placeOrder();

        Assert.assertTrue(checkout.isSuccess(), "Order success message not shown");
    }
}
