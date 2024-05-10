package com.learn.dams.repository;


import com.learn.dams.entities.Concept;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConceptRepo extends CrudRepository<Concept, Integer> {

//	List<Concept> findByIdentifierIgnoreCase(String identifier);
////	 @Query(nativeQuery = true,value = "call onlyRoot")
//	List<Concept> findAllRoots();
//
//	 List<Concept> findByLabel(String label);
//	List<Concept> findAllByIdentifierAndLabel(String identifier, String label);
//	List<Concept> findAllByIdentifier(String identifier);
}
