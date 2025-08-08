package br.ce.wcaquino.taskbackend.model;

// 2. Importações atualizadas para 'jakarta.persistence'
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent; // 1. Adicione esta importação
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição da tarefa não pode estar em branco")
    @Column(nullable = false)
    private String task;

    @NotNull(message = "A data de vencimento não pode ser nula")
    @FutureOrPresent(message = "A data de vencimento não pode ser no passado") // 2. Corrija a anotação aqui
    @Column(nullable = false)
    private LocalDate dueDate;

}