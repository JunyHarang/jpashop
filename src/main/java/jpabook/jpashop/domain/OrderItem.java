package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)      // 기본 생성자를 외부에서 함부로 쓸 수 없게 막음

public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 당시 주문 가격

    private int count; // 주문 당시 주문 수량

    public static OrderItem createOrderItem(Item item, int orderPrice, int count) { /* 생성 Method */
        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);

        return orderItem;

    } // createOrderItem 끝

    public void cancel() { /* 비즈니스 로직 */
        // 주문 취소 버튼을 누르면 맨 처음 주문 수량을 다시 원복해줘야 한다.
        getItem().addStock(count);
    } // cancel 끝


    public int getTotalPrice() { /* 조회 로직 (주문 상품 전체 가격 조회) */
        return getOrderPrice() * getCount();
    }

} // Class 끝
