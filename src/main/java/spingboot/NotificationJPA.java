package spingboot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationJPA extends JpaRepository<NotificationEntity, Long> {
	
	@Query(value = "SELECT n.id, n.name, n.hight, sum(p.quantity) as total "
			+ " FROM notification n inner join push p on n.id = p.notification_id "
			+ " GROUP BY n.id, n.name, n.hight ORDER BY ?#{#pageable}",
		    nativeQuery = true)
	Page<NotificationEntity> findNotifications(Pageable pageable);
}
