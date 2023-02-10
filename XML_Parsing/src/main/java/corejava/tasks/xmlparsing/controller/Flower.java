package corejava.tasks.xmlparsing.controller;

public class Flower {
    private final DefaultProperties defaultProperties;
    private final VisualParameters visualParameters;
    private final GrowingTips growingTips;

    public Flower(DefaultProperties defaultProperties,
                  VisualParameters visualParameters,
                  GrowingTips growingTips) {
        this.defaultProperties = defaultProperties;
        this.visualParameters = visualParameters;
        this.growingTips = growingTips;
    }

    public DefaultProperties getDefaultProperties() {
        return defaultProperties;
    }

    public VisualParameters getVisualParameters() {
        return visualParameters;
    }

    public GrowingTips getGrowingTips() {
        return growingTips;
    }

    @Override
    public String toString() {
        return defaultProperties.toString() + "\n   "
                + visualParameters.toString() + "\n   "
                + growingTips.toString();
    }

    public static class DefaultProperties {
        public String name;
        public String soil;
        public String origin;
        public String multiplying;

        public DefaultProperties(String name, String soil,
                                 String origin, String multiplying) {
            this.name = name;
            this.soil = soil;
            this.origin = origin;
            this.multiplying = multiplying;
        }

        @Override
        public String toString() {
            return "DefaultProperties{" +
                    "name='" + name + '\'' +
                    ", soil='" + soil + '\'' +
                    ", origin='" + origin + '\'' +
                    ", multiplying='" + multiplying + '\'' +
                    '}';
        }
    }

    public static class VisualParameters {
        public String stemColour;
        public String leafColour;
        public String aveLenFlowerMeasure;
        public int aveLenFlower;

        public VisualParameters(String stemColour, String leafColour,
                                String aveLenFlowerMeasure, int aveLenFlower) {
            this.stemColour = stemColour;
            this.leafColour = leafColour;
            this.aveLenFlowerMeasure = aveLenFlowerMeasure;
            this.aveLenFlower = aveLenFlower;
        }

        @Override
        public String toString() {
            return "VisualParameters{" +
                    "stemColour='" + stemColour + '\'' +
                    ", leafColour='" + leafColour + '\'' +
                    ", aveLenFlowerMeasure='" + aveLenFlowerMeasure + '\'' +
                    ", aveLenFlower=" + aveLenFlower +
                    '}';
        }
    }

    public static class GrowingTips {
        public String tempretureMeasure;
        public int tempreture;
        public String lightRequiring;
        public String wateringMeasure;
        public int watering;

        public GrowingTips(String tempretureMeasure,
                           int tempreture, String lightRequiring,
                           String wateringMeasure, int watering) {
            this.tempretureMeasure = tempretureMeasure;
            this.tempreture = tempreture;
            this.lightRequiring = lightRequiring;
            this.wateringMeasure = wateringMeasure;
            this.watering = watering;
        }

        @Override
        public String toString() {
            return "GrowingTips{" +
                    "tempretureMeasure='" + tempretureMeasure + '\'' +
                    ", tempreture=" + tempreture +
                    ", lightRequiring='" + lightRequiring + '\'' +
                    ", wateringMeasure='" + wateringMeasure + '\'' +
                    ", watering=" + watering +
                    '}';
        }
    }

}
