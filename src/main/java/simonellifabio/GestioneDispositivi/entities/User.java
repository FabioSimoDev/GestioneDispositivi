package simonellifabio.GestioneDispositivi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String avatarURL;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Device> devices;
}
