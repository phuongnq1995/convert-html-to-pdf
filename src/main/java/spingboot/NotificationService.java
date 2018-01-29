package spingboot;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	@Autowired
	NotificationJPA repo;
	
	public Page<NotificationEntity> findNotificationEntity(){
		return repo.findNotifications(new PageRequest(1, 3, Sort.Direction.DESC, "total"));
	}
}
