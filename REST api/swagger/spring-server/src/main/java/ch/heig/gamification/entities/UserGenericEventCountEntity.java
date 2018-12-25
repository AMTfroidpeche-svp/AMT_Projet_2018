package ch.heig.gamification.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="USERGENERICEVENTCOUNT")
public class UserGenericEventCountEntity implements Serializable {

    //table1ID is userName, table2ID is the EventName
    @EmbeddedId
    private LinkTableId id;

    private int value;

    public UserGenericEventCountEntity() {
    }

    public UserGenericEventCountEntity(LinkTableId id){
        this.id = id;
    }

    public LinkTableId getId() {
        return id;
    }

    public void setid(LinkTableId id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void incValue(){value++;}


    @Override
    public boolean equals(Object obj) {
        return id.equals(((UserGenericEventCountEntity) obj).getId());
    }
}
