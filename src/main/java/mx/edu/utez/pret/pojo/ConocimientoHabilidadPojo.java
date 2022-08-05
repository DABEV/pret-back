package mx.edu.utez.pret.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ConocimientoHabilidadPojo {
    private List<String> conocimientos;
    private List<String> habilidades;
}
