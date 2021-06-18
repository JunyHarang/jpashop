package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughtStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 비즈니스 로직

    /**
     * stock (제고) 증가
     * @param quantity
     */
    
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    } // addStock 끝

    /**
     * stock (제고) 감소
     * @param quantity
     */

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) {
            throw new NotEnoughtStockException("제고 수량이 0보다 작을 수 없습니다!");
        }

        this.stockQuantity = restStock;
    } // removeStock 끝

} // Class 끝
