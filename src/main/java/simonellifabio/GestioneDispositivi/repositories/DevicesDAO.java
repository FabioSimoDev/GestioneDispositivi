package simonellifabio.GestioneDispositivi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simonellifabio.GestioneDispositivi.entities.Device;
import simonellifabio.GestioneDispositivi.entities.User;
import simonellifabio.GestioneDispositivi.entities.enums.DeviceType;

import java.util.List;
import java.util.UUID;

@Repository
public interface DevicesDAO extends JpaRepository<Device, UUID> {
    public List<Device> findByType(DeviceType type);

    public List<Device> findByUser(User user);
}
