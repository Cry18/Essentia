package com.essentia.essentiauser.service;

import java.util.List;

public interface  ParfumerService {
    List<String> findAllParfumers();
    List<String> findLikeNameParfumers(String name);
}
