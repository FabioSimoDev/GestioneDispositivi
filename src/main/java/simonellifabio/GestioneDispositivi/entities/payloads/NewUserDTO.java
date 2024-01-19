package simonellifabio.GestioneDispositivi.entities.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

//questo viene usato per richiesta di un body user
public record NewUserDTO(@Email(message = "Email non valida")
                         @NotNull(message = "L'email non può essere null")
                         String email,
                         @NotNull(message = "Il nome non può essere null")
                         String name,
                         @NotNull(message = "Il cognome non può essere null")
                         String surname,
                         @NotNull(message = "L'username non può essere null")
                         String username) {
}
