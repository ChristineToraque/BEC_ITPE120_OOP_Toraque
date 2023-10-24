class Cat extends Animal {
    String breed;

    public cat(String name, String breed) {
        super(name); 
        this.breed = breed;
    }
    public void speak() {
        System.out.println(name + " (a " + breed + " cat) meow.");
    }

    public void fetch() {
        System.out.println(name + " fetches a toy.");
    }
}
