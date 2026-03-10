package com.example.sehetak_bethakaa.repository;

import com.example.sehetak_bethakaa.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    List<Disease> findByNameContainingIgnoreCase(String name);

    List<Disease> findByGenderIn(List<String> genders);

    List<Disease> findByTypeIn(List<String> types);

    List<Disease> findByIdIn(List<Long> ids);

    // Custom query for type AND gender filtering
    @Query("SELECT d FROM Disease d WHERE d.type IN :types AND d.gender IN :genders")
    List<Disease> findByTypeAndGenderIn(@Param("types") List<String> types,
            @Param("genders") List<String> genders);

}