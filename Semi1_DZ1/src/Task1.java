import java.util.Arrays;
import java.util.List;

public class Task1 {
    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(3, 4, 1, 5, 2, 10, 99);

        double r = (double) list.stream().filter(el -> el % 2 == 0).reduce(Integer::sum).get()
                / list.stream().filter(el -> el % 2 == 0).count();

        System.out.println(r);


    }
}
