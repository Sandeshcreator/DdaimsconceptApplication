package com.learn.dams.services.impl;

import com.learn.dams.entities.Closure;
import com.learn.dams.entities.Concept;
import com.learn.dams.exception.ObjectNotFoundException;
import com.learn.dams.repository.ClosureRepo;
import com.learn.dams.repository.ConceptRepo;
import com.learn.dams.services.ClosureService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClosureServiceImpl implements ClosureService {
    private static final Logger logger = LoggerFactory.getLogger(ClosureService.class);
    @Autowired
    private ConceptRepo conceptRepo;
    @Autowired
    private ClosureRepo closureRepo;
    @Autowired
    private  Closure closure;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void createClosure(Long conceptId , String identifier) throws ObjectNotFoundException, ChangeSetPersister.NotFoundException {
        Concept concept = conceptRepo.findById(conceptId)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        saveToTree(concept,identifier);

    }

    public Concept saveToTree(Concept parent, String identifier) throws ObjectNotFoundException {
        Concept ret = new Concept();
        ret.setIdentifier(identifier);
        ret = saveToTree(parent, ret);
        return ret;
    }


    @Transactional
    public Concept save(Concept concept) {
        concept = conceptRepo.save(concept);
        return concept;
    }

    @Transactional
    public Concept saveToTree(Concept parent, Concept node) throws ObjectNotFoundException {
        return saveToTreeFast(parent, node);
    }

    @Transactional
    public Concept saveToTreeFast(Concept parent, Concept node) throws ObjectNotFoundException {
//        if (node != null && node.getIdentifier() == null) {
//            throw new ObjectNotFoundException("saveToTree. Identifier is null", logger);
//        }
        if (node != null && node.getIdentifier().length() > 0) {
            if (parent == null) {//search for tree root with the same identifier
                List<Concept> concepts = conceptRepo.findByIdentifierIgnoreCase(node.getIdentifier());
                for (Concept conc : concepts) {
                    List<Closure> list = closureRepo.findByChild(conc);
                    //if(conc.getParents().size()==1) {
                    if (list.size() == 1) {
                        if (node.getLabel() != null) {
                            conc.setLabel(node.getLabel().trim());
                        } else {
                            if (conc.getLabel() == null) {
                                conc.setLabel("");
                            }
                        }
                        conc = conceptRepo.save(conc);
                        try {
                            entityManager.flush();
                        } catch (Exception e) {
                            // nothing to do
                        }
                        if (parent != null) {
                            entityManager.refresh(parent);    //to ensure new children
                        }
                        return conc;                                //we will return the root of the tree with the same identifier
                    }
                }
            }
            List<Closure> toInsert = new ArrayList<Closure>();
            //closure to itself
            Closure closure = new Closure();
            closure.setChild(node);
            closure.setParent(node);
            closure.setLevel(0);
            toInsert.add(closure);
            if (parent != null) {
                parent = loadConceptById(parent.getId());

                //Concept oldNode = findConceptInBranchByIdentifier(parent, node.getIdentifier());
                Concept oldNode = findActivConceptInBranchByIdentifier(parent, node.getIdentifier());
                if (oldNode.getId() > 0) {
                    if (node.getLabel() != null) {
                        oldNode.setLabel(node.getLabel().trim());
                    }
                    oldNode.setActive(node.getActive());
                    oldNode = conceptRepo.save(oldNode);
                    try {
                        entityManager.flush();
                    } catch (Exception e) {
                        // nothing to do
                    }
                    if (parent != null) {
                        entityManager.refresh(parent);    //to ensure new children
                    }
                    return oldNode;                                    //we will return the node with the same identifier
                }

                //closures to all parents of the parent and parent itself ONLY FOR NEW CONCEPT!!!!
                List<Closure> allParents = closureRepo.findByChild(parent);
                for (Closure clos : allParents) {
                    Closure closure1 = new Closure();
                    closure1.setChild(node);
                    closure1.setParent(clos.getParent());
                    closure1.setLevel(clos.getLevel() + 1);
                    toInsert.add(closure1);
                }
            }
            closureRepo.saveAll(toInsert);
            node = conceptRepo.save(node);
            entityManager.flush();
            if (parent != null) {
                entityManager.refresh(parent);    //to ensure new children
            }
            entityManager.refresh(node);
            return node;
        } else {
            throw new ObjectNotFoundException("Node is null or node identifier not defined. Cannot save to the tree!", logger);
        }
    }


    @Transactional
    public Concept loadConceptById(long id) throws ObjectNotFoundException {
        Optional<Concept> parento = conceptRepo.findById(id);
        if (parento.isPresent()) {
            return parento.get();
        } else {
            throw new ObjectNotFoundException("loadConceptById. Parent concept not found, id=" + id, logger);
        }
    }

    @Transactional
    public Concept findActivConceptInBranchByIdentifier(Concept root, String identifier) throws ObjectNotFoundException {
        List<Closure> childs = closureRepo.findInBranchByConceptIdentifier(root, identifier);
        if (childs.size() == 1) {
            return childs.get(0).getChild();
        } else {
            if (childs.size() == 0) {
                Concept ret = new Concept();
                ret.setIdentifier(identifier);
                return ret;
            } else {
                List<Closure> activ = new ArrayList<Closure>();
                for (Closure clo : childs) {
                    if (clo.getChild().getActive()) {
                        activ.add(clo);
                    }
                }
                if (activ.size() == 0) {
                    Concept ret1 = new Concept();
                    ret1.setIdentifier(identifier);
                    return ret1;
                } else if (activ.size() == 1) {
                    return activ.get(0).getChild();
                } else {
                    throw new ObjectNotFoundException(
                            "findConceptInBranchByIdentifier.  The branch has more than one child with the same Identifier. Closures IDs are" + activ
                                    + " branch root is " + root, logger);
                }
            }
        }
    }




}
