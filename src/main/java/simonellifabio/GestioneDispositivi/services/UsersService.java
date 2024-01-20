package simonellifabio.GestioneDispositivi.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import simonellifabio.GestioneDispositivi.entities.Device;
import simonellifabio.GestioneDispositivi.entities.User;
import simonellifabio.GestioneDispositivi.entities.enums.DeviceType;
import simonellifabio.GestioneDispositivi.entities.payloads.NewAvatarResponseDTO;
import simonellifabio.GestioneDispositivi.entities.payloads.NewDeviceDTO;
import simonellifabio.GestioneDispositivi.entities.payloads.NewUserDTO;
import simonellifabio.GestioneDispositivi.exceptions.ItemNotFoundException;
import simonellifabio.GestioneDispositivi.repositories.DevicesDAO;
import simonellifabio.GestioneDispositivi.repositories.UsersDAO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private DevicesDAO devicesDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

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
    public User findByIdAndUpdate(UUID id, NewUserDTO body) {
        User found = this.findById(id);
        found.setSurname(body.surname());
        found.setName(body.name());
        found.setEmail(body.email());
        found.setUsername(body.username());
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

    public User uploadPicture(MultipartFile file, UUID userId) throws IOException {

        User user = this.findById(userId);

        String url = (String) cloudinaryUploader.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap())
                .get("url");

        //metto l'url nell'utente e lo aggiorno nel DB
        user.setAvatarURL(url);
        usersDAO.save(user);
        return user;
    }

    public User findByDevice(UUID id){
        Optional<Device> found = devicesDAO.findById(id);
        Device device = new Device();
        if(found.isPresent()) device = found.get();
        else throw new ItemNotFoundException(id);
        return usersDAO.findByDevices(device);
    }
}
