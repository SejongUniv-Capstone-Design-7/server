package capstone.capstone7.domain.diagnosis_result.repository;

import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResultMonthlyCount;
import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResultOfRegionDto;
import capstone.capstone7.domain.diagnosis_result.entity.DiagnosisResult;
import capstone.capstone7.domain.member.entity.enums.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DiagnosisResultRepository extends JpaRepository<DiagnosisResult, Long> {

    // 월별 지역별 병해충별 발생횟수
    @Query("select new capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResultOfRegionDto(d.diseaseName, count(*)) from DiagnosisResult d where d.createdDate >= :startDate and d.createdDate < :endDate and d.region = :region group by d.diseaseName")
    List<DiagnosisResultOfRegionDto> findByRegion(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("region") Region region);

    // 월별 지역별 병해충 발생횟수
    @Query("select new capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResultMonthlyCount(d.region, count(*)) from DiagnosisResult d where d.createdDate >= :startDate and d.createdDate < :endDate group by d.region")
    List<DiagnosisResultMonthlyCount> findMonthlyDiseaseCount(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
