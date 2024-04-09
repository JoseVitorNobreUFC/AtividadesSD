public class Calculadora {
    public static int add(int ...values){
        int sum = 0;
        for(int i = 0; i < values.length; i++){
            sum += values[i];
        }
        return sum;
    }

    public static int sub(int ...values){
        int sub = values[0];
        for(int i = 1; i < values.length; i++){
            sub -= values[i];
        }
        return sub;
    }

    public static int mul(int ...values){
        int mul = 1;
        for(int i = 0; i < values.length; i++){
            mul *= values[i];
        }
        return mul;
    }

    public static int div(int ...values){
        int div = values[0];
        for(int i = 1; i < values.length; i++){
            if(values[i] == 0)
                return -1;
            div /= values[i];
        }
        return div;
    }
}
