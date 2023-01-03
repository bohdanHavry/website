package com.example.store.services;

import com.example.store.entity.Good;
import com.example.store.entity.ShoppingCart;
import com.example.store.entity.User;
import com.example.store.repository.GoodRepo;
import com.example.store.repository.ShopCartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCartService {
    @Autowired
    private ShopCartRepo shopCartRepo;

    @Autowired
    private GoodRepo goodRepo;

    public List<ShoppingCart> listCartItem (User user){
        return shopCartRepo.findByUser(user);
    }

    public Integer addGoodToCart (Long goodId, Integer count, User user){
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
    }
}
