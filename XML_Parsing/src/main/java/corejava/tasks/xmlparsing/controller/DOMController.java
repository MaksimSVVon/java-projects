package corejava.tasks.xmlparsing.controller;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

/**
 * Controller for DOM parser.
 */
public class DOMController {

	private final String xmlFileName;

	public DOMController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public Flowers parse() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(xmlFileName));
			NodeList list = document.getDocumentElement().getElementsByTagName("flower");
			Flowers flowers = new Flowers();
			for (int i = 0; i < list.getLength(); ++i) {
				flowers.add(parseFlower((Element) list.item(i)));
			}
			return flowers;
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new RuntimeException(e);
		}
	}

	private Flower parseFlower(Element flower) {
		String name = flower.getElementsByTagName("name").item(0).getTextContent();
		String soil = flower.getElementsByTagName("soil").item(0).getTextContent();
		String origin = flower.getElementsByTagName("origin").item(0).getTextContent();
		String multiplying = flower.getElementsByTagName("multiplying").item(0).getTextContent();

		Flower.DefaultProperties props = new Flower.DefaultProperties(name, soil, origin, multiplying);
		Flower.VisualParameters visPar = parseVisualParameters((Element) flower.getElementsByTagName("visualParameters").item(0));
		Flower.GrowingTips groTips = parseGrowingTips((Element) flower.getElementsByTagName("growingTips").item(0));

		return new Flower(props, visPar, groTips);
	}

	private Flower.VisualParameters parseVisualParameters(Element vp) {
		String stemColour = vp.getElementsByTagName("stemColour").item(0).getTextContent();
		String leafColour = vp.getElementsByTagName("leafColour").item(0).getTextContent();
		String aveLenFlowerMeasure = vp.getElementsByTagName("aveLenFlower").item(0).getAttributes().item(0).getTextContent();
		int aveLenFlower = Integer.parseInt(vp.getElementsByTagName("aveLenFlower").item(0).getTextContent());

		return new Flower.VisualParameters(stemColour, leafColour, aveLenFlowerMeasure, aveLenFlower);
	}

	private Flower.GrowingTips parseGrowingTips(Element gt) {
		String tempretureMeasure = gt.getElementsByTagName("tempreture").item(0).getAttributes().item(0).getTextContent();
		int tempreture = Integer.parseInt(gt.getElementsByTagName("tempreture").item(0).getTextContent());
		String lighting = gt.getElementsByTagName("lighting").item(0).getAttributes().item(0).getTextContent();
		String wateringMeasure = gt.getElementsByTagName("watering").item(0).getAttributes().item(0).getTextContent();
		int watering = Integer.parseInt(gt.getElementsByTagName("watering").item(0).getTextContent());

		return new Flower.GrowingTips(tempretureMeasure, tempreture, lighting, wateringMeasure, watering);
	}

}
