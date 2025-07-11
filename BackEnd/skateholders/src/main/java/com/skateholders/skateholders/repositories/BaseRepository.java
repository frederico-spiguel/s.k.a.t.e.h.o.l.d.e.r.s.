package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository extends JpaRepository<Base, Long> {
    // O mtodo findAll() já está incluído por padrão e é tudo que precisamos.
}