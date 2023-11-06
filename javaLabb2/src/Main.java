public class Main {
    public static void main(String[] args) {
        /***final double INIT_SICK_PROB = 0.2, GET_WELL_PROB=0.2, DIE_PROB=0.002,INFECT_PROB=0.02,
                RANGE = 100, VACCINATION_RATE = 0.5, VACCINATION_EFFICIENTS = 0.8;
        final int DAYS_IMMUNE = 1000;
        Village firstVillage = new Village(1000,false, INIT_SICK_PROB, GET_WELL_PROB,
                DIE_PROB,INFECT_PROB,DAYS_IMMUNE, RANGE, VACCINATION_RATE, VACCINATION_EFFICIENTS,true,true);
        System.out.println("Whit vaccination");
        fistVillage.runSimulation();
        Village secondVillage = new Village(1000,false, INIT_SICK_PROB, GET_WELL_PROB,
                DIE_PROB,INFECT_PROB, RANGE, VACCINATION_RATE, VACCINATION_EFFICIENTS);
        System.out.println("Without vaccination");
        secondVillage.runSimulation();
        DisplayPandemic display = new DisplayPandemic(fistVillage);
        //int sickPeople = fistVillage.countSick();
        int daysPassed = 0;
        while (fistVillage.countSick()>0){
            fistVillage.dayPassesAll();
            display.show();
            //sickPeople = fistVillage.countSick();
            daysPassed++;
            //System.out.println("Days passed: "+daysPassed);
            //System.out.println("Number of sick people are: "+sickPeople);
            //System.out.println("Number of dead people: "+fistVillage.countDead());
        }
        System.out.println("Total days passed: "+daysPassed);
        System.out.println("Total deaths: "+fistVillage.countDead());
        System.out.println("Immune people at the end: "+fistVillage.countImmunePeople());*/
        Village sverige = new Village(1000,true,0,0.2,0.002,0.02,
                1000,113,0.8,0.8,true,true);
        Village norge = new Village(1000,false,0,0.2,0.002,0.02,
                1000,80,1,1,true,true);
        System.out.println("Sverige");
        sverige.runSimulation(true);
        System.out.println("Norge");
        norge.runSimulation(true);

    }
}


class Person{
    boolean isSick, isDead = false, isVaccinated=false, partyMode;
    final double INIT_SICK_PROB, GET_WELL_PROB, DIE_PROB,INFECT_PROB, RANGE, VACCINATION_RATE, VACCINATION_EFFICIENTS,
            DISTANCING = 0.8;
    int xPos, yPos, daysLeftImmune = 0, daysPassed = 0;
    final int DAYS_IMMUNE;

    public Person(int villageArea, boolean isVillageVaccinated, double INIT_SICK_PROB, double GET_WELL_PROB, double DIE_PROB,
                  double INFECT_PROB,int DAYS_IMMUNE, double RANGE, double VACCINATION_RATE, double VACCINATION_EFFICIENTS,
                  boolean covidMode, boolean partyMode) {
        if(isVillageVaccinated&&Math.random()<VACCINATION_RATE){
            this.isVaccinated = true;
            this.INFECT_PROB = INFECT_PROB-INFECT_PROB*VACCINATION_EFFICIENTS;
        }
        else{
            this.INFECT_PROB = INFECT_PROB;
        }
        this.INIT_SICK_PROB = INIT_SICK_PROB;
        this.GET_WELL_PROB = GET_WELL_PROB;
        this.DIE_PROB = DIE_PROB;
        this.DAYS_IMMUNE = DAYS_IMMUNE;
        if(Math.random()<DISTANCING) {
            this.RANGE = RANGE/2;
        }
        else{
            this.RANGE = RANGE;
        }
        this.VACCINATION_RATE = VACCINATION_RATE;
        this.VACCINATION_EFFICIENTS = VACCINATION_EFFICIENTS;
        this.partyMode = partyMode;

        xPos = (int)(Math.random()*villageArea);
        yPos = (int)(Math.random()*villageArea);
        if(covidMode){
            if(xPos>400&&xPos<600&&yPos>400&&yPos<600){
                isSick = true;
            }
        }
        else{
            isSick = Math.random() < INIT_SICK_PROB;
        }
    }

    void dayPasses(Person[] population){
        daysPassed++;
        if(daysLeftImmune!=0){
            daysLeftImmune--;
        }
        if(isSick) {
            if (Math.random() < GET_WELL_PROB) {
                isSick = false;
                daysLeftImmune=DAYS_IMMUNE;
                return;
            }
            else if(Math.random() < DIE_PROB){
                isDead = true;
                isSick = false;
                return;
            }

            for (Person person : population) {
                if(person!=this){
                    double distance = Math.sqrt(Math.pow(this.xPos-person.xPos,2) + Math.pow(this.yPos-person.yPos,2));
                    if(partyMode&&daysPassed%10==0){
                        if(distance<300){
                            infect(person);
                        }
                    }
                    else {
                        if (distance < RANGE) {
                            infect(person);
                        }
                    }
                }

            }
        }
    }

    void becomesInfected(){
        if(daysLeftImmune == 0) {
            if (!isDead && Math.random() < INFECT_PROB) {
                isSick = true;
            }
        }
    }

    void infect(Person victim){
        victim.becomesInfected();
    }

}

class Village{
    final int SIZE, VILLAGE_AREA = 1000;
    Person[] population;
    boolean isVaccinated;

    public Village(int SIZE, boolean isVaccinated, double INIT_SICK_PROB, double GET_WELL_PROB, double DIE_PROB,
                   double INFECT_PROB,int DAYS_IMMUNE, double RANGE, double VACCINATION_RATE, double VACCINATION_EFFICIENTS,
                   boolean covidMode, boolean partyMode) {
        this.SIZE = SIZE;
        this.isVaccinated = isVaccinated;
        this.population = new Person[SIZE];
        for(int i = 0;i<this.population.length;i++){
            this.population[i] = new Person(VILLAGE_AREA,isVaccinated,INIT_SICK_PROB,GET_WELL_PROB,DIE_PROB,INFECT_PROB,
                    DAYS_IMMUNE,RANGE,VACCINATION_RATE,VACCINATION_EFFICIENTS,covidMode,partyMode);
        }
    }

    int countSick(){
        int sickCount = 0;
        for(int i = 0;i < population.length;i++){
            if(population[i].isSick){
                sickCount++;
            }
        }
        return sickCount;
    }

    int countDead(){
        int deadPeople = 0;
        for (int i = 0; i < population.length; i++) {
            if(population[i].isDead){
                deadPeople++;
            }
        }
        return deadPeople;
    }

    void dayPassesAll(){
        for (int i = 0; i < population.length; i++) {
            population[i].dayPasses(population);
        }
    }

    void runSimulation(boolean graphicalDisplay){
        int daysPassed = 0;
        if(graphicalDisplay) {
            DisplayPandemic display = new DisplayPandemic(this);
            while (this.countSick()>0){
                this.dayPassesAll();
                daysPassed++;
                display.show();
            }
        }
        else{
            while (this.countSick()>0){
                this.dayPassesAll();
                daysPassed++;
            }
        }

        System.out.println("Total days passed: "+daysPassed);
        System.out.println("Total deaths: "+this.countDead());
        System.out.println("Immune people at the end: "+this.countImmunePeople());
        System.out.println("R0: "+1.0/(1.0-(double)(countImmunePeople())/SIZE));

    }

    int countImmunePeople(){
        int immuneCount = 0;
        for(Person person:population){
            if(person.daysLeftImmune>0) immuneCount++;
        }
        return immuneCount;
    }
}