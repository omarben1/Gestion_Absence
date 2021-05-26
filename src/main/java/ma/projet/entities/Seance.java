package ma.projet.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Seance implements Serializable {

	private static final long serialVersionUID = -8600385042823214686L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date date;
	private int nombreSemaines;
	@Temporal(TemporalType.TIME)
	private Date heureDebut;
	@Temporal(TemporalType.TIME)
	private Date heureFin;
	@Column(length = 10)
	private String nature;
	@OneToOne
	private Matiere matiere;
	@OneToMany(mappedBy="seance")
	private List<Absence> absences = new ArrayList<>();
}