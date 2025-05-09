package com.essentia.essentiauser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.essentia.essentiauser.entity.User;
@Repository
public interface UserRepository extends CrudRepository<User,Integer>{
	User findById(int id);
	User findByName(String name);
}
