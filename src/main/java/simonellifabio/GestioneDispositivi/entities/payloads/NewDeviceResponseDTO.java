package simonellifabio.GestioneDispositivi.entities.payloads;

import java.util.UUID;

//questo viene usato come return per le richieste POST|PUT
public record NewDeviceResponseDTO(UUID id) {
}
