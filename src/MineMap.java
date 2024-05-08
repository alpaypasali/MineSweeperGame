import java.util.Random;

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