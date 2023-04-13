package it.emgtech.commandr.util;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper {
    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final MapperFacade mapper = mapperFactory.getMapperFacade();

    public <S, D> D map(S var1, Class<D> var2) {
        return mapper.map( var1, var2 );
    }
}
