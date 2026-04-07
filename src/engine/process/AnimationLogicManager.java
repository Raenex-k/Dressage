package engine.process;

import java.util.ArrayList;
import java.util.Random;

import config.AppConfiguration;
import engine.map.Cell;
import engine.map.Room;
import engine.map.RoomType;
import engine.mobile.AnimalType;
import engine.strategy.PunitionRecompenseType;

public class AnimationLogicManager {

    private Random random = new Random();
    private Room room;
    private AnimalType type;
    
    private String nameimg;
    private PunitionRecompenseType punirecomp;

    private static int pr = 0;
    private static int ds = 0;
    private static int ob = 0;
    private int degp =0;

    public AnimationLogicManager(Room room, AnimalType type){
        this.room = room;
        this.type = type;
    }

    // ---------------- RECHERCHE LA CELLULE D'UN  OBJET ----------------

    public Cell chercherCellObj(String obj,
                                int startRow, int endRow,
                                int startCol, int endCol){

        ArrayList<Cell> cellules = new ArrayList<>();

        for(int i=startRow;i<endRow;i++){
            for(int j=startCol;j<endCol;j++){

                Cell cell = room.getCells()[i][j];

                if(cell.getObject()!=null &&
                   cell.getObject().getName().equalsIgnoreCase(obj)){

                    cellules.add(cell);
                }
            }
        }

        if(!cellules.isEmpty())
            return cellules.get(random.nextInt(cellules.size()));

        return null;
    }
    
    //------------ CHERCHER UNE CELLULE VIDE ---------------

    public Cell chercherCellVide(int startRow, int endRow,
                                int startCol, int endCol){

        ArrayList<Cell> cellules = new ArrayList<>();

        for(int i=startRow;i<endRow;i++){
            for(int j=startCol;j<endCol;j++){

                Cell cell = room.getCells()[i][j];

                if(cell.getObject() == null){
                    cellules.add(cell);
                }
            }
        }

        if(!cellules.isEmpty())
            return cellules.get(random.nextInt(cellules.size()));

        return null;
    }

    public Cell chercherCellSansObjet(int startRow,int endRow,int startCol,int endCol){

        ArrayList<Cell> cellules = new ArrayList<>();

        for(int i=startRow;i<endRow;i++){
            for(int j=startCol;j<endCol;j++){

                Cell cell = room.getCells()[i][j];

                if(cell.getObject()==null){
                    cellules.add(cell);
                }
            }
        }

        if(!cellules.isEmpty())
            return cellules.get(random.nextInt(cellules.size()));

        return null;
    }

    // ---------------- MÉTHODE PRINCIPALE ----------------

    	public Cell CellCibleAnimation(RoomType piece, boolean actionBonne){

            resetPrDsOb();
            degp = 0;

            Cell c = null;

            switch(piece){

                case SALON:
                    c = animationSalon(actionBonne);
                    break;

                case CUISINE:
                    c = animationCuisine(actionBonne);
                    break;

                case CHAMBRE:
                    c = animationChambre(actionBonne);
                    break;

                case JARDIN:
                    c = animationJardin(actionBonne);
                    break;
            }

            //  Calcul unique du degré
            setDegreRecompensePunition(punirecomp);

            return c;
        
    }

    // ---------------- SALON ----------------

