

public class Individual implements Comparable<Individual> {

  private double[] genotype;
  private Double fitness = 0.0 ;

  public Individual(double[] genotype_values, double fitness_value){
      genotype = genotype_values;
      fitness = fitness_value;
  }

  public double[] get_genotype(){
    return genotype;
  }

  public double get_fitness(){
     return fitness;
  }

  public int compareTo(Individual other_individual){
      return this.fitness.compareTo(other_individual.get_fitness());
  }

}
