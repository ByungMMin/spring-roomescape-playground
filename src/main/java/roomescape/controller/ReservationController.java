package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;

import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    // 예약 관리 페이지 응답
    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    // 예약 추가 페이지 응답
    @GetMapping("/new-reservation")
    public String newReservation() {
        return "new-reservation";
    }

    // 예약 목록 조회 API
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok().body(reservations);
    }

    // 예약 추가 API
    @PostMapping("/reservations/reservation")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        reservation.setId(index.getAndIncrement()); // 예약에 ID를 부여합니다.
        reservations.add(reservation); // 예약을 목록에 추가합니다.
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    // 예약 삭제 API
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build(); // 204 No Content 상태 반환
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found 상태 반환
        }
    }
}
