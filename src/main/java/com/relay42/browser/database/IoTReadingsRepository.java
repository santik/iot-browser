package com.relay42.browser.database;

import com.relay42.browser.database.IOTReadingsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IoTReadingsRepository extends JpaRepository<IOTReadingsModel, String> {
    List<IOTReadingsModel> findByDeviceIdAndCreatedBetween(String deviceId, Date startDate, Date endDate);

    List<IOTReadingsModel> findByGroupIdAndCreatedBetween(String groupId, Date startDate, Date endDate);
}
