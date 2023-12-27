import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
public final class Main {
    private Main() {

    }
    private static File file = new File("input.txt");
    private static FileReader fr;

    static {
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static final BufferedReader READER = new BufferedReader(fr);
    private static int days;
    private static String li;
    public static void main(String[] args) throws IOException, GrassOutOfBoundsException {
        li = READER.readLine();
        String[] el = li.split(" ");
        try {
            if (el.length > 1  || li.equals(" ")) {
                throw new InvalidInputsException();
            }
        } catch (InvalidInputsException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        try {
            days = Integer.parseInt(li);
        } catch (NumberFormatException e) {
            InvalidInputsException invalidInputsException = new InvalidInputsException();
            System.out.println(invalidInputsException.getMessage());
            System.exit(0);
        }
        final int dmin = 1;
        final int dmax = 30;
        try {
            if (days > dmax || days < dmin) {
                throw new InvalidInputsException();
            }
        } catch (InvalidInputsException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        String line = READER.readLine();
        String[] el1 = line.split(" ");
        try {
            if (el1.length > 1  || line.equals(" ")) {
                throw new InvalidInputsException();
            }
        } catch (InvalidInputsException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        float grass = 0;
        try {
            grass = Float.parseFloat(line);
        } catch (NumberFormatException e) {
            InvalidInputsException invalidInputsException = new InvalidInputsException();
            System.out.println(invalidInputsException.getMessage());
            System.exit(0);
        }
        final int grassmax = 100;
        try {
            if (grass > grassmax || grass < 0) {
                throw new GrassOutOfBoundsException();
            }
        } catch (GrassOutOfBoundsException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        List<Animal> listAnimal = readAnimals();
        runSimulation(days, grass, listAnimal);
        printAnimals(listAnimal);
    }

    private static List<Animal> readAnimals() throws IOException {
        List<Animal> animalList = new ArrayList<>();
        String l = READER.readLine();
        String[] e1 = l.split(" ");
        try {
            if (e1.length > 1 || l.equals(" ")) {
                throw new InvalidInputsException();
            }
        } catch (InvalidInputsException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        int n = 0;
        try {
            n = Integer.parseInt(l);
        } catch (NumberFormatException e) {
            InvalidInputsException invalidInputsException = new InvalidInputsException();
            System.out.println(invalidInputsException.getMessage());
            System.exit(0);
        }
        final int minn = 20;
        try {
            if (n > minn || n < 1) {
                throw new InvalidInputsException();
            }
        } catch (InvalidInputsException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Animal animal;
        String line = null;
        float weight;
        float speed;
        float energy;
        for (int i = 0; i < n; i++) {
            try {
                line = READER.readLine();
            } catch (IOException e) {
                InvalidInputsException invalidInputsException = new InvalidInputsException();
                System.out.println(invalidInputsException.getMessage());
                System.exit(0);
            }
            String[] elements = null;
            try {
                elements = line.split(" ");
            } catch (NullPointerException e) {
                InvalidInputsException invalidInputsException = new InvalidInputsException();
                System.out.println(invalidInputsException.getMessage());
                System.exit(0);
            }
            final int leng = 4;
            try {
                if (elements.length > leng || line.equals(" ")) {
                    throw new InvalidNumberOfAnimalParametersException();
                }
            } catch (InvalidNumberOfAnimalParametersException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            try {
                if (!(elements[0].equals("Lion") || elements[0].equals("Boar") || elements[0].equals("Zebra"))) {
                    throw new InvalidInputsException();
                }
            } catch (InvalidInputsException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            final int el3 = 3;
            weight = Float.parseFloat(elements[1]);
            speed = Float.parseFloat(elements[2]);
            energy = Float.parseFloat(elements[el3]);
            final int minw = 5;
            final int maxw = 200;
            try {
                if (weight < minw || weight > maxw) {
                    throw new WeightOutOfBoundsException();
                }
            } catch (WeightOutOfBoundsException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            final int minspeed = 5;
            final int maxspeed = 60;
            try {
                if (speed < minspeed || speed > maxspeed) {
                    throw new SpeedOutOfBoundsException();
                }
            } catch (SpeedOutOfBoundsException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            final int maxenergy = 100;
            try {
                if (energy < 0 || energy > maxenergy) {
                    throw new EnergyOutOfBoundsException();
                }
            } catch (EnergyOutOfBoundsException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            switch (elements[0]) {
                case "Lion":
                    animal = new Lion(weight, speed, energy);
                    animalList.add(animal);
                    break;
                case "Zebra":
                    animal = new Zebra(weight, speed, energy);
                    animalList.add(animal);
                    break;
                case "Boar":
                    animal = new Boar(weight, speed, energy);
                    animalList.add(animal);
                    break;
                default:
                    break;
            }
        }
        return animalList;
    }
    private static void runSimulation(int d, float grass, List<Animal> animalList) {
        Field field = new Field(grass);
        removeDeadAnimals(animalList);
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < animalList.size(); j++) {
                animalList.get(j).eat(animalList, animalList.get(j), field);
                removeDeadAnimals(animalList);
            }
            for (Animal animal : animalList) {
                animal.decrementEnergy();
            }
            field.glassGrow();
            removeDeadAnimals(animalList);
        }
    }
    private static void removeDeadAnimals(List<Animal> animalList) {
        int count = 0;
        for (int i = 0; i < animalList.size(); i++) {
            if (animalList.get(i).getEnergy() <= 0.0) {
                animalList.remove((int) i);
                i--;
            }
        }
    }
    private static void printAnimals(List<Animal> animals) {
        for (Animal animal: animals) {
            animal.makeSound();
        }
    }
}

abstract class Animal {
    private float weight;
    private float speed;
    private float energy;

    protected Animal(float w, float s, float e) {
        this.weight = w;
        this.speed = s;
        this.energy = e;
    }
    public float getWeight() {
        return this.weight;
    }
    public float getSpeed() {
        return this.speed;
    }
    public float getEnergy() {
        return this.energy;
    }
    public void setWeight(float w) {
        this.weight = w;
    }
    public void setSpeed(float s) {
        this.speed = s;
    }
    abstract void makeSound();
    public void setEnergy(float e) {
        final float hun = 100.0F;
        if (e > hun) {
            this.energy = hun;
        } else {
            this.energy = e;
        }
    }
    public void decrementEnergy() {
        setEnergy(this.energy - 1);
    }
    abstract void eat(List<Animal> animalList, Animal animal, Field field);
}
enum AnimalSounds {
    ZEBRA("Ihoho"),
    LION("Roar"),
    BOAR("Oink");
    private final String sound;

    AnimalSounds(String s) {
        this.sound = s;
    }

    public String getSound() {
        return sound;
    }
}
class Zebra extends Animal implements Herbivore {
    Zebra(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }

    public void eat(List<Animal> animalList, Animal animal,  Field field) {
        grazeInField(animal, field);
    }
    @Override
    public void makeSound() {
        System.out.println(AnimalSounds.ZEBRA.getSound());
    }
    @Override
    public void grazeInField(Animal animal, Field field) {
        final int ten = 10;
        if (animal.getEnergy() > 0.0) {
            if (field.getGrassAmount() > 0) {
                if (field.getGrassAmount() > animal.getWeight() / ten) {
                    animal.setEnergy(animal.getEnergy() + animal.getWeight() / ten);
                    field.setGrassAmount(field.getGrassAmount() - animal.getWeight() / ten);
                }
            }
        }
    }
}
class Boar extends Animal implements Omnivore {
    Boar(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }
    public void eat(List<Animal> animalList, Animal animal, Field field) {
        grazeInField(animal, field);
        huntPrey(animal, choosePrey(animalList, animal));
    }
    @Override
    public void makeSound() {
        System.out.println(AnimalSounds.BOAR.getSound());
    }
    @Override
    public void grazeInField(Animal animal, Field field) {
        final int hun = 10;
        if (animal.getEnergy() > 0.0) {
            if (field.getGrassAmount() > animal.getWeight() / hun) {
                animal.setEnergy(animal.getEnergy() + animal.getWeight() / hun);
                field.setGrassAmount(field.getGrassAmount() - animal.getWeight() / hun);
            }
        }
    }
    @Override
    public Animal choosePrey(List<Animal> animalList, Animal animal) {
        int index = animalList.indexOf(animal);
        Animal animal1 = animalList.get((index + 1) % animalList.size());
        return animal1;
    }
    @Override
    public void huntPrey(Animal animal1, Animal animal2) {
        if (animal1.getEnergy() > 0.0) {
            if (animal1 == animal2) {
                SelfHuntingException selfHuntingException = new SelfHuntingException();
                System.out.println(selfHuntingException.getMessage());
            } else if (animal1.getClass().getSimpleName().equals(animal2.getClass().getSimpleName())) {
                CannibalismException cannibalismException = new CannibalismException();
                System.out.println(cannibalismException.getMessage());
            } else if (animal1.getEnergy() > animal2.getEnergy() || animal1.getSpeed() > animal2.getSpeed()) {
                animal2.setEnergy(0F);
                animal1.setEnergy(animal1.getEnergy() + animal2.getWeight());
            } else {
                TooStrongPreyException tooStrongPreyException = new TooStrongPreyException();
                System.out.println(tooStrongPreyException.getMessage());
            }
        }
    }
}
class Lion extends Animal implements Carnivore  {
    Lion(float weight, float speed, float energy) {
        super(weight, speed, energy);
    }
    public void eat(List<Animal> animalList, Animal animal, Field field) {
        huntPrey(animal, choosePrey(animalList, animal));
    }

    @Override
    public Animal choosePrey(List<Animal> animalList, Animal animal) {
        int index = animalList.indexOf(animal);
        Animal animal1 = animalList.get((index + 1) % animalList.size());
        return animal1;
    }
    @Override
    public void huntPrey(Animal animal1, Animal animal2) {
        if (animal1.getEnergy() > 0.0) {
            if (animal1 == animal2) {
                SelfHuntingException selfHuntingException = new SelfHuntingException();
                System.out.println(selfHuntingException.getMessage());
            } else if (animal1.getClass().getSimpleName().equals(animal2.getClass().getSimpleName())) {
                CannibalismException cannibalismException = new CannibalismException();
                System.out.println(cannibalismException.getMessage());
            } else if (animal1.getEnergy() > animal2.getEnergy() || animal1.getSpeed() > animal2.getSpeed()) {
                animal2.setEnergy(0F);
                animal1.setEnergy(animal1.getEnergy() + animal2.getWeight());
            } else {
                TooStrongPreyException tooStrongPreyException = new TooStrongPreyException();
                System.out.println(tooStrongPreyException.getMessage());
            }
        }
    }
    @Override
    public void makeSound() {
        System.out.println(AnimalSounds.LION.getSound());
    }
}
interface Herbivore {
    void grazeInField(Animal animal, Field field);
}
interface Carnivore {
    Animal choosePrey(List<Animal> animalList, Animal animal);
    void huntPrey(Animal animal1, Animal animal2);
}

interface Omnivore extends Carnivore, Herbivore {
}



class Field {
    private float grassAmount;

    Field(float grass) {
        this.grassAmount = grass;
    }
    public float getGrassAmount() {
        return this.grassAmount;
    }
    public void glassGrow() {
        this.grassAmount = getGrassAmount() * 2;
        final int hun = 100;
        if (this.grassAmount > hun) {
            this.grassAmount = hun;
        }
    }
    public void setGrassAmount(float grass) {
        this.grassAmount = grass;
    }
}
class SelfHuntingException {
    SelfHuntingException() {

    }
    public String getMessage() {
        return "Self-hunting is not allowed";
    }
}
class TooStrongPreyException {
    TooStrongPreyException() {

    }
    public String getMessage() {
        return "The prey is too strong or too fast to attack";
    }
}

class CannibalismException {
    CannibalismException() {

    }
    public String getMessage() {
        return "Cannibalism is not allowed";
    }
}

class GrassOutOfBoundsException extends Throwable {
    GrassOutOfBoundsException() {

    }

    public String getMessage() {
        return "The grass is out of bounds";
    }
}

class InvalidInputsException extends Throwable {
    InvalidInputsException() {

    }

    public String getMessage() {
        return "Invalid inputs";
    }
}

class InvalidNumberOfAnimalParametersException extends Throwable {
    InvalidNumberOfAnimalParametersException() {

    }

    public String getMessage() {
        return "Invalid number of animal parameters";
    }
}

class WeightOutOfBoundsException extends Throwable {
    WeightOutOfBoundsException() {

    }

    public String getMessage() {
        return "The weight is out of bounds";
    }
}

class EnergyOutOfBoundsException extends Throwable {
    EnergyOutOfBoundsException() {

    }

    public String getMessage() {
        return "The energy is out of bounds";
    }
}

class SpeedOutOfBoundsException extends Throwable {
    SpeedOutOfBoundsException() {

    }
    public String getMessage() {
        return "The speed is out of bounds";
    }
}
