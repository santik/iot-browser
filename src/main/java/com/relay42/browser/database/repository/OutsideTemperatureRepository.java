package com.relay42.browser.database.repository;

import com.relay42.browser.database.model.OutsideTemperatureModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OutsideTemperatureRepository extends JpaRepository<OutsideTemperatureModel, String> {
    List<OutsideTemperatureModel> findByDeviceIdAndCreatedBetween(String deviceId, ZonedDateTime startDate, ZonedDateTime endDate);
    List<OutsideTemperatureModel> findByGroupIdAndCreatedBetween(String groupId, ZonedDateTime startDate, ZonedDateTime endDate);
}
