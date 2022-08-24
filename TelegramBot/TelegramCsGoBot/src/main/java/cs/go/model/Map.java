package cs.go.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "maps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Map extends AbstractBaseEntity {

    @Column(name = "mapName")
    @NotBlank
    private String mapName;

    @Override
    public String toString() {

        return "mapName = " + mapName;
    }
}
