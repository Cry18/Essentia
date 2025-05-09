package com.essentia.essentiaadministration.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaadministration.dto.PerfumeDto;
import com.essentia.essentiaadministration.entity.Brand;
import com.essentia.essentiaadministration.entity.Parfumer;
import com.essentia.essentiaadministration.entity.Perfume;
import com.essentia.essentiaadministration.entity.PerfumeNote;
import com.essentia.essentiaadministration.entity.PerfumePrfNotes;
import com.essentia.essentiaadministration.exception.ResourceNotFoundException;
import com.essentia.essentiaadministration.repository.BrandRepository;
import com.essentia.essentiaadministration.repository.ParfumerRepository;
import com.essentia.essentiaadministration.repository.PerfumeNoteRepository;
import com.essentia.essentiaadministration.repository.PerfumePrfNotesRepository;
import com.essentia.essentiaadministration.repository.PerfumeRepository;
import com.essentia.essentiaadministration.repository.ReviewRepository;
import com.essentia.essentiaadministration.service.PerfumeService;

import jakarta.transaction.Transactional;
@Service
public class PerfumeServiceImpl implements PerfumeService {

    @Autowired
    private ReviewRepository reviewRepository;

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
    
    @Override
    public PerfumeDto findMostDesiredPerfume() {
        Perfume mostDesiredPerfume = perfumeRepository.findMostDesiredPerfume();
        if (mostDesiredPerfume != null) {
            PerfumeDto perfumeDto = new PerfumeDto(mostDesiredPerfume.getName(), mostDesiredPerfume.getBrand().getName(),null,null,null);
            perfumeDto.setId(mostDesiredPerfume.getId());
            return perfumeDto;
        } else {
            throw new ResourceNotFoundException("No desired perfumes found.");
        }
    }

    @Override
    public PerfumeDto findMostAppreciatedPerfume() {
        Perfume mostAppreciatedPerfume = reviewRepository.findMostAppreciatedPerfume();
        if (mostAppreciatedPerfume != null) {
            PerfumeDto perfumeDto = new PerfumeDto(mostAppreciatedPerfume.getName(), mostAppreciatedPerfume.getBrand().getName(),null,null,null);
            perfumeDto.setId(mostAppreciatedPerfume.getId());
            return perfumeDto;
        } else {
            throw new ResourceNotFoundException("No appreciated perfumes found.");
        }
    }
}
