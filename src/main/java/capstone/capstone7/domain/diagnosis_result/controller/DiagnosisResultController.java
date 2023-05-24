package capstone.capstone7.domain.diagnosis_result.controller;

import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisRequestDto;
import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResponseDto;
import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResultFromAIServer;
import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResultOfRegionDto;
import capstone.capstone7.domain.diagnosis_result.service.DiagnosisResultService;
import capstone.capstone7.global.auth.entity.LoginUser;
import capstone.capstone7.global.common.response.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DiagnosisResultController {
    private final DiagnosisResultService diagnosisResultService;

    @PostMapping(value = "/diagnosis",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponseDto<DiagnosisResponseDto> diagnosis(@RequestPart(value = "request") DiagnosisRequestDto diagnosisRequestDto, @RequestParam(value = "file") MultipartFile multipartFile, @AuthenticationPrincipal  LoginUser loginUser){
        return new BaseResponseDto<>(diagnosisResultService.diagnosis(multipartFile, diagnosisRequestDto, loginUser));
    }

    @GetMapping(value = "/test")
    public String test(){
        return diagnosisResultService.test();
    }

    @GetMapping(value = "/diagnosis-result")
    public BaseResponseDto<List<DiagnosisResultOfRegionDto>> diagnosisResultOfRegion(@RequestParam(value = "region") String region){

        return new BaseResponseDto<>(diagnosisResultService.diagnosisResultOfRegion(region));
    }

}
