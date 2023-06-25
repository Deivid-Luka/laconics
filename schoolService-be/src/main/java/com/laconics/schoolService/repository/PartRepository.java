package com.laconics.schoolService.repository;

import com.laconics.schoolService.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Part findByName(String name);

    void deleteByName(String name);
}
