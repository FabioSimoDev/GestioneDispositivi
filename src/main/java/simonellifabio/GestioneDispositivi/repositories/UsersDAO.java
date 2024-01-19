package simonellifabio.GestioneDispositivi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simonellifabio.GestioneDispositivi.entities.User;

import java.util.UUID;

@Repository
public interface UsersDAO extends JpaRepository<User, UUID> {
}
