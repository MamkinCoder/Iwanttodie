import javax.faces.bean.*;
import java.text.DateFormat;
import java.util.*;

@ManagedBean
@ApplicationScoped
public class DateTimeBean {
    private Date currentDate;
    private Locale locale;
    private String dateOut;

    public DateTimeBean(){
    }

    public String getDateOut() {
        currentDate = new Date();
        locale = new Locale("ru");
        DateFormat dateFormatter;
        dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        DateFormat timeFormatter =
                DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
        dateOut = dateFormatter.format(currentDate) + " " + timeFormatter.format(currentDate);
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut = dateOut;
    }


    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
