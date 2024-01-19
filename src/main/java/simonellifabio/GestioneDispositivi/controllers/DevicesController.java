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
import simonellifabio.GestioneDispositivi.entities.enums.DeviceType;
import simonellifabio.GestioneDispositivi.entities.payloads.*;
import simonellifabio.GestioneDispositivi.services.DevicesService;

import java.util.ArrayList;
import java.util.List;
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
    public NewDeviceGetResponseDTO getDeviceById(@PathVariable UUID deviceId) {
        Device device = devicesService.findById(deviceId);
        return new NewDeviceGetResponseDTO(
                device.getId(),
                device.getType(),
                device.getUser().getId()
        );
    }

    @GetMapping("/user/{userId}")
    public List<Device> getByUser(@PathVariable UUID userId){
        return devicesService.findByUser(userId);
    }

    @GetMapping("/")
    public List<NewDeviceGetResponseDTO> getByType(@RequestParam(name = "type", required = false) String type){
        //mettere controllo per vedere se il type è valido
        DeviceType deviceType = DeviceType.valueOf(type);
        List<Device> devices = devicesService.findByType(deviceType);
        List<NewDeviceGetResponseDTO> newDeviceGetResponseDTOList = new ArrayList<>();
        devices.forEach(device -> {
            NewDeviceGetResponseDTO newDeviceGetResponseDTO = new NewDeviceGetResponseDTO(device.getId(), device.getType(), device.getUser().getId());
            newDeviceGetResponseDTOList.add(newDeviceGetResponseDTO);
        });
        return newDeviceGetResponseDTOList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewDeviceResponseDTO createDevice(@RequestBody @Validated NewDeviceDTO newDevicePayload, BindingResult validation) {
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
            return new NewDeviceResponseDTO(newDevice.getId());
        }
    }

    @PutMapping("/{deviceId}")
    public NewDeviceResponseDTO getDeviceByIdAndUpdate(@PathVariable UUID deviceId, @RequestBody NewDeviceDTO modifiedDevicePayload) {
        Device device = devicesService.findByIdAndUpdate(deviceId, modifiedDevicePayload);
        return new NewDeviceResponseDTO(device.getId());
    }

    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getDeviceByIdAndDelete(@PathVariable UUID deviceId) {
        devicesService.findByIdAndDelete(deviceId);
    }
}
