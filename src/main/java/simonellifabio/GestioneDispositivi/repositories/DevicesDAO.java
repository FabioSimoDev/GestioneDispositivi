package simonellifabio.GestioneDispositivi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simonellifabio.GestioneDispositivi.entities.Device;

import java.util.UUID;

@Repository
public interface DevicesDAO extends JpaRepository<Device, UUID> {
}
