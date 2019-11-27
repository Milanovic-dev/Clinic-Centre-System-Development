package repository;

import model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrugRepository extends JpaRepository<Drug,Long> {

    public Drug findByCode(String code);

    public Drug findByName(String name);

}
