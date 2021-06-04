package task.one;

public class TaskOne {
    public static void main(String[] atrs) {
        System.out.println(pow(3, 9));
    }

    public static int pow(int number, int stepen) {
        if (number == 0){
            return 1;
        }else if (number == 1){
            return stepen;
        }else {
            return stepen * pow(number - 1, stepen);
        }
    }
}
