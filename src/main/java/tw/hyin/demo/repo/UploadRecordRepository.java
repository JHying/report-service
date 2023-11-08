package tw.hyin.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tw.hyin.demo.entity.UploadRecord;

@RepositoryRestResource(exported = false)
public interface UploadRecordRepository extends JpaRepository<UploadRecord, Integer>, JpaSpecificationExecutor<UploadRecord> {

}