package cashregister;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CashRegisterTest {

   ByteArrayOutputStream outputStream;

  @BeforeEach
  public void setUp() {
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  @Test
  public void should_print_the_real_purchase_when_call_process() {
    Printer printer = new Printer();
    CashRegister cashRegister = new CashRegister(printer);
    Item item = new Item("Cola", 2.5);
    Item[] items = new Item[]{item};
    Purchase purchase = new Purchase(items);

    cashRegister.process(purchase);

    assertThat(outputStream.toString()).isEqualTo("Cola\t2.5\n");
  }

  @Test
  public void should_print_the_stub_purchase_when_call_process() {
    String message = "mock Message";
    Purchase purchase = mock(Purchase.class);
    CashRegister cashRegister = new CashRegister(new Printer());
    when(purchase.asString()).thenReturn(message);

    cashRegister.process(purchase);

    assertThat(outputStream.toString()).isEqualTo(message);

  }

  @Test
  public void should_verify_with_process_call_with_mockito() {
    Printer printer = mock(Printer.class);
    Purchase purchase = mock(Purchase.class);
    CashRegister cashRegister = new CashRegister(printer);

    cashRegister.process(purchase);

    verify(printer).print(purchase.asString());
  }

}
