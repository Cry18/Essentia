package com.essentia.essentiaAdministration.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaAdministration.dto.PerfumeDto;
import com.essentia.essentiaAdministration.entity.Brand;
import com.essentia.essentiaAdministration.entity.Parfumer;
import com.essentia.essentiaAdministration.entity.Perfume;
import com.essentia.essentiaAdministration.entity.PerfumeNote;
import com.essentia.essentiaAdministration.entity.PerfumePrfNotes;
import com.essentia.essentiaAdministration.exception.ResourceNotFoundException;
import com.essentia.essentiaAdministration.repository.BrandRepository;
import com.essentia.essentiaAdministration.repository.ParfumerRepository;
import com.essentia.essentiaAdministration.repository.PerfumeNoteRepository;
import com.essentia.essentiaAdministration.repository.PerfumePrfNotesRepository;
import com.essentia.essentiaAdministration.repository.PerfumeRepository;
import com.essentia.essentiaAdministration.service.PerfumeService;

import jakarta.transaction.Transactional;
@Service
public class PerfumeServiceImpl implements PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;
    
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ParfumerRepository parfumerRepository;

    @Autowired
    private PerfumeNoteRepository perfumeNoteRepository;

    @Autowired
    private PerfumePrfNotesRepository perfumePrfNoteRepository;
  

    @Override
    @Transactional
    public void create(PerfumeDto perfume) {
        Brand brand = brandRepository.findByName(perfume.getBrand());
        if (brand == null){
            throw new ResourceNotFoundException("Brand not found: " + perfume.getBrand());
        }

        //crea una lista di profumieri
        List<Parfumer> parfumers = new ArrayList<>();
        //popola la lista a partire dalla lista di nomi dei profumieri del dto
        for (int i = 0; i < perfume.getParfumers().size(); i++) {
            String parfumerName = perfume.getParfumers().get(i);
            Parfumer parfumer = parfumerRepository.findByName(parfumerName);
            if (parfumer != null) {
                parfumers.add(parfumer);
            } else {
                throw new ResourceNotFoundException("Parfumer not found: " + parfumerName);
            }
        }

        Perfume newPerfume = new Perfume(
                perfume.getName(),
                perfume.getDescription(),
                brand,
                parfumers
        );

        perfumeRepository.save(newPerfume);

        //popola la tabella ponte PerfumePrfNotes
        for (int i = 0; i < perfume.getNotes().size(); i++) {
            PerfumeNote Note = perfumeNoteRepository.findByName(perfume.getNotes().get(i).getName());
            if (Note == null) {
                throw new ResourceNotFoundException("Note not found: " + perfume.getNotes().get(i).getName());
            }
            PerfumePrfNotes perfumeNote = new PerfumePrfNotes(
                    newPerfume,
                    Note,
                    perfume.getNotes().get(i).getType()
            );
            perfumePrfNoteRepository.save(perfumeNote);
            }
        }
    

    @Override
    public void updatePerfume(int id, PerfumeDto perfume) {
        Perfume perfumeUpdated = perfumeRepository.findById(id);
        if (perfumeUpdated != null) {
            perfumeUpdated.setName(perfume.getName());
            perfumeUpdated.setDescription(perfume.getDescription());

            // Update the brand
            Brand brand = brandRepository.findByName(perfume.getBrand());
            if (brand == null) {
                throw new ResourceNotFoundException("Brand not found: " + perfume.getBrand());
            }
            perfumeUpdated.setBrand(brand);

            // Update the parfumers
            List<Parfumer> parfumers = new ArrayList<>();
            for (String parfumerName : perfume.getParfumers()) {
                Parfumer parfumer = parfumerRepository.findByName(parfumerName);
                if (parfumer == null) {
                    throw new ResourceNotFoundException("Parfumer not found: " + parfumerName);
                }
                    parfumers.add(parfumer);
            }
            perfumeUpdated.setParfumers(parfumers);

            // Save the updated perfume
            perfumeRepository.save(perfumeUpdated);
        }
        /*        for (int i = 0; i < perfume.getNotes().size(); i++) {
            PerfumeNote Note = perfumeNoteRepository.findByName(perfume.getNotes().get(i).getName());
            if (Note == null) {
                throw new ResourceNotFoundException("Note not found: " + perfume.getNotes().get(i).getName());
            }
            PerfumePrfNotes perfumeNote = new PerfumePrfNotes(
                    newPerfume,
                    Note,
                    perfume.getNotes().get(i).getType()
            );
            perfumePrfNoteRepository.save(perfumeNote);
            } */

    }

    @Override
    public void deleteById(int id) {
        Perfume perfume = perfumeRepository.findById(id);
		if (perfume != null) {
			perfumeRepository.delete(perfume);
		} else throw new ResourceNotFoundException("Perfume not found with id: " + id);
    }
    
}
