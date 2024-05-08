import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    private int row;
    private int column;
    private MineMap map;
    private MineBoard board;
    private boolean gameRunning = true;
    /*
     Oyunu başlatırken satır ve sütun sayılarını alarak bir
     Mayın Tarlası ve
     Mayın Tahtası oluşturur
     */
    MineSweeper(int row, int column) {
        // Satır ve sütun sayılarını kontrol eder, en az 3 olmalıdır
        int[] validValues = setRowAndColumn(row, column);
        this.row = validValues[0];
        this.column = validValues[1];
        this.map = new MineMap(this.row, this.column);// Mayın haritası oluşturulur
        this.board = new MineBoard(this.row, this.column);// Mayın tahtası oluşturulur
    }
    // Satır ve sütun sayılarını kontrol ederek geçerli değerler alınmasını sağlar
    private int[] setRowAndColumn(int row, int column) {
        Scanner scanner = new Scanner(System.in);
        while (row < 3 || column < 3) {
            System.out.println("Satır ve sütun değerleri en az 3 olmalıdır. Lütfen geçerli değerler girin:");
            System.out.print("Satır: ");
            row = scanner.nextInt();
            System.out.print("Sütun: ");
            column = scanner.nextInt();
        }
        return new int[]{row, column};
    }

    // Oyunu çalıştırır
    public void run() {
        System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz !");
        map.createMines(); // Mayın haritasını oluşturur
        board.createBoard(); // Mayın tahtasını oluşturur
        board.printBoard(); // Mayın tahtasını yazdırır
        while (isGameRunning()){
            takeGuess();// Tahmin alır
            System.out.println("=============================================");
            if (isGameRunning())  // Oyun devam ediyorsa sadece board'u tekrar yazdır
                board.printBoard();
        }
    }
    // Oyunun devam edip etmediğini kontrol eder
    public boolean isGameRunning() {
        return gameRunning;
    }
    // Oyuncunun tahminini alır ve sonucu işler
    private void takeGuess() {
        Scanner scanner = new Scanner(System.in);
        int guessRow, guessColumn;
    // Geçerli bir tahmin alana kadar döngüyü devam ettirir
        do {
            System.out.print("Tahmin satırını girin: ");
            guessRow = scanner.nextInt();
            System.out.print("Tahmin sütununu girin: ");
            guessColumn = scanner.nextInt();

            if (!isValidGuess(guessRow, guessColumn)) {
                System.out.println("Geçersiz tahmin. Lütfen satır ve sütun değerlerini kontrol edin.");
            } else if (board.isCellOpened(guessRow, guessColumn)) {
                System.out.println("Bu koordinat daha önce seçildi, başka bir koordinat girin");
            } else {
                break;
            }
        } while (true);
        // Tahminin sonucunu kontrol eder ve oyun durumunu günceller
        if (map.hasMine(guessRow, guessColumn)) {
            System.out.println("Game Over.");
            gameRunning = false;
            return;
        }
       int countMines = map.countAdjacentMines(guessRow , guessColumn);
        board.calculateCellValues(guessRow,guessColumn,countMines);
        if(board.isGameWon()){

           System.out.println("Oyunu Kazandınız !");
           gameRunning = false;
           board.printBoard();
           return;
        }
    }
    // Girilen tahminin geçerli olup olmadığını kontrol eder
    private boolean isValidGuess(int guessRow, int guessColumn) {
        return guessRow >= 0 && guessRow < row && guessColumn >= 0 && guessColumn < column;
    }
}




