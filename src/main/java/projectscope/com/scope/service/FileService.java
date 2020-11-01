package projectscope.com.scope.service;

import org.springframework.stereotype.Service;
import projectscope.com.scope.entity.FileEntity;
import projectscope.com.scope.exception.FileNotFoundException;
import projectscope.com.scope.repository.FileRepository;


@Service
public class FileService {
    
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public FileEntity getFile(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId));
    }

    public FileEntity saveFile(FileEntity file) {
        return fileRepository.save(file);
    }
}
