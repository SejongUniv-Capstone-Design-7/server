package capstone.capstone7.global.S3;


import capstone.capstone7.global.error.exception.custom.BusinessException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static capstone.capstone7.global.error.enums.ErrorMessage.CANNOT_UPLOAD;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultS3Url;

   // @Value("${file.local-path}")
   // private String defaultLocalPath;

    // 병해충 진단 사진 로컬에 저장
    /*
    public void uploadFileToLocal(MultipartFile file, String crop_sort) {
        if(file != null){
            String fileUrl = defaultLocalPath + File.separator + getDiagnosisFileNameSendToAIServer(file, crop_sort);

            // 사진을 저장할 폴더가 생성되어있는지 확인하고, 생성되어있지 않다면 폴더 생성
            // 아래에서 사진 저장시, 사진을 저장할 폴더가 생성되어있지 않다면 IOException 발생
            File folder = new File(defaultLocalPath);
            if(!folder.exists()){
                folder.mkdirs();
            }
            
            // 파일 업로드
            File uploadFile = new File(fileUrl);
            try{
                file.transferTo(uploadFile);
                // FileCopyUtils.copy(file.getBytes(), uploadFile);
            } catch (IOException e){
                throw new BusinessException(CANNOT_UPLOAD);
            }
        }
    }
     */

/*
    public File convertFromMultipartToFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        File convFile = new File(multipartFile.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        return convFile;
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }
*/

    // S3에 파일 업로드
    public String uploadFileToS3(MultipartFile multipartFile, Long userId) {
        // 비어있는 파일인지 확인
        if (multipartFile == null) return null;
        if (multipartFile.isEmpty()) return null;
        String imageType = getExtension(multipartFile);
        String savedFileName = getSavedFileName(userId, multipartFile);// 저장할 파일명
        ObjectMetadata metadata = new ObjectMetadata();
        
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(bucketName, savedFileName, inputStream, metadata);
        } catch (IOException e) {
            log.error("Failed to upload image", e);
           // throw new BusinessException(ErrorMessage.INVALID_FILE_UPLOAD);
        }
        
        return getResourceUrl(savedFileName); // S3에 저장된 사진의 Url 반환
    }

    // S3에서 파일 삭제
    public void deleteFile(String fileUrl) {
        String fileName = getFileNameFromResourceUrl(fileUrl);
        amazonS3.deleteObject(bucketName, fileName);
    }

    // 병해충 진단에 사용되는 파일명 생성
    public String getDiagnosisFileNameSendToAIServer(MultipartFile file, String crop_sort) {
        log.info("filefullname send to AIserver : {}", file.getOriginalFilename());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter currentTime = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        log.info("file ext send to AIserver : {}", getExtension(file));
        return String.format("%s_%s_%s.%s",
                crop_sort, now.format(currentTime), getRandomUUID(), getExtension(file));
    }

    // S3에 저장되는 파일명 생성
    private String getSavedFileName(Long userId, MultipartFile multipartFile) {
        return String.format("user%s/%s-%s", userId, getRandomUUID(), multipartFile.getOriginalFilename());
    }

    // 랜덤 문자열 획득
    private String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    // S3에 저장된 사진의 Url 받아오기
    private String getResourceUrl(String savedFileName) {
        return amazonS3.getUrl(bucketName, savedFileName).toString();
    }

    // 원본 파일의 파일명 얻어오기
    private String getFileNameFromResourceUrl(String fileUrl) {
        return fileUrl.replace(defaultS3Url + "/", "");
    }


    // 파일의 확장자 체크
    public String getExtension(MultipartFile multipartFile) {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        return extension;
    }

}
