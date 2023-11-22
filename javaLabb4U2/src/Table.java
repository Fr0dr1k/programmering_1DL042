import java.awt.*;
import java.util.ArrayList;

public class Table {

    final private Color BACKGROUND_COLOR;
    private final int TABLE_HEIGHT,
            TABLE_WIDTH,
            PEOPLE_RADIUS = 15;
    private ArrayList<Person> people = new ArrayList<>();

    public Table(int TABLE_HEIGHT, int TABLE_WIDTH, Color BACKGROUND_COLOR) {
        this.TABLE_HEIGHT = TABLE_HEIGHT;
        this.TABLE_WIDTH = TABLE_WIDTH;
        this.BACKGROUND_COLOR = BACKGROUND_COLOR;

        addPeople(15,0.2);
    }

    void addPeople(int numberOfPeople, double riskOfSick){
        for(int i=0;i<numberOfPeople;i++){

            Coord posNew = new Coord(Math.random()*TABLE_WIDTH/2,Math.random()*TABLE_HEIGHT);
            while (!acceptableSpawn(posNew)){
                posNew = new Coord(Math.random()*TABLE_WIDTH/2,Math.random()*TABLE_HEIGHT);
            }
            final int initialMaxSpeed = 3;
            Coord speedNew = new Coord(Math.random()*initialMaxSpeed,Math.random()*initialMaxSpeed);
            people.add(new Person(posNew,PEOPLE_RADIUS,Math.random()<riskOfSick,speedNew));

        }
    }

    boolean acceptableSpawn(Coord posNew){
        for(Person person:people){
            if(Coord.distance(posNew,person.pos)<2*PEOPLE_RADIUS){
                return false;
            }
            if(posNew.x>TABLE_HEIGHT-2*PEOPLE_RADIUS||posNew.y>TABLE_WIDTH/2-2*PEOPLE_RADIUS){
                return false;
            }
        }
        return true;
    }

    void update(){
        if(people.size() > 0) {
            for (Person ball : people) {
                ball.update(this);
            }
        }
    }

    void makePeopleSick(Coord mouse){
        for(Person person:people){
            if(Coord.distance(person.center(),mouse)<=PEOPLE_RADIUS){
                person.makeSick();
            }
        }
    }

    void draw(Graphics2D graphics2D){
        graphics2D.setColor(BACKGROUND_COLOR);
        graphics2D.fillRect(0,0,TABLE_WIDTH,TABLE_HEIGHT);

        for(Person ball : people) {
            ball.draw(graphics2D);
        }

        if(Main.simPaused){
            final int fontSize = 13,
                    labelX = 820,
                    labelY = 170;
            graphics2D.setFont(new Font("Arial",Font.BOLD,fontSize));
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawString("The number of dead are: "+numberOfDead(),labelX,labelY);
        }
    }

    public int getTABLE_HEIGHT() {
        return TABLE_HEIGHT;
    }

    public int getTABLE_WIDTH() {
        return TABLE_WIDTH;
    }

    int numberOfDead(){
        int dead = 0;
        for (Person person:people) {
            if(!person.isAlive){
                dead++;
            }
        }
        return dead;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

}
