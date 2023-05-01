package capstone.capstone7.domain.diagnosis_result.controller;

import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisRequestDto;
import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResultFromAIServer;
import capstone.capstone7.domain.diagnosis_result.service.DiagnosisResultService;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class DiagnosisResultController {
    private final DiagnosisResultService diagnosisResultService;

    @PostMapping(value = "/diagnosis",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponseDto<DiagnosisResultFromAIServer> diagnosis(@RequestPart(value = "request") DiagnosisRequestDto diagnosisRequestDto, @RequestParam(value = "file") MultipartFile multipartFile){
        return new BaseResponseDto<>(diagnosisResultService.diagnosis(multipartFile, diagnosisRequestDto));
    }
}
