package com.natorigatto.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.natorigatto.course.entities.OrderItem;
import com.natorigatto.course.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
