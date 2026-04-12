package engine.process;

public class DefaultSkillStrategy implements SkillEvolutionStrategy {

    @Override
    public double[] compute(double scoreP, double scoreD, double scoreO,
                            int impactPr, int impactDs, int impactOb, int deg) {

        double gainPr = impactPr * Math.log(1 + Math.abs(impactPr));
        double gainDs = impactDs * Math.log(1 + Math.abs(impactDs));
        double gainOb = impactOb * Math.log(1 + Math.abs(impactOb));

        double fatigue = Math.abs(deg) * 0.45;

        return new double[]{
            gainPr - fatigue,
            gainDs - fatigue,
            gainOb - fatigue
        };
    }
}