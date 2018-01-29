package spingboot;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="push")
public class PushEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	
	@ManyToOne
	@JoinColumn(name="notification_id")
	private NotificationEntity notificationEntity;
	
	private long quantity;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public NotificationEntity getNotificationEntity() {
		return notificationEntity;
	}

	public void setNotificationEntity(NotificationEntity notificationEntity) {
		this.notificationEntity = notificationEntity;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
	
}
