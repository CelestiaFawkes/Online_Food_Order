package Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		Address shipping = order.getDeliveryAddress();
		Address saveAddress = addressRepository.save(shipping);
		if(!user.getAddresses().contains(saveAddress)) {
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
		for(CartItems cartItem : cart.getItem())
		{
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
		resturant.getOrders().add(saveOrder);;
		
		return createdOrder;
	}

	@Override
	public Order updatePrder(Long OrderId, String OrderStatus) throws Exception {
		Order order = findOrderById(OrderId);
		if(OrderStatus.equals("OUT_FOR_DELIVERY")
				|| OrderStatus.equals("DELIVERED")
	            || OrderStatus.equals("COMPLETED")
	            || OrderStatus.equals("PENDING")) {
			order.setOrderStatus(OrderStatus);
			return orderRepository.save(order);
		}
		throw new Exception("Please select valid order status");
	}

	@Override
	public void cancelOrder(Long OrderId) throws Exception {
		
		Order order = findOrderById(OrderId);
		orderRepository.deleteById(OrderId);
		
	}

	private Order findOrderById(Long orderId) throws Exception {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		if(optionalOrder.isEmpty())
		{
			throw new Exception("Order not found");
		}
		
		return optionalOrder.get();
	}

	@Override
	public List<Order> getUserOrders(Long userId) throws Exception {
		return orderRepository.findByCustomerId(userId);
	}

	@Override
	public List<Order> getResturantOrder(Long resturantId, String OrderStatus) throws Exception {
		List<Order> Orders = orderRepository.findByResturantId(resturantId);
		if(OrderStatus!=null)
		{
			Orders = Orders.stream().filter(Order -> Order.getOrderStatus()
					.equals(OrderStatus))
					.collect(Collectors.toList());
		}
		
		return Orders;
	}

}
