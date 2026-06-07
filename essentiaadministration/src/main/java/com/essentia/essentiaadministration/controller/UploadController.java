package com.essentia.essentiaadministration.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Gestione upload e serving delle immagini delle entità (brand, parfumer, perfume).
 *
 * Upload:  POST /api/admin/upload          — richiede ROLE_ADMIN (via SecurityConfig)
 * Serving: GET  /api/admin/uploads/{file}  — pubblico (permitAll in SecurityConfig)
 *
 * Le immagini vengono salvate nella directory configurata da app.upload.dir
 * (default ./uploads in locale, /uploads nel container Docker via UPLOAD_DIR env).
 *
 * URL restituita: /api/admin/uploads/{uuid}.ext
 * Salvare questo URL nel campo imageUrl dell'entità tramite PUT /api/admin/{entity}/update/{id}
 */
@RestController
@RequestMapping("/api/admin/")
public class UploadController {

    private static final Logger logger = LogManager.getLogger(UploadController.class);

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    /**
     * POST /api/admin/upload
     * Carica un file immagine e restituisce la URL pubblica da salvare nel DB.
     */
    @PostMapping("upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Il file è vuoto");
        }
        try {
            String ext = getExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID().toString() + ext;
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(file.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            String url = "/api/admin/uploads/" + filename;
            logger.info("File caricato: {}", url);
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            logger.error("Errore durante il caricamento del file", e);
            return ResponseEntity.internalServerError().body("Caricamento fallito");
        }
    }

    /**
     * GET /api/admin/uploads/{filename}
     * Serve le immagini caricate. Endpoint pubblico (nessun token richiesto).
     */
    @GetMapping("uploads/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
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

    private String getExtension(String filename) {
        if (filename == null) return "";
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex >= 0 ? filename.substring(dotIndex) : "";
    }
}
