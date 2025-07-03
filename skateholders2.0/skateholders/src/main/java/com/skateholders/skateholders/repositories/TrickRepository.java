package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.Trick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrickRepository extends JpaRepository<Trick, Long> {}
