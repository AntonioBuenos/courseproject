package by.smirnov.guitarshopproject.service;

import by.smirnov.guitarshopproject.model.Guitar;
import by.smirnov.guitarshopproject.repository.guitar.HibernateGuitarRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuitarService {

    private final HibernateGuitarRepo hibernateGuitarRepo;

    public Guitar findById(Long id) {
        return hibernateGuitarRepo.findById(id);
    }

    public List<Guitar> findAll() {
        return hibernateGuitarRepo.findAll();
    }

    public List<Guitar> findAll(int limit, int offset) {
        return hibernateGuitarRepo.findAll(limit, offset);
    }

    public void create(Guitar object) {
        hibernateGuitarRepo.create(object);
    }

    public Guitar update(Guitar guitar) {
        return hibernateGuitarRepo.update(guitar);
    }

    public void delete(Long id) {
        hibernateGuitarRepo.delete(id);
    }

    public Double showAverageGuitarPrice() {
/*        Session session = sessionFactory.getCurrentSession();

        List<Double> result = session.createQuery("select avg(p.price) from Guitar p where p.isDeleted = false", Double.class)
                .getResultList();*/
        return hibernateGuitarRepo.showAverageGuitarPrice();
    }
}
