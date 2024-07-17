package Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Dto.ResturantDto;
import Model.Address;
import Model.Resturant;
import Model.User;
import Repository.AddressRepository;
import Repository.ResturantRepository;
import Repository.UserRepository;
import request.CreateResturantRequest;

@Service
public class ResturantServiceImpl implements ResturantService {

	private static final Logger logger = LoggerFactory.getLogger(ResturantServiceImpl.class);

	@Autowired
	private ResturantRepository resturantRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Resturant createResturant(CreateResturantRequest req, User user) {
		logger.debug("Creating restaurant for user '{}'", user.getEmail());

		Address address = addressRepository.save(req.getAddress());
		Resturant resturant = new Resturant();
		resturant.setAddress(address);
		resturant.setContactInformation(req.getContactInformation());
		resturant.setCuisineType(req.getCuisine());
		resturant.setDescription(req.getDescription());
		resturant.setImages(req.getImages());
		resturant.setRestName(req.getName());
		resturant.setOpeningHours(req.getOpeningHours());
		resturant.setRegistrationDate(LocalDateTime.now());
		resturant.setOwner(user);

		Resturant savedResturant = resturantRepository.save(resturant);
		logger.info("Restaurant created successfully with ID '{}'", savedResturant.getId());

		return savedResturant;
	}

	@Override
	public Resturant updateResturant(Long resturantId, CreateResturantRequest updatedRequested) throws Exception {
		logger.debug("Updating restaurant with ID '{}'", resturantId);

		Resturant resturant = findResturantById(resturantId);
		if (updatedRequested.getCuisine() != null) {
			resturant.setCuisineType(updatedRequested.getCuisine());
		}
		if (updatedRequested.getDescription() != null) {
			resturant.setDescription(updatedRequested.getDescription());
		}
		if (updatedRequested.getName() != null) {
			resturant.setRestName(updatedRequested.getName());
		}

		Resturant updatedResturant = resturantRepository.save(resturant);
		logger.info("Restaurant with ID '{}' updated successfully", resturantId);

		return updatedResturant;
	}

	@Override
	public void deleteResturant(Long resturantId) throws Exception {
		logger.debug("Deleting restaurant with ID '{}'", resturantId);

		Resturant resturant = findResturantById(resturantId);
		resturantRepository.delete(resturant);

		logger.info("Restaurant with ID '{}' deleted successfully", resturantId);
	}

	@Override
	public List<Resturant> getAllResturant() {
		logger.debug("Fetching all restaurants");

		List<Resturant> resturants = resturantRepository.findAll();

		logger.info("Fetched {} restaurants", resturants.size());
		return resturants;
	}

	@Override
	public List<Resturant> searchResturant(String Keyword) {
		logger.debug("Searching restaurants with keyword '{}'", Keyword);

		List<Resturant> resturants = resturantRepository.findBySearchQuery(Keyword);

		logger.info("Found {} restaurants matching the keyword '{}'", resturants.size(), Keyword);
		return resturants;
	}

	@Override
	public Resturant findResturantById(Long id) throws Exception {
		logger.debug("Fetching restaurant with ID '{}'", id);

		Optional<Resturant> opt = resturantRepository.findById(id);
		if (opt.isEmpty()) {
			logger.error("Restaurant not found with ID '{}'", id);
			throw new Exception("Restaurant not found with id " + id);
		}

		Resturant resturant = opt.get();
		logger.info("Restaurant fetched successfully with ID '{}'", id);
		return resturant;
	}

	@Override
	public Resturant getResturantByUserId(Long UserId) throws Exception {
		logger.debug("Fetching restaurant for user with ID '{}'", UserId);

		Resturant resturant = resturantRepository.findOwnerById(UserId);
		if (resturant == null) {
			logger.error("Restaurant not found for user with ID '{}'", UserId);
			throw new Exception("Restaurant not found by Id " + UserId);
		}

		logger.info("Restaurant fetched successfully for user with ID '{}'", UserId);
		return resturant;
	}

	@Override
	public ResturantDto addToFavorites(Long resturantid, User user) throws Exception {
		logger.debug("Adding restaurant with ID '{}' to favorites for user '{}'", resturantid, user.getEmail());

		Resturant resturant = resturantRepository.findResturantById(resturantid);
		ResturantDto dto = new ResturantDto();
		dto.setDescription(resturant.getDescription());
		dto.setImages(resturant.getImages());
		dto.setTitle(resturant.getRestName());
		dto.setId(resturantid);

		if (user.getFavorites().contains(dto)) {
			user.getFavorites().remove(dto);
		} else {
			user.getFavorites().add(dto);
		}

		userRepository.save(user);
		logger.info("Restaurant with ID '{}' added/removed from favorites for user '{}'", resturantid, user.getEmail());

		return dto;
	}

	@Override
	public Resturant updateResturantStatus(Long id) throws Exception {
		logger.debug("Updating restaurant status with ID '{}'", id);

		Resturant resturant = findResturantById(id);
		resturant.setOpen(!resturant.isOpen());
		Resturant updatedResturant = resturantRepository.save(resturant);

		logger.info("Restaurant status updated successfully with ID '{}'", id);
		return updatedResturant;
	}

}
