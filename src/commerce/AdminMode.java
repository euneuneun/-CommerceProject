package commerce;

import java.util.List;
import java.util.Scanner;

// 관리자 모드: 관리자 인증과 상품 추가/수정/삭제, 전체 상품 현황을 담당한다
public class AdminMode {
    private static final String PASSWORD = "admin123";
    private static final int MAX_ATTEMPTS = 3;

    private final List<Category> categories;
    private final Cart cart;

    public AdminMode(List<Category> categories, Cart cart){
        this.categories = categories;
        this.cart = cart;
    }

    public void start(Scanner scanner){
        if (!authenticate(scanner)){
            return;
        }

        while (true){
            printMenu();
            System.out.print("번호를 입력하세요: ");

            try{
                int input = Integer.parseInt(scanner.nextLine());

                switch (input){
                    case 0 -> {
                        System.out.println("메인으로 돌아갑니다.");
                        return;
                    }
                    case 1 -> addProduct(scanner);
                    case 2 -> updateProduct(scanner);
                    case 3 -> deleteProduct(scanner);
                    case 4 -> printAllProducts();
                    default -> System.out.println("올바른 번호를 입력해주세요.");
                }
            }catch (NumberFormatException e){
                System.out.println("숫자만 입력해주세요.");
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    // 비밀번호를 최대 3번까지 입력받는다
    private boolean authenticate(Scanner scanner){
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++){
            System.out.print("관리자 비밀번호를 입력해주세요: ");
            if (PASSWORD.equals(scanner.nextLine())){
                return true;
            }
            System.out.printf("비밀번호가 틀렸습니다. (%d/%d)%n", attempt, MAX_ATTEMPTS);
        }
        System.out.printf("비밀번호를 %d회 틀려 메인 메뉴로 돌아갑니다.%n", MAX_ATTEMPTS);
        return false;
    }

    private void printMenu(){
        System.out.println();
        System.out.println("[ 관리자 모드 ]");
        System.out.println("1. 상품 추가");
        System.out.println("2. 상품 수정");
        System.out.println("3. 상품 삭제");
        System.out.println("4. 전체 상품 현황");
        System.out.println("0. 메인으로 돌아가기");
    }

    private void addProduct(Scanner scanner){
        System.out.println();
        System.out.println("어느 카테고리에 상품을 추가하시겠습니까?");
        for (int i = 0; i < categories.size(); i++){
            System.out.printf("%d. %s%n", i + 1, categories.get(i).getName());
        }
        System.out.print("번호를 입력하세요: ");
        int input = Integer.parseInt(scanner.nextLine());

        if (input < 1 || input > categories.size()){
            System.out.println("올바른 번호를 입력해주세요.");
            return;
        }
        Category category = categories.get(input - 1);

        System.out.println();
        System.out.printf("[ %s 카테고리에 상품 추가 ]%n", category.getName());
        System.out.print("상품명을 입력해주세요: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()){
            System.out.println("상품명은 비워둘 수 없습니다.");
            return;
        }
        // 나머지 정보를 다 입력받기 전에 중복부터 확인한다
        if (category.hasProduct(name)){
            System.out.println("이미 같은 이름의 상품이 있습니다: " + name);
            return;
        }

        System.out.print("가격을 입력해주세요: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("상품 설명을 입력해주세요: ");
        String description = scanner.nextLine();
        System.out.print("재고수량을 입력해주세요: ");
        int stock = Integer.parseInt(scanner.nextLine());

        // 가격이나 재고가 음수면 Product 생성자에서 예외가 발생한다
        Product product = new Product(name, price, description, stock);

        System.out.println();
        System.out.printf("%s | 재고: %d개%n", product, product.getStock());
        System.out.println("위 정보로 상품을 추가하시겠습니까?");
        if (!confirm(scanner)){
            System.out.println("상품 추가를 취소했습니다.");
            return;
        }

        category.addProduct(product);
        System.out.println("상품이 성공적으로 추가되었습니다!");
    }

    private void updateProduct(Scanner scanner){
        System.out.println();
        System.out.print("수정할 상품명을 입력해주세요: ");
        String name = scanner.nextLine().trim();

        Category category = findCategoryContaining(name);
        if (category == null){
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }
        Product product = category.findProduct(name);
        System.out.printf("현재 상품 정보: %s | 재고: %d개%n", product, product.getStock());

        System.out.println();
        System.out.println("수정할 항목을 선택해주세요:");
        System.out.println("1. 가격");
        System.out.println("2. 설명");
        System.out.println("3. 재고수량");
        System.out.print("번호를 입력하세요: ");
        int input = Integer.parseInt(scanner.nextLine());

        switch (input){
            case 1 -> {
                int before = product.getPrice();
                System.out.printf("현재 가격: %,d원%n", before);
                System.out.print("새로운 가격을 입력해주세요: ");
                product.setPrice(Integer.parseInt(scanner.nextLine()));
                System.out.printf("%s의 가격이 %,d원 → %,d원으로 수정되었습니다.%n",
                        product.getName(), before, product.getPrice());
            }
            case 2 -> {
                String before = product.getDescription();
                System.out.printf("현재 설명: %s%n", before);
                System.out.print("새로운 설명을 입력해주세요: ");
                product.setDescription(scanner.nextLine());
                System.out.printf("%s의 설명이 수정되었습니다. (%s → %s)%n",
                        product.getName(), before, product.getDescription());
            }
            case 3 -> {
                int before = product.getStock();
                System.out.printf("현재 재고: %d개%n", before);
                System.out.print("새로운 재고수량을 입력해주세요: ");
                product.setStock(Integer.parseInt(scanner.nextLine()));
                System.out.printf("%s의 재고가 %d개 → %d개로 수정되었습니다.%n",
                        product.getName(), before, product.getStock());
            }
            default -> System.out.println("올바른 번호를 입력해주세요.");
        }
    }

    private void deleteProduct(Scanner scanner){
        System.out.println();
        System.out.print("삭제할 상품명을 입력해주세요: ");
        String name = scanner.nextLine().trim();

        Category category = findCategoryContaining(name);
        if (category == null){
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }
        Product product = category.findProduct(name);

        System.out.printf("%s | 재고: %d개%n", product, product.getStock());
        System.out.printf("%s 카테고리에서 위 상품을 삭제하시겠습니까?%n", category.getName());
        if (!confirm(scanner)){
            System.out.println("상품 삭제를 취소했습니다.");
            return;
        }

        category.removeProduct(product);
        // 삭제된 상품이 장바구니에 있으면 장바구니에서도 제거한다
        if (cart.remove(product)){
            System.out.println("장바구니에 담겨 있던 상품도 함께 제거되었습니다.");
        }
        System.out.printf("%s 상품이 삭제되었습니다.%n", product.getName());
    }

    private void printAllProducts(){
        System.out.println();
        System.out.println("[ 전체 상품 현황 ]");
        for (Category category : categories){
            System.out.printf("%n[ %s ]%n", category.getName());
            List<Product> products = category.getProducts();
            if (products.isEmpty()){
                System.out.println("등록된 상품이 없습니다.");
                continue;
            }
            for (Product product : products){
                System.out.printf("- %s | 재고: %d개%n", product, product.getStock());
            }
        }
    }

    // 상품명이 들어 있는 카테고리를 찾는다 (없으면 null)
    private Category findCategoryContaining(String productName){
        return categories.stream()
                .filter(category -> category.hasProduct(productName))
                .findFirst()
                .orElse(null);
    }

    // "1. 확인 2. 취소"를 입력받아 확인이면 true, 취소면 false를 반환한다
    private boolean confirm(Scanner scanner){
        System.out.println("1. 확인    2. 취소");
        System.out.print("번호를 입력하세요: ");
        int input = Integer.parseInt(scanner.nextLine());

        if (input == 1) return true;
        if (input == 2) return false;
        throw new IllegalArgumentException("올바른 번호를 입력해주세요.");
    }
}
