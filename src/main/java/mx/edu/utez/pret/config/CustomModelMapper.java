package mx.edu.utez.pret.config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import mx.edu.utez.pret.model.*;
import mx.edu.utez.pret.pojo.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class CustomModelMapper {

    private ModelMapper modelMapper;
    
    public CustomModelMapper () {
        this.modelMapper = new ModelMapper();

        /*** El orden de colocar los TypeMap IMPORTA MUCHO!!  ***/

        TypeMap<EstadoRepublica, EstadoRepublicaPojo> estadoRepublicaPropertyMapper = this.modelMapper.createTypeMap(EstadoRepublica.class, EstadoRepublicaPojo.class);
        estadoRepublicaPropertyMapper.addMappings(mapper -> {
            mapper.skip(EstadoRepublicaPojo::setCandidatos);
            mapper.skip(EstadoRepublicaPojo::setUniversidades);
            mapper.skip(EstadoRepublicaPojo::setReclutadores);
        });

        TypeMap<Rol, RolPojo> rolPropertyMapper = this.modelMapper.createTypeMap(Rol.class, RolPojo.class);
        rolPropertyMapper.addMappings(mapper -> {
            mapper.skip(RolPojo::setId);
            mapper.skip(RolPojo::setUsuarios);
        });

        TypeMap<Beneficio, BeneficioPojo> beneficioPropertyMapper = this.modelMapper.createTypeMap(Beneficio.class, BeneficioPojo.class);
        beneficioPropertyMapper.addMappings(mapper -> {
            mapper.skip(BeneficioPojo::setVacantes);
        });

        TypeMap<Puesto, PuestoPojo> puestoPropertyMapper = this.modelMapper.createTypeMap(Puesto.class, PuestoPojo.class);
        puestoPropertyMapper.addMappings(mapper -> {
            mapper.skip(PuestoPojo::setReclutadores);
        });

        TypeMap<Candidato, CandidatoPojo> candidatoPropertyMapper = this.modelMapper.createTypeMap(Candidato.class, CandidatoPojo.class);
        candidatoPropertyMapper.addMappings(mapper -> {
            mapper.skip(CandidatoPojo::setContrasena);
        });

        TypeMap<Reclutador, ReclutadorPojo> reclutadorPropertyMapper = this.modelMapper.createTypeMap(Reclutador.class, ReclutadorPojo.class);
        reclutadorPropertyMapper.addMappings(mapper -> {
            mapper.skip(ReclutadorPojo::setContrasena);
            mapper.skip(ReclutadorPojo::setVacantes);
        });
        
        TypeMap<Vacante, VacantePojo> vacantePropertyMapper = this.modelMapper.createTypeMap(Vacante.class, VacantePojo.class);
        vacantePropertyMapper.addMappings(mapper -> {
            mapper.skip(VacantePojo::setPostulaciones);
            mapper.skip(VacantePojo::setCandidatosEnFavoritos);
            mapper.skip(VacantePojo::setBeneficios);
        });
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
          .stream()
          .map(element -> modelMapper.map(element, targetClass))
          .collect(Collectors.toList());
    }

    public <S, T> Set<T> mapSet(Set<S> source, Class<T> targetClass) {
        return source
          .stream()
          .map(element -> modelMapper.map(element, targetClass))
          .collect(Collectors.toSet());
    }

    public <S,  T> T map (S source, Class<T> destinationType) {
        return this.modelMapper.map(source, destinationType);
    } 
}
