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
        while (row < 2 || column < 2) {
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
        System.out.println("Haritanız Oluşturuldu!");
        map.createMines(); // Mayın haritasını oluşturur
        map.printMap();
        System.out.println("Oyun Tahtanız Oluşturuldu!");
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
            guessRow = getIntegerInput(scanner, "Tahmin satırını girin: ");
            guessColumn = getIntegerInput(scanner, "Tahmin sütununu girin: ");

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
    //İnt değer giriş kontrolü , string değer girilirse int değer alana kadar döngü devam eder
    private int getIntegerInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Geçersiz giriş. Lütfen bir tam sayı girin.");
                scanner.next(); // Bufferı temizle
            }
        }
    }

    // Girilen tahminin geçerli olup olmadığını kontrol eder
    private boolean isValidGuess(int guessRow, int guessColumn) {
        return guessRow >= 0 && guessRow < row && guessColumn >= 0 && guessColumn < column;
    }
}

class MineMap {
    private int[][] map;
    private  int size;
    private  int row;
    private  int column;


    // Belirtilen satır ve sütun sayılarına göre bir Mayın Haritası oluşturur
    MineMap(int row, int column) {
        this.row = row;
        this.column = column;
        this.map = new int[row][column];
        this.size = row * column;
    }
    // Mayınları rastgele yerleştirir
    public void createMines() {
        Random random = new Random();
        int randRow, randCol;
        int minesToPlace = size / 4; // Mayınların yerleştirileceği hücre sayısı

        // Belirlenen sayıda mayını rastgele yerleştirme döngüsü
        for (int i = 0; i < minesToPlace; i++) {
            randRow = random.nextInt(row); // Rastgele satır seçimi
            randCol = random.nextInt(column); // Rastgele sütun seçimi

            // Eğer seçilen hücre zaten bir mayın içermiyorsa, o hücreyi bir mayın olarak işaretle
            if (map[randRow][randCol] != -1) {
                map[randRow][randCol] = -1;
            } else {
                // Eğer seçilen hücre zaten bir mayın içeriyorsa, bu adımı tekrarlamak için döngü değişkenini azalt
                i--;
            }
        }
    }
    // Mayın haritasını yazdırır
    public void printMap(){
        for(int[] u : map){
            for(int elem : u){
                System.out.print(elem +" ");
            }
            System.out.println(" ");
        }
    }
    // Belirtilen hücrede bir mayın olup olmadığını kontrol eder
    public boolean hasMine(int row, int column) {
        // Verilen hücrede bir mayın varsa true döndür, aksi halde false döndür
        return map[row][column] == -1;
    }
    // Belirtilen hücrenin komşularındaki mayın sayısını sayar
    public int countAdjacentMines(int row, int column) {
        int count = 0;
        // Komşuları tarayarak mayınları say
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                // Geçerli hücre sınırlar içinde mi kontrol et
                if (isValidCell(i, j)) {
                    // Mayın varsa sayacı artır
                    if (map[i][j] == -1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    // Belirtilen hücrenin geçerli bir hücre olup olmadığını kontrol eder
    private boolean isValidCell(int row, int column) {
        return row >= 0 && row < this.row && column >= 0 && column < this.column;
    }
}

class MineBoard {
    private String[][] board;
    private  int row;
    private  int column;
    // Belirtilen satır ve sütun sayılarına göre bir Mayın Tahtası oluşturur
    MineBoard(int row, int column) {
        this.row = row;
        this.column = column;
        this.board = new String[row][column];
    }
    // Mayın tahtasını '-' karakterleri ile doldurur
    public void createBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                board[i][j] = "-";
            }
        }
    }
    // Mayın tahtasını konsola yazdırır
    public void printBoard(){
        for(String[] u : board){
            for(String elem : u){
                System.out.print(elem +" ");
            }
            System.out.println(" ");
        }
    }
    // Belirtilen hücrenin açılıp açılmadığını kontrol eder
    public boolean isCellOpened(int row, int column) {
        return !board[row][column].equals("-");
    }
    // Belirtilen hücreye komşu olan mayınların sayısını tahtada gösterir
    public void calculateCellValues(int row , int column , int value){
        board[row][column] = String.valueOf(value);
    }
    // Oyunun kazanılıp kazanılmadığını kontrol eder
    public boolean isGameWon() {
        int totalCells = row * column;
        int closedCells = 0;
        // Tüm hücrelerin durumunu kontrol et
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                // Eğer hücre açılmamışsa,kapalı hücre sayısını arttır
                if (!isCellOpened(i, j)) {
                    closedCells++;
                }
            }
        }
        // Eğer açılmayan hücre sayısı, toplam hücre sayısının 1/4'üne eşitse, oyun kazanılmıştır
        return closedCells == totalCells / 4;
    }

}




