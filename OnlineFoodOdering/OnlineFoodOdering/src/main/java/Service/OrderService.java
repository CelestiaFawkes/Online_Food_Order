package Service;

import java.util.List;

import Model.Order;
import Model.User;
import request.OrderRequest;

public interface OrderService {

	public Order createOrder(OrderRequest order, User user) throws Exception;

	public Order updatePrder(Long OrderId, String OrderStatus) throws Exception;

	public void cancelOrder(Long OrderId) throws Exception;

	public List<Order> getUserOrders(Long userId) throws Exception;

	public List<Order> getResturantOrder(Long resturantId, String OrderStatus) throws Exception;

}