    private Cell animationSalon(boolean actionBonne){

        int sr=0;
        int er=AppConfiguration.LINE_COUNT/2;
        int sc=0;
        int ec=AppConfiguration.COLUMN_COUNT/2;

        Cell c = null;
        setPunitionRecompense(null);
        if(actionBonne){


            int choix = random.nextInt(4);

            switch(choix){

                case 0:
                    c = chercherCellObj("tapis",sr,er,sc,ec);
                    nameimg="tapisenbas";
                    pr = 2; ds = 3; ob = 3;
                    if(c !=null) {setPunitionRecompense(PunitionRecompenseType.CARESSER);}
                    break;

                case 1:
                	if(type == AnimalType.CHAT) {
                    c = chercherCellObj("balle",sr,er,sc,ec);
                    nameimg="ballejouer";}
                	else {
                		c = chercherCellObj("cercle",sr,er,sc,ec);
                        nameimg="cerclenhaut";
                	}
                	if(c !=null) {setPunitionRecompense(PunitionRecompenseType.ENCOURAGER);}
                    pr = 2; ds = 4; ob = 3;
                    break;

                case 2:
                    c = chercherCellObj("canape",sr,er,sc,ec);
                    nameimg="canape";
                    pr = 2; ds = 3; ob = 3;
                    break;
            }

        }else{

            int choix = random.nextInt(3);

            switch(choix){

                case 0:
                    c = chercherCellObj("canape",sr,er,sc,ec);
                    nameimg="canapegriffe";
                    pr = -2; ds = -2; ob = -2;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.REPRIMANDE);
                   
                    }
                    break;

                case 1:
                    c = chercherCellObj("tablebasse",sr,er,sc,ec);
                    if(type == AnimalType.CHAT) {
                    nameimg="tablebasseanim";}
                    else {
                    	nameimg="tablebassecasser";
                    }
                    pr = -1; ds = -2; ob = -1;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.DIRE_STOP);
                    
                    }
                    break;

                case 2:
                    c = chercherCellObj("vasefragile",sr,er,sc,ec);
                    nameimg="vasefragilecasse";
                    pr = -1; ds = -1; ob = -3;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.FRAPPER);
                    }
                    break;
                case 3:
                	if(type == AnimalType.CHAT) {
                		c = chercherCellObj("tapis",sr,er,sc,ec);
                        nameimg="tapisgriffer";
                        pr = -1; ds = -2; ob = -2;
                        if (c!=null) {setPunitionRecompense(PunitionRecompenseType.FRAPPER);}
                	}
                	else {
                	c=chercherCellVide(sr,er,sc,ec);
                    nameimg="besoins";
                    pr = -3; ds = -2; ob = -1;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.FRAPPER);
                    }
                	}
                   break;
            }
        }

        return c;
    }

    // ---------------- CUISINE ----------------

    private Cell animationCuisine(boolean actionBonne){

        int sr=AppConfiguration.LINE_COUNT/2;
        int er=AppConfiguration.LINE_COUNT;
        int sc=AppConfiguration.COLUMN_COUNT/2;
        int ec=AppConfiguration.COLUMN_COUNT;
        setPunitionRecompense(null);
        Cell c = null;

        if(actionBonne){

            setPunitionRecompense(null);

            int choix = random.nextInt(3);

            switch(choix){

                case 0:
                	if (type == AnimalType.CHAT) {
                    c = chercherCellObj("gamelle",sr,er,sc,ec);
                    nameimg="gamelleenbas";}
                	else {c = chercherCellObj("os",sr,er,sc,ec);
                    nameimg="osenhaut";}
                	if(c !=null) {setPunitionRecompense(PunitionRecompenseType.CARESSER);}
                    pr = 3; ds = 3; ob = 3;
                    break;

                case 1:
                    c = chercherCellObj("placard",sr,er,sc,ec);
                    nameimg="placard";
                	
                	if (c != null) {setPunitionRecompense(PunitionRecompenseType.ENCOURAGER);}
                    pr = 1; ds = 2; ob = 2;
                    break;

                case 2:
                    c = chercherCellObj("refrigerateur",sr,er,sc,ec);
                    nameimg="refrigerateur";
                    pr = 1; ds = 2; ob = 3;
                    break;
            }

        }else{

            int choix = random.nextInt(3);

            switch(choix){

                case 0:
          
                    c = chercherCellObj("placard",sr,er,sc,ec);
                    nameimg="placardouvert";                	
                    pr = -1; ds = -3; ob = -2;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.REPRIMANDE);
                    }
                    break;

                case 1:
                    c = chercherCellObj("tablemanger",sr,er,sc,ec);
                    nameimg="tablemangerenbas";
                    pr = -1; ds = -2; ob = -1;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.DIRE_STOP);
                   }
                    break;

                case 2:
                    c = chercherCellObj("poubelle",sr,er,sc,ec);
                    nameimg="poubellefouiller";
                    pr = -2; ds = -3; ob = -2;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.REPRIMANDE);
                    }
                    break;
            }
        }
      
        return c;
    }

    // ---------------- CHAMBRE ----------------

    private Cell animationChambre(boolean actionBonne){

        int sr=0;
        int er=AppConfiguration.LINE_COUNT/2;
        int sc=AppConfiguration.COLUMN_COUNT/2;
        int ec=AppConfiguration.COLUMN_COUNT;
        setPunitionRecompense(null);
        Cell c=null;

        if(actionBonne){

            setPunitionRecompense(null);

            int choix=random.nextInt(2);

            switch(choix){

                case 0:
                    c=chercherCellObj("panier",sr,er,sc,ec);
                    nameimg="panierenbas";
                    pr = 2; ds = 3; ob = 3;
                    setPunitionRecompense(PunitionRecompenseType.CARESSER);
                    break;

                case 1:
                	if (type == AnimalType.CHAT) {
                    c=chercherCellObj("armoire",sr,er,sc,ec);
                    nameimg="armoire";
                	}else {
                		c=chercherCellObj("chaussure",sr,er,sc,ec);
                        nameimg="chaussure";
                	}
                    pr = 2; ds = 2; ob = 3;
                    break;
            }

        }else{

            int choix=random.nextInt(4);

            switch(choix){

                case 0:
                    c=chercherCellObj("lit",sr,er,sc,ec);
                    nameimg="litenbas";
                    pr = 0; ds = -1; ob = -1;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.DIRE_STOP);
                    }
                    break;

                case 1:
                	if (type == AnimalType.CHAT) {
                    c=chercherCellObj("armoire",sr,er,sc,ec);
                    nameimg="armoireouverte";
                	
                    pr = -1; ds = -2; ob = -2;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.REPRIMANDE);
                    }
                	}else {
                		c=chercherCellObj("chaussures",sr,er,sc,ec);
                        nameimg="chaussuresabime";
                        pr = -2; ds = -2; ob = -2;
                        if (c!=null) {setPunitionRecompense(PunitionRecompenseType.REPRIMANDE);
                        }
                	}
                	break;
                	
                case 2:
                	c=chercherCellObj("lampe",sr,er,sc,ec);
                    nameimg="lampecasser";
                    pr = -1; ds = -2; ob = -2;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.REPRIMANDE);
                    }
                    break;

            }
        }

        return c;
    }

    // ---------------- JARDIN ----------------

    private Cell animationJardin(boolean actionBonne){

        int sr=AppConfiguration.LINE_COUNT/2;
        int er=AppConfiguration.LINE_COUNT;
        int sc=0;
        int ec=AppConfiguration.COLUMN_COUNT/2;

        Cell c=null;
        setPunitionRecompense(null);
        if(actionBonne){

            setPunitionRecompense(null);

            int choix = random.nextInt(2);
            
            switch(choix){

                case 0:
                    c=chercherCellObj("arbre",sr,er,sc,ec);
                    nameimg="arbre";
                    pr = 2; ds = 2; ob = 2;
                    break;

                case 1:
                    c=chercherCellObj("jouetExterieur",sr,er,sc,ec);
                    nameimg="jouetavec";
                    pr = 3; ds = 4; ob = 4;
                    setPunitionRecompense(PunitionRecompenseType.CARESSER);
                    break;

                case 2:
                	if(type == AnimalType.CHAT) {
                    c=chercherCellObj("litiere",sr,er,sc,ec);
                    nameimg="litiereavecbesoin";}
                	else {
                		c=chercherCellVide(sr,er,sc,ec);
                		nameimg="besoincreuser";
                	}
                    pr = 3; ds = 4; ob = 3;
                    break;
            }

        }else{

            int choix=random.nextInt(3);

            switch(choix){

                case 0:
                	if(type == AnimalType.CHAT) { 
                	c=chercherCellSansObjet(sr,er,sc,ec);
                    nameimg="besoins";
                    pr = -3; ds = -3; ob = -3;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.FRAPPER);
                    }
                    }else {               	
                    c=chercherCellObj("barriere",sr,er,sc,ec);
                    nameimg="barrierecasser";
                    pr = -1; ds = -2; ob = -2;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.REPRIMANDE);
                   }
                    }
                    break;

                case 1:
                	if(type == AnimalType.CHAT) {
                    c=chercherCellObj("arbre",sr,er,sc,ec);
                    nameimg="arbregrinpper";}
                	else {
                		c= chercherCellObj("plante",sr,er,sc,ec);
                        nameimg="planterenverse";
                	}
                    pr = -1; ds = -2; ob = -2;
                    if (c!=null) {setPunitionRecompense(PunitionRecompenseType.DIRE_STOP);
                    }
                    break;

            }
        }			
        return c;
    }
    
    //-----------------  Degre de punition / Recompense  -------------------
    private void setDegreRecompensePunition(PunitionRecompenseType type){

        if(type == null){
            degp = 0;
            return;
        }

        switch(type){

            case ENCOURAGER:
                degp = 1;
                break;

            case REPRIMANDE:
                degp = -1;
                break;

            case DIRE_STOP:
                degp = -2;
                break;

            case FRAPPER:
                degp = -3;
                break;

            case CARESSER:
                degp = 2; 
                break;

            default:
                degp = 0;
                break;
        }
    }

    public String getImgName(){ return nameimg; }

    public PunitionRecompenseType getPunitionRecompense(){ return punirecomp; }

    public void setPunitionRecompense(PunitionRecompenseType p){ punirecomp = p; }

    public int getPr() {return pr;}
    public int getDs() {return ds;}
    public int getOb() {return ob;}

    public void resetPrDsOb() {
        pr = 0;
        ds = 0;
        ob = 0;
    }

	public int getDegPunition() {
		return degp;
	}
}