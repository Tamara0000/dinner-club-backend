package com.dinnerclub.repository;

import com.dinnerclub.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer>, JpaSpecificationExecutor<Guest> {
    Optional<Guest> findByEmail(String email);
    List<Guest> findAllByNameLike(String name);
}
