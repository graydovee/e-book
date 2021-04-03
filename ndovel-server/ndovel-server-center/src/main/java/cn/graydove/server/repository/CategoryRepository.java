package cn.graydove.server.repository;

import cn.graydove.server.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * @author graydove
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
