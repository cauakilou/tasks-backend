package testes; // Recomendo usar o pacote correto

import br.ce.wcaquino.taskbackend.controller.TaskController;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

// MUDANÇA 1: Usar as importações do JUnit 4 e Mockito antigo
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;

// MUDANÇA 2: A classe e os métodos de teste DEVEM ser 'public' no JUnit 4
public class TaskControllerIT {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;

    // MUDANÇA 3: Usar @Before para inicializar os mocks manualmente
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        // Arrange
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());

        // Act & Assert - MUDANÇA 4: Testar exceções com o padrão 'try-catch'
        try {
            controller.save(todo);
            // Se o código chegar aqui, a exceção não foi lançada, então o teste deve falhar
            Assert.fail("Não deveria chegar neste ponto");
        } catch (ValidationException e) {
            // Verifica a mensagem da exceção
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() {
        // Arrange
        Task todo = new Task();
        todo.setTask("Descricao");

        // Act & Assert
        try {
            controller.save(todo);
            Assert.fail("Não deveria chegar neste ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() {
        // Arrange
        Task todo = new Task();
        todo.setTask("Descricao");
        todo.setDueDate(LocalDate.of(2010, 1, 1));

        // Act & Assert
        try {
            controller.save(todo);
            Assert.fail("Não deveria chegar neste ponto");
        } catch (ValidationException e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException {
        // Arrange
        Task todo = new Task();
        todo.setTask("Descricao");
        todo.setDueDate(LocalDate.now());

        // Act
        controller.save(todo);

        // Assert - MUDANÇA 5: Usar Mockito.verify para o teste de sucesso (isso continua igual)
        Mockito.verify(taskRepo).save(todo);
    }
}