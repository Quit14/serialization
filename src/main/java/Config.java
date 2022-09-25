import org.json.simple.parser.ParseException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Config {
    private boolean load = false; // регулирует загрузку корзины из файла
    private boolean format = false; // формат выгрузки корзины
    private File basketFile; //файл для сохранения корзины
    private boolean save = false; //регулирует сохранение корзины
    private File saveFile; // имя файла, в который будет сохраняться корзина
    private boolean saveFormat = false;
    private boolean log = false;
    private File logFile;

    public boolean isLoad() {
        return load;
    }

    public boolean isFormat() {
        return format;
    }

    public File getBasketFile() {
        return basketFile;
    }

    public void checkLoad() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        Element root = doc.getDocumentElement();
        Element loadTag = (Element) root.getElementsByTagName("load").item(0);
        NodeList childNode = loadTag.getChildNodes();
        for (int i = 0; i < childNode.getLength(); i++) {
            Node node = childNode.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getTagName().equals("fileName")) {
                    basketFile = new File(element.getTextContent());
                }

                if (element.getTagName().equals("enabled")) {
                    if (element.getTextContent().equals("true")) {
                        load = true;
                    }
                }
                if (element.getTagName().equals("format")) {
                    if (element.getTextContent().equals("json")) {
                        format = true;
                    }
                }
            }
        }
    }


    public void checkSave() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        Element root = doc.getDocumentElement();
        Element saveTag = (Element) root.getElementsByTagName("save").item(0);
        NodeList childNode = saveTag.getChildNodes();
        for (int i = 0; i < childNode.getLength(); i++) {
            Node node = childNode.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getTagName().equals("fileName")) {
                    saveFile = new File(element.getTextContent());
                }

                if (element.getTagName().equals("enabled")) {
                    if (element.getTextContent().equals("true")) {
                        save = true;
                    }
                }
                if (element.getTagName().equals("format")) {
                    if (element.getTextContent().equals("json")) {
                        saveFormat = true;
                    }
                }
            }
        }
    }


    public boolean isSave() {
        return save;
    }

    public File getSaveFile() {
        return saveFile;
    }

    public boolean isSaveFormat() {
        return saveFormat;
    }

    public boolean isLog() {
        return log;
    }

    public File getLogFile() {
        return logFile;
    }

    public void checkLog() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        Element root = doc.getDocumentElement();
        Element logTag = (Element) root.getElementsByTagName("log").item(0);
        NodeList childNode = logTag.getChildNodes();
        for (int i = 0; i < childNode.getLength(); i++) {
            Node node = childNode.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getTagName().equals("fileName")) {
                    logFile = new File(element.getTextContent());
                }
                if (element.getTagName().equals("enabled")) {
                    if (element.getTextContent().equals("true")) {
                        log = true;
                    }
                }
            }
        }
    }
}









