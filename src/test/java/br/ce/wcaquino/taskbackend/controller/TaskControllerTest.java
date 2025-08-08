// 1. Pacote correto
package br.ce.wcaquino.taskbackend.controller;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

// 2. Importações corretas e limpas do JUnit 5
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

// 3. Anotação para inicializar o Mockito com JUnit 5
@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    // Os campos não precisam ser 'public'
    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;

    // O método @Before com MockitoAnnotations.initMocks(this) já não é necessário

    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        // Arrange
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());

        // Act & Assert - 4. Forma moderna e segura de testar exceções
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            controller.save(todo);
        });

        // Verifica a mensagem da exceção
        Assertions.assertEquals("Fill the task description", exception.getMessage());
    }

    @Test
    public void naoDeveSalvarTarefaSemData() {
        // Arrange
        Task todo = new Task();
        todo.setTask("Descricao");

        // Act & Assert
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            controller.save(todo);
        });

        Assertions.assertEquals("Fill the due date", exception.getMessage());
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() {
        // Arrange
        Task todo = new Task();
        todo.setTask("Descricao");
        todo.setDueDate(LocalDate.of(2010, 1, 1));

        // Act & Assert
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            controller.save(todo);
        });

        Assertions.assertEquals("Due date must not be in past", exception.getMessage());
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException {
        // Arrange
        Task todo = new Task();
        todo.setTask("Descricao");
        todo.setDueDate(LocalDate.now());

        // Act
        controller.save(todo);

        // Assert - 5. Verifica se o método .save() do repositório foi realmente chamado
        Mockito.verify(taskRepo).save(todo);
    }
}