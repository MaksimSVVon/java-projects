package corejava.tasks.xmlparsing;

import corejava.tasks.xmlparsing.controller.*;

public class Main {
	
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			return;
		}
		
		String xmlFileName = args[0];
		System.out.println("Input ==> " + xmlFileName);
		Flowers flowers;

		////////////////////////////////////////////////////////
		// DOM
		////////////////////////////////////////////////////////
		
		// get
		DOMController domController = new DOMController(xmlFileName);

		// gain a container
		flowers = domController.parse();

		// sort (case 1) by name
		flowers.sortByFlowerName();

		// save
		String outputXmlFile = "output.dom.xml";
		FlowersWriter.writeToFile(outputXmlFile, flowers);
		System.err.println(XMLValidator.validateXML(outputXmlFile, "input.xsd"));

		////////////////////////////////////////////////////////
		// SAX
		////////////////////////////////////////////////////////
		
		// get
		SAXController saxController = new SAXController(xmlFileName);
		// gain a container
		flowers = saxController.parse();

		// sort  (case 2)
		flowers.sortByAverageLenOfFlower();

		// save
		outputXmlFile = "output.sax.xml";
		FlowersWriter.writeToFile(outputXmlFile, flowers);
		System.err.println(XMLValidator.validateXML(outputXmlFile, "input.xsd"));

		////////////////////////////////////////////////////////
		// StAX
		////////////////////////////////////////////////////////
		
		// get
		STAXController staxController = new STAXController(xmlFileName);

		// gain a container
		flowers = staxController.parse();

		// sort  (case 3)
		flowers.sortByVolumeOfWatering();

		// save
		outputXmlFile = "output.stax.xml";
		FlowersWriter.writeToFile(outputXmlFile, flowers);
		System.err.println(XMLValidator.validateXML(outputXmlFile, "input.xsd"));


		System.err.println(XMLValidator.validateXML("invalidXML.xml", "input.xsd"));
	}

}
