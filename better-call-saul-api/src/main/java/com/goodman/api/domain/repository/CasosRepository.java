package com.goodman.api.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goodman.api.domain.model.Caso;

@Repository
public interface CasosRepository extends JpaRepository<Caso,UUID> {

}
