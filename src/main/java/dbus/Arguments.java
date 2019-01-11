package dbus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class Arguments {

    private Map<Class<?>, Map<String, Object>> heterogeneousNamedArguments = new HashMap<>();

    <T> Optional<T> getArgument(String name, Class<T> clazz) {
        return getOptional(heterogeneousNamedArguments, clazz)
                .flatMap(m -> getOptional(m, name))
                .map(clazz::cast);
    }

    <T> void putArgument(String name, Class<T> clazz, T value) {
        getOptional(heterogeneousNamedArguments, clazz)
                .ifPresentOrElse(m -> m.put(name, value), () -> createNewTypeMapWith(clazz, name, value));
    }

    private <T> void createNewTypeMapWith(Class<T> clazz, String name, T value) {
        HashMap<String, Object> typeMap = new HashMap<>();
        typeMap.put(name, value);
        heterogeneousNamedArguments.put(clazz, typeMap);
    }

    private <K, V> Optional<V> getOptional(Map<K, V> map, K key) {
        return Optional.ofNullable(map.get(key));
    }
}
