package com.community.Community.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.community.Community.models.Report;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{

}
