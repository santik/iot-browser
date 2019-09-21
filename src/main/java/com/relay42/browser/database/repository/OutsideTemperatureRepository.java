package com.relay42.browser.database.repository;

import com.relay42.browser.database.model.IOTReadingsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OutsideTemperatureRepository extends JpaRepository<IOTReadingsModel, String> {
    List<IOTReadingsModel> findByDeviceIdAndCreatedBetween(String deviceId, Date startDate, Date endDate);

    List<IOTReadingsModel> findByGroupIdAndCreatedBetween(String groupId, Date startDate, Date endDate);
}
