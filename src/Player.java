public class Player {
    private final String name;
    private final Character sign;

    public Player(String name) {
        this.name = name;
        this.sign = name.toUpperCase().charAt(0);
    }


}
