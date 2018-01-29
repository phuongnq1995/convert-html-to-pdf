package spingboot;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="notification")
public class NotificationEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private long id;
	
	@OneToMany(mappedBy="notificationEntity")
	private Set<PushEntity> pushEntitys;

	private String name;
	private long hight;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getHight() {
		return hight;
	}
	public void setHight(long hight) {
		this.hight = hight;
	}
	public Set<PushEntity> getPushEntitys() {
		return pushEntitys;
	}
	public void setPushEntitys(Set<PushEntity> pushEntitys) {
		this.pushEntitys = pushEntitys;
	}
	
}
