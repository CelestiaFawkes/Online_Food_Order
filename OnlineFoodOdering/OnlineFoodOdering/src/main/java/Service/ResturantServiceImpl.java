package Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
	
	@Autowired
	private ResturantRepository resturantRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Resturant createResturant(CreateResturantRequest req, User user) {
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
		return resturantRepository.save(resturant);
	}

	@Override
	public Resturant updateResturant(Long resturantId, CreateResturantRequest updatedRequested) throws Exception {
		
		Resturant resturant = findResturantById(resturantId);
		if(resturant.getCuisineType()!=null)
		{
			resturant.setCuisineType(updatedRequested.getCuisine());
		}
		if(resturant.getDescription()!=null)
		{
			resturant.setDescription(updatedRequested.getDescription());
		}
		if(resturant.getRestName()!=null)
		{
			resturant.setRestName(updatedRequested.getName());
		}
		return resturantRepository.save(resturant);
	}

	@Override
	public void deleteResturant(Long resturantId) throws Exception {
		
		Resturant resturant = findResturantById(resturantId);
		resturantRepository.delete(resturant);
		
	}

	@Override
	public List<Resturant> getAllResturant() {
		return resturantRepository.findAll();
	}

	@Override
	public List<Resturant> searchResturant(String Keyword) {
		return resturantRepository.findBySearchQuery(Keyword);
	}

	@Override
	public Resturant findResturantById(Long id) throws Exception {
		Optional<Resturant> opt = resturantRepository.findById(id);
		
		if(opt.isEmpty())
		{
			throw new Exception("Resturant not found with id " + id);
		}
		return opt.get();
	}

	@Override
	public Resturant getResturantByUserId(Long UserId) throws Exception {
		Resturant resturant = resturantRepository.findOwnerById(UserId);
		if(resturant == null)
		{
			throw new Exception("Resturant not found by Id" + UserId);
		}
		return resturant;
	}

	@Override
	public ResturantDto addToFavorites(Long resturantid, User user) throws Exception {
		Resturant resturant  = resturantRepository.findResturantById(resturantid);
		ResturantDto dto = new ResturantDto();
		dto.setDescription(resturant.getDescription());
		dto.setImages(resturant.getImages());
		dto.setTitle(resturant.getRestName());
		dto.setId(resturantid);
		
		if(user.getFavorites().contains(dto))
		{
			user.getFavorites().remove(dto);
		}
		else
		
			user.getFavorites().add(dto);
		
		userRepository.save(user);
		return dto;
	}

	@Override
	public Resturant updateResturantStatus(Long id) throws Exception {
		// TODO Auto-generated method stub
		Resturant resturant = findResturantById(id);
		resturant.setOpen(!resturant.isOpen());
		return resturantRepository.save(resturant);
	}

}
