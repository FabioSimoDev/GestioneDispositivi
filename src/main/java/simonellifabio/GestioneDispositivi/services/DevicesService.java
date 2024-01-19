package simonellifabio.GestioneDispositivi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simonellifabio.GestioneDispositivi.repositories.DevicesDAO;

@Service
public class DevicesService {
    @Autowired
    private DevicesDAO devicesDAO;
}
