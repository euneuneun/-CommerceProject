package commerce.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Category {

    private final String name;
    private final List<Product> products;

    public Category(String name, List<Product> products){
        this.name = name;
        // Arrays.asList로 받은 리스트는 크기가 고정이라 추가/삭제가 안 되므로 복사해서 저장한다
        this.products = new ArrayList<>(products);
    }

    public String getName(){
        return name;
    }

    public List<Product> getProducts(){
        return Collections.unmodifiableList(products);
    }

    // 조건(람다)에 맞는 상품만 골라서 반환한다
    public List<Product> filterProducts(Predicate<Product> condition){
        return products.stream()
                .filter(condition)
                .toList();
    }

    // 이 카테고리 안에 같은 이름의 상품이 있는지 확인한다
    public boolean hasProduct(String productName){
        return products.stream()
                .anyMatch(p -> p.getName().equals(productName));
    }

    // 이름으로 상품을 찾는다 (없으면 null)
    public Product findProduct(String productName){
        return products.stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    // 상품 추가는 이 메서드로만 가능하다 (같은 카테고리 내 중복 상품명 검증)
    public void addProduct(Product product){
        if (hasProduct(product.getName())){
            throw new IllegalArgumentException("이미 같은 이름의 상품이 있습니다: " + product.getName());
        }
        products.add(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }
}
