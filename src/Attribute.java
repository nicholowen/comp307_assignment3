public class Attribute {

  //initialised as 1 to avoid zero occurrences resulting in a probability of 0
  public int isSpamPresent = 1;
  public int isSpamAbsent  = 1;
  public int notSpamAbsent  = 1;
  public int notSpamPresent = 1;

  private double pr_IsSpamPresent;
  private double pr_NotSpamPresent;
  private double pr_isSpamAbsent;
  private double pr_NotSpamAbsent;

  public Attribute(){}

  /**
   * The class label == 1, SPAM
   * Counts the presence of this attribute - 1 = Present, 0 = Absent.
   */
  public void addTrue(int attr){
    if(attr == 1) isSpamPresent++;
    else isSpamAbsent++;
  }

  /**
   * The class label == 0, NOT SPAM
   * Counts the presence of this attribute - 1 = Present, 0 = Absent.
   */
  public void addFalse(int attr){
    if(attr == 1) notSpamPresent++;
    else notSpamAbsent++;
  }

  /**
   * Evaluates all the counts into their respective probabilities.
   */
  public void evaluate(){

    int isSpamTotal = isSpamPresent + isSpamAbsent;
    pr_IsSpamPresent = (double)isSpamPresent/isSpamTotal;
    pr_isSpamAbsent = (double)isSpamAbsent/isSpamTotal;

    int notSpamTotal = notSpamPresent + notSpamAbsent;
    pr_NotSpamPresent = (double)notSpamPresent/notSpamTotal;
    pr_NotSpamAbsent = (double)notSpamAbsent/notSpamTotal;

  }

  public double getIsSpamPresentPr() { return pr_IsSpamPresent; }

  public double getIsSpamAbsentPr() { return pr_isSpamAbsent; }

  public double getNotSpamPresentPr() { return pr_NotSpamPresent; }

  public double getNotSpamAbsentPr() { return pr_NotSpamAbsent; }

}
