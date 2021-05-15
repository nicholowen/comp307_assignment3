
import java.util.ArrayList;

public class Main {

  // total number of spam and not spam instances (classes)
  private static int spamTotal = 0;
  private static int notSpamTotal = 0;

  // probability of the the classes occurring after training
  static double trainedSpamProbability;
  static double trainedNotSpamProbability;

  // training and test lists containing an
  static ArrayList<int[]> trainingInstances;
  static ArrayList<int[]> testInstances;

  // list of attributes, containing
  static ArrayList<Attribute> attributes = new ArrayList<>();

  public Main(){

  }

  public static void init(){

    Util u = new Util();
    // reads both files into memory - an arraylist of integer arrays.

    trainingInstances = u.readFile("spamLabelled.dat");
    testInstances =  u.readFile("spamUnlabelled.dat");

    //set the number of attributes to 1 less of an instance length
    int num_attributes = trainingInstances.get(0).length - 1;

    //creates an attribute object for each attribute
    for(int i = 0; i < num_attributes; i++){
      attributes.add(new Attribute());
    }

    for (int[] instance : trainingInstances) {

      boolean isSpamLabel = false; // class label - 1 == true, 0 == false

      //checks to see what the class for this instance is. Totals up both classes
      if (instance[instance.length - 1] == 1) {
        isSpamLabel = true;
        spamTotal++;
      } else {
        notSpamTotal++;
      }

      //for each attribute, increment the count of this attributes presence depending on the instance label
      for (int i = 0; i < instance.length - 1; i++) {
        if (isSpamLabel) {
          attributes.get(i).addTrue(instance[i]);
        } else {
          attributes.get(i).addFalse(instance[i]);
        }
      }
    }
    System.out.println("Number of training instances: " + trainingInstances.size());
    System.out.println("Class Label == 1: " + spamTotal);
    System.out.println("Class Label == 0: " + notSpamTotal);

  }

  public static void training(){

    trainedSpamProbability = spamTotal/(double)(trainingInstances.size());
    trainedNotSpamProbability = notSpamTotal/(double)(trainingInstances.size());

    System.out.println("\n\nTRAINING...");
    System.out.println("------------------------------------------------------\n");
    System.out.printf("Attribute Probabilities: \nClass 1 Total Probability = %f\nClass 0 Total Probability = %f\n" +
          "Attr# = 1 and Attr# = 0\n", trainedSpamProbability, trainedNotSpamProbability);
    System.out.println( "|-------------|------------|------------|------------|");
    System.out.println( "|  ATTRIBUTE  |   CLASS    |     1      |      0     |");
    System.out.println( "|-------------|------------|------------|------------|");

    for(int i = 0; i < attributes.size(); i ++){
      Attribute a = attributes.get(i);
      a.evaluate();
      if(i < 9) System.out.print("|  ");
      else System.out.print("| ");
      System.out.printf("    " +(i+1) + "      |    SPAM    |  %f  |  %f  |\n", a.getIsSpamPresentPr(), a.getIsSpamAbsentPr());
      System.out.printf("|             |  NOT SPAM  |  %f  |  %f  |\n", a.getNotSpamPresentPr(), a.getNotSpamAbsentPr());
      System.out.println( "|-------------|------------|------------|------------|");
    }



  }


  public static void test(double spamPr, double notSpamPr){

    System.out.println("\n\nTESTING...");
    System.out.println("------------------------------------------------------\n");
    System.out.println("Probability and Class prediction on Unlabelled Data:\n");

    //for each instance...
    for(int i = 0; i < testInstances.size(); i++) {
      int[] instance = testInstances.get(i);
      double totalInstanceProbSpam = 1;
      double totalInstanceProbNotSpam = 1;

      //for each attribute...
      for(int j = 0; j < instance.length; j++){
        if(instance[j] == 1){
          // P(Attr# = 1 | C = 1)
          totalInstanceProbSpam *= attributes.get(j).getIsSpamPresentPr();
          // P(Attr# = 1 | C = 0)
          totalInstanceProbNotSpam *= attributes.get(j).getNotSpamPresentPr();
        }else {
          // P(Attr# = 0 | C = 1)
          totalInstanceProbSpam *= attributes.get(j).getIsSpamAbsentPr();
          // P(Attr# = 0 | C = 0)
          totalInstanceProbNotSpam *= attributes.get(j).getNotSpamAbsentPr();
        }
      }

      // multiply by the probability of the respective class
      totalInstanceProbSpam *= spamPr;        // P(C = 1)
      totalInstanceProbNotSpam *= notSpamPr;  // P(C = 0)


      System.out.println("Instance " + (i+1) + ":");

        System.out.printf("\tP (C = 1 | F) = %10.9f\n", totalInstanceProbSpam);
        System.out.printf("\tP (C = 0 | F) = %10.9f\n", totalInstanceProbNotSpam);
      if(totalInstanceProbSpam > totalInstanceProbNotSpam){
        System.out.println("\tPredicted Class: SPAM");
      }else{
        System.out.println("\tPredicted Class: NOT SPAM");

      }
    }
  }

  public static void main(String[] args){

    System.out.println("\nInitialising data...\n");
    init();
    training();
    test(trainedSpamProbability, trainedNotSpamProbability);

    System.out.println("\n\n**FINISHED**");
  }

}
