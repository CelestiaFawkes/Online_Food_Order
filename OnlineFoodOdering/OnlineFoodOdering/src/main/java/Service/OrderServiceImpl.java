package Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.Address;
import Model.Cart;
import Model.CartItems;
import Model.Order;
import Model.Orderitems;
import Model.Resturant;
import Model.User;
import Repository.AddressRepository;
import Repository.OrderItemRepository;
import Repository.OrderRepository;
import Repository.ResturantRepository;
import Repository.UserRepository;
import request.OrderRequest;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ResturantRepository resturantRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ResturantService resturantService;

	@Override
	public Order createOrder(OrderRequest order, User user) throws Exception {
		logger.debug("Creating order for user '{}'", user.getEmail());

		Address shipping = order.getDeliveryAddress();
		Address saveAddress = addressRepository.save(shipping);
		if (!user.getAddresses().contains(saveAddress)) {
			user.getAddresses().add(saveAddress);
			userRepository.save(user);
		}

		Resturant resturant = resturantService.findResturantById(order.getResturantId());
		Order createdOrder = new Order();
		createdOrder.setCustomer(user);
		createdOrder.setCreatedAt(new Date());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.setDeliveryAddress(saveAddress);
		createdOrder.setResturant(resturant);
		Cart cart = cartService.findCartByUserId(user.getId());
		List<Orderitems> orderItems = new ArrayList<>();
		for (CartItems cartItem : cart.getItem()) {
			Orderitems orderItem = new Orderitems();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngredients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalItems(cartItem.getTotalprice());
			Orderitems savedOrderItems = orderItemRepository.save(orderItem);
			orderItems.add(savedOrderItems);
		}

		createdOrder.setItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotal());
		Order saveOrder = orderRepository.save(createdOrder);
		resturant.getOrders().add(saveOrder);

		logger.info("Order created successfully for user '{}'", user.getEmail());
		return createdOrder;
	}

	@Override
	public Order updatePrder(Long OrderId, String OrderStatus) throws Exception {
		logger.debug("Updating order status for order ID '{}'", OrderId);

		Order order = findOrderById(OrderId);
		if (OrderStatus.equals("OUT_FOR_DELIVERY") || OrderStatus.equals("DELIVERED") || OrderStatus.equals("COMPLETED")
				|| OrderStatus.equals("PENDING")) {
			order.setOrderStatus(OrderStatus);
			Order updatedOrder = orderRepository.save(order);

			logger.info("Order status updated successfully for order ID '{}'", OrderId);
			return updatedOrder;
		}
		throw new Exception("Please select valid order status");
	}

	@Override
	public void cancelOrder(Long OrderId) throws Exception {
		logger.debug("Cancelling order for order ID '{}'", OrderId);

		Order order = findOrderById(OrderId);
		orderRepository.deleteById(OrderId);

		logger.info("Order cancelled successfully for order ID '{}'", OrderId);
	}

	private Order findOrderById(Long orderId) throws Exception {
		logger.debug("Fetching order with ID '{}'", orderId);

		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		if (optionalOrder.isEmpty()) {
			logger.error("Order not found with ID '{}'", orderId);
			throw new Exception("Order not found");
		}

		Order order = optionalOrder.get();
		logger.info("Order fetched successfully with ID '{}'", orderId);
		return order;
	}

	@Override
	public List<Order> getUserOrders(Long userId) throws Exception {
		logger.debug("Fetching orders for user with ID '{}'", userId);

		List<Order> orders = orderRepository.findByCustomerId(userId);

		logger.info("Fetched {} orders for user with ID '{}'", orders.size(), userId);
		return orders;
	}

	@Override
	public List<Order> getResturantOrder(Long resturantId, String OrderStatus) throws Exception {
		logger.debug("Fetching orders for restaurant with ID '{}' and status '{}'", resturantId, OrderStatus);

		List<Order> orders = orderRepository.findByResturantId(resturantId);
		if (OrderStatus != null) {
			orders = orders.stream().filter(order -> order.getOrderStatus().equals(OrderStatus))
					.collect(Collectors.toList());
		}

		logger.info("Fetched {} orders for restaurant with ID '{}' and status '{}'", orders.size(), resturantId,
				OrderStatus);
		return orders;
	}

}
