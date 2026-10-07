package commerce;

import java.util.List;
import java.util.Scanner;

public class CommerceSystem {
    private static final int CART_MENU = 4;    // 장바구니 확인
    private static final int CANCEL_MENU = 5;  // 주문 취소

    private final List<Category> categories;
    private final Cart cart = new Cart();

    public CommerceSystem(List<Category> categories){
        this.categories = categories;
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            printCategory();
            System.out.print("번호를 입력하세요: ");

            try{
                int input = Integer.parseInt(scanner.nextLine());

                if (input == 0){
                    System.out.println("커머스 플랫폼을 종료합니다.");
                    break;
                }

                if (input == CART_MENU || input == CANCEL_MENU){
                    // 장바구니가 비어 있으면 [ 주문 관리 ] 메뉴가 없으므로 예외를 던진다
                    if (cart.isEmpty()){
                        throw new IllegalArgumentException("장바구니가 비어 있습니다. 올바른 번호를 입력해주세요.");
                    }

                    if (input == CART_MENU){
                        checkCartAndOrder(scanner);
                    } else {
                        cancelOrder();
                    }
                    continue;
                }

                if(input < 1 || input >categories.size()){
                    System.out.println("올바른 번호를 입력해주세요.");
                    continue;
                }

                Category selected = categories.get(input - 1);
                selectProduct(scanner, selected);

            }catch (NumberFormatException e){
                System.out.println("숫자만 입력해주세요.");
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

        }

        scanner.close();
    }

    private void printCategory(){
        System.out.println();
        System.out.println("[ 실시간 커머스 플랫폼 메인 ]");
        for(int j =0; j<categories.size();j++){
            Category p = categories.get(j);
            System.out.printf("%d. %s %n",j+1,p.getName());
        }
        System.out.println("0. 종료         | 프로그램 종료");

        // 장바구니에 상품이 있을 때만 주문 관리 메뉴를 출력한다
        if (!cart.isEmpty()){
            System.out.println();
            System.out.println("[ 주문 관리 ]");
            System.out.printf("%d. 장바구니 확인    | 장바구니를 확인 후 주문합니다.%n", CART_MENU);
            System.out.printf("%d. 주문 취소       | 진행중인 주문을 취소합니다.%n", CANCEL_MENU);
        }
    }

    private void selectProduct(Scanner scanner, Category category){
        List<Product> products = category.getProducts();
        System.out.println();
        System.out.printf("[ %s 카테고리 ]%n", category.getName());
        printProducts(products);
        System.out.print("번호를 입력하세요: ");

        try{
            int input = Integer.parseInt(scanner.nextLine());

            if (input == 0){
                return;
            }

            if(input < 1 || input > products.size()){
                System.out.println("올바른 번호를 입력해주세요.");
                return;
            }

            Product p = products.get(input - 1);
            System.out.printf("선택한 상품: %s | 재고: %d개%n", formatProduct(p), p.getStock());

            askAddToCart(scanner, p);

        }catch (NumberFormatException e){
            System.out.println("숫자만 입력해주세요.");
        }
    }

    private void printProducts(List<Product> products){
        for (int k = 0; k<products.size();k++){
            Product p = products.get(k);
            System.out.printf("%d. %-15s | %,10d원 | %s | 재고: %d개%n",
                    k+1, p.getName(), p.getPrice(), p.getDescription(), p.getStock());
        }

        System.out.println("0. 뒤로가기     | 메인으로 돌아가기");
    }

    // 상품을 장바구니에 담을지 묻고, 재고를 확인한 뒤 담는다
    private void askAddToCart(Scanner scanner, Product p){
        System.out.println();
        System.out.printf("\"%s\"%n", formatProduct(p));
        System.out.println("위 상품을 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
        System.out.print("번호를 입력하세요: ");

        int input = Integer.parseInt(scanner.nextLine());

        if (input == 2){
            System.out.println("장바구니 추가를 취소했습니다.");
            return;
        }

        if (input != 1){
            System.out.println("올바른 번호를 입력해주세요.");
            return;
        }

        // 재고는 주문 확정 때 줄어들기 때문에, 장바구니에 이미 담긴 수량까지 합쳐서 확인한다
        int inCart = cart.getQuantityOf(p);
        if (!p.hasEnoughStock(inCart + 1)){
            System.out.printf("재고가 부족합니다. (재고: %d개, 장바구니에 담긴 수량: %d개)%n", p.getStock(), inCart);
            return;
        }

        cart.add(p, 1);
        System.out.printf("%s가 장바구니에 추가되었습니다.%n", p.getName());
        System.out.println();
        System.out.println("아래 메뉴를 선택해주세요.");
    }

    // 장바구니 내역과 총 금액을 보여주고 주문 여부를 묻는다
    private void checkCartAndOrder(Scanner scanner){
        System.out.println();
        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println();
        System.out.println("[ 장바구니 내역 ]");
        for (CartItem item : cart.getItems()){
            System.out.printf("%s | 수량: %d개%n", formatProduct(item.getProduct()), item.getQuantity());
        }
        System.out.println();
        System.out.println("[ 총 주문 금액 ]");
        System.out.printf("%,d원%n", cart.getTotalPrice());
        System.out.println();
        System.out.println("1. 주문 확정      2. 메인으로 돌아가기");
        System.out.print("번호를 입력하세요: ");

        int input = Integer.parseInt(scanner.nextLine());

        if (input == 2){
            return;
        }

        if (input != 1){
            System.out.println("올바른 번호를 입력해주세요.");
            return;
        }

        placeOrder();
    }

    // 주문 확정: 재고를 차감하고 장바구니를 비운다
    private void placeOrder(){
        // 중간에 재고가 부족해 일부 상품만 차감되는 일이 없도록 먼저 전부 확인한다
        for (CartItem item : cart.getItems()){
            if (!item.getProduct().hasEnoughStock(item.getQuantity())){
                System.out.printf("%s의 재고가 부족해 주문할 수 없습니다.%n", item.getProduct().getName());
                return;
            }
        }

        int totalPrice = cart.getTotalPrice(); // clear() 전에 계산해야 0원이 나오지 않는다
        System.out.printf("주문이 완료되었습니다! 총 금액: %,d원%n", totalPrice);

        for (CartItem item : cart.getItems()){
            Product product = item.getProduct();
            int before = product.getStock(); // "30개 → 29개" 출력을 위해 차감 전 재고를 저장한다
            product.decreaseStock(item.getQuantity());
            System.out.printf("%s 재고가 %d개 → %d개로 업데이트되었습니다.%n",
                    product.getName(), before, product.getStock());
        }

        cart.clear();
    }

    private void cancelOrder(){
        cart.clear();
        System.out.println("진행중인 주문을 취소했습니다. 장바구니를 비웠습니다.");
    }

    // 상품 정보를 "이름 | 가격 | 설명" 형식의 문자열로 만든다
    private String formatProduct(Product p){
        return String.format("%s | %,d원 | %s", p.getName(), p.getPrice(), p.getDescription());
    }
}
