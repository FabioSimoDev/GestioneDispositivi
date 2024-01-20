package simonellifabio.GestioneDispositivi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonellifabio.GestioneDispositivi.entities.Device;
import simonellifabio.GestioneDispositivi.entities.User;
import simonellifabio.GestioneDispositivi.entities.enums.DeviceType;
import simonellifabio.GestioneDispositivi.entities.payloads.NewDeviceDTO;
import simonellifabio.GestioneDispositivi.exceptions.ItemNotFoundException;
import simonellifabio.GestioneDispositivi.repositories.DevicesDAO;
import simonellifabio.GestioneDispositivi.repositories.UsersDAO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DevicesService {
    @Autowired
    private DevicesDAO devicesDAO;

    @Autowired
    private UsersService usersService;

    public Page<Device> getDevices(int page, int size, String orderBy) {
        if (size >= 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return devicesDAO.findAll(pageable);
    }

    @Transactional
    public Device save(NewDeviceDTO body){
        Device newDevice = new Device();
        User user = usersService.findById(body.userId());
        newDevice.setType(DeviceType.valueOf(body.type()));
        newDevice.setId(body.userId());
        return devicesDAO.save(newDevice);
    }

    public Device findById(UUID id){
        return devicesDAO.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    public void findByIdAndDelete(UUID id){
        Device found = this.findById(id);
        devicesDAO.delete(found);
    }

    public Device findByIdAndUpdate(UUID id, NewDeviceDTO body){
        Device found = this.findById(id);
        found.setType(DeviceType.valueOf(body.type()));
        return devicesDAO.save(found);
    }

    public List<Device> findByUser(UUID userId){
        User user = usersService.findById(userId);
        return devicesDAO.findByUser(user);
    }

    public List<Device> findByType(DeviceType type){
        return devicesDAO.findByType(type);
    }
}
