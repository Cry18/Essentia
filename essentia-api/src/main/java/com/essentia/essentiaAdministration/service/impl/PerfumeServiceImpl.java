package com.essentia.essentiaAdministration.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.essentia.essentiaAdministration.dto.PerfumeDto;
import com.essentia.essentiaAdministration.entity.Brand;
import com.essentia.essentiaAdministration.entity.Parfumer;
import com.essentia.essentiaAdministration.entity.Perfume;
import com.essentia.essentiaAdministration.entity.PerfumeNote;
import com.essentia.essentiaAdministration.entity.PerfumePrfNotes;
import com.essentia.essentiaAdministration.repository.BrandRepository;
import com.essentia.essentiaAdministration.repository.ParfumerRepository;
import com.essentia.essentiaAdministration.repository.PerfumeNoteRepository;
import com.essentia.essentiaAdministration.repository.PerfumeRepository;
import com.essentia.essentiaAdministration.service.PerfumeService;

public class PerfumeServiceImpl implements PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;
    
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ParfumerRepository parfumerRepository;

    @Autowired
    private PerfumeNoteRepository perfumeNoteRepository;

    @Override
    public List<PerfumeDto> findAll() {
		  /*List<Perfume> perfumes = (List<Perfume>) perfumeRepository.findAll();
		  List<PerfumeDto> perfumesDto = perfumes.stream()
			  .map(perfume -> new PerfumeDto(perfume.getName(), perfume.getDescription()))
			  .toList();
		  return perfumesDto;*/
        return null;
    }

    @Override
    public void create(PerfumeDto perfume) {
        Brand brand = brandRepository.findByName(perfume.getBrand());
        Perfume perfume2 = perfumeRepository.findByName(perfume.getName());

        //crea una lista di profumieri
        List<Parfumer> parfumers = new ArrayList<>();
        //popola la lista a partire dalla lista di nomi dei profumieri del dto
        for (int i = 0; i < perfume.getParfumers().size(); i++) {
            String parfumerName = perfume.getParfumers().get(i);
            Parfumer parfumer = parfumerRepository.findByName(parfumerName);
            if (parfumer != null) {
                parfumers.add(parfumer);
            }
        }
        List<PerfumePrfNotes> perfumeNotes = new ArrayList<>();
        //popola la lista a partire dalla lista di note del dto
        for (int i = 0; i < perfume.getNotes().size(); i++) {
            PerfumeNote note = perfumeNoteRepository.findByName(perfume.getNotes().get(i).getName());;
            PerfumePrfNotes perfumeNote = new PerfumePrfNotes(perfume2, note, perfume.getNotes().get(i).getType());
            
            if(perfumeNote != null) {
                perfumeNotes.add(perfumeNote);
            }

        }

        Perfume newPerfume = new Perfume(
                perfume.getName(),
                perfume.getDescription(),
                brand,
                parfumers,
                perfumeNotes
        );

        perfumeRepository.save(newPerfume);
    }

    @Override
    public void updatePerfume(int id, PerfumeDto perfume) {
    }

    @Override
    public void deleteById(int id) {
        Perfume perfume = perfumeRepository.findById(id);
		if (perfume != null) {
			perfumeRepository.delete(perfume);
		}
    }
    
}
