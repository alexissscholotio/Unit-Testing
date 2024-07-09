import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.EmailCliente;
import org.example.NotificadorEmail;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class NotificadorEmailTest {

    @Mock
    private EmailCliente emailClienteMock;

    @Test
    public void testNotificar() {
        NotificadorEmail notificador = new NotificadorEmail(emailClienteMock);
        notificador.notificar("ejemplo@test.com", "Hola Mundo");

        verify(emailClienteMock).enviar("ejemplo@test.com", "Hola Mundo");
    }

    @Test
    public void testNotificarConDireccionVacia() {
        NotificadorEmail notificador = new NotificadorEmail(emailClienteMock);
        notificador.notificar("", "Mensaje");
        verify(emailClienteMock, times(0)).enviar(anyString(), anyString());
    }

    @Test
    public void testNotificarConMensajeNulo() {
        NotificadorEmail notificador = new NotificadorEmail(emailClienteMock);
        notificador.notificar("admin@gerencia.com", null);
        verify(emailClienteMock, times(0)).enviar(anyString(), anyString());
    }

    @Test
    public void testNotificarConDireccionInvalida() {
        NotificadorEmail notificador = new NotificadorEmail(emailClienteMock);
        notificador.notificar("hola", "Mensaje");

        verify(emailClienteMock, times(1)).enviar(anyString(), anyString());
    }


    @Test
    public void testNotificarConMensajeYDireccionLargas() {
        String direccionLarga = "ejemplo.de.mensaje.y.direccion.largo@test.com";
        String mensajeLargo = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla scelerisque id nisi convallis vulputate. Praesent congue sapien ac purus sollicitudin, sit amet dapibus ligula finibus. " +
                "Maecenas eleifend porta tincidunt. Aenean dignissim ipsum ac suscipit cursus. Nam porta scelerisque quam ut interdum.";
        NotificadorEmail notificador = new NotificadorEmail(emailClienteMock);
        notificador.notificar(direccionLarga, mensajeLargo);


        verify(emailClienteMock).enviar(direccionLarga, mensajeLargo);
    }
}