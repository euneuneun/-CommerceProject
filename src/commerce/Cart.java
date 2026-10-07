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

    // 특정 상품을 장바구니에서 제거한다 (관리자가 상품을 삭제했을 때 사용)
    public boolean remove(Product product){
        return items.removeIf(item -> item.getProduct() == product);
    }

    // 상품명으로 장바구니에서 제거한다. 제거된 상품이 있으면 true
    public boolean removeByName(String productName){
        // stream.filter로 해당 이름이 아닌 상품만 남긴다
        List<CartItem> remaining = items.stream()
                .filter(item -> !item.getProduct().getName().equals(productName))
                .toList();

        boolean removed = remaining.size() != items.size();
        items.clear();
        items.addAll(remaining);
        return removed;
    }
}
