package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.Cart;
import Model.CartItems;
import Model.User;
import Repository.CartRepository;
import Service.CartService;
import Service.userService;
import request.AddCartItemRequest;
import request.UpdateCartItemRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private userService UserService;

	@PutMapping("/add/cart")
	public ResponseEntity<CartItems> addItemToCart(@RequestBody AddCartItemRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {
		logger.info("Adding item to cart for user with JWT: {}", jwt);

		CartItems cartItem = cartService.addItemToCart(req, jwt);
		return new ResponseEntity<>(cartItem, HttpStatus.OK);
	}

	@PutMapping("/cart-item/update")
	public ResponseEntity<CartItems> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception {
		logger.info("Updating cart item quantity with cartItemId: {} for user with JWT: {}", req.getCartItemId(), jwt);

		CartItems cartItem = cartService.updateCartItemsQuantity(req.getCartItemId(), req.getQuantity());
		return new ResponseEntity<>(cartItem, HttpStatus.OK);
	}

	@PutMapping("/cart-item/{id}/remove")
	public ResponseEntity<Cart> removeCartItem(@PathVariable Long id, @RequestHeader("Authorization") String jwt)
			throws Exception {
		logger.info("Removing cart item with id: {} for user with JWT: {}", id, jwt);

		Cart cartItem = cartService.removeItemsFromcart(id, jwt);
		return new ResponseEntity<>(cartItem, HttpStatus.OK);
	}

	@PutMapping("/cart/clear")
	public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
		logger.info("Clearing cart for user with JWT: {}", jwt);

		Cart cart = cartService.clearCart(jwt);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@GetMapping("/cart/user")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
		logger.info("Fetching cart for user with JWT: {}", jwt);

		User user = UserService.findUserByJwtToken(jwt);
		Cart cart = cartService.findCartByUserId(user.getId());
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
}
