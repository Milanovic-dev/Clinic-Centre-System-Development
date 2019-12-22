package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Clinic;
import model.Priceslist;

public interface PriceListRepository extends JpaRepository<Priceslist,Long>{
	public List<Priceslist> findAllByClinic(Clinic c);
	public Priceslist findByTypeOfExamination(String typeOfExamination);
	public Priceslist findByTypeOfExaminationAndDeleted(String typeOfExamination, Boolean deleted);
	public List<Priceslist> findAllByPrice(Long price);
}
