package kernel.tests;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.SensorsLot;
import kernel.structural.laws.RandomLaw;
import kernel.units.Duration;
import kernel.units.Frequency;
import kernel.units.TimeUnit;
import kernel.visitor.DatabaseHelper;
import kernel.visitor.InfluxDbHelper;
import kernel.visitor.SslVisitor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KernelTest {

    @Captor
    private ArgumentCaptor<List<Measurement>> measurementsCaptor;
    @Captor
    private ArgumentCaptor<String> sensorLotNameCaptor;
    @Captor
    private ArgumentCaptor<String> lawNameCaptor;

    @Test
    public void randomLawTest() throws Exception {
        Application app = new Application();

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        Date startDate = format.parse("10/02/2018 09:25:00");
        Date endDate = format.parse("10/02/2018 09:30:00");

        app.setStartDate(startDate);
        app.setEndDate(endDate);

        RandomLaw randomLaw = new RandomLaw();
        randomLaw.setName("randomLaw");

        app.addLaw(randomLaw);

        SensorsLot randomLot = new SensorsLot();
        randomLot.setSensorsNumber(1);
        randomLot.setLawName("randomLaw");
        randomLot.setName("randomLot");
        randomLot.setFrequencyValue(new Frequency(1, new Duration(1, TimeUnit.Second)).getValue());
        randomLot.generatesSensors(randomLaw);

        app.addSensorLot(randomLot);

        DatabaseHelper databaseHelper = mock(InfluxDbHelper.class);

        SslVisitor visitor = new SslVisitor(databaseHelper);
        visitor.visit(app);

        //300s = 5min
        verify(databaseHelper, times(300))
                .sendToDatabase(measurementsCaptor.capture(), sensorLotNameCaptor.capture(), lawNameCaptor.capture());

        final List<List<Measurement>> measurementsArgument = measurementsCaptor.getAllValues();
        final String lotArgument = sensorLotNameCaptor.getValue();
        final String lawArgument = lawNameCaptor.getValue();

        assertEquals("randomLaw", lawArgument);
        assertEquals("randomLot", lotArgument);
        assertEquals(300, measurementsArgument.size());
        assertTrue(startDate.getTime() == measurementsArgument.get(0).get(0).getTimeStamp());
    }

}
