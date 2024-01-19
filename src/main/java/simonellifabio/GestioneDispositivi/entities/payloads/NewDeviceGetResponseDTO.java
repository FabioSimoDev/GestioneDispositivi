package simonellifabio.GestioneDispositivi.entities.payloads;

import simonellifabio.GestioneDispositivi.entities.enums.DeviceType;

import java.util.UUID;

//questo viene usato per tutte le get ai device, cosi da mostrare solo gli id degli utenti.
public record NewDeviceGetResponseDTO(UUID id, DeviceType type, UUID userId) {
}
