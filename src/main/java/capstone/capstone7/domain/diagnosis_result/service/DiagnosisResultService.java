package capstone.capstone7.domain.diagnosis_result.service;

import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisRequestDto;
import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResponseDto;
import capstone.capstone7.domain.diagnosis_result.dto.DiagnosisResultFromAIServer;
import capstone.capstone7.domain.diagnosis_result.entity.DiagnosisResult;
import capstone.capstone7.domain.diagnosis_result.entity.enums.DiseaseName;
import capstone.capstone7.domain.diagnosis_result.repository.DiagnosisResultRepository;
import capstone.capstone7.global.S3.FileService;
import capstone.capstone7.global.auth.entity.LoginUser;
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

import java.util.Arrays;
import java.util.List;

import static capstone.capstone7.global.error.enums.ErrorMessage.EMPTY_FILE;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class DiagnosisResultService {
    private final FileService fileService;
    private final DiagnosisResultRepository diagnosisResultRepository;
    private WebClient webClient;
    private final List<String> errorMessages = Arrays.asList("정확한 진단입니다.", "진단의 정확도가 낮습니다.",
            "작물 선택이 잘못되었을 가능성이 큽니다. 작물 종류를 올바르게 선택 후 재 검사 해주세요.",
            "지원되지 않는 작물일 가능성이 큽니다.");

    @Value("${ai-server:url}")
    private String aiServerUrl;
    @PostConstruct
    public void initWebClient() {
        webClient = WebClient.create(aiServerUrl);
    }

    public DiagnosisResponseDto diagnosis(MultipartFile cropImage, DiagnosisRequestDto diagnosisRequestDto, LoginUser loginUser){
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

        Boolean isCorrect = diagnosisResultFromAIServer.getErrnum() == 1 ? true : false;
        DiagnosisResponseDto diagnosisResponseDto = new DiagnosisResponseDto(isCorrect, diagnosisResultFromAIServer.getDisease_name(),
                diagnosisResultFromAIServer.getIn_info(), diagnosisResultFromAIServer.getOut_info(),
                diagnosisResultFromAIServer.getClass_prob_list(), errorMessages.get(diagnosisResultFromAIServer.getErrnum() - 1));
        
        // 로그인한 유저이고, 올바른 병해충 검출을 수행한 경우, AI서버에서 받아온 병해충 진단 정보를 diagnosis Result에 저장
        if(loginUser != null && isCorrect){
            DiseaseName mostDisease = getMostDisease(diagnosisResultFromAIServer.getClass_prob_list());
            System.out.println("mostDisease = " + mostDisease);
            DiagnosisResult diagnosisResult = DiagnosisResult.builder()
                    .member(loginUser.getMember())
                    .diseaseName(getMostDisease(diagnosisResultFromAIServer.getClass_prob_list()))
                    .build();

            diagnosisResultRepository.save(diagnosisResult);
        }

        return diagnosisResponseDto;

    }

    // 받아온 정보 중에서 가장 probability가 높은 질병 추출해 Enum 반환
    private DiseaseName getMostDisease(List<Float> class_prob_list){
        int max_prob_idx = 0;
        float max_prob = 0;
        for(int i =0; i <= class_prob_list.size(); i++){
            if(class_prob_list.get(i) >= max_prob){
                max_prob_idx = i;
            }
        }

        return DiseaseName.getEnumByIdx(max_prob_idx);
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
