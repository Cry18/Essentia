package com.essentia.essentiaUser.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.essentia.essentiaUser.entity.Brand;
import com.essentia.essentiaUser.entity.Perfume;
import com.essentia.essentiaUser.entity.User;
import com.essentia.essentiaUser.repository.UserRepository;
import com.essentia.essentiaUser.repository.PerfumeRepository;
import com.essentia.essentiaUser.repository.ReviewRepository;
import com.essentia.essentiaUser.service.PerfumeService;
@Service
public class PerfumeServiceImpl implements PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<String> findAllPerfumes() {
        List<Perfume> perfumes = perfumeRepository.findAll();
        List<String> perfumeNames = new ArrayList<>();
        for (Perfume perfume : perfumes) {
            perfumeNames.add(perfume.getName());
        }
        return perfumeNames;
    }
    
    @Override
    public void setSignature(int userId, int perfumeId){
        User user = userRepository.findById(userId);	
        Perfume perfume = perfumeRepository.findById(perfumeId);
        user.setSignature(perfume);
	
		userRepository.save(user);		
    }

    @Override
    public void createReview(){
        
    }
}
