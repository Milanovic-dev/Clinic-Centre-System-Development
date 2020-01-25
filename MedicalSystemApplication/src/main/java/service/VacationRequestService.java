package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.User;
import model.VacationRequest;

import repository.VacationRequestRepository;

@Service
public class VacationRequestService 
{
	@Autowired
	public VacationRequestRepository vacationRequestRepository;
	
	public List<VacationRequest> findAllByUser(User user)
	{
		return vacationRequestRepository.findAllByUser(user);
	}
	
	
	public void save(VacationRequest request)
	{
		vacationRequestRepository.save(request);
	}
}
