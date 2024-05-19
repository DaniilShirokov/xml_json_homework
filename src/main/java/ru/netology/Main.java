package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.lang.module.ModuleDescriptor.read;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        List<Employee> list = parseXML("data.xml");
        System.out.println(list.toString());
        String json = listToJson(list);
        writeString(json);
    }

    private static void writeString(String inputDate) throws IOException {
        FileWriter writer = new FileWriter("data.json");
        writer.write(inputDate);
        writer.close();
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        return json;
    }
    private static List<Employee> parseXML(String s) throws IOException, SAXException, ParserConfigurationException {
        List<Employee> staff = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory. newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse( new File(s));
        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element employee = (Element) node;
                NamedNodeMap map = employee.getAttributes();
                System.out.println(map.getLength());
                Employee newEmployee1 = new Employee(
                        Long.parseLong(employee.getElementsByTagName("id").item(0).getTextContent()),
                                    employee.getElementsByTagName("firstName").item(0).getTextContent(),
                                    employee.getElementsByTagName("lastName").item(0).getTextContent(),
                                    employee.getElementsByTagName("country").item(0).getTextContent(),
                        Integer.parseInt(employee.getElementsByTagName("age").item(0).getTextContent())
                );
                staff.add(newEmployee1);
            }
        }
        return staff;
    }



}