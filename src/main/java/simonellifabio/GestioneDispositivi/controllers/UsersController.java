package simonellifabio.GestioneDispositivi.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import simonellifabio.GestioneDispositivi.entities.Device;
import simonellifabio.GestioneDispositivi.entities.User;
import simonellifabio.GestioneDispositivi.entities.payloads.*;
import simonellifabio.GestioneDispositivi.services.UsersService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "id") String orderBy){
        return usersService.getUsers(page, size, orderBy);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId) {
        return usersService.findById(userId);
    }

    @GetMapping("/device/{deviceId}")
    public User getUserByDevice(@PathVariable UUID deviceId){
        return usersService.findByDevice(deviceId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO createUser(@RequestBody @Validated NewUserDTO newUserPayload, BindingResult validation) {
        System.out.println(validation);
        if (validation.hasErrors()) {
            validation.getAllErrors().get(0).getDefaultMessage();
            try {
                throw new BadRequestException(validation.getAllErrors().get(0).getDefaultMessage());
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        } else {
            User newUser = usersService.save(newUserPayload);
            return new NewUserResponseDTO(newUser.getId());
        }
    }

    @PutMapping("/{userId}")
    public NewUserResponseDTO getUserByIdAndUpdate(@PathVariable UUID userId, @RequestBody NewUserDTO modifiedUserPayload) {
        User user = usersService.findByIdAndUpdate(userId, modifiedUserPayload);
        return new NewUserResponseDTO(user.getId());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getUserByIdAndDelete(@PathVariable UUID userId) {
        usersService.findByIdAndDelete(userId);
    }

    @PostMapping("/{userId}/devices")
    public NewDeviceResponseDTO addDeviceToUser(@PathVariable UUID userId, @RequestBody NewDeviceDTO newDeviceDTO) {
        Device device = usersService.addDeviceToUser(userId, newDeviceDTO);
        return new NewDeviceResponseDTO(device.getId());
    }

    @PostMapping("/{userId}/upload")
    public User uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable UUID userId) throws IOException {
        return usersService.uploadPicture(file, userId);
    }
}
