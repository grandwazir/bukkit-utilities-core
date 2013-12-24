package name.richardson.james.bukkit.utilities.persistence.database;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class AbstractRecord implements Record {

	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp createdAt;

	@Id
	@GeneratedValue
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Timestamp modifiedAt;

	@Version
	private int version;

	@Override
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public long getId() {
		return id;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public Timestamp getModifiedAt() {
		return modifiedAt;
	}

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public void setModifiedAt(Timestamp modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	@PrePersist
	public void onCreate() {
		setCreatedAt(AbstractRecord.getTimeNow());
	}

	@PreUpdate
	public void onSave() {
		setModifiedAt(AbstractRecord.getTimeNow());
	}

	private static Timestamp getTimeNow() {
		return new Timestamp(System.currentTimeMillis());
	}

}
