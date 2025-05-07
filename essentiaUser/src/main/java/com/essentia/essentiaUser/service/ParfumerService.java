package com.essentia.essentiaUser.service;

import java.util.List;

public interface  ParfumerService {
    List<String> findAllParfumers();
    List<String> findLikeNameParfumers(String name);
}
