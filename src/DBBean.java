import oracle.jdbc.OracleDriver;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

@ManagedBean(name = "dbBean")
    @SessionScoped
    public class DBBean {

        private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orbis";
        private static final String USER = "s225037";
        private static final String PASS = "xKby9598";
        private static final String TABLE_NAME = "CHECK_RESULTS";
        private final String sessionID;
        private final Logger logger;
        private Connection connection;

        public DBBean(){
            FacesContext fCtx = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
            sessionID = session.getId();
            logger = Logger.getLogger("logger");
            try {
                logger.addHandler(new FileHandler("log.txt"));
               // Class.forName("oracle.jdbc.driver.OracleDriver");
                DriverManager.registerDriver(new OracleDriver());
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }

        public int addResult() {
            Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String xstr = requestParameterMap.get("form:param-x_input");
            String ystr = requestParameterMap.get("form:param-y");
            String rstr = requestParameterMap.get("form:param-r");
            logger.info("X=" + xstr);
            logger.info("Y=" + ystr);
            logger.info("R" + rstr);
            double x;
            double y;
            double r;
            try {
                x = Double.parseDouble(xstr.replace(',', '.'));
                y = Double.parseDouble(ystr.replace(',', '.'));
                r = Double.parseDouble(rstr.replace(',', '.'));
            } catch (Exception e) {
                return -1;
            }
//            if (!MatchingManager.valid(x, y, r))
//                return -1;
//            boolean check = MatchingManager.match(x, y, r);
            StringBuilder request = new StringBuilder();
            request.append("INSERT INTO ");
            request.append(TABLE_NAME);
            request.append(" (x,y,r,result,sessionID) VALUES (");
            request.append(x);
            request.append(",");
            request.append(y);
            request.append(",");
            request.append(r);
            request.append(",'");
           // request.append(check);
            request.append("','");
            request.append(sessionID);
            request.append("');");
            logger.info("try");
            try {
                return connection.createStatement().executeUpdate(request.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("added");
            return -1;
        }

        public static List<ResultRow> resultRows = new ArrayList<ResultRow>();
        public List<ResultRow> getAllResults() {
            //List<ResultRow> resultRows = new ArrayList<ResultRow>();
                try {
                    ResultSet resultSet = connection.createStatement().executeQuery(
                            "SELECT * FROM " + TABLE_NAME);
                    while (resultSet.next()) {
                        ResultRow resultRow = new ResultRow();
                        resultRow.setX(resultSet.getString("x"));
                        resultRow.setY(resultSet.getString("y"));
                        resultRow.setR(resultSet.getString("r"));
                        resultRow.setResult(resultSet.getString("result"));
                        resultRows.add(resultRow);
                    }
                    resultSet.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            return resultRows;
        }

        @PreDestroy
        private void clearResults() {
            try {
                connection.createStatement().executeUpdate("DELETE FROM " + TABLE_NAME +
                        " WHERE sessionID = '" + sessionID + "';");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public class ResultRow{
            public String x, y, r, result;

            public String getR() {
                return r;
            }

            public String getX() {
                return x;
            }

            public String getResult() {
                return result;
            }

            public String getY() {
                return y;
            }

            public void setR(String r) {
                this.r = r;
            }

            public void setX(String x) {
                this.x = x;
            }

            public void setY(String y) {
                this.y = y;
            }

            public void setResult(String result) {
                this.result = result;
            }
        }
    }

