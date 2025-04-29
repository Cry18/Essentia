package com.essentia.essentia_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentia_api.entity.User;
@Repository
public interface UserRepository extends CrudRepository<User,Integer>{
	User findById(int id);
	User findByName(String name);
}
