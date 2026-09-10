package com.example.demo.scheduler;

import com.example.demo.entities.Attachment;
import com.example.demo.repositories.AttachmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleanUpScheduler {
    public final AttachmentRepository attachmentRepository;


    public CleanUpScheduler(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }
//fixed dely run next execution after the time the previous execution is complete
//    fixed rte run the process every fixed time even if previous task not completed yet
//    initil dely : define waiting time before starting the proces first time
//    cron expression let us write time using a string
//        @Scheduled ( initialDelay = 100000, fixedDelay = 5000)
//        @Scheduled ( cron = "* * * * * *")
    public List<Attachment> fetchDeletedAttachments(){
        System.out.println("Fetching Data ...");
        List<Attachment> attachmentList= attachmentRepository.findByIsDeleted(true);
        System.out.println(attachmentList);
        return attachmentList;
    }
}
