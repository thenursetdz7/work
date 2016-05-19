package camt.se331.shoppingcart.service;

import camt.se331.shoppingcart.dao.ShoppingCartDao;
import camt.se331.shoppingcart.entity.Product;
import camt.se331.shoppingcart.entity.SelectedProduct;
import camt.se331.shoppingcart.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Dto on 4/6/2015.
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartDao shoppingCartDao;

    @Override
    @Transactional
    public ShoppingCart findById(Long id) {
        return shoppingCartDao.findById(id);
    }

    @Override
    public List<ShoppingCart> getShoppingCarts() {
        return null;
    }

    @Override
    public List<ShoppingCart> getShoppingCartBetween(Date stateDate, Date stopDate) {
        return null;
    }

    @Override
    public ShoppingCart addShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartDao.addShoppingCart(shoppingCart);
    }

    @Override
    public ShoppingCart deleteShoppingCart(ShoppingCart shoppingCart) {
       return null;
    }

    @Override
    public ShoppingCart addSelectedProduct(ShoppingCart shoppingCart, Product product) {
        // Check whether the product is in the shopping cart or not
        // if there are some, increase the amount of product, otherwise add a new selected product to the cart
        for(SelectedProduct selectedProduct:shoppingCart.getSelectedProducts()){
            if (selectedProduct.getProduct().equals(product)){
                selectedProduct.setAmount(selectedProduct.getAmount()+1);
                return shoppingCart;
            }
        }
        SelectedProduct selectedProduct = new SelectedProduct(product,1);
        shoppingCart.getSelectedProducts().add(selectedProduct);
        return shoppingCart;
    }
}
