package com.zoey.ppmtools.repositories;

import com.zoey.ppmtools.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String projectId);

    @Override
    Iterable<Project> findAll();

    Iterable<Project> findAllByProjectLeader(String username);

    @Override
    Iterable<Project> findAllById(Iterable<Long> iterable);
}
