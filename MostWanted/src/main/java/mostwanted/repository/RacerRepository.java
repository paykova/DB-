package mostwanted.repository;

import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Integer> {
    Optional<Racer> findByName(String name);

    @Query("" +
            "SELECT r " +
            "FROM mostwanted.domain.entities.Racer r " +
            "JOIN r.cars c " +
            "GROUP BY r " +
            "ORDER BY size(r.cars) DESC, r.name"
    )
    List<Racer> exportRacingCars();

    // тази заявка не е пълна..
    // SELECT r.name, c.brand, c.model, c.year_of_production
    // FROM racers r JOIN cars c on r.id = c.racer_id
    // GROUP BY c.id ORDER BY count(c.id) DESC, r.name
}
