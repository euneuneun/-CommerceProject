package commerce.product;

public class Product {
    private String name;    // 상품명
    private int price;  // 가격
    private String description; // 설명
    private int stock;

    // 생성자: 객체를 만들 때 값을 채워넣는 통로
    public Product(String name, int price, String description, int stock){
        this.name = name;
        validatePrice(price);
        validateStock(stock);
        this.price = price;
        this.description = description;
        this.stock = stock;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }
    public int getStock() { return stock; }

    public void setPrice(int price) {
        validatePrice(price);
        this.price = price;
    }

    public void setStock(int stock) {
        validateStock(stock);
        this.stock = stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static void validatePrice(int price) {
        if (price < 0) throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
    }

    private static void validateStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("재고는 0 이상이어야 합니다.");
    }

    public boolean hasEnoughStock(int quantity){
        return stock>=quantity;
    }

    public void decreaseStock(int quantity){
        if(stock<quantity)
            throw new IllegalArgumentException("재고가 부족합니다: "+name);
        this.stock -= quantity;
    }

    // 상품 정보를 "이름 | 가격 | 설명" 형식으로 표현한다
    @Override
    public String toString() {
        return String.format("%s | %,d원 | %s", name, price, description);
    }
}
