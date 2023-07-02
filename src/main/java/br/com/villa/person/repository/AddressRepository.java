package br.com.villa.person.repository;

import br.com.villa.person.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    Optional<Address> findById(UUID uuid);

    List<Address> findAllByPersonId(UUID personId);
}
