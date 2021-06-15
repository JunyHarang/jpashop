package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    // GeneratedValue는 DB에 시퀀스 넘버를 만들어 주는 것
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded   // 내장 타입이라는 의미
    private Address address;

    //한명이 여러개의 상품을 주문 하기 때문에 일대다 관계
    @OneToMany(mappedBy = "member")     // order Table에 있는 Member Field에 의해 Mapping 되었다는 의미
    private List<Order> orders = new ArrayList<>();
} // Class 끝
