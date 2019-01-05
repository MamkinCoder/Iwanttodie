import javax.faces.bean.*;
import java.text.DateFormat;
import java.util.*;

@ManagedBean
@ApplicationScoped
public class DateTimeBean {

    int text =0;

    private Date currentDate;
    private Locale locale = new Locale("ru");
    private String dateOut;
    private int name;
    private int counter = 0;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getWelcomeMessage() {
        counter++;
        return "Hello " + counter;
    }

    public DateTimeBean(){

        name=0;

    }

    public String getDateOut() {
        currentDate = new Date();
        DateFormat dateFormatter;
        dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, locale);
        DateFormat timeFormatter =
                DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);
        dateOut = dateFormatter.format(currentDate) + " " + timeFormatter.format(currentDate);
        return dateOut;
    }

    public int getText(){
        text++;
        return text;
    }



}
