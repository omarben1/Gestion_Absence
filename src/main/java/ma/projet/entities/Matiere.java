package ma.projet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Matiere implements Serializable {

	private static final long serialVersionUID = -1019850018293893841L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 40)
	private String intitule;
	@OneToOne
	@JsonIgnore
	private Professeur professeur;
	@OneToOne
	private Module module ;
	@OneToMany(mappedBy = "matiere",fetch = FetchType.EAGER)
	private List<Seance> seance = new ArrayList<>() ;
}
