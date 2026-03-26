package engine.skill;


public class Skill {

    private final SkillTypes name;
    private int accumuler;

    public Skill(SkillTypes name){
        this.name = name;
        this.accumuler = 0;
    }

    public void increase(int amount){
        accumuler = Math.min(100, accumuler + amount);
    }

    public void decrease(int amount){
        accumuler = Math.max(0, accumuler - amount);
    }

    public int getaccumuler(){ return accumuler; }
    public void setaccumuler(int v){ accumuler=v; }
    public SkillTypes getSkillType(){ return name; }
}