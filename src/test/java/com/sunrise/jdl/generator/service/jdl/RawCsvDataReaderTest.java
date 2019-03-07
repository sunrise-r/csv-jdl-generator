package com.sunrise.jdl.generator.service.jdl;

import com.sunrise.jdl.generator.entities.RawData;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RawCsvDataReaderTest {

    private RawCsvDataReader rawCsvDataReader = new RawCsvDataReader();

    @Test
    public void getRawData() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("twoEntities.csv");
        List<RawData> result = rawCsvDataReader.getRawData(stream);
        Assert.assertEquals(20, result.size());
        RawData data = result.get(0);
        //,ContactDataCorrection,Корректировка Договор, Корректировки Договора,legalAddress,Юридический адрес,Юридический адрес,Address,1,2,3,4,5,6
        Assert.assertEquals("ContactDataCorrection", data.getEntityName());
        Assert.assertEquals("Корректировка Договор", data.getEntityLabel());
        Assert.assertEquals("Корректировки Договора", data.getEntityPluralLabel());
        Assert.assertEquals("legalAddress", data.getFieldName());
        Assert.assertEquals("Юридический адрес", data.getFieldLabel());
        Assert.assertEquals("Юридический адрес", data.getFieldDescription());
        Assert.assertEquals("1", data.getFieldLength());
        Assert.assertEquals("2", data.getFieldInputMask());
        Assert.assertEquals("3", data.getFieldRequired());
        Assert.assertEquals("4", data.getFieldComment());
        Assert.assertEquals("5", data.getFieldValidation());
        Assert.assertEquals("6", data.getFieldInputType());
    }
}
