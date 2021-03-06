package ma.projet.repositorie;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ma.projet.entities.Matiere;
import ma.projet.entities.Seance;
@RepositoryRestResource
public interface SeanceRepository extends JpaRepository<Seance, Long> {

	@Query(value = "select seance.id from seance s where s.heure_debut <= CAST(?1 AS TIME ) AND "
			+ "  CAST(?1 AS TIME ) <= s.heure_fin AND CAST(?2 AS DATE ) = s.date" , nativeQuery = true)
	List<Seance> findByCurrentHourAndCurrentDay(String currentHeure,String currentDay, Long idProf); 
	@Modifying(clearAutomatically = true)
	@Query(value = "update seance set date = adddate(date,interval 1 week), nombre_semaines = nombre_semaines-1 where "
			+ "id = ?1 and nombre_semaines >= 1", nativeQuery = true)
	void decrementDateAndSemaineNumberByIdSeance(Long idSeance);
	
	@Query(value = "select * from seance left outer join matiere on seance.matiere_id=matiere.id WHERE (seance.heure_debut <=  ?1 AND ?1 <= seance.heure_fin AND seance.date = ?2) "
					+"AND matiere.professeur_id = ?3" ,nativeQuery = true)
	Optional<Seance> findIdSeance(Date currentHeure, Date date, Long idProf);
	
	List<Seance> findByMatiere(Matiere matiere);
	
}
