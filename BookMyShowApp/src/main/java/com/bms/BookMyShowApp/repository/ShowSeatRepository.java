package com.bms.BookMyShowApp.repository;

import com.bms.BookMyShowApp.entity.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Query("select s.seatLabel from ShowSeat s where s.show.id = :showId and s.reserved=false order by s.id")
    List<String> findAvailableLabels(@Param("showId") Long showId);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // Ye add karna zaroori hai kyuki Agar do log ek saath API hit karenge, toh dono ko laghega ki seat khali hai aur dono book kar lenge. Double Booking ka risk hai.
    @Query("select s from ShowSeat s where s.show.id = :showId and s.seatLabel in :labels")
    List<ShowSeat> findForUpdate(@Param("showId") Long showId, @Param("labels") List<String> labels);
}
