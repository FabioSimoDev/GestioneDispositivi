package simonellifabio.GestioneDispositivi.services;

import org.apache.coyote.BadRequestException;
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
import simonellifabio.GestioneDispositivi.entities.payloads.NewUserDTO;
import simonellifabio.GestioneDispositivi.exceptions.ItemNotFoundException;
import simonellifabio.GestioneDispositivi.repositories.DevicesDAO;
import simonellifabio.GestioneDispositivi.repositories.UsersDAO;

import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private DevicesDAO devicesDAO;

    public Page<User> getUsers(int page, int size, String orderBy){
        if(size >= 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return usersDAO.findAll(pageable);
    }


    public User findById(UUID id) {
        return usersDAO.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }

    @Transactional
    public User save(NewUserDTO body){
        usersDAO.findByEmail(body.email()).ifPresent(user -> {
            try {
                throw new BadRequestException("l'email " + user.getEmail() + " è già in uso");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        });

        User newUser = new User();
        newUser.setSurname(body.surname());
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setUsername(body.username());
        return usersDAO.save(newUser);
    }

    @Transactional
    public User findByIdAndUpdate(UUID id, User body) {
        User found = this.findById(id);
        found.setSurname(body.getSurname());
        found.setName(body.getName());
        found.setEmail(body.getEmail());
        found.setUsername(body.getUsername());
        return usersDAO.save(found);
    }

    public void findByIdAndDelete(UUID id){
        User found = this.findById(id);
        usersDAO.delete(found);
    }
    @Transactional
    public Device addDeviceToUser(UUID userId, NewDeviceDTO newDeviceDTO) {
        User user = usersDAO.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException(userId));


        Device device = new Device();
        //Trasformo la stringa 'type' per farla corrispondere al tipo corretto.
        //Deve essere una stringa in 'NewDeviceDTO' per poterla validare.
        //(Mi sono dovuto informare su come farlo)
        device.setType(DeviceType.valueOf(newDeviceDTO.type()));
        device.setUser(user);

        return devicesDAO.save(device);
    }
}
