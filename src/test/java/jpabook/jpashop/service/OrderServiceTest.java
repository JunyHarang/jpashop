// Copyright 주니하랑ⓒ 2021. All Rights Reserved.
// Email - junyharang8592@gmail.com
// Blog - https://junyharang.tistory.com/
// GitHug - https://github.com/JunyHarang

package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;


    @Test
    public void 상품주문() throws Exception {

        // given → 테스트를 준비하는 과정 (~가 주어지고)
        Member member = createMember();

        // 이름, 가격, 재고
        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        // when → 실제로 테스트를 실행하는 과정 (~을 했을 때)
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then → 테스트를 검증하는 과정 (~한 값이 나와야 함.)
        Order getOrder = orderRepository.findOne(orderId);

        Assert.assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        Assert.assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        Assert.assertEquals("주문 가격은 가격 곱하기 수량이다.", 10000 * 2, getOrder.getTotalPrice() );
        Assert.assertEquals("주문 시 주문 수량만큼 재고가 줄어야 한다.", 8, item.getStockQuantity());

    } // Method 끝


    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception  {

        // given → 테스트를 준비하는 과정 (~가 주어지고)
        Member member = createMember();

        Item item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;
        // when → 실제로 테스트를 실행하는 과정 (~을 했을 때)
        orderService.order(member.getId(), item.getId(), orderCount);

        // then → 테스트를 검증하는 과정 (~한 값이 나와야 함.)
        fail("재고 수량 부족 예외가 발생해야 합니다.");

    } // Method 끝

    @Test
    public void 주문취소() throws Exception {

        // given → 테스트를 준비하는 과정 (~가 주어지고)
        Member member = createMember();
        Book item =  createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when → 실제로 테스트를 실행하는 과정 (~을 했을 때)
        orderService.cancelOrder(orderId);

        // then → 테스트를 검증하는 과정 (~한 값이 나와야 함.)
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소 시 상태는 취소가 되어야 합니다!", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품의 재고는 다시 원복되어야 합니다!", 10, item.getStockQuantity());

    } // Method 끝

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "서초대로", "123-123"));

        em.persist(member);

        return member;
    } // createMember 끝


    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);

        em.persist(book);

        return book;
    } // createBook 끝

} // Class 끝