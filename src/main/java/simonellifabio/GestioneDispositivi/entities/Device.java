package simonellifabio.GestioneDispositivi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import simonellifabio.GestioneDispositivi.entities.enums.DeviceType;

import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    private UUID id;
    private DeviceType type;

    @ManyToOne
    private User user;
}
