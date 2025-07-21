package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.PiattoRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
public class PiattoService {

	@Autowired
	PiattoRepository piattoRepository;
	 public Piatto findById(Long id) {
	        return this.piattoRepository.findById(id).orElse(null);
	}

    public Piatto save(Piatto piatto) {
        return piattoRepository.save(piatto);
    }

    public void deleteById(Long id) {
        piattoRepository.deleteById(id);
    }

    public List<Piatto> findAll() {
        return (List<Piatto>) piattoRepository.findAll();
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

   


}
