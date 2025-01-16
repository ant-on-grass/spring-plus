package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.repository.LogRepository;
import org.example.expert.domain.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LogService {

  private final LogRepository logRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW) // 2.
  public void logSave( User user) {

    Log newLog = logRepository.save(new Log(user, true));
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW) // 3.
  public Log logSavefalse(Long logId,User user) {
    if(logRepository.existsById(logId)) {
      return logRepository.findById(logId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    Log newLog = logRepository.save(new Log(logId,user, false));
    return newLog;
  }

  @Transactional // 3.
  public void logSavetrue(User user, Log log) {
    log.setPassOrNot(true);
    Log modifyLog = logRepository.save(log);  // 업데이트로 //
  }

}
