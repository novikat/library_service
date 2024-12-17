package novikat.library_service.domain.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Version
    protected Integer version;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseModel)) return false;
        BaseModel baseModel = (BaseModel) o;
        return id.equals(baseModel.id) && version.equals(baseModel.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
