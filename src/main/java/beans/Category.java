package beans;

public enum Category {
    FOOD,
    ELECTRICITY,
    RESTAURANT,
    VACATION;

    public int getTableValue(){
        return this.ordinal()+1;
    }
    public static Category categoryFromTable(int id){
        return Category.values()[id-1];
    }
}
