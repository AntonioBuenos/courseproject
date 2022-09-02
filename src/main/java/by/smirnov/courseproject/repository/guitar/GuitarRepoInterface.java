package by.smirnov.courseproject.repository.guitar;

import by.smirnov.courseproject.model.Guitar;
import by.smirnov.courseproject.repository.CRUDRepository;

public interface GuitarRepoInterface extends CRUDRepository<Long, Guitar> {

Double showAverageGuitarPrice();
}
