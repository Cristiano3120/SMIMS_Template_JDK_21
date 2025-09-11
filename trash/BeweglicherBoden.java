// TODO: Das muss hier weg und ins Game.
private void moveBackground(double offsetX) {

    ScalablePicture[] backgrounds = new ScalablePicture[4];// TODO: Das hier richtig machen.
    for (int j = 0; j < backgrounds.length; j++) { // Gehe durch alle Bodenelemente durch ...
        backgrounds[j].move(-offsetX, 0); // ... und bewege das jeweils aktuelle Element nach links.
        if (backgrounds[j].getShapeX() + backgrounds[j].getShapeWidth() < 0) { // Sind wir links über den Rand hinaus?
            System.out.println("moving " + j);
            int prevIndex = j <= 0 ? backgrounds.length - 1 : j - 1; // bestimme den Index des vorherigen Bodenelements
            backgrounds[j].moveTo(backgrounds[prevIndex].getShapeX() + backgrounds[prevIndex].getShapeWidth(), backgrounds[j].getShapeY()); // "teleportiere" das ELement hinter seinen Vorgänger, sprich: ganz nach rechts
        }
    }
}