package kr.co.imguru.domain.file.controller;

import kr.co.imguru.domain.file.entity.File;
import kr.co.imguru.domain.file.repository.FileRepository;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FileRestController {

    private final FileRepository fileRepository;

    @PostMapping("/file/{fileCategory}/{fileKey}/{fileName}")
    public ResponseFormat<File> createFile(@PathVariable String fileCategory,
                                           @PathVariable Long fileKey,
                                           @PathVariable String fileName) {

        //FileCategory 가 member 라면, 있는지 확인해보고 있으면 삭제하고 저장.
        if (fileCategory.equals("member")) {
            Optional<File> memberFile = fileRepository.findOneFileByFileKey(fileCategory, fileKey);
            memberFile.ifPresent(fileRepository::delete);
        }

        String fileUrl = "http://211.62.99.58:9081/" + fileCategory + "_" + fileKey + "_" + fileName;

        File file = new File(fileUrl, fileCategory, fileKey, fileName);

        fileRepository.save(file);

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, file);
    }

}
