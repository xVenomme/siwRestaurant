package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Menu;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.MenuRepository;
import it.uniroma3.siw.repository.PiattoRepository;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
public class PiattoService {

	@Autowired
	PiattoRepository piattoRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	
	 public Piatto findById(Long id) {
	        return this.piattoRepository.findById(id).orElse(null);
	}

    public Piatto save(Piatto piatto) {
        return piattoRepository.save(piatto);
    }


    public List<Piatto> findAll() {
        return (List<Piatto>) piattoRepository.findAll();
    }
   
    @Transactional
    public void deleteById(Long id) {
        Piatto piatto = piattoRepository.findById(id).orElse(null);
        if (piatto != null) {
            // Rimuovi il piatto da tutti i menu che lo contengono
            for (Menu menu : piatto.getMenu()) {
                menu.getPiatti().remove(piatto);
            }
            piattoRepository.delete(piatto);
        }
    }


    public Piatto saveWithImage(Piatto piatto, MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path uploadDir = Paths.get("src/main/resources/static/images/dishes");
            Files.createDirectories(uploadDir);
            Files.copy(file.getInputStream(), uploadDir.resolve(fileName),
                       StandardCopyOption.REPLACE_EXISTING);
            piatto.setPhoto(fileName);   // campo già presente in Piatto :contentReference[oaicite:8]{index=8}
        }
        return piattoRepository.save(piatto);
    }

    public void updateWithImage(Piatto piatto, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            piatto.setPhoto(file.getOriginalFilename());
            // salva file su disco se necessario
        } else {
            Piatto existing = piattoRepository.findById(piatto.getId()).orElse(null);
            if (existing != null && existing.getPhoto() != null && !existing.getPhoto().isBlank())
                piatto.setPhoto(existing.getPhoto());
            else
                piatto.setPhoto("default.jpg"); // fallback se non esiste immagine
        }

        piattoRepository.save(piatto);
    }

   


}
