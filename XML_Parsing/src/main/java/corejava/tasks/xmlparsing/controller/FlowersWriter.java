package corejava.tasks.xmlparsing.controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class FlowersWriter {
    private FlowersWriter() {}

    public static void writeToFile(String fileName, Flowers flwrs) {
        try (FileOutputStream output = new FileOutputStream(fileName)) {
            List<Flower> flowers = flwrs.toList();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("flowers");
            root.setAttribute("xmlns", "http://www.nure.ua");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "http://www.nure.ua input.xsd ");
            document.appendChild(root);

            for (Flower f : flowers) {
                //create flower division
                Element flower = document.createElement("flower");

                //add default properties
                Flower.DefaultProperties props = f.getDefaultProperties();
                Element name = document.createElement("name");
                name.setTextContent(props.name);
                Element soil = document.createElement("soil");
                soil.setTextContent(props.soil);
                Element origin = document.createElement("origin");
                origin.setTextContent(props.origin);
                Element multiplying = document.createElement("multiplying");
                multiplying.setTextContent(props.multiplying);

                flower.appendChild(name);
                flower.appendChild(soil);
                flower.appendChild(origin);

                // add new division "visualParameters" and fill it with data from object f
                Element visualParameters = document.createElement("visualParameters");
                Flower.VisualParameters vp = f.getVisualParameters();
                Element stemColour = document.createElement("stemColour");
                stemColour.setTextContent(vp.stemColour);
                Element leafColour = document.createElement("leafColour");
                leafColour.setTextContent(vp.leafColour);
                Element aveLenFlower = document.createElement("aveLenFlower");
                aveLenFlower.setAttribute("measure", vp.aveLenFlowerMeasure);
                aveLenFlower.setTextContent(Integer.toString(vp.aveLenFlower));

                visualParameters.appendChild(stemColour);
                visualParameters.appendChild(leafColour);
                visualParameters.appendChild(aveLenFlower);

                flower.appendChild(visualParameters);

                // create new division "growingTips" and fill it with data from object f
                Element growingTips = document.createElement("growingTips");
                Flower.GrowingTips gt = f.getGrowingTips();
                Element tempreture = document.createElement("tempreture");
                tempreture.setAttribute("measure", gt.tempretureMeasure);
                tempreture.setTextContent(Integer.toString(gt.tempreture));
                Element lighting = document.createElement("lighting");
                lighting.setAttribute("lightRequiring", gt.lightRequiring);
                Element watering = document.createElement("watering");
                watering.setAttribute("measure", gt.wateringMeasure);
                watering.setTextContent(Integer.toString(gt.watering));

                growingTips.appendChild(tempreture);
                growingTips.appendChild(lighting);
                growingTips.appendChild(watering);

                flower.appendChild(growingTips);

                // append the last property to the flower division
                flower.appendChild(multiplying);

                // append created flower to the document
                root.appendChild(flower);
            }
            writeXML(document, output);
        } catch (ParserConfigurationException | IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeXML(Document document, OutputStream output) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(document);
        StreamResult stream = new StreamResult(output);

        transformer.transform(source, stream);
    }
}
