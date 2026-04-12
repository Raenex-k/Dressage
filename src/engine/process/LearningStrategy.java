package engine.process;
public interface LearningStrategy {
    double updatePourcentage(double current,
                             int deg,
                             double memoire,
                             double fatigue,
                             int compteurBonnes,
                             double scoreP,
                             double scoreD,
                             double scoreO);
}