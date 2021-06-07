package ma.projet.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ma.projet.dto.ProfesseurDto;
import ma.projet.entities.Absence;
import ma.projet.reponse.AbsenceParMatiere;
import ma.projet.reponse.ProfesseurResponse;
import ma.projet.reponse.SeanceDetailsResponse;
import ma.projet.request.EtudiantSeanceRequest;
import ma.projet.request.ProfesseurRequest;
import ma.projet.service.AbsenceMangementService;

@RestController
@CrossOrigin(origins = "*")
public class ProfesseurController {
	
	@Autowired
	private AbsenceMangementService absenceMangementService;
	
	@PostMapping("/professeur/absence" )
	public ResponseEntity<List<Absence>> absence (@RequestBody EtudiantSeanceRequest etudiantSeanceRequest) {
		
		System.out.println(etudiantSeanceRequest.getEtudiantRequests());
		System.out.println( etudiantSeanceRequest.getIdSeance());
		
		absenceMangementService.setAbsence(etudiantSeanceRequest.getEtudiantRequests(), etudiantSeanceRequest.getIdSeance());
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	
	}
	@PostMapping(path = "/admin/create" , produces =  {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
		     consumes =  {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ProfesseurResponse> createProfesseur(@RequestBody ProfesseurRequest professeurRequest){
		ProfesseurDto professeurDto = new ModelMapper().map(professeurRequest, ProfesseurDto.class);
	    absenceMangementService.createProfesseur(professeurDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(path="/professeur/etudiants",produces =  {MediaType.APPLICATION_JSON_VALUE},
		     consumes =  {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<SeanceDetailsResponse> getEtudiants(Principal principal) throws ParseException, JsonProcessingException {
		SeanceDetailsResponse seanceDetailsResponse = absenceMangementService.getEtudiantsByProf(principal.getName());
		System.out.println(new ObjectMapper().writeValueAsString(seanceDetailsResponse));
		return new ResponseEntity<>(seanceDetailsResponse,HttpStatus.ACCEPTED);
	}
	@GetMapping(path="/professeur/absences/matieres",produces =  {MediaType.APPLICATION_JSON_VALUE},
		     consumes =  {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<AbsenceParMatiere>> getEtudiantsAbsencesByMatiere(Principal principal){
		
		return new ResponseEntity<List<AbsenceParMatiere>>(absenceMangementService.getAbsencesEtudiant(principal.getName()), HttpStatus.OK)	;
		
	}
	
	@GetMapping(path="/professeur/absences/seance/etudiants",produces =  {MediaType.APPLICATION_JSON_VALUE},
		     consumes =  {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<AbsenceParMatiere> getEtudiantsByMatiere(Principal principal) throws ParseException{
		
		return new ResponseEntity<AbsenceParMatiere>(absenceMangementService.getEtudiant(principal.getName()),HttpStatus.OK) ;
		
	}
}
