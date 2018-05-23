import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения сущностей
 */
public class Entity {
    private String className;
    private ArrayList<Field> fields;



    public Entity(String className, ArrayList fields) {
        this.className = className;
        this.fields = fields;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }
}
