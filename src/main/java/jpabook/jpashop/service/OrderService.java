package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) { /* 주문하기 기능 */
        // Entity 조회
        Member member = memberRepository.findOne(memberId);

        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();

        // 회원 정보에 있는 주소를 가지고, 배송 주소에 입력
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    } // order 끝

    @Transactional
    public void cancelOrder(Long orderId) { /* 주문 취소 기능 */
        // 주문 객체(Entity) 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        order.cancel();

    } // cancelOrder 끝


//    public List<Order> findOrders(OrderSearch orderSearch) { /* 상품 검색 기능 */
//
//    } // findOrders 끝
} // Class 끝
