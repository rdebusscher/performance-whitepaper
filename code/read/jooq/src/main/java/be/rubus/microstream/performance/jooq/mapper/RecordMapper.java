package be.rubus.microstream.performance.jooq.mapper;

import org.jooq.Field;
import org.jooq.Record;

import java.util.List;

@FunctionalInterface
public interface RecordMapper<T> {

    T buildFrom(Record record, List<Field<?>> fieldInfo);
}
