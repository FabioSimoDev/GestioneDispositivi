package simonellifabio.GestioneDispositivi.entities.payloads;

public record NewUserDTO(String email,
                         String name,
                         String surname,
                         String username) {
}
