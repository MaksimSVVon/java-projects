package corejava.tasks.factories;

class PlotFactories {

    public PlotFactory classicDisneyPlotFactory(Character hero, Character beloved, Character villain) {
        return () -> () -> String.format("%s is young and adorable. " +
                "%s and %s love each other. " +
                "%s interferes with their " +
                "happiness, but loses in the end.",
                hero.name(), hero.name(), beloved.name(), villain.name());
    }

    public PlotFactory contemporaryDisneyPlotFactory(Character hero, EpicCrisis epicCrisis, Character funnyFriend) {
        return () -> () -> String.format("In the beginning, %s feels" +
                " a bit awkward and uncomfortable. " +
                "But personal issue fades, when a " +
                "big trouble comes - %s. " +
                "%s stands up against it, but with " +
                "no success at first. But then, by putting " +
                "self together and with the help of friends, " +
                "including spectacular, funny %s," +
                " %s restores the spirit, overcomes " +
                "the crisis and gains gratitude and respect.",
                hero.name(), epicCrisis.name(), hero.name(), funnyFriend.name(), hero.name());
    }

    public PlotFactory marvelPlotFactory(Character[] heroes, EpicCrisis epicCrisis, Character villain) {
        return () -> () -> {
            StringBuilder s = new StringBuilder();
            s.append("%s").append(" threatens the world. But ");
            for (int i = 0; i < heroes.length; ++i) {
                if (i != heroes.length - 1) {
                    s.append("the brave ").append(heroes[i].name());
                } else {
                    if (heroes.length != 1)
                        s.append(" and the brave ").append(heroes[i].name());
                    else
                        s.append("the brave ").append(heroes[i].name());
                }
                if (i < heroes.length - 2)
                    s.append(", ");
            }
            if (heroes.length == 1)
                s.append(" is ");
            else
                s.append(" are ");
            s.append("on guard. So, no way that intrigues of %s will bend the willpower of");
            if (heroes.length != 1)
                s.append(" inflexible heroes.");
            else
                s.append(" the inflexible hero.");
            return String.format(s.toString(), epicCrisis.name(), villain.name());
        };
    }
}
