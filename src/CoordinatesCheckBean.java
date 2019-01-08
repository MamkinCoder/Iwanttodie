import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class CoordinatesCheckBean {
    private String name;
    private int x;
    private float r;
    private float y;

    public CoordinatesCheckBean() {
        this.name = "Default";
    }

    public boolean hitOrMissIGuessTheyNeverMissHuh(){
        if (x>=0 && y<=Math.sqrt(Math.pow(r,2)-x) && x<=r){ //четветь круга
            return true;
        }
        else if (x<0 && y<r/2 && x>= -r){ //прямоугольник
            return true;
        }
        else if (x>0 && y<r/2 && x>= -r && x<r && y<0 && Math.pow(x,2) + Math.pow(y, 2) == Math.pow(r,2)){ //треугольник
            return true;
        }
        else return false;
    }

    @Override
    public String toString() {
        return "R: " + r + ", Y: " + y + ", X: " + x;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
