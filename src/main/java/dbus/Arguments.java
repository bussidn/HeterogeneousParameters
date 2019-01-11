package dbus;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class Arguments {

    private Map<Class<?>, Map<String, Object>> heterogeneousNamedArguments = new HashMap<>();
    private Map<Class<?>, Map<String, List<?>>> heterogeneousNamedArgumentLists = new HashMap<>();

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

    <T> void putArgumentList(String key, Class<T> clazz, List<T> values) {
        getOptional(heterogeneousNamedArgumentLists, clazz)
                .ifPresentOrElse(m -> m.put(key, values), () -> createNewListTypeMapWith(clazz, key, values));
    }

    private <T> void createNewListTypeMapWith(Class<T> clazz, String key, List<T> values) {
        HashMap<String, List<?>> typeMap = new HashMap<>();
        typeMap.put(key, values);
        heterogeneousNamedArgumentLists.put(clazz, typeMap);
    }

    <T> List<T> getArgumentList(String key, Class<T> clazz) {
        return getOptional(heterogeneousNamedArgumentLists, clazz)
                .flatMap(m -> getOptional(m, key))
                .map(l -> map(l, clazz::cast))
                .orElseGet(Collections::emptyList);
    }

    private <T> List<T> map(List<?> list, Function<Object, T> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }
}
