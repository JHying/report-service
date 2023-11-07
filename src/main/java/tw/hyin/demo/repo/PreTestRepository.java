package tw.hyin.demo.repo;

import tw.hyin.demo.entity.PreTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PreTestRepository extends JpaRepository<PreTest, Integer>, JpaSpecificationExecutor<PreTest> {

}