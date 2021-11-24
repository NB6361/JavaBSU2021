package by.labscheme.labscheme;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProgramRepo extends CrudRepository<Program, Long> {
    Iterable<Program> findAllBy();
}
