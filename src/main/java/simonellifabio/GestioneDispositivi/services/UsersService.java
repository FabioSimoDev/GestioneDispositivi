package simonellifabio.GestioneDispositivi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simonellifabio.GestioneDispositivi.repositories.UsersDAO;

@Service
public class UsersService {
    @Autowired
    private UsersDAO usersDAO;
}
