package com.learn.dams.repository;


import com.learn.dams.entities.Closure;
import com.learn.dams.entities.Concept;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClosureRepo extends CrudRepository<Closure, Long> {

	List<Closure> findByChild(Concept parent);
//	List<Closure> findByChildOrderByLevelAsc(Concept parent);




//	List<Closure> findByParentAndLevel(Concept parent, int level);
//	List<Closure> findByParent(Concept parent);
//	List<Closure> findByChildOrderByLevelDesc(Concept node);
//	@Procedure
//	void moveSubTree(long rootNodeID, long newParentId);
//
//	@Query(value="call dictvariables(?1,?2)", nativeQuery = true)
//	List<String> dictvariables(long rootid, String varname);
//
//	@Query(value="select clo from Closure clo inner join clo.child child where clo.level=1 and child.identifier=:identifier and clo.parent=:parent")
@Query(value="select clo from Closure clo" +
		" inner join clo.child child" +
		" where clo.level=1 and child.identifier=:identifier and clo.parent=:parent")
	List<Closure> findInBranchByConceptIdentifier(@Param("parent") Concept parent, @Param("identifier") String identifier);

}
