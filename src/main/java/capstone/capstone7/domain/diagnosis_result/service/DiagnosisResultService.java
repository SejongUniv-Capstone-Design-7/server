package capstone.capstone7.domain.diagnosis_result.service;

import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisRequestDto;
import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResponseDto;
import capstone.capstone7.global.S3.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class DiagnosisResultService {
    private final FileService fileService;

    public DiagnosisResponseDto diagnosis(MultipartFile cropImage, DiagnosisRequestDto diagnosisRequestDto){
        fileService.uploadFileLocal(cropImage, diagnosisRequestDto.getCrop_sort());
        return null;
    }
}
