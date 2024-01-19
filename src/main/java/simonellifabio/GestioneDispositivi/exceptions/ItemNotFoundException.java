package simonellifabio.GestioneDispositivi.exceptions;

import java.util.UUID;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(UUID id){
        super("L'elemento con l'id " + id + " non è stato trovato.");
    }

    public ItemNotFoundException(){
        super("Elemento non trovato");
    }
}
