package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Contact;
<<<<<<< HEAD
import com.smart.entities.User;

public interface ContactRepository extends JpaRepository<Contact, Integer>{

	@Query("from Contact c where c.user.id =:userId")
	
	public Page<Contact> getContactsByUser(@Param("userId") int userId, Pageable pageable);
	
	public List<Contact> findByNameContainingAndUser(String name, User user);
=======

public interface ContactRepository extends JpaRepository<Contact, Integer>{

	@Query("from Contact c where c.user.id =:userId")
	
	public Page<Contact> getContactsByUser(@Param("userId") int userId, Pageable pageable);
>>>>>>> branch 'main' of https://github.com/parascode/smart-contact-manager.git
}
