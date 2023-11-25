package com.ziyao.harbor.elasticsearch.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author ziyao zhang
 * @since 2023/11/17
 */
@Service
public class AuditLogService {


    @Autowired
    private AuditLogRepository auditLogRepository;

    @PostConstruct
    public void saveList() {
//        ArrayList<AuditLog> auditLogs = Lists.newArrayList(
//                new AuditLog("1", 0L, "新增操作"),
//                new AuditLog("2", 1L, "新增操作1"),
//                new AuditLog("3", 0L, "新增操作2"),
//                new AuditLog("4", 2L, "新增操作3"),
//                new AuditLog("5", 3L, "新增操作4"),
//                new AuditLog("6", 2L, "新增操作5")
//        );
        AuditLog auditLog = new AuditLog();
        auditLog.setId("1");
        auditLog.setLevel(2L);
        auditLog.setLog("zzzzzzzzz");
        AuditLog auditLogq = auditLogRepository.save(auditLog);
        System.out.println(auditLogq);
//        Iterable<AuditLog> auditLogs1 = auditLogRepository.saveAll(auditLogs);
//        for (AuditLog auditLog : auditLogs1) {
//            System.out.println(auditLog);
//        }
    }
}
