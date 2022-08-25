package mx.edu.utez.pret.config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import mx.edu.utez.pret.model.*;
import mx.edu.utez.pret.pojo.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class CustomModelMapper {

    private ModelMapper modelMapper;
    
    public CustomModelMapper () {
        this.modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        PropertyMap<ExperienciaLaboral, ExperienciaLaboralPojo> experienciaLaboralPropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getCandidato());
            }
        };

        PropertyMap<EstadoVacante, EstadoVacantePojo> estadoVacantePropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getPostulaciones());
            }
        };

        PropertyMap<EstadoRepublica, EstadoRepublicaPojo> estadoRepublicaPropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getCandidatos());
                skip(destination.getUniversidades());
                skip(destination.getReclutadores());
            }
        };

        PropertyMap<Rol, RolPojo> rolPropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getUsuarios());
            }
        };

        PropertyMap<Beneficio, BeneficioPojo> beneficioPropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getVacantes());
            }
        };

        PropertyMap<Puesto, PuestoPojo> puestoPropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getReclutadores());
            }
        };

        PropertyMap<Curso, CursoPojo> cursoPropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getCandidato());
            }

        };

        PropertyMap<Certificacion, CertificacionPojo> certificacionPropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getCandidato());
            }

        };

        PropertyMap<Candidato, CandidatoPojo> candidatoPropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getContrasena());
                skip(destination.getContactos());
                skip(destination.getPostulaciones());
            }
        };

        PropertyMap<Reclutador, ReclutadorPojo> reclutadorPropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getContrasena());
                skip(destination.getVacantes());
            }
        };
        
        PropertyMap<Vacante, VacantePojo> vacantePropertyMapper = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getPostulaciones());
                skip(destination.getCandidatosEnFavoritos());
            }
        };

        PropertyMap<IdiomaCandidato, IdiomaCandidatoPojo> idiomaCandidatoPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getCandidato());
            }
        };

        PropertyMap<Idioma, IdiomaPojo> idiomaPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getCandidatos());
            }
        };

        PropertyMap<Estudio, EstudioPojo> estudioPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getCandidato());
            }
        };

        PropertyMap<Universidad, UniversidadPojo> universidadPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                skip(destination.getEstudios());
            }
        };

        modelMapper.addMappings(experienciaLaboralPropertyMapper);
        modelMapper.addMappings(estadoVacantePropertyMapper);
        modelMapper.addMappings(estadoRepublicaPropertyMapper);
        modelMapper.addMappings(rolPropertyMapper);
        modelMapper.addMappings(beneficioPropertyMapper);
        modelMapper.addMappings(puestoPropertyMapper);
        modelMapper.addMappings(cursoPropertyMapper);
        modelMapper.addMappings(certificacionPropertyMapper);
        modelMapper.addMappings(candidatoPropertyMapper);
        modelMapper.addMappings(reclutadorPropertyMapper);
        modelMapper.addMappings(vacantePropertyMapper);
        modelMapper.addMappings(idiomaCandidatoPropertyMap);
        modelMapper.addMappings(idiomaPropertyMap);
        modelMapper.addMappings(universidadPropertyMap);
        modelMapper.addMappings(estudioPropertyMap);
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
