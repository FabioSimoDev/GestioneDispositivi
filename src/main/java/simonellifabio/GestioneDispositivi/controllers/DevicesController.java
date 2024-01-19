package simonellifabio.GestioneDispositivi.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import simonellifabio.GestioneDispositivi.entities.Device;
import simonellifabio.GestioneDispositivi.entities.User;
import simonellifabio.GestioneDispositivi.entities.payloads.NewDeviceDTO;
import simonellifabio.GestioneDispositivi.entities.payloads.NewUserDTO;
import simonellifabio.GestioneDispositivi.entities.payloads.NewUserResponseDTO;
import simonellifabio.GestioneDispositivi.services.DevicesService;

import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DevicesController {
    @Autowired
    DevicesService devicesService;

    @GetMapping
    public Page<Device> getDevices(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String orderBy){

        return devicesService.getDevices(page, size, orderBy);
    }

    @GetMapping("/{deviceId}")
    public Device getDeviceById(@PathVariable UUID deviceId) {
        return devicesService.findById(deviceId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO createDevice(@RequestBody @Validated NewDeviceDTO newDevicePayload, BindingResult validation) {
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            try {
                throw new BadRequestException("Ci sono errori nel payload!");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        } else {
            Device newDevice = devicesService.save(newDevicePayload);
            return new NewUserResponseDTO(newDevice.getId());
        }
    }

    @PutMapping("/{deviceId}")
    public Device getDeviceByIdAndUpdate(@PathVariable UUID deviceId, @RequestBody NewDeviceDTO modifiedDevicePayload) {
        return devicesService.findByIdAndUpdate(deviceId, modifiedDevicePayload);
    }

    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getDeviceByIdAndDelete(@PathVariable UUID deviceId) {
        devicesService.findByIdAndDelete(deviceId);
    }
}
