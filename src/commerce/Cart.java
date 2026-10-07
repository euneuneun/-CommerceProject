package commerce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void add(Product product, int quantity){
        for (CartItem item:items){
            if(item.getProduct().getName().equals(product.getName())){
                item.addQuantity(quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public int getQuantityOf(Product product){
        return items.stream()
                .filter(item -> item.getProduct().getName().equals(product.getName()))
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public int getTotalQuantity(){
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public int getTotalPrice(){
        return items.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();
    }

    public boolean isEmpty() {return items.isEmpty();}

    public List<CartItem> getItems() { return Collections.unmodifiableList(items);}

    public void clear(){items.clear();}
}
