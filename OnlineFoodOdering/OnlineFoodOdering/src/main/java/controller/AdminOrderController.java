package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Model.Order;
import Model.User;
import Service.OrderService;
import Service.userService;
import request.OrderRequest;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

	private static final Logger logger = LoggerFactory.getLogger(AdminOrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private userService UserService;

	@GetMapping("/orders/resturant/{id}")
	public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long id,
			@RequestParam(required = false) String Order_status, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("Fetching order history for restaurant with ID '{}' and status '{}'", id, Order_status);

		List<Order> order = orderService.getResturantOrder(id, Order_status);

		logger.info("Fetched {} orders for restaurant with ID '{}'", order.size(), id);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

	@GetMapping("/orders/{id}/{order_status}")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @PathVariable String order_status,
			@RequestHeader("Authorization") String jwt) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		logger.info("Updating status for order with ID '{}' to '{}'", id, order_status);

		Order order = orderService.updatePrder(id, order_status);

		logger.info("Updated status successfully for order with ID '{}'", id);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
}
