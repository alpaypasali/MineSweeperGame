import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz !");
        System.out.println("Oluşturmak istediğiniz haritanın değerlerini giriniz");
        System.out.println("Satır sayısını giriniz :");
        int row = scanner.nextInt();
        System.out.println("Satır sayısını giriniz :");
        int column = scanner.nextInt();

        //oyunu oluşturmak için bir consructor oluşturur
        MineSweeper mineSweeper = new MineSweeper(row , column);
        //oyunu başlatır
        mineSweeper.run();
    }
}
