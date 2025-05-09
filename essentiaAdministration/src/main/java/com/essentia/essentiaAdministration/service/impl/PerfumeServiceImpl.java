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
    public PerfumeDto create(PerfumeDto perfume) {
        Brand brand = brandRepository.findById(perfume.getBrand());
        if (brand == null){
            throw new ResourceNotFoundException("Brand not found: " + perfume.getBrand());
        }

        //crea una lista di profumieri
        List<Parfumer> parfumers = new ArrayList<>();
        //popola la lista a partire dalla lista di nomi dei profumieri del dto
        for (int i = 0; i < perfume.getParfumers().size(); i++) {
            int parfumerId = perfume.getParfumers().get(i);
            Parfumer parfumer = parfumerRepository.findById(parfumerId);
            if (parfumer != null) {
                parfumers.add(parfumer);
            } else {
                throw new ResourceNotFoundException("Parfumer not found with id: " + parfumerId);
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
            PerfumeNote Note = perfumeNoteRepository.findById(perfume.getNotes().get(i).getNoteId());
            if (Note == null) {
                throw new ResourceNotFoundException("Note not found: " + perfume.getNotes().get(i).getNoteId());
            }
            PerfumePrfNotes perfumeNote = new PerfumePrfNotes(
                    newPerfume,
                    Note,
                    perfume.getNotes().get(i).getType()
            );
            perfumePrfNoteRepository.save(perfumeNote);
            }
            perfume.setId(newPerfume.getId());
            return perfume;
        }
    

    @Override
    @Transactional
    public PerfumeDto updatePerfume(int id, PerfumeDto perfume) {
        Perfume perfumeUpdated = perfumeRepository.findById(id);
        if (perfumeUpdated != null) {
            perfumeUpdated.setName(perfume.getName());
            perfumeUpdated.setDescription(perfume.getDescription());

            // Update the brand
            Brand brand = brandRepository.findById(perfume.getBrand());
            if (brand == null) {
                throw new ResourceNotFoundException("Brand not found: with id:" + perfume.getBrand());
            }
            perfumeUpdated.setBrand(brand);

            // Update the parfumers
            List<Parfumer> parfumers = new ArrayList<>();
            for (int parfumerId : perfume.getParfumers()) {
                Parfumer parfumer = parfumerRepository.findById(parfumerId);
                if (parfumer == null) {
                    throw new ResourceNotFoundException("Parfumer not found: with id:" + parfumerId);
                }
                    parfumers.add(parfumer);
            }
            perfumeUpdated.setParfumers(parfumers);

            // Save the updated perfume
            perfumeRepository.save(perfumeUpdated);
        }
                for (int i = 0; i < perfume.getNotes().size(); i++) {
            PerfumeNote Note = perfumeNoteRepository.findById(perfume.getNotes().get(i).getNoteId());
            if (Note == null) {
                throw new ResourceNotFoundException("Note not found: " + perfume.getNotes().get(i).getNoteId());
            }
            PerfumePrfNotes perfumeNote = new PerfumePrfNotes(
                    perfumeUpdated,
                    Note,
                    perfume.getNotes().get(i).getType()
            );
            perfumePrfNoteRepository.save(perfumeNote);
            }
            perfume.setId(id);
            return perfume;
    }

    @Override
    @Transactional
    public PerfumeDto deleteById(int id) {
        Perfume perfume = perfumeRepository.findById(id);
		if (perfume != null) {
			perfumeRepository.delete(perfume);
		} else throw new ResourceNotFoundException("Perfume not found with id: " + id);
        PerfumeDto perfumeDto = new PerfumeDto(perfume.getName(), perfume.getBrand().getId(), null, null, null);
        perfumeDto.setId(id);
        return perfumeDto;
    }
    
    @Override
    public PerfumeDto findMostDesiredPerfume() {
        Perfume mostDesiredPerfume = perfumeRepository.findMostDesiredPerfume();
        if (mostDesiredPerfume != null) {
            PerfumeDto perfumeDto = new PerfumeDto(mostDesiredPerfume.getName(), mostDesiredPerfume.getBrand().getId(),null,null,null);
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
            PerfumeDto perfumeDto = new PerfumeDto(mostAppreciatedPerfume.getName(), mostAppreciatedPerfume.getBrand().getId(),null,null,null);
            perfumeDto.setId(mostAppreciatedPerfume.getId());
            return perfumeDto;
        } else {
            throw new ResourceNotFoundException("No appreciated perfumes found.");
        }
    }
}
