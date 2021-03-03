package ar.com.ada.second.tdvr.model.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import java.util.IdentityHashMap;
import java.util.Map;

@Component
public class AvoidingMappingContext {
    private Map<Object, Object> knowInstances = new IdentityHashMap<>();

    @BeforeMapping
    public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
        return (T) knowInstances.get(source);
    }

    @BeforeMapping
    public void storeMappedInstance (Object source, @MappingTarget Object target) {
        knowInstances.put(source, target);
    }
}
