package de.samply.serializer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javax.xml.datatype.XMLGregorianCalendar;

public class XmlMapperConfiguration {

  public static XmlMapper createXmlMapper() {
    XmlMapper xmlMapper = new XmlMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(XMLGregorianCalendar.class, new XMLGregorianCalendarSerializer());
    xmlMapper.registerModule(module);
    return xmlMapper;
  }
}