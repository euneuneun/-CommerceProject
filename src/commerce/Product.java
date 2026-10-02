package commerce;

public class Product {
    private String name;    // 상품명
    private int price;  // 가격
    private String desciption; // 설명
    private int stock;

    // 생성자: 객체를 만들 때 값을 채워넣는 통로
    public Product(String name, int price, String desciption, int stock){
        this.name = name;
        this.price = price;
        this.desciption = desciption;
        this.stock = stock;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getDesciption() { return desciption; }
    public int getStock() { return stock; }

    public void setPrice(int price) {
        if (price < 0) throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        this.price = price;
    }

    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("재고는 0 이상이어야 합니다.");
        this.stock = stock;
    }

}
