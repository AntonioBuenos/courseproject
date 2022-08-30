package by.smirnov.courseproject.repository.guitar;

import by.smirnov.courseproject.model.Guitar;
import by.smirnov.courseproject.repository.CRUDRepository;

import java.util.Map;

public interface GuitarRepoInterface extends CRUDRepository<Long, Guitar> {

Map<String, Object> showAverageGuitarPrice();
}
