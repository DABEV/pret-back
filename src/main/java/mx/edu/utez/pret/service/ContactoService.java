package mx.edu.utez.pret.service;

import java.util.Optional;

import mx.edu.utez.pret.model.Contacto;
import mx.edu.utez.pret.model.ContactoId;

public interface ContactoService {
    Optional<Contacto> obtenerPorId(ContactoId id);
    Contacto guardar(Contacto contacto);
    Boolean eliminar(ContactoId id);
}
