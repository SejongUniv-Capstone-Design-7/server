package capstone.capstone7.domain.diagnosis_result.repository;

import capstone.capstone7.domain.diagnosis_result.entity.DiagnosisResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisResultRepository extends JpaRepository<DiagnosisResult, Long> {
}
