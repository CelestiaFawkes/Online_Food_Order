package Service;

import java.util.List;

import Dto.ResturantDto;
import Model.Resturant;
import Model.User;
import request.CreateResturantRequest;

public interface ResturantService {

	public Resturant createResturant(CreateResturantRequest req, User user);

	public Resturant updateResturant(Long resturantId, CreateResturantRequest updatedRequested) throws Exception;

	public void deleteResturant(Long resturantId) throws Exception;

	public List<Resturant> getAllResturant();

	public List<Resturant> searchResturant(String Keyword);

	public Resturant findResturantById(Long id) throws Exception;

	public Resturant getResturantByUserId(Long UserId) throws Exception;

	public ResturantDto addToFavorites(Long resturantid, User user) throws Exception;

	public Resturant updateResturantStatus(Long id) throws Exception;

}
