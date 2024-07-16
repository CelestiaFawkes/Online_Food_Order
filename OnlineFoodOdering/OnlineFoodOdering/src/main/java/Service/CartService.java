package Service;

import Model.Cart;
import Model.CartItems;
import request.AddCartItemRequest;

public interface CartService {
	
	public CartItems addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
	
	public CartItems updateCartItemsQuantity(long cartItemId,int quantity ) throws Exception;
	
	public Cart removeItemsFromcart(Long cartItemId, String jwt) throws Exception;
	
	public Long calculateCartItems(Cart cart) throws Exception;
	
	public Cart findCartById(Long id) throws Exception;
	
	public Cart findCartByUserId(Long userId)throws Exception;
	
	public Cart clearCart(String jwt) throws Exception;

	
	

}
