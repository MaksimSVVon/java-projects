package corejava.tasks.xmlparsing.controller;

import org.xml.sax.helpers.DefaultHandler;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Controller for StAX parser.
 */
public class STAXController extends DefaultHandler {

	private String xmlFileName;

	public STAXController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public Flowers parse() {
		try (FileInputStream input = new FileInputStream(xmlFileName)) {
			XMLInputFactory factory = XMLInputFactory.newFactory();
			XMLStreamReader reader = factory.createXMLStreamReader(input);
			Flowers flowers = new Flowers();

			StringBuilder s = new StringBuilder();
			// DefaultProperties
			String name = null;
			String soil = null;
			String origin = null;
			String multiplying = null;
			// VisualParameters
			String stemColour = null;
			String leafColour = null;
			String aveLenFlowerMeasure = null;
			Integer aveLenFlower = null;
			// GrowingTips
			String tempretureMeasure = null;
			Integer tempreture = null;
			String lightRequiring = null;
			String wateringMeasure = null;
			Integer watering = null;

			int eventType = reader.getEventType();

			while (reader.hasNext()) {
				eventType = reader.next();

				if (eventType == XMLEvent.CHARACTERS) {
					s.append(reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength());
				}

				if (eventType == XMLEvent.START_ELEMENT) {
					for (int i = 0; i < reader.getAttributeCount(); ++i) {
						String attr = reader.getAttributeLocalName(i);
						String attrVal = reader.getAttributeValue(i);

						if (attr.equals("measure")) {
							switch (reader.getName().getLocalPart()) {
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

				if (eventType == XMLEvent.END_ELEMENT) {
					String elVal = s.toString().trim();
					switch (reader.getName().getLocalPart()) {
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
					if (reader.getName().getLocalPart().equals("flower")) {
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
			}
			return flowers;
		} catch (IOException | XMLStreamException e) {
			throw new RuntimeException(e);
		}
	}
}