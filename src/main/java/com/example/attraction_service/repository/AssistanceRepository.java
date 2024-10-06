package com.example.attraction_service.repository;

import com.example.attraction_service.entity.Assistance;
import com.example.attraction_service.entity.AssistanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностями {@link Assistance}.
 */

@Repository
public interface AssistanceRepository extends JpaRepository<Assistance, Long> {
    /**
     * Метод для получения услуги по типу и исполнителю из базы данных.
     *
     * @param assistanceType - тип услуги {@link AssistanceType}
     * @param executor - исполнитель
     * @return возвращает Optional<Assistance>
     */

    Optional<Assistance> findByAssistanceTypeAndExecutor(AssistanceType assistanceType, String executor);
}
