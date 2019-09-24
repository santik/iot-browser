package com.relay42.browser.database;

import com.relay42.browser.database.IOTReadingsModel;
import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

class IOTReadingsModelTest {

    @Test
    public void createFromKafkaMessage_withOutsideTemperature_shouldCreateCorrectModel() {
        //arange
        OutsideTemperature outsideTemperature = new OutsideTemperature("someDeviceId", "someGroupId", 1d, new Date());

        //act
        IOTReadingsModel model = IOTReadingsModel.createFromKafkaMessage(outsideTemperature);

        //assert
        assertEquals(outsideTemperature.getDeviceId(), model.getDeviceId());
        assertEquals(outsideTemperature.getGroupId(), model.getGroupId());
        assertEquals(Double.valueOf(outsideTemperature.getValue()), Double.valueOf(model.getValue()));
        assertEquals(outsideTemperature.getCreated(), model.getCreated());
    }

    @Test
    public void testCreateFromKafkaMessage_withOutsideHumidity_shouldCreateCorrectModel() {
        //arange
        OutsideHumidity outsideHumidity = new OutsideHumidity("someDeviceId", "someGroupId", 1d, new Date());

        //act
        IOTReadingsModel model = IOTReadingsModel.createFromKafkaMessage(outsideHumidity);

        //assert
        assertEquals(outsideHumidity.getDeviceId(), model.getDeviceId());
        assertEquals(outsideHumidity.getGroupId(), model.getGroupId());
        assertEquals(Double.valueOf(outsideHumidity.getValue()), Double.valueOf(model.getValue()));
        assertEquals(outsideHumidity.getCreated(), model.getCreated());
    }

    @Test
    public void testCreateFromKafkaMessage_withWindSpeed_shouldCreateCorrectModel() {
        //arange
        WindSpeed windSpeed = new WindSpeed("someDeviceId", "someGroupId", 1d, new Date());

        //act
        IOTReadingsModel model = IOTReadingsModel.createFromKafkaMessage(windSpeed);

        //assert
        assertEquals(windSpeed.getDeviceId(), model.getDeviceId());
        assertEquals(windSpeed.getGroupId(), model.getGroupId());
        assertEquals(Double.valueOf(windSpeed.getValue()), Double.valueOf(model.getValue()));
        assertEquals(windSpeed.getCreated(), model.getCreated());
    }
}
