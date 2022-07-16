package mx.edu.utez.pret.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

@Component
public class CustomModelMapper {
    
    private Mapper modelMapper;
    
    public CustomModelMapper () {
        this.modelMapper = DozerBeanMapperBuilder.buildDefault();
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
          .stream()
          .map(element -> modelMapper.map(element, targetClass))
          .collect(Collectors.toList());
    }

    public <S,  T> T map (S source, Class<T> destinationType) {
        return this.modelMapper.map(source, destinationType);
    } 
}
