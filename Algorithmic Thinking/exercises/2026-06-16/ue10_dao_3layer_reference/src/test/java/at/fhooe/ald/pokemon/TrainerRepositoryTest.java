package at.fhooe.ald.pokemon;

import at.fhooe.ald.pokemon.model.Trainer;
import at.fhooe.ald.pokemon.dataaccess.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class TrainerRepositoryTest {

    @Autowired
    private TrainerRepository trainerRepository;

    @Test
    void trainer_saveAndFindById() {
        Trainer ash = trainerRepository.save(new Trainer("Ash"));
        assertTrue(ash.getId() > 0, "id should be set after save");
        Trainer found = trainerRepository.findById(ash.getId()).orElseThrow();
        assertEquals("Ash", found.getName());
    }

    @Test
    void trainer_findAll() {
        trainerRepository.save(new Trainer("Misty"));
        trainerRepository.save(new Trainer("Brock"));
        List<Trainer> all = trainerRepository.findAll();
        assertTrue(all.size() >= 2);
    }

    @Test
    void trainer_update() {
        Trainer gary = trainerRepository.save(new Trainer("Gary"));
        gary.setBadgeCount(8);
        trainerRepository.save(gary);
        assertEquals(8, trainerRepository.findById(gary.getId()).orElseThrow().getBadgeCount());
    }
}
