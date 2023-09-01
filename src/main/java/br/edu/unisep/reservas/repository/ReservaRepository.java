package br.edu.unisep.reservas.repository;

import br.edu.unisep.reservas.model.Reserva;
import br.edu.unisep.reservas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {


    @Query(value = "select * " +
            "from reserva " +
            "where data_reserva between :data_inicio  and :data_final " +
            "and status = 0",
            nativeQuery = true)
    Reserva findByDataDevolucao(@Param("data_inicio") String dataInicio, @Param("data_final") String dataFinal);
}
