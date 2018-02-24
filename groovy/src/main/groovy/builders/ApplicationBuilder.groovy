package builders

import kernel.datasources.laws.DataSource

import java.text.DateFormat
import java.text.SimpleDateFormat

class ApplicationBuilder {
    private Date startDate
    private Date endDate
    private List<EntityBuilder<DataSource>> builders
    private static final DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)

    ApplicationBuilder start(String startDateString) {
        this.startDate = format.parse(startDateString)
        return this
    }

    ApplicationBuilder end(String endDateString) {
        this.endDate = format.parse(endDateString)
        return this
    }

    ApplicationBuilder play(EntityBuilder<DataSource>... builder) {
        this.builders = builder.toList()
        return this
    }

    Date getStartDate() {
        return startDate
    }

    Date getEndDate() {
        return endDate
    }

    List<EntityBuilder<DataSource>> getBuilders() {
        return builders
    }
}
