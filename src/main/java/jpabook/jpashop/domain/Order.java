package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")     // Table명 선언
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // order와 Member는 다대일 관계
    @ManyToOne(fetch = LAZY)
    // FK에 대한 설정
    @JoinColumn(name = "member_id")
    private Member member;

    // casecade = CascadeType.ALL이란?
    // OrderItems에다가 Data만 넣어두고 Order를 저장하면 OrderItems List도 같이 저장되게 만드는 것
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;        // 주문 시간

    private OrderStatus status; // 주문상태 [Order / Cancle]
    
    // 연관관계 Method Start
    public void setMember(Member member) {
        this.member = member;

        member.getOrders().add(this);

        /* 위의 내용은 아래와 같다.
            public static void main(String[] args) {
                Member member = new Member();
                Order order = new Order();

                member.getOrders().add(order);
                order.setMember(member);
            }
         */
    } // setMember 끝

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);

        orderItem.setOrder(this);
    } // addOrderITem 끝

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    } // setDelivery 끝
    
    // == 연관관계 Method 끝

}// Class 끝
