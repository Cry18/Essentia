package com.essentia.essentiauser.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.essentia.essentiauser.dto.UserProfileDto;
import com.essentia.essentiauser.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/user/")
public class ProfileController {

    private static final Logger logger = LogManager.getLogger(ProfileController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    /**
     * GET /api/user/profile
     * Restituisce il profilo completo dell'utente autenticato:
     * signature scent, profumi preferiti, scaffali.
     */
    @GetMapping("profile")
    public UserProfileDto getProfile() {
        int userId = getCurrentUserId();
        logger.debug("GET /profile - userId: {}", userId);
        return userServiceImpl.getUserProfile(userId);
    }

    /**
     * POST /api/user/profile/image
     * Carica la foto profilo dell'utente autenticato.
     * Salva il file e aggiorna imageUrl nel DB.
     * Restituisce la URL pubblica: /api/user/uploads/{uuid}.ext
     */
    @PostMapping("profile/image")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Il file è vuoto");
        }
        int userId = getCurrentUserId();
        try {
            String ext = getExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID().toString() + ext;
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(file.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            String url = "/api/user/uploads/" + filename;
            userServiceImpl.updateProfileImage(userId, url);
            logger.info("Foto profilo aggiornata per userId: {} → {}", userId, url);
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            logger.error("Errore durante il caricamento della foto profilo per userId: {}", userId, e);
            return ResponseEntity.internalServerError().body("Caricamento fallito");
        }
    }

    /**
     * GET /api/user/uploads/{filename}
     * Serve le foto profilo. Endpoint pubblico (nessun token richiesto).
     */
    @GetMapping("uploads/{filename:.+}")
    public ResponseEntity<Resource> serveProfileImage(@PathVariable String filename) {
        Path file = Paths.get(uploadDir).resolve(filename);
        Resource resource = new FileSystemResource(file);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        String contentType;
        try {
            contentType = Files.probeContentType(file);
        } catch (IOException e) {
            contentType = null;
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
    }

    private int getCurrentUserId() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private String getExtension(String filename) {
        if (filename == null) return "";
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex >= 0 ? filename.substring(dotIndex) : "";
    }
}
