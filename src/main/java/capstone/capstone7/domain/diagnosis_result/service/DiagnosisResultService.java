package capstone.capstone7.domain.diagnosis_result.service;

import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisRequestDto;
import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisRequestToAIServer;
import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResultFromAIServer;
import capstone.capstone7.global.S3.FileService;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import static capstone.capstone7.global.error.enums.ErrorMessage.EMPTY_FILE;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class DiagnosisResultService {
    private final FileService fileService;
    private WebClient webClient;

    @Value("${ai-server:url}")
    private String aiServerUrl;
    @PostConstruct
    public void initWebClient() {
        webClient = WebClient.create(aiServerUrl);
    }

    public DiagnosisResultFromAIServer diagnosis(MultipartFile cropImage, DiagnosisRequestDto diagnosisRequestDto){
        if(cropImage.isEmpty()){
            throw new BusinessException(EMPTY_FILE);
        }

        // fileService.uploadFileToLocal(cropImage, diagnosisRequestDto.getCrop_sort());
        // File crop_image_file = fileService.convertFromMultipartToFile(cropImage);
        String fileName = fileService.getDiagnosisFileNameSendToAIServer(cropImage, diagnosisRequestDto.getCrop_sort());
        log.info("filename : {}", fileName);

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("image", cropImage.getResource()).filename(fileName);
        multipartBodyBuilder.part("crop_sort", diagnosisRequestDto.getCrop_sort());

        DiagnosisResultFromAIServer diagnosisResultFromAIServer = webClient.post()
                .uri("/model")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .retrieve()
                .bodyToMono(DiagnosisResultFromAIServer.class)
                .doOnError(e -> log.error("Mapping Error : ", e))
                .block();


        // 받아온 정보로 diagnosis Result에 저장
            /*DiagnosisResult.builder()
                    .member()
                    .diseaseName()
                    .build();*/

        return diagnosisResultFromAIServer;

    }

    // 받아온 정보 중에서 가장 probability가 높은 질병 추출
    private void getMostDisease(DiagnosisResultFromAIServer diagnosisResult){

    }

    public String test(){
        String block = webClient.get()
                .uri("/test")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("Mapping Error : ", e))
                .block();
        return block;
    }


}
