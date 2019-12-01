package my.upm.emma.testing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DaoInter extends JpaRepository<responsEntity,Long> {

}
