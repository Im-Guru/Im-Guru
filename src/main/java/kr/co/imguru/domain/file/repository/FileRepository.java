package kr.co.imguru.domain.file.repository;

import kr.co.imguru.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query("SELECT f " +
            "FROM File f " +
            "WHERE f.fileCategory=:fileCategory " +
            "AND f.fileKey=:fileKey")
    List<File> findFileByFileKey(String fileCategory, Long fileKey);

    @Query("SELECT f " +
            "FROM File f " +
            "WHERE f.fileCategory=:fileCategory " +
            "AND f.fileKey=:fileKey")
    Optional<File> findOneFileByFileKey(String fileCategory, Long fileKey);

    @Modifying
    @Query("DELETE FROM File f " +
            "WHERE f.fileCategory = :fileCategory " +
            "AND f.fileKey = :fileKey")
    void deleteFileByFileKey(String fileCategory, Long fileKey);

}
