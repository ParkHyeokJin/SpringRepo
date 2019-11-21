package scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScheduledTasks {
	@Scheduled(fixedRate = 10000)
	public void fixedRate() throws InterruptedException {
		log.info("fidex Rate Test");
	}
	
	@Scheduled(fixedDelay = 10000)
	public void fixedDelay() throws InterruptedException {
		log.info("fidex Delay Test");
	}
	
	@Scheduled(cron = "*/10 * * * * *")
	public void cron() {
		log.info("cron Test");
	}
}
