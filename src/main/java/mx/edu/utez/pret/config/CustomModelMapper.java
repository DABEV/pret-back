package mx.edu.utez.pret.config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import mx.edu.utez.pret.model.Rol;
import mx.edu.utez.pret.pojo.RolPojo;
import mx.edu.utez.pret.model.EstadoRepublica;
import mx.edu.utez.pret.pojo.EstadoRepublicaPojo;

@Component
public class CustomModelMapper {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    
    private ModelMapper modelMapper;
    
    public CustomModelMapper () {
        this.modelMapper = modelMapper();
        
        TypeMap<Rol, RolPojo> rolPropertyMapper = this.modelMapper.createTypeMap(Rol.class, RolPojo.class);
        rolPropertyMapper.addMappings(mapper -> {
            mapper.skip(RolPojo::setId);
            mapper.skip(RolPojo::setUsuarios);
        });
        
        TypeMap<EstadoRepublica, EstadoRepublicaPojo> estadoRepublicaPropertyMapper = this.modelMapper.createTypeMap(EstadoRepublica.class, EstadoRepublicaPojo.class);
        estadoRepublicaPropertyMapper.addMappings(mapper -> {
            mapper.skip(EstadoRepublicaPojo::setCandidatos);
            mapper.skip(EstadoRepublicaPojo::setUniversidades);
            mapper.skip(EstadoRepublicaPojo::setReclutadores);
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
