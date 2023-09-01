package br.edu.unisep.reservas.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_reserva")
    private Date dataReserva;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_devolucao")
    private Date dataDevolucao;

    @ManyToOne
    @JoinColumn(name = "equipamento")
    private Equipamento equipamento;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private User usuario;

    @Column(name = "criado_em", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date criadoEm;
    @Column(name = "criado_por", nullable = false)
    @CreatedBy
    private String criadoPor;
    @Column(name = "alterado_em")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date alteradoEm;

    @Column(name = "alterado_por")
    @LastModifiedBy
    private String alteradoPor;
}
