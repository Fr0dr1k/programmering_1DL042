public class Main {
    public static void main(String[] args) {
        final double INIT_SICK_PROB = 0.2, GET_WELL_PROB=0.5, DIE_PROB=0.1,INFECT_PROB=.2, RANGE = 100,
                VACCINATION_RATE = 0.5, VACCINATION_EFFICIENTS = 0.8;
        Village fistVillage = new Village(1000,true, INIT_SICK_PROB, GET_WELL_PROB,
                DIE_PROB,INFECT_PROB, RANGE, VACCINATION_RATE, VACCINATION_EFFICIENTS);
        System.out.println(fistVillage.countSick());
        int sickPeople = fistVillage.countSick();
        int daysPassed = 0;
        while (sickPeople>0){
            fistVillage.dayPassesAll();
            sickPeople = fistVillage.countSick();
            daysPassed++;
            System.out.println("Days passed: "+daysPassed);
            //System.out.println("Number of sick people are: "+sickPeople);
            System.out.println("Number of dead people: "+fistVillage.countDead());
        }
    }
}

class Person{
    boolean isSick, isDead = false, isVaccinated=false;
    final double INIT_SICK_PROB, GET_WELL_PROB, DIE_PROB,INFECT_PROB, RANGE, VACCINATION_RATE, VACCINATION_EFFICIENTS;
    int xPos, yPos, daysLeftImmune = 0;
    final int DAYS_IMMUNE = 3;

    public Person(int villageArea, boolean isVillageVaccinated, double INIT_SICK_PROB, double GET_WELL_PROB, double DIE_PROB,
                  double INFECT_PROB, double RANGE, double VACCINATION_RATE, double VACCINATION_EFFICIENTS) {
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
        this.RANGE = RANGE;
        this.VACCINATION_RATE = VACCINATION_RATE;
        this.VACCINATION_EFFICIENTS = VACCINATION_EFFICIENTS;

        isSick = Math.random() < INIT_SICK_PROB;
        xPos = (int)(Math.random()*villageArea);
        yPos = (int)(Math.random()*villageArea);

    }

    void dayPasses(Person[] population){
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
                    if(distance<RANGE){
                        infect(person);
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
    private Person[] population;
    boolean isVaccinated;

    public Village(int SIZE, boolean isVaccinated, double INIT_SICK_PROB, double GET_WELL_PROB, double DIE_PROB,
                   double INFECT_PROB, double RANGE, double VACCINATION_RATE, double VACCINATION_EFFICIENTS) {
        this.SIZE = SIZE;
        this.isVaccinated = isVaccinated;
        this.population = new Person[SIZE];
        for(int i = 0;i<this.population.length;i++){
            this.population[i] = new Person(VILLAGE_AREA,isVaccinated,INIT_SICK_PROB,GET_WELL_PROB,DIE_PROB,INFECT_PROB,
                    RANGE,VACCINATION_RATE,VACCINATION_EFFICIENTS);
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
}