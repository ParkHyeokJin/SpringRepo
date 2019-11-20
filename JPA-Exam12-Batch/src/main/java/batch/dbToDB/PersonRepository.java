package batch.dbToDB;

import org.springframework.data.jpa.repository.JpaRepository;

import batch.fileToDB.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
