package com.natorigatto.course.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.natorigatto.course.entities.Order;
import com.natorigatto.course.entities.OrderItem;
import com.natorigatto.course.entities.Product;
import com.natorigatto.course.entities.User;
import com.natorigatto.course.entities.enums.OrderStatus;
import com.natorigatto.course.repositories.OrderItemRepository;
import com.natorigatto.course.repositories.OrderRepository;
import com.natorigatto.course.repositories.ProductRepository;
import com.natorigatto.course.repositories.UserRepository;
import com.natorigatto.course.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Order findById(Long id) {
		Optional<Order> order = orderRepository.findById(id);
		return order.get();
	}

	public Order insert(Order order) {

		// Buscar e vincular o User (Client) ao pedido
		User client = userRepository.findById(order.getClient().getId())
				.orElseThrow(() -> new ResourceNotFoundException(order.getClient().getId()));
		order.setClient(client);
		
		order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
		order.setMoment(Instant.now());

		// Salvar o pedido primeiro para garantir o ID
		Order savedOrder = orderRepository.save(order);

		// Iterar sobre os OrderItems para associar ao Product
		for (OrderItem item : order.getItems()) {
			Product product = productRepository.findById(item.getProduct().getId())
					.orElseThrow(() -> new ResourceNotFoundException(item.getProduct().getId()));

			item.setProduct(product);
			item.setOrder(order);
			item.setPrice(product.getPrice());

			orderItemRepository.save(item);

		}

		return savedOrder;
	}
	
}
