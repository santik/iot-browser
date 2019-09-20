package com.relay42.browser.database.model;

import com.relay42.generated.OutsideTemperature;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class OutsideTemperatureModel implements Serializable {

    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String deviceId;
    private String groupId;
    private Date created;
    private Double value;

    public OutsideTemperatureModel() {
    }

    private OutsideTemperatureModel(String deviceId, String groupId, Date created, Double value) {
        this.deviceId = deviceId;
        this.groupId = groupId;
        this.created = created;
        this.value = value;
    }

    public static OutsideTemperatureModel createFromKafkaMessage(OutsideTemperature outsideTemperature) {
        return new OutsideTemperatureModel(
                outsideTemperature.getDeviceId(),
                outsideTemperature.getGroupId(),
                outsideTemperature.getCreated(),
                outsideTemperature.getValue()
                );
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getGroupId() {
        return groupId;
    }

    public Date getCreated() {
        return created;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "OutsideTemperatureModel{" +
                "id='" + id + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", created=" + created +
                ", value=" + value +
                '}';
    }
}
