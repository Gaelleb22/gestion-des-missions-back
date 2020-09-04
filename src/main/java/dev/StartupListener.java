package dev;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.domain.Collegue;
import dev.domain.LigneDeFrais;
import dev.domain.Mission;
import dev.domain.Nature;
import dev.domain.NoteDeFrais;
import dev.domain.Role;
import dev.domain.SignatureNumerique;
import dev.domain.Statut;
import dev.domain.Transport;
import dev.repository.CollegueRepo;
import dev.repository.LigneDeFraisRepo;
import dev.repository.MissionRepo;
import dev.repository.NatureRepo;
import dev.repository.NoteDeFraisRepo;

/**
 * Code de démarrage de l'application. Insertion de jeux de données.
 */
@Component
@Transactional
public class StartupListener {
  
	private String appVersion;
	private PasswordEncoder passwordEncoder;
	private CollegueRepo collegueRepo;
	private NatureRepo natureRepo;
	private MissionRepo missionRepo;
	private NoteDeFraisRepo noteRepo;
	private LigneDeFraisRepo ligneRepo;

	public StartupListener(@Value("${app.version}") String appVersion, PasswordEncoder passwordEncoder,
			CollegueRepo collegueRepo, NatureRepo natureRepo, MissionRepo missionRepo, NoteDeFraisRepo noteRepo,
			LigneDeFraisRepo ligneRepo) {
		this.appVersion = appVersion;
		this.passwordEncoder = passwordEncoder;
		this.collegueRepo = collegueRepo;
		this.natureRepo = natureRepo;
		this.missionRepo = missionRepo;
		this.noteRepo = noteRepo;
		this.ligneRepo = ligneRepo;
	}

	@EventListener(ContextRefreshedEvent.class)
	public void onStart() {

		// Création de deux utilisateurs
		Collegue col1 = new Collegue();
		// col1.setSignatureNumerique(new SignatureNumerique());
		col1.setNom("Admin");
		col1.setPrenom("DEV");
		col1.setEmail("admin@dev.fr");
		col1.setMotDePasse(passwordEncoder.encode("superpass"));
		col1.setRole(Role.ROLE_ADMINISTRATEUR);

		Collegue col2 = new Collegue();
		// col2.setSignatureNumerique(new SignatureNumerique());
		col2.setNom("User");
		col2.setPrenom("DEV");
		col2.setEmail("user@dev.fr");
		col2.setMotDePasse(passwordEncoder.encode("superpass"));
		col2.setRole(Role.ROLE_UTILISATEUR);
		this.collegueRepo.save(col2);

		Collegue col3 = new Collegue();
		// col3.setSignatureNumerique(new SignatureNumerique());
		col3.setNom("Manager");
		col3.setPrenom("DEV");
		col3.setEmail("manager@dev.fr");
		col3.setMotDePasse(passwordEncoder.encode("superpass"));
		col3.setRole(Role.ROLE_MANAGER);
		this.collegueRepo.save(col3);

		// Création de missions pour un collègue
		Nature nat1 = new Nature();
		nat1.setLibelle("Formation");
		nat1.setPayee(true);
		nat1.setVersementPrime(true);
		nat1.setPourcentagePrime(BigDecimal.valueOf(10));
		nat1.setDebutValidite(LocalDate.now().minusMonths(10));
		nat1.setFinValidite(LocalDate.now().plusMonths(3));
		nat1.setPlafondFrais(BigDecimal.valueOf(150));
		nat1.setDepassementFrais(true);

		Mission mis1 = new Mission();
		mis1.setSignatureNumerique(new SignatureNumerique());
		mis1.setDateDebut(LocalDate.now());
		mis1.setDateFin(LocalDate.now().plusDays(10));
		mis1.setVilleDepart("Mickeyville");
		mis1.setVilleArrivee("Donaldville");
		mis1.setPrime(BigDecimal.valueOf(1000));
		mis1.setStatut(Statut.INITIALE);
		mis1.setTransport(Transport.TRAIN);
    
		Mission mis2 = new Mission();
		mis2.setSignatureNumerique(new SignatureNumerique());
		mis2.setDateDebut(LocalDate.of(2020, 5, 2));
		mis2.setDateFin(LocalDate.of(2020, 5, 8));
		mis2.setVilleDepart("Mickeyville");
		mis2.setVilleArrivee("Donaldville");
		mis2.setPrime(BigDecimal.valueOf(500));
		mis2.setStatut(Statut.VALIDEE);
		mis2.setTransport(Transport.TRAIN);
        
		Mission mis3 = new Mission();
		mis3.setSignatureNumerique(new SignatureNumerique());
		mis3.setDateDebut(LocalDate.of(2020, 7, 30));
		mis3.setDateFin(LocalDate.of(2020, 8, 6));
		mis3.setVilleDepart("Mickeyville");
		mis3.setVilleArrivee("Donaldville");
		mis3.setPrime(BigDecimal.valueOf(100));
		mis3.setStatut(Statut.VALIDEE);
		mis3.setTransport(Transport.TRAIN);
        
		Mission mis4 = new Mission();
		mis4.setSignatureNumerique(new SignatureNumerique());
		mis4.setDateDebut(LocalDate.of(2019, 7, 30));
		mis4.setDateFin(LocalDate.of(2019, 8, 6));
		mis4.setVilleDepart("Mickeyville");
		mis4.setVilleArrivee("Donaldville");
		mis4.setPrime(BigDecimal.valueOf(100));
		mis4.setStatut(Statut.VALIDEE);
		mis4.setTransport(Transport.TRAIN);
    
		Mission mis5 = new Mission();
		mis5.setSignatureNumerique(new SignatureNumerique());
		mis5.setDateDebut(LocalDate.of(2020, 10, 3));
		mis5.setDateFin(LocalDate.of(2020, 10, 16));
		mis5.setVilleDepart("Mickeyville");
		mis5.setVilleArrivee("Donaldville");
		mis5.setStatut(Statut.EN_ATTENTE_VALIDATION);
		mis5.setTransport(Transport.TRAIN);

		NoteDeFrais note1 = new NoteDeFrais();
		note1.setDateDeSaisie(LocalDate.now());
		note1.setSignatureNumerique(new SignatureNumerique());

		LigneDeFrais l1 = new LigneDeFrais();
		l1.setDate(LocalDate.now());
		l1.setNature("hotel bien cher");
		l1.setMontant(BigDecimal.valueOf(200));
		l1.setNoteDeFrais(note1);

		LigneDeFrais l2 = new LigneDeFrais();
		l2.setDate(LocalDate.now());
		l2.setNature("petit pain");
		l2.setMontant(BigDecimal.valueOf(0.15));
		l2.setNoteDeFrais(note1);

		mis1.setNature(nat1);
		note1.addLigneFrais(l1);
		note1.addLigneFrais(l2);
		this.noteRepo.save(note1);
		mis1.setNoteDeFrais(note1);
		col1.addMission(mis1);
    
		mis2.setNature(nat1);
		mis3.setNature(nat1);
		mis4.setNature(nat1);
		mis5.setNature(nat1);
		col1.addMission(mis2);
		col1.addMission(mis3);
		col1.addMission(mis4);
		col3.addMission(mis5);

		this.ligneRepo.save(l1);
		this.ligneRepo.save(l2);
		this.natureRepo.save(nat1);
		this.collegueRepo.save(col1);
		this.missionRepo.save(mis1);
		this.missionRepo.save(mis2);
		this.missionRepo.save(mis3);
		this.missionRepo.save(mis4);
		this.missionRepo.save(mis5);

	}

}
