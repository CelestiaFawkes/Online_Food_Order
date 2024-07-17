package Service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import Model.Cart;
import Model.CartItems;
import Model.Food;
import Model.User;
import Repository.CartItemRepository;
import Repository.CartRepository;
import Repository.FoodRepository;
import request.AddCartItemRequest;

public class CartServiceImp implements CartService {

	private static final Logger logger = LoggerFactory.getLogger(CartServiceImp.class);

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private userService UserService;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private FoodService foodService;

	@Override
	public CartItems addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		Food food = foodService.findFoodById(req.getFoodId());
		Cart cart = cartRepository.findByCustomerId(user.getId());

		for (CartItems cartItem : cart.getItem()) {
			if (cartItem.getFood().equals(food)) {
				int newQuantity = cartItem.getQuantity() + req.getQuantity();
				return updateCartItemsQuantity(cartItem.getId(), newQuantity);
			}
		}

		CartItems newCartItems = new CartItems();
		newCartItems.setFood(food);
		newCartItems.setCart(cart);
		newCartItems.setQuantity(req.getQuantity());
		newCartItems.setIngredients(req.getIngredients());
		newCartItems.setTotalprice(req.getQuantity() * food.getPrice());
		CartItems savedCartItem = cartItemRepository.save(newCartItems);
		cart.getItem().add(savedCartItem);

		logger.info("Item {} added to cart for user {}", food.getName(), user.getId());
		return savedCartItem;
	}

	@Override
	public CartItems updateCartItemsQuantity(long cartItemId, int quantity) throws Exception {
		Optional<CartItems> cartItemOptional = cartItemRepository.findById(cartItemId);
		if (cartItemOptional.isEmpty()) {
			throw new Exception("Cart item not found");
		}
		CartItems item = cartItemOptional.get();
		item.setQuantity(quantity);
		item.setTotalprice(item.getFood().getPrice() * quantity);
		CartItems updatedItem = cartItemRepository.save(item);

		logger.info("Cart item {} quantity updated to {}", item.getId(), quantity);
		return updatedItem;
	}

	@Override
	public Cart removeItemsFromcart(Long cartItemId, String jwt) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		Cart cart = cartRepository.findByCustomerId(user.getId());
		Optional<CartItems> cartItemOptional = cartItemRepository.findById(cartItemId);
		if (cartItemOptional.isEmpty()) {
			throw new Exception("Cart item not found");
		}
		CartItems item = cartItemOptional.get();
		cart.getItem().remove(item);

		logger.info("Item {} removed from cart for user {}", item.getFood().getName(), user.getId());
		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartItems(Cart cart) throws Exception {
		Long total = 0L;
		for (CartItems cartItem : cart.getItem()) {
			total += cartItem.getTotalprice() * cartItem.getQuantity();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> cartOptional = cartRepository.findById(id);
		if (cartOptional.isEmpty()) {
			throw new Exception("Cart not found with id: " + id);
		}
		return cartOptional.get();
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		Optional<Cart> cartOptional = cartRepository.findById(userId);
		if (cartOptional.isEmpty()) {
			throw new Exception("Cart not found with user id: " + userId);
		}
		return cartOptional.get();
	}

	@Override
	public Cart clearCart(String jwt) throws Exception {
		User user = UserService.findUserByJwtToken(jwt);
		Cart cart = findCartByUserId(user.getId());
		cart.getItem().clear();

		logger.info("Cart cleared for user {}", user.getId());
		return cartRepository.save(cart);
	}
}
