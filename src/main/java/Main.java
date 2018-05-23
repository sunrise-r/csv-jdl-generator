import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static Map<String, Entity> entities = new LinkedHashMap<String, Entity>();

    public static void main(String[] args) {

        String pathToFile = readFilePath();
        readFilesInDirectory(pathToFile);

        for (Entity entity : entities.values()) {
            System.out.println("class " + entity.getClassName() + "{");
            for (Field field : entity.getFields()) {
                System.out.println("    " + field.getFieldType() + " " + field.getFieldName() + "(" + field.getFieldLength() + ");");
            }
            System.out.println("}");
        }
    }

    public static void readFilesInDirectory(String pathToDirectory) {
        File directory = new File(pathToDirectory);
        File[] files = directory.listFiles();
        for (File file : files) {
            readDataFromCSV(file);
        }
    }

    // Считываем данные из csv-файла и сохраняем их в карту entities
    private static void readDataFromCSV(File pathToFile) {

        try {
            Reader in = new FileReader(pathToFile);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            String className = "";
            for (CSVRecord record : records) {
                String s1 = record.get(1);
                String fieldName = record.get(2);
                String fieldType = record.get(5);
                String fieldLength = record.get(6);

                if (!s1.equals("") && !s1.contains("П")) {
                    className = s1;
                    Field field = new Field(fieldType, fieldName, fieldLength);
                    ArrayList<Field> arrayList = new ArrayList<Field>();
                    arrayList.add(field);
                    Entity entity = new Entity(className, arrayList);
                    entities.put(className, entity);
                } else if (s1.equals("") && entities.size() > 0) {
                    entities.get(className).getFields().add(new Field(fieldType, fieldName, fieldLength));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO exception");
            e.printStackTrace();
        }
    }

    // Метод для чтения пути папки
    public static String readFilePath() {
        System.out.print("Введите путь папки: ");
        String directoryPathName = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            directoryPathName = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directoryPathName;
    }

    // Метод проверяет существование файла. Входной параметр - путь к файлу. Если файл существует - возращается true
    public static Boolean checkFileExistence(String folderPathString) {

        File file = new File(folderPathString);
        if (file.exists() && !file.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }
}
