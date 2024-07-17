package controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.Order;
import Model.User;
import Service.OrderService;
import Service.userService;
import request.OrderRequest;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private userService UserService;

	@PostMapping("/orders")
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("Creating order for user with ID: {}", user.getId());
		Order order = orderService.createOrder(req, user);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@GetMapping("/orders/user")   
	public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("Fetching order history for user with ID: {}", user.getId());
		List<Order> orders = orderService.getUserOrders(user.getId());
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
}
