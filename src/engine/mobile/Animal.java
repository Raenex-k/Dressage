package engine.mobile;

import engine.map.Cell;
import engine.skill.Skill;
import engine.skill.SkillTypes;

public abstract class Animal {

    private String name;
    private AnimalType type;
    private Cell position;

    private Skill proprete;
    private Skill discipline;
    private Skill obeissance;
    
    
    public Animal(String name,AnimalType type , Cell position){
        this.name = name;
        this.position = position;
        this.type = type;
        this.proprete = new Skill(SkillTypes.Proprete);
        this.discipline = new Skill(SkillTypes.Discipline);
        this.obeissance = new Skill(SkillTypes.Discipline);
    }

    public void moveTo(Cell cell){
    	if(cell != null) {
        this.position = cell;
    	}
    }

    public AnimalType getType() {return type;}

    public Skill getProprete(){ return proprete; }
    public Skill getDiscipline(){ return discipline; }
    public Skill getObeissance(){ return obeissance; }

	public Cell getPosition() {
		return position;
	}
	
	public void setPosition(Cell c) {
		position = c;
	}
	
	public void resetSkills() {
	    this.proprete.setaccumuler(0);
	    this.discipline.setaccumuler(0);
	    this.obeissance.setaccumuler(0);
	}
	
}