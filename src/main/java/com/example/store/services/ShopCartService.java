package com.example.store.services;

import com.example.store.entity.CartItem;
import com.example.store.entity.Good;
import com.example.store.entity.ShoppingCart;
import com.example.store.entity.User;
import com.example.store.repository.CartItemRepo;
import com.example.store.repository.GoodRepo;
import com.example.store.repository.ShopCartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ShopCartService {
    @Autowired
    private ShopCartRepo shopCartRepo;
    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private GoodRepo goodRepo;

    @Autowired
    private MainService mainService;

    @Autowired
    private GoodService goodService;

    public ShoppingCart addShoppingCart(Long id_good, String sessionToken, Integer count, Principal principal){
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setCount(count);
        cartItem.setDate(new Date());
        cartItem.setGood(goodService.getGoodById(id_good));
        cartItem.setUser(mainService.getUserByPrincipal(principal));
        shoppingCart.getItems().add(cartItem);
        shoppingCart.setSessionToken(sessionToken);
        shoppingCart.setDate(new Date());
        return shopCartRepo.save(shoppingCart);
    }

    public ShoppingCart addToExistingShoppingCart(Long id_good, String sessionToken, Integer count, Principal principal){
        ShoppingCart shoppingCart = shopCartRepo.findBySessionToken(sessionToken);
        Good g = goodService.getGoodById(id_good);
        User u = mainService.getUserByPrincipal(principal);
        Boolean productDoesExistInTheCart = false;
        if (shoppingCart != null) {
            Set<CartItem> items = shoppingCart.getItems();
            for (CartItem item : items) {
                if (item.getGood().equals(g)) {
                    productDoesExistInTheCart = true;
                    item.setCount(item.getCount() + count);
                    shoppingCart.setItems(items);
                    return shopCartRepo.saveAndFlush(shoppingCart);
                }

            }
        }
        if(!productDoesExistInTheCart && (shoppingCart != null))
        {
            CartItem cartItem1 = new CartItem();
            cartItem1.setDate(new Date());
            cartItem1.setCount(count);
            cartItem1.setGood(g);
            cartItem1.setUser(u);
            shoppingCart.getItems().add(cartItem1);
            return shopCartRepo.saveAndFlush(shoppingCart);
        }

        return this.addShoppingCart(id_good, sessionToken, count, principal);
    }

    public ShoppingCart getShoppingCartBySessionToken(String sessionToken) {

        return shopCartRepo.findBySessionToken(sessionToken);
    }

    public void updateShoppingCartItem(Integer id, Integer count) {
        CartItem cartItem = cartItemRepo.findById(id).get();
        cartItem.setCount(count);
        cartItemRepo.saveAndFlush(cartItem);
    }

    public ShoppingCart removeCartIemFromShoppingCart(Integer id, String sessionToken) {
        ShoppingCart shoppingCart = shopCartRepo.findBySessionToken(sessionToken);
        Set<CartItem> items = shoppingCart.getItems();
        CartItem cartItem = null;
        for(CartItem item : items) {
            if(item.getId().equals(id)) {
                cartItem = item;
            }
        }
        items.remove(cartItem);
        //cartItemRepo.delete(cartItem);
        shoppingCart.setItems(items);
        return shopCartRepo.save(shoppingCart);
    }

    /*public List<ShoppingCart> listCartItem (User user){
        return shopCartRepo.findByUser(user);
    }*/

    /*public Integer addGoodToCart (Long goodId, Integer count, User user){
        Integer addedCount = count;
        Good good = goodRepo.findById(goodId).get();

        ShoppingCart shoppingCart = shopCartRepo.findByGoodAndUser(user, good);

        if( shoppingCart != null ){
            addedCount = shoppingCart.getCount() + count;
            shoppingCart.setCount(addedCount);
        } else {
            shoppingCart = new ShoppingCart();
            shoppingCart.setCount(count);
            shoppingCart.setUser(user);
            shoppingCart.setGood(good);
        }

        shopCartRepo.save(shoppingCart);

        return addedCount;
    }*/
}
