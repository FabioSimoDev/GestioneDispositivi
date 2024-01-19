package simonellifabio.GestioneDispositivi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simonellifabio.GestioneDispositivi.entities.Device;
import simonellifabio.GestioneDispositivi.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersDAO extends JpaRepository<User, UUID> {
    public Optional<User> findByEmail(String email);

    public List<User> findByDevices(Device device);
}
