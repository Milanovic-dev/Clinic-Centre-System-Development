package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Clinic;
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
	
	public List<VacationRequest> findAllByClinic(Clinic clinic)
	{
		return vacationRequestRepository.findAllByClinic(clinic);
	}
	
	public void save(VacationRequest request)
	{
		vacationRequestRepository.save(request);
	}
	
	public void delete(VacationRequest request)
	{
		vacationRequestRepository.delete(request);
	}
	
	public Optional<VacationRequest> findById(VacationRequest request)
	{
		return vacationRequestRepository.findById(request.getId());
	}
}
