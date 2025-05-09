package com.essentia.essentiauser.service;

import java.util.List;

public interface PerfumeService {
    List<String> findAllPerfumes();
    void setSignature(int userId, int perfumeId);
    void createReview();
}
