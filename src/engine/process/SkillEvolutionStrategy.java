package engine.process;

public interface SkillEvolutionStrategy {
    double[] compute(double scoreP, double scoreD, double scoreO,
                     int impactPr, int impactDs, int impactOb, int deg);
}