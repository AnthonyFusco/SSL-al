package kernel.tests;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.SensorsLot;
import kernel.structural.laws.MarkovChainLaw;
import kernel.structural.laws.RandomLaw;
import kernel.units.Duration;
import kernel.units.Frequency;
import kernel.units.TimeUnit;
import kernel.visitor.DatabaseHelper;
import kernel.visitor.InfluxDbHelper;
import kernel.visitor.SslVisitor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class KernelTest {

    @Captor
    private ArgumentCaptor<List<Measurement>> measurementsCaptor;
    @Captor
    private ArgumentCaptor<String> sensorLotNameCaptor;
    @Captor
    private ArgumentCaptor<String> lawNameCaptor;

    private Application app;
    private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
    private Date startDate;
    private Date endDate;

    @Before
    public void setUp() throws Exception {
        app = new Application();

        startDate = format.parse("10/02/2018 09:25:00");
        endDate = format.parse("10/02/2018 09:30:00");

        app.setStartDate(startDate);
        app.setEndDate(endDate);
    }

    @Test
    public void randomLawTest() throws Exception {
        addRandomLot();

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

    @Test
    public void MarkovTest() throws Exception {
        addMarkovLot();

        DatabaseHelper databaseHelper = mock(InfluxDbHelper.class);

        SslVisitor visitor = new SslVisitor(databaseHelper);
        visitor.visit(app);

        //300s = 5min
        verify(databaseHelper, times(300))
                .sendToDatabase(measurementsCaptor.capture(), sensorLotNameCaptor.capture(), lawNameCaptor.capture());

        final List<List<Measurement>> measurementsArgument = measurementsCaptor.getAllValues();
        final String lotArgument = sensorLotNameCaptor.getValue();
        final String lawArgument = lawNameCaptor.getValue();

        assertEquals("markovChainLaw", lawArgument);
        assertEquals("markovLot", lotArgument);
        assertEquals(300, measurementsArgument.size());
        assertTrue(startDate.getTime() == measurementsArgument.get(0).get(0).getTimeStamp());
    }

    @Test
    public void MarkovAndRandomTest() throws Exception {
        addMarkovLot();
        addRandomLot();

        DatabaseHelper databaseHelper = mock(InfluxDbHelper.class);

        SslVisitor visitor = new SslVisitor(databaseHelper);
        visitor.visit(app);

        //600s = 5min * 2sensors
        verify(databaseHelper, times(600))
                .sendToDatabase(measurementsCaptor.capture(), sensorLotNameCaptor.capture(), lawNameCaptor.capture());

        final List<List<Measurement>> measurementsArgument = measurementsCaptor.getAllValues();

        assertTrue(lawNameCaptor.getAllValues().contains("markovChainLaw"));
        assertTrue(lawNameCaptor.getAllValues().contains("randomLaw"));

        assertTrue(sensorLotNameCaptor.getAllValues().contains("markovLot"));
        assertTrue(sensorLotNameCaptor.getAllValues().contains("randomLot"));

        assertEquals(600, measurementsArgument.size());
        assertTrue(startDate.getTime() == measurementsArgument.get(0).get(0).getTimeStamp());
    }

    private void addRandomLot() {
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
    }

    private void addMarkovLot() {
        MarkovChainLaw markovChainLaw = new MarkovChainLaw();
        markovChainLaw.setName("markovChainLaw");
        markovChainLaw.setChangeStateFrequencyValue(
                new Frequency(1, new Duration(1, TimeUnit.Second)).getValue());
        markovChainLaw.setCurrState(0);

        //[[0.3, 0.2, 0.5], [0.15, 0.8, 0.05], [0.25, 0.25, 0.5]]
        List<Double> l1 = Arrays.asList(0.3, 0.2, 0.5);
        List<Double> l2 = Arrays.asList(0.15, 0.8, 0.05);
        List<Double> l3 = Arrays.asList(0.25, 0.25, 0.5);
        markovChainLaw.setMatrix(Arrays.asList(l1, l2, l3));

        app.addLaw(markovChainLaw);

        SensorsLot markovLot = new SensorsLot();
        markovLot.setSensorsNumber(1);
        markovLot.setLawName("markovChainLaw");
        markovLot.setName("markovLot");
        markovLot.setFrequencyValue(new Frequency(1, new Duration(1, TimeUnit.Second)).getValue());
        markovLot.generatesSensors(markovChainLaw);

        app.addSensorLot(markovLot);
    }
}
