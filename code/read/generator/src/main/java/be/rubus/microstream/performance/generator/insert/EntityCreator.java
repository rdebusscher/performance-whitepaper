package be.rubus.microstream.performance.generator.insert;

@FunctionalInterface
public interface EntityCreator<P,E> {

    E createEntity(P pojo);
}
