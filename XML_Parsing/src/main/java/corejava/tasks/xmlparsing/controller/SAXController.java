package corejava.tasks.xmlparsing.controller;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Controller for SAX parser.
 */
public class SAXController extends DefaultHandler {
	
	private String xmlFileName;

	public SAXController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public Flowers parse() {
		try {
			Flowers flowers = new Flowers();

			DefaultHandler handler = new DefaultHandler() {
				StringBuilder s = new StringBuilder();
				// DefaultProperties
				String name;
				String soil;
				String origin;
				String multiplying;
				// VisualParameters
				String stemColour;
				String leafColour;
				String aveLenFlowerMeasure;
				int aveLenFlower;
				// GrowingTips
				String tempretureMeasure;
				int tempreture;
				String lightRequiring;
				String wateringMeasure;
				int watering;

				@Override
				public void characters(char[] ch, int start, int length) throws SAXException {
					s.append(ch, start, length);
				}

				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
					for (int i = 0; i < attributes.getLength(); ++i) {
						String attr = attributes.getQName(i);
						String attrVal = attributes.getValue(i);

						if (attr.equals("measure")) {
							switch (qName) {
								case "aveLenFlower":
									aveLenFlowerMeasure = attrVal;
									break;
								case "tempreture":
									tempretureMeasure = attrVal;
									break;
								case "watering":
									wateringMeasure = attrVal;
							}
						} else {
							lightRequiring = attrVal;
						}
					}
				}

				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {
					String elVal = s.toString().trim();
					switch (qName) {
						case "name":
							name = elVal;
							break;
						case "soil":
							soil = elVal;
							break;
						case "origin":
							origin = elVal;
							break;
						case "multiplying":
							multiplying = elVal;
							break;
						case "stemColour":
							stemColour = elVal;
							break;
						case "leafColour":
							leafColour = elVal;
							break;
						case "aveLenFlower":
							aveLenFlower = Integer.parseInt(elVal);
							break;
						case "tempreture":
							tempreture = Integer.parseInt(elVal);
							break;
						case "watering":
							watering = Integer.parseInt(elVal);
							break;
					}
					if (qName.equals("flower")) {
						Flower.DefaultProperties dp =
								new Flower.DefaultProperties(name, soil, origin, multiplying);
						Flower.VisualParameters vp =
						        new Flower.VisualParameters(stemColour, leafColour, aveLenFlowerMeasure, aveLenFlower);
						Flower.GrowingTips gp =
								new Flower.GrowingTips(tempretureMeasure, tempreture, lightRequiring, wateringMeasure, watering);
						flowers.add(new Flower(dp, vp, gp));
					}
					s.setLength(0);
				}
			};

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();

			parser.parse(new File(xmlFileName), handler);

			return flowers;
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new RuntimeException(e);
		}
	}
}