package simonellifabio.GestioneDispositivi.entities.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record NewDeviceDTO(
        @NotNull(message = "Il dispositivo non può essere null.")
        @Pattern(regexp = "ASSEGNATO|DISMESSO|DISPONIBILE|MANUTENZIONE", message = "il tipo del dispositivo non è valido")
        String type) {
}
