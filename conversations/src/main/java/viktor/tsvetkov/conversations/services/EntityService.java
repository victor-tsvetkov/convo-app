package viktor.tsvetkov.conversations.services;

import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.entities.EntityItem;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class EntityService<Entity extends EntityItem, EntityRepo extends JpaRepository<Entity, UUID>> {

    private final EntityRepo repository;

    public void save(@Nonnull Entity entity) throws Exception {
        if (entity.getId() == null || !repository.existsById(entity.getId())) {
            repository.save(entity);
            log.info("Entity with id {} was successfully saved", entity.getId());
        } else {
            log.warn("Entity with id {} already exists", entity.getId());
            throw new Exception("The entity with id " + entity.getId() + " already exists");
        }
    }

    public Entity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error("Entity with id {} wasn't found", id);
            return new EntityNotFoundException("Entity with id " + id + " wasn't found");
        });
    }

    public List<Entity> findEntitiesByIds(List<UUID> ids) {
        return repository.findAllById(ids);
    }

    public void remove(UUID id) {
        Entity entity = repository.findById(id).orElseThrow(() -> {
            log.error("Entity with id {} wasn't found", id);
            return new EntityNotFoundException("Entity with id " + id + "wasn't found");
        });
        repository.delete(entity);
        log.info("Entity with id {} wasn't found", id);
    }
}
