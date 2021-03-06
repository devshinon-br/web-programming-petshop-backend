package com.petshop.trabalho.veterinary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface VeterinaryRepository extends JpaRepository<Veterinary, Long> {
    Set<Veterinary> findByIdIn(Collection<Long> personCollection);
}
