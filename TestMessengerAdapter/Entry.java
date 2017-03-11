import java.util.regex.Pattern;

/**
 * Created by Trympyrym on 10.03.2017.
 */
public class Entry
{
    private Pattern pattern;
    private String answer;
    private int weight;

    public Entry() {
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getAnswer() {
        return answer;
    }

    public int getWeight() {
        return weight;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "pattern=" + pattern +
                ", answer='" + answer + '\'' +
                ", weight=" + weight +
                '}';
    }
}
