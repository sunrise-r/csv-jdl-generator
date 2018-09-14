package com.sunrise.jdl.generator.service;

import com.sunrise.jdl.generator.actions.Action;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Класс считывает действия (объекты класс Action)
 */
public class ActionService {

    /**
     * Номер ячеек файла excel из которых читаются данные
     */
    public static final int DISPLAY_TEST = 3;
    public static final int GROUP = 4;
    public static final int STYLE = 5;
    public static final int CODE = 6;

    /**
     * Метод читает данные из потока и, на основе этих данных,
     * создает обхекты класса Action и сохраняет их в List
     * @param stream
     * @return  List<Action> actions
     */
    public Collection<Action> readDataFromCSV(InputStream stream) {
        List<Action> actions = new ArrayList<>();
        try {
            Reader in = new InputStreamReader(stream);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);

            for (CSVRecord record : records) {
                String displayType = record.get(DISPLAY_TEST);
                String group = record.get(GROUP);
                String style = record.get(STYLE);
                String code = record.get(CODE);
                actions.add(new Action(displayType, group, style, code));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actions;
    }
}