package com.relay42.browser.database;

import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class IOTReadingsModel implements Serializable {

    private static final String ID_GENERATOR = "system-uuid";

    @Id
    @GeneratedValue(generator = ID_GENERATOR)
    @GenericGenerator(name = ID_GENERATOR, strategy = "uuid")
    private String id;

    private String deviceId;
    private String groupId;
    private Date created;
    private Double value;
    private String type;

    public IOTReadingsModel() {
    }

    private IOTReadingsModel(String deviceId, String groupId, Date created, Double value, String type) {
        this.deviceId = deviceId;
        this.groupId = groupId;
        this.created = created;
        this.value = value;
        this.type = type;
    }

    public static IOTReadingsModel createFromKafkaMessage(OutsideTemperature outsideTemperature) {
        return new IOTReadingsModel(
                outsideTemperature.getDeviceId(),
                outsideTemperature.getGroupId(),
                outsideTemperature.getCreated(),
                outsideTemperature.getValue(),
                OutsideTemperature.class.getSimpleName()
                );
    }

    public static IOTReadingsModel createFromKafkaMessage(OutsideHumidity outsideHumidity) {
        return new IOTReadingsModel(
                outsideHumidity.getDeviceId(),
                outsideHumidity.getGroupId(),
                outsideHumidity.getCreated(),
                outsideHumidity.getValue(),
                OutsideHumidity.class.getSimpleName()
        );
    }

    public static IOTReadingsModel createFromKafkaMessage(WindSpeed windSpeed) {
        return new IOTReadingsModel(
                windSpeed.getDeviceId(),
                windSpeed.getGroupId(),
                windSpeed.getCreated(),
                windSpeed.getValue(),
                WindSpeed.class.getSimpleName()
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
        return "IOTReadingsModel{"
                + "id='" + id + '\''
                + ", deviceId='" + deviceId + '\''
                + ", groupId='" + groupId + '\''
                + ", created=" + created
                + ", value=" + value
                + ", type='" + type + '\''
                + '}';
    }
}
