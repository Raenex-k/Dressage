package engine.process;

public class DefaultLearningStrategy implements LearningStrategy {

    @Override
    public double updatePourcentage(double current, int deg,
                                     double memoire, double fatigue,
                                     int compteurBonnes,
                                     double scoreP, double scoreD, double scoreO) {

        double apprentissage = Math.log(1 + memoire);

        if(deg >= 0) apprentissage *= 2;
        else apprentissage *= -0.8;

        double effetFatigue = fatigue * 0.1;
        double delta = apprentissage - effetFatigue;

        double bonus = 0;
        if(compteurBonnes > 10)
            bonus = Math.sqrt(compteurBonnes) * 0.01;

        double passif = (scoreP + scoreD + scoreO)/600;

        return current + delta + bonus + passif;
    }
}